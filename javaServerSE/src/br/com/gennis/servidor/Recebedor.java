/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.servidor;

import br.com.gennis.controle.Controle;
import br.com.gennis.controle.ControleGrupo;
import br.com.gennis.controle.PontoAcesso;
import br.com.gennis.dados.Acesso;
import br.com.gennis.dados.Cadastros;
import br.com.gennis.dados.DBAcessos;
import br.com.gennis.dados.DBCadastro;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author MONITORAMENTO
 */
public class Recebedor implements Runnable {

    private InputStream cliente;
    public Socket socketLinear;
    private Cadastros cadastros;
    private DBCadastro dBCadastro = new DBCadastro();
    private Controle controle;
    private ControleGrupo controleGrupo;

    private DBAcessos dbAcessos = new DBAcessos();
    private Acesso acesso = new Acesso();
    private PontoAcesso pontoAcesso;
    private String tipoEvento = null;
    private String unidade;
    private String torre;
    private boolean bateria = true;
    private String serial;
    public static boolean importaDados = false;

    public Recebedor(InputStream cliente, PontoAcesso pontoAcesso, Socket socket, Controle controle, ControleGrupo controleGrupo) {
        this.cliente = cliente;
        this.pontoAcesso = pontoAcesso;
        this.socketLinear = socket;
        this.controle = controle;
        this.controleGrupo = controleGrupo;

    }

    public static int convert(int n) {
        return Integer.parseInt(String.valueOf(n), 10);
    }

    public void run() {

        String panico = "0";
        String localizador = "";

        LinkedList list = new LinkedList();

        byte[] bufResp = new byte[80];
        byte[] tipo = new byte[1];
        byte[] id = new byte[4];

        Scanner s = new Scanner(this.cliente);

        int cont = 0;

        // recebe msgs do servidor e imprime na tela
        DataInputStream dat = new DataInputStream(cliente);
        while (true) {
            if (pontoAcesso.isConectado()) {

                //System.out.println(s.nextLine());
                //System.out.println(x);
                // String x =  s.nextLine();
                try {

                    //String x =  bf.toString();
                    dat.read(bufResp);

                    // bufResp = x.getBytes();
                    // bf.read(bufResp);
                    //servidor.read(bufResp);
                    String dados = javax.xml.bind.DatatypeConverter.printHexBinary(bufResp);
                    System.out.println(dados);

                    id[0] = bufResp[3];
                    id[1] = bufResp[4];
                    id[2] = bufResp[5];
                    id[3] = bufResp[6];

                    tipo[0] = bufResp[3];

                    serial = javax.xml.bind.DatatypeConverter.printHexBinary(id);

                    if (dados.substring(0, 5).contains("002A")) {

                        serial = "###313" + serial;

                        TelaInstance.getServidor().distribuiMensagem(serial);

                    }

                    if (!serial.equals("60000000")) {

                        if (serial.charAt(0) == '0') {

                            panico = "0";
                            cadastros = new Cadastros();

                            serial = serial.substring(1, serial.length());
                            cadastros = dBCadastro.selectCadastroID(serial);

                            acesso.setNome(cadastros.getNome());
                            acesso.setUnidade(cadastros.getUnidade());
                            acesso.setDocumento(cadastros.getDocumento());
                            acesso.setAcesso("Garagem");
                            acesso.setSituacao("Entrada");

                            localizador = String.valueOf(dbAcessos.inserirEntrada(acesso));

                            if (pontoAcesso.isFluxo() || pontoAcesso.isGrupoFluxo()) {

                                boolean registra = controle.entrada(serial, pontoAcesso.getTempoFluxo());
                                boolean registraGupo = controleGrupo.entrada(serial, pontoAcesso.getTempoFluxo());
                                if (registra || pontoAcesso.isGrupoFluxo()) {

                                    if (pontoAcesso.isGrupoFluxo()) {

                                        if (registraGupo) {

                                            if (!pontoAcesso.isFluxo() || registra) {
                                                TelaInstance.getServidor().distribuiMensagem("###000$;" + pontoAcesso.getNome() + ";" + pontoAcesso.getTipo() + ";"
                                                        + pontoAcesso.getIdPonto() + ";" + serial.substring(2) + ";" + serial + ";0;");
                                                System.out.println("-------------> Autorizado pelo grupo");
                                            }
                                        } else {
                                            System.out.println("-------------> Não autorizado pelo grupo");
                                        }

                                    } else {

                                        System.out.println("-------------> Registra " + registra + " --------------->   " + pontoAcesso.getNome());
                                        System.out.println("-------------> Autorizado pelo pareamento");

                                        TelaInstance.getServidor().distribuiMensagem("###000$;" + pontoAcesso.getNome() + ";" + pontoAcesso.getTipo() + ";"
                                                + pontoAcesso.getIdPonto() + ";" + serial + ";" + serial + ";0;");
                                    }

                                }

                                // controle.imprimir(pontoAcesso.getNome());
                                System.out.println("++++++++++++++++++++++++++++++++++++++++++ Fim chamada  ++++++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("");

                            }

                        }

                        if (serial.charAt(0) == '9') {

                            panico = "1";
                            cadastros = new Cadastros();

                            serial = serial.substring(1, serial.length());
                            cadastros = dBCadastro.selectCadastroID(serial);

                            acesso.setNome(cadastros.getNome());
                            acesso.setUnidade(cadastros.getGrupoUnidades().toString());
                            acesso.setAcesso("Garagem");
                            acesso.setSituacao("PÂNICO");

                            localizador = String.valueOf(dbAcessos.inserirEntrada(acesso));

                            serial += ";" + localizador + "#;" + bateria + ";";

                            TelaInstance.getServidor().distribuiMensagem("###009$;" + pontoAcesso.getNome() + ";" + pontoAcesso.getTipo() + ";" + pontoAcesso.getIdPonto() + ";" + serial + ";" + localizador);

                        }

                        //  TelaInstanceCliente.getClienteServidor().distriuServidor(controle);
                        String solentack = "solentack;"
                                + cadastros.getNome() + ";"
                                + cadastros.getVeiculo().getPlaca() + ";"
                                + cadastros.getVeiculo().getMarca() + ";"
                                + cadastros.getCodigo() + ";"
                                + cadastros.getTipo().toUpperCase() + " - " + cadastros.getGrupoUnidades().toString() + ";"
                                + panico + ";"
                                + cadastros.isBloqueioPessoa() + ";"
                                + cadastros.getVeiculo().isBloqueio() + ";"
                                + cadastros.getVeiculo().isMotocilcleta() + ";"
                                + cadastros.getDocumento() + ";"
                                + "false;@"
                                + localizador + "#";

                        // System.out.println(solentack);
                        TelaInstance.getServidor().distribuiMensagem(solentack);

                    }

                    //System.out.println("Res " + bufResp[0]);
                } catch (Exception ex) {

                    // Cliente.conectado = false;
                    System.out.println(ex);
                }

                list.clear();

                for (int i = 0; i < bufResp.length; i++) {

                    list.add(bufResp[i]);

                }

                if (bufResp[14] > 0) {

                    unidade = String.valueOf(bufResp[14]) + String.valueOf(bufResp[15]);

                } else {
                    unidade = String.valueOf(bufResp[15]);
                }

                // unidade = unidade + blocos[bufResp[0]];
            }

        }
    }

    public void acionarSaida(Socket s) {

        try {
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

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

}
