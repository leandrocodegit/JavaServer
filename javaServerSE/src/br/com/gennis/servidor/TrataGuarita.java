/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.servidor;


import br.com.gennis.controle.Controle;
import br.com.gennis.controle.PontoAcesso;
import br.com.gennis.dados.ControleRF;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author LEANDRO
 */
public class TrataGuarita implements Runnable {

    private InputStream cliente;
    private ServidorGuarita servidor;
    private List<ControleRF> listaDispositivos = new ArrayList<ControleRF>();

    Socket socket;
    private String unidade = "";
    private String torre;
    private String controle;
    private List<PontoAcesso> listaPonto;
    private Controle controleFluxo;
    

    public TrataGuarita(InputStream cliente, ServidorGuarita servidor,List<PontoAcesso> listaPonto) {
        this.cliente = cliente;
        this.servidor = servidor;
        this.listaPonto = listaPonto;
        this.controleFluxo = controleFluxo;

    }

    public void run() {
        // quando chegar uma msg, distribui pra todos

        runs();

    }

    public void runs() {

        boolean bo = true;

        LinkedList list = new LinkedList();

        byte[] id = new byte[4];

        Scanner s = new Scanner(this.cliente);
        DataInputStream dat = new DataInputStream(cliente);

        int cont = 0;
        byte[] bufResp = new byte[80];

        // recebe msgs do servidor e imprime na tela
       

        while (true) {

            ControleRF dispositivos = new ControleRF();
            try {
                
                
                dat.read(bufResp);
                
                
                //System.out.println("UTF "  + s.nextLine());

            } catch (Exception ex) {

            }

            String comando = javax.xml.bind.DatatypeConverter.printHexBinary(bufResp);

            System.out.println("CMD " + comando);
            
            String comandoResposta = comando.substring(0, 4);
            String resposta = comando.substring(0, 6);
            
          if (resposta.contains("00040")) {                  
                  
                 String serial =  comando.substring(7, 14);
                  int idModulo = 0;
                  
                  if(listaPonto.size() >0){
                  
                  TelaInstance.getServidor().distribuiMensagem("###000$;"  + listaPonto.get(idModulo).getNome()+ ";" + listaPonto.get(idModulo).getTipo()+ ";"  + 
                                 listaPonto.get(idModulo).getIdPonto()+ ";" + serial + ";" + serial + ";0;");
                  }
                  
                  System.out.println("aqui");
                  
                  boolean sentido = false;

                            if (listaPonto.get(idModulo).getSentido() == 0) {
                                sentido = true;
                            }
                            System.out.println("Ativando");

                            boolean registra = controleFluxo.entrada(controle,listaPonto.get(idModulo).getTempoFluxo());

                            
                            controleFluxo.imprimir(listaPonto.get(idModulo).getNome());
                  
              }
            
            
              if (resposta.contains("004304")) {                  
                  TelaInstance.getServidor().distribuiMensagem("###4304");
                  
              }
            

            if (comandoResposta.contains("0046")) {

                String tipo = comando.substring(4, 5);

                if (tipo.equals("1")) {
                    dispositivos.setTipo("Controle RF");
                }
                dispositivos.setSerial(comando.substring(5, 12));
                int decodeL = Integer.parseInt(comando.substring(18, 20), 16);
                int decodeH = Integer.parseInt(comando.substring(16, 18), 16);

                String uniL = String.valueOf(decodeL);
                String uniH = String.valueOf(decodeH);

                int torre = Integer.parseInt(comando.substring(20, 22), 16) + 1;

                if (decodeH > 1) {

                    if (decodeL < 10) {

                        unidade = uniH + "0" + String.valueOf(decodeL);
                    } else {
                        unidade = uniH + uniL;
                    }

                } else {
                    unidade = uniL;
                }

               // System.out.println(comando.substring(68, 80));
                String placa = "";

                int indiceInicial = 68;
                int indiceFinal = 70;

                for (int i = 0; i < 7; i++) {

                    try {

                        int placaCarac = Integer.parseInt(comando.substring(indiceInicial, indiceFinal),16);
                        
                        char c = (char) placaCarac;
                        
                        placa += c;

                        indiceInicial += 2;
                        indiceFinal += 2;

                    } catch (Exception err) {

                    }

                }
                
                
                
                dispositivos.setPlaca(placa);
                dispositivos.setCor(cor(comando.substring(66,68)));
                dispositivos.setMarca(marca(comando.substring(64,66)));
               

                
                dispositivos.setTorre(torre(String.valueOf(torre)));
                dispositivos.setNome(nome(comando.substring(26, 66)));
                dispositivos.setUnidade(unidade);

                //dispositivos.setTipo(comando.);
                servidor.confirmacao();

                if (resposta.contains("0046F0")) {

                    servidor.confirmacao();
                    servidor.transferirDados(listaDispositivos);
                    TelaInstance.getServidor().distribuiMensagem("###956$");
                    servidor.resetGuarita();
                    break;

                } else {
                    listaDispositivos.add(dispositivos);
                }

                //System.out.println(comando);
            }

        }

    }

    public String nome(String array) {
        String descricao = "";

        if (array.length() > 0) {

            int inicio = 0;
            int fim = 2;
            for (int i = 0; i < 20; i++) {

                int decode = Integer.parseInt(array.substring(inicio, fim), 16);

                char f = (char) decode;

                descricao += String.valueOf(f);

                inicio += 2;
                fim += 2;
            }

        }

        return descricao;
    }

