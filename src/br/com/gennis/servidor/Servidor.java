/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.servidor;


import br.com.gennis.dados.ClientesConectados;
import br.com.gennis.dados.Conexoes;
import br.com.gennis.dados.DBCadastro;
import br.com.gennis.dados.DispositivosMobile;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LEANDRO
 */
public class Servidor implements Runnable {

    private Socket socket;
    private Socket socketLinear;
    private DispositivosMobile dispositivos = new DispositivosMobile();
    private DBCadastro dbDispositivos = new DBCadastro();

    @Override
    public void run() {
        try {
            executa();
        } catch (IOException ex) {

        }
    }

    private int porta;
    private List<PrintStream> clientes;    
    private List<Conexoes> listaConexoes;
    private ServidorGuarita servidorGuarita;

    
    
    
    

    public Servidor(int porta) {
        
        this.porta = porta;
        this.clientes = new ArrayList<PrintStream>();
        this.listaConexoes = new ArrayList<Conexoes>();
       // this.servidorGuarita = servidorGuarita;

    }

    public void executa() throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);

        while (true) {
            // aceita um cliente
            socket = servidor.accept();

           
            ClientesConectados clientesConectados = new ClientesConectados();

            // adiciona saida do cliente à lista
            PrintStream ps = new PrintStream(socket.getOutputStream());
            this.clientes.add(ps);
            
            
             enviarPedidoMac(ps, "###66550");

            Conexoes conexoes = new Conexoes();
            /*
             conexoes.setTipo("Cliente");
             conexoes.setIdConexao(String.valueOf(clientes.indexOf(ps)));
             conexoes.setEnderecoIP(String.valueOf(socket.getInetAddress()));
             conexoes.setPortaTCP(String.valueOf(socket.getPort()));
             conexoes.setPrintStream(ps);
             conexoes.setSocket(socket);
             */

            /*
            
             if(conexoes.getEnderecoIP().equals("/10.0.0.10")){
             socketLinear = socket;
                
             }
             */
            listaConexoes.add(conexoes);

            // clientesConectados.setPrintStream(ps);
            // clientesConectados.setEndereçoIp(String.valueOf(socket.getInetAddress()));
            //TelaInstance.getTelaServidor().atualizarTabela(listaConexoes);
            // cria tratador de cliente numa nova thread
            TrataCliente tc
                    = new TrataCliente(socket.getInputStream(), this);
            new Thread(tc).start();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            

        }

    }

    /*

     public void distribuiMensagems(String msg) {
     

     Conexoes clienteSocket = null;
     boolean error = false;
     for (Conexoes cx : listaConexoes) {

     PrintStream cliente = cx.getPrintStream();
     Socket socket = cx.getSocket();

     try {
     dispositivos = dbDispositivos.selectCadastroID(msg);
                    
                    
     } catch (Exception ex) {
     Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
     }
           
            
     String ip = cx.getEnderecoIP().substring(1);
            
            
     if(ip.trim().contains("127.0.0.1")){
                              
                
     cliente.println(msg);
                
     }
            
            
     if(ip.trim().contains("10.0.0.111")){
               
     cliente.println("solentack;" +
     dispositivos.getNome()+";" +
     dispositivos.getPlaca()+";"+
     dispositivos.getSenha()+";"+
     dispositivos.getUnidade()+";"+
     dispositivos.getLocalizador());
                
                
     System.out.println("solentack;" +
     dispositivos.getNome()+";" +
     dispositivos.getPlaca()+";"+
     dispositivos.getSenha()+";"+
     dispositivos.getUnidade()+";"+
     dispositivos.getLocalizador());;
           
            
            
     }
             

     if (cliente.checkError()) {

     System.out.println("Erro");
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
                
     TelaInstance.getTelaServidor().atualizarTabela(listaConexoes);
     error = false;
     }

     }

     }
     */
    public void enviarPedidoMac(PrintStream cx,String msg) {

        cx.println(msg);
        

    }

   synchronized public void distribuiMensagem(String msg) {

     //  System.out.println("++++++++++ " + msg);
        
        PrintStream cxDesconect = null;
       
       
        if (!msg.equals("ack")) {

            if (msg.contains("solentack") || msg.contains("###")) {
                
                //System.out.println(" ---- " + msg);
                boolean error = false;
                for (PrintStream cx : clientes) {

                    cx.println(msg);
                   
                    if(cx.checkError()){
                        
                        cxDesconect = cx;
                        error = true;
                        
                    }
                    

                }
                
                 
                
                if(error){
                     clientes.remove(cxDesconect);
                    
                     
                }
               
            }
        }

    }

}
