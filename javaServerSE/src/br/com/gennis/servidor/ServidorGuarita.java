/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.servidor;


import br.com.gennis.controle.PontoAcesso;
import br.com.gennis.dados.Conexoes;
import br.com.gennis.dados.ControleRF;
import br.com.gennis.dados.DBCadastro;
import br.com.gennis.dados.DBControleRF;
import br.com.gennis.db.DBPontoAcesso;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LEANDRO
 */
public class ServidorGuarita implements Runnable {

    private Socket socket;
    private ControleRF dispositivos = new ControleRF();
    private DBCadastro dbDispositivos = new DBCadastro();
    private DBPontoAcesso dbPontoAcesso = new DBPontoAcesso();

    @Override
    public void run() {
        try {
            executa();
        } catch (IOException ex) {

        }
    }

    private int porta;
    private List<PrintStream> clientes = new ArrayList<>();

    private List<PontoAcesso> listaPonto;
    private List<Conexoes> listaConexoes;

    public ServidorGuarita(int porta) {
        this.porta = porta;
    }

    public ServidorGuarita(int porta, List<PontoAcesso> listaPonto) {

        this.porta = porta;
        this.listaPonto = listaPonto;
        this.listaConexoes = new ArrayList<Conexoes>();

        System.out.println("Server");

    }

    public String getMacIP(String mac) {
        return mac;
    }

    public void executa() throws IOException {

        try {
            ServerSocket servidor = new ServerSocket(this.porta);

            while (true) {
                // aceita um cliente
                System.out.println("Server guarita");
                socket = servidor.accept();

                // adiciona saida do cliente à lista
                InetAddress inetAddress = socket.getInetAddress();

                if (dbPontoAcesso.isDispositivoAtivo(inetAddress.getHostAddress())) {
                    System.out.println("Aceito conexão");
                } else {
                    System.out.println("Delete conexão");
                    socket.close();
                    continue;
                }

                PrintStream ps = new PrintStream(socket.getOutputStream());
                this.clientes.add(ps);

                System.out.println("IP " + inetAddress.getHostAddress());

                System.out.println("**************************");

                Conexoes conexoes = new Conexoes();
                conexoes.setTipo("Cliente");
                conexoes.setIdConexao(String.valueOf(clientes.indexOf(ps)));
                conexoes.setEnderecoIP(String.valueOf(socket.getInetAddress()));
                conexoes.setPortaTCP(String.valueOf(socket.getPort()));
                conexoes.setPrintStream(ps);
                conexoes.setSocket(socket);

                listaConexoes.add(conexoes);

                // TelaInstanceModulo.getTelaServidor().atualizarTabela(listaConexoes);
                // cria tratador de cliente numa nova thread
                TrataGuarita tc
                        = new TrataGuarita(socket.getInputStream(), this, listaPonto);
                new Thread(tc).start();

            }

        } catch (Exception erro) {
            System.out.println("Erro linear");
            erro.printStackTrace();
            System.out.println(erro.getMessage());
        }
    }

    public void distribuiMensagems(String msg) {

        Conexoes clienteSocket = null;
        boolean error = false;
        for (Conexoes cx : listaConexoes) {

            PrintStream cliente = cx.getPrintStream();
            Socket socket = cx.getSocket();

            if (cliente.checkError()) {

                cliente.close();
                try {
                    socket.close();
                } catch (Exception ex) {
                    System.out.println("Erro ao remover socket!");
                }

                clienteSocket = cx;
                error = true;
                break;

            }

            if (error) {

                listaConexoes.remove(clienteSocket);

                // TelaInstanceModulo.getTelaServidor().atualizarTabela(listaConexoes);
                error = false;
            }

        }

    }