    public String torre(String codigo) {

        if (codigo.equals("1")) {
            return "A";
        }
        if (codigo.equals("2")) {
            return "B";
        }
        if (codigo.equals("3")) {
            return "C";
        }
        if (codigo.equals("4")) {
            return "D";
        }
        if (codigo.equals("5")) {
            return "E";
        }
        if (codigo.equals("6")) {
            return "F";
        }
        if (codigo.equals("7")) {
            return "G";
        }
        if (codigo.equals("8")) {
            return "H";
        }
        if (codigo.equals("9")) {
            return "I";
        }
        if (codigo.equals("10")) {
            return "J";
        }
        if (codigo.equals("11")) {
            return "K";
        }
        if (codigo.equals("12")) {
            return "L";
        }
        if (codigo.equals("13")) {
            return "M";
        }
        if (codigo.equals("14")) {
            return "N";
        }
        if (codigo.equals("15")) {
            return "O";
        }
        if (codigo.equals("16")) {
            return "P";
        }
        if (codigo.equals("17")) {
            return "Q";
        }
        if (codigo.equals("18")) {
            return "R";
        }
        if (codigo.equals("19")) {
            return "S";
        }
        if (codigo.equals("20")) {
            return "T";
        }
        if (codigo.equals("21")) {
            return "U";
        }
        if (codigo.equals("22")) {
            return "V";
        }
        if (codigo.equals("23")) {
            return "W";
        }
        if (codigo.equals("24")) {
            return "X";
        }
        if (codigo.equals("25")) {
            return "Y";
        }
        if (codigo.equals("26")) {
            return "Z";
        }

        return "";
    }
    
    public String marca(String codigo) {

        if (codigo.equals("00")) {
            return "AUDI";
        }
        if (codigo.equals("01")) {
            return "BMW";
        }
        if (codigo.equals("02")) {
            return "CHEVROLET";
        }
        if (codigo.equals("03")) {
            return "CHRYSLER";
        }
        if (codigo.equals("04")) {
            return "CITROEN";
        }
        if (codigo.equals("05")) {
            return "FERRARI";
        }
        if (codigo.equals("06")) {
            return "FIAT";
        }
        if (codigo.equals("07")) {
            return "FORD";
        }
        if (codigo.equals("08")) {
            return "GM";
        }
        if (codigo.equals("09")) {
            return "HONDA";
        }
        if (codigo.equals("0A")) {
            return "HYUNDAI";
        }
        if (codigo.equals("0B")) {
            return "IMPORTADO";
        }
        if (codigo.equals("0C")) {
            return "JAGUAR";
        }
        if (codigo.equals("0D")) {
            return "JEEP";
        }
        if (codigo.equals("0E")) {
            return "KIA";
        }
        if (codigo.equals("0F")) {
            return "LAMBORGHINI";
        }
        if (codigo.equals("10")) {
            return "LAND ROVER";
        }
        if (codigo.equals("11")) {
            return "MAZDA";
        }
        if (codigo.equals("12")) {
            return "MERCEDES";
        }
        if (codigo.equals("13")) {
            return "MITSUBISHI";
        }
        if (codigo.equals("14")) {
            return "MOTO";
        }
        if (codigo.equals("15")) {
            return "NISSAN";
        }
        if (codigo.equals("16")) {
            return "VEICULO";
        }
        if (codigo.equals("17")) {
            return "PEUGEOT";
        }
        if (codigo.equals("18")) {
            return "PORSCHE";
        }
        if (codigo.equals("19")) {
            return "RENAULT";
        }
          if (codigo.equals("1A")) {
            return "SUBARU";
        }
        if (codigo.equals("1B")) {
            return "SUZUKI";
        }
        if (codigo.equals("1C")) {
            return "TOYOTA";
        }
        if (codigo.equals("1D")) {
            return "VOLKSWAGEN";
        }
        if (codigo.equals("1E")) {
            return "VOLVO";
        }
         if (codigo.equals("1F")) {
            return "SEM VEICULO";
        }

        return "";
    }
    
    
    
    public String cor(String codigo) {

        if (codigo.equals("00")) {
            return "AMARELO";
        }
        if (codigo.equals("01")) {
            return "AZUL";
        }
        if (codigo.equals("02")) {
            return "BEGE";
        }
        if (codigo.equals("03")) {
            return "BRANCO";
        }
        if (codigo.equals("04")) {
            return "CINZA";
        }
        if (codigo.equals("05")) {
            return "DOURADO";
        }
        if (codigo.equals("06")) {
            return "FANTASIA";
        }
     
        if (codigo.equals("07")) {
            return "GRENA";
        }
        if (codigo.equals("08")) {
            return "LARANJA";
        }
        if (codigo.equals("09")) {
            return "MARROM";
        }
        if (codigo.equals("0A")) {
            return "PRATA";
        }
        if (codigo.equals("0B")) {
            return "PRETO";
        }
        if (codigo.equals("0C")) {
            return "ROSA";
        }
        if (codigo.equals("0D")) {
            return "ROXO";
        }
        if (codigo.equals("0E")) {
            return "VERDE";
        }
        if (codigo.equals("0F")) {
            return "VERMELHO";
        }
       

        return "";
    }


}