    public void importarDispositivos() {

        int check = 0;
        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            byte[] imp = {(byte) 0x00, (byte) 0x46, (byte) 0x00};

            for (byte xr : imp) {
                check += (0xff & xr);
            }
            imp[2] = (byte) check;

            dout.write(imp);

        } catch (Exception ex) {

        }
    }

    public void resetGuarita() {

        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            byte[] ok = {(byte) 0x00, (byte) 0x2b, (byte) 0x2b};

            dout.write(ok);

        } catch (Exception ex) {

        }
    }

    public void confirmacao() {

        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            byte[] ok = {(byte) 0x00};

            dout.write(ok);

        } catch (Exception ex) {

        }
    }
    //Aciona saida do modulo guarita

    public void acionarSaida() {

        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            int check = 0;

            byte[] cmd = {(byte) 0x00, (byte) 0x0d, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00};

            for (byte xr : cmd) {
                check += (0xff & xr);
            }

            cmd[6] = (byte) check;

            dout.write(cmd);

        } catch (Exception ex) {

        }
    }

    public void reenviar() {

        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            byte[] ok = {(byte) 0xFF};

            dout.write(ok);

        } catch (Exception ex) {

        }
    }

    public void atualizarReceptor() {

        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            String reset[] = {"00", "1d", ""};
            byte[] ok = {(byte) 0x00, (byte) 0x1d, (byte) 0x1d};

            dout.write(ok);

        } catch (Exception ex) {

        }
    }

    private DBControleRF dbControleRF = new DBControleRF();

    public void transferirDados(List<ControleRF> listaDispositivos) {

        for (int i = 0; i < listaDispositivos.size(); i++) {

            dbControleRF.insertControleRF(listaDispositivos.get(i));

        }

    }

    private char[] label(String label) {

        char[] c = new char[20];

        for (int i = 0; i < 20; i++) {
            c[i] = 20;
        }

        for (int i = 0; i < label.length(); i++) {

            c[i] = label.charAt(i);

            if (i == 19) {
                break;
            }

        }

        return c;

    }

    private char[] placa(String placa) {

        char[] c = new char[20];

        if (placa.length() > 6) {
            for (int i = 0; i < placa.length(); i++) {

                c[i] = placa.charAt(i);
            }
        }

        return c;

    }

    public void atualizaTelaGuarita(String enderecoIP) {

        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            char[] c = label(enderecoIP);
            int check1 = 0;

            byte[] l1 = {(byte) 0x00, (byte) 0x01, 'S', 'R', 'V',
                (byte) c[0], (byte) c[1], (byte) c[2], (byte) c[3], (byte) c[4], (byte) c[5], (byte) c[6], (byte) c[7],
                (byte) c[8], (byte) c[9], (byte) c[10], (byte) c[11], (byte) c[12], (byte) c[13], (byte) c[14], (byte) c[15], (byte) c[16]};

            for (byte xr : l1) {
                check1 += (0xff & xr);
            }

            byte[] l1ok = {(byte) 0x00, (byte) 0x01, 'S', 'R', 'V',
                (byte) c[0], (byte) c[1], (byte) c[2], (byte) c[3], (byte) c[4], (byte) c[5], (byte) c[6], (byte) c[7],
                (byte) c[8], (byte) c[9], (byte) c[10], (byte) c[11], (byte) c[12], (byte) c[13], (byte) c[14], (byte) c[15], (byte) c[16], (byte) check1};

            dout.write(l1ok);
            char espaco = 20;

            int check2 = 0;

            byte[] l2 = {(byte) 0x00, (byte) 0x02, 'G', 'E', 'N', 'N', 'I', 'S', (byte) espaco,
                'T', 'E', 'C', 'N', 'O', 'L', 'O', 'G', 'I', 'A', (byte) espaco, (byte) espaco, (byte) espaco};

            for (byte xr : l2) {
                check2 += (0xff & xr);
            }

            byte[] l2ok = {(byte) 0x00, (byte) 0x02,
                'G', 'E', 'N', 'N', 'I', 'S', (byte) espaco,
                'T', 'E', 'C', 'N', 'O', 'L', 'O', 'G', 'I', 'A', (byte) espaco, (byte) espaco, (byte) espaco, (byte) check2};

            dout.write(l2ok);

        } catch (Exception ex) {

        }

    }

    private static boolean falha = false;

    public static byte[] hexStringToByteArray(String s) {

        falha = false;
        byte[] b = new byte[s.length() / 2];
        try {

            for (int i = 0; i < b.length; i++) {
                int index = i * 2;
                int v = Integer.parseInt(s.substring(0, 2), 16);

                b[0] = (byte) v;
            }
        } catch (Exception e) {
            falha = true;
            TelaInstance.getServidor().distribuiMensagem("###501");
        }

        return b;
    }

    public void cadastrarControle(String dados) {

        String[] cmd = dados.split(";");

        byte[] c1 = hexStringToByteArray(cmd[1].substring(1, 3));
        byte ser1 = c1[0];
        byte[] c2 = hexStringToByteArray(cmd[1].substring(3, 5));
        byte ser2 = c2[0];
        byte[] c3 = hexStringToByteArray(cmd[1].substring(5, 7));
        byte ser3 = c3[0];

        byte marca = Byte.parseByte(cmd[5], 16);
        byte cor = Byte.parseByte(cmd[6], 16);

        System.out.println("Marca " + marca);
        System.out.println("Cor " + cor);

        char[] c = label(cmd[4]);
        char[] placa = placa(cmd[7]);

        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            //String y = String.valueOf(unidade_h);
            //String x = String.format("%X", unidade_l);
            String unidade = cmd[2];

            int u1 = 0;
            int u2 = 0;

            if (unidade.length() < 3) {
                unidade = "0" + unidade;

            }

            u1 = Integer.parseInt(unidade.substring(1, 3));

            String x = String.format("%X", u1);
            String y = String.format("%X", u2);

            byte unh = Byte.parseByte(y);
            byte unl = Byte.parseByte(x, 16);

            //Criar unidade_H
            String s0 = "1" + cmd[1].substring(0, 1);
            String ConverteS0 = String.format(s0, 16);
            byte ser0 = (byte) Byte.parseByte(ConverteS0, 16);

            byte[] arControle = {0x00, (byte) 0x43, (byte) 0x00, (byte) ser0, (byte) ser1, (byte) ser2, (byte) ser3, 0x00,
                0x01, (byte) unh, (byte) unl, 0x00, 0x00, 0x01, (byte) c[0], (byte) c[1], (byte) c[2], (byte) c[3], (byte) c[4],
                (byte) c[5], (byte) c[6], (byte) c[7], (byte) c[8], (byte) c[9], (byte) c[10], (byte) c[11], (byte) c[12],
                (byte) c[13], (byte) c[14], (byte) c[15], (byte) c[16], (byte) c[17], 0x10, (byte) marca, (byte) cor,
                (byte) placa[0], (byte) placa[1], (byte) placa[2], (byte) placa[3], (byte) placa[4],
                (byte) placa[5], (byte) placa[6], (byte) 0x00};

            int checkSum = 0;

            for (byte xr : arControle) {
                checkSum += (0xff & xr);
            }

            arControle[42] = (byte) checkSum;

            dout.write(arControle);
            if (falha == false) {
                TelaInstance.getServidor().distribuiMensagem("###500");
            } else {
                TelaInstance.getServidor().distribuiMensagem("###501");

            }

        } catch (Exception ex) {
            TelaInstance.getServidor().distribuiMensagem("###501");
        }

    }

    public void apagarDispositivo(String dados) {

        String[] cmd = dados.split(";");

        byte[] c1 = hexStringToByteArray(cmd[1].substring(1, 3));
        byte ser1 = c1[0];
        byte[] c2 = hexStringToByteArray(cmd[1].substring(3, 5));
        byte ser2 = c2[0];
        byte[] c3 = hexStringToByteArray(cmd[1].substring(5, 7));
        byte ser3 = c3[0];

        String s0 = "1" + cmd[1].substring(0, 1);
        String ConverteS0 = String.format(s0, 16);
        byte ser0 = (byte) Byte.parseByte(ConverteS0, 16);

        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            byte[] arControle = {0x00, (byte) 0x43, (byte) 0x04, (byte) ser0, (byte) ser1, (byte) ser2, (byte) ser3, 0x00,
                0x00, 0x00, 0x0b, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00};

            int checkSum = 0;

            for (byte xr : arControle) {
                checkSum += (0xff & xr);
            }

            arControle[arControle.length - 1] = (byte) checkSum;

            dout.write(arControle);
            dbControleRF.removeDispositivo(cmd[1]);

            atualizarReceptor();

        } catch (Exception ex) {
            System.out.println("Erro ao apagar");
            //TelaInstance.getServidor().distribuiMensagem("###501");
        }

    }

}
