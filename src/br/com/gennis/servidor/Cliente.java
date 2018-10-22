/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.servidor;

import br.com.gennis.acura.Device;
import br.com.gennis.acura.TestaConexaoTAG;
import br.com.gennis.controle.Controle;
import br.com.gennis.controle.ControleGrupo;
import br.com.gennis.controle.PontoAcesso;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author LEANDRO
 */
public class Cliente extends Device implements Runnable {

    private PontoAcesso pontoAcesso;
    private Socket socket;
    private PrintStream saida;
    private Controle controle;
    private ControleGrupo controleGrupo;

    @Override
    public void run() {
        conectar();
    }

    public Cliente(PontoAcesso pontoAcesso, Controle controle, ControleGrupo controleGrupo) {
        this.pontoAcesso = pontoAcesso;
        this.controleGrupo = controleGrupo;
        //this.testaConexaoTAG = testaConexaoTAG;
        pontoAcesso.setConectado(false);
        this.controle = controle;

    }

   @Override
    public void conectar() {

        try {

            while (pontoAcesso.isConectado() == false) {

                TelaInstance.getServidor().distribuiMensagem("###111;" + pontoAcesso.getIdPonto()+ ";Reconectando...");

                socket = new Socket();
                try {

                    System.out.println("Conectando... " + pontoAcesso.getNome() + " " + pontoAcesso.getIdPonto());

                    Thread.sleep(2000);
                    InetSocketAddress ip = new InetSocketAddress(pontoAcesso.getIp(), pontoAcesso.getPortaTCP());
                    //InetSocketAddress ip = new InetSocketAddress(SingletonPerfil.getPerfilSistema().getIpModulo(), SingletonPerfil.getPerfilSistema().getPortaTCPModulo());

                    // enderecoIP = String.valueOf(ipLocal.getHostAddress());
                    socket.connect(ip, 2000);

                    saida = new PrintStream(socket.getOutputStream());

                    pontoAcesso.setConectado(true);
                    System.out.println("Conectado   " + pontoAcesso.getNome() + " " + socket.getLocalPort());
                    
                    
                   

                    break;
                } catch (Exception ex) {

                    TelaInstance.getServidor().distribuiMensagem("###111;" + pontoAcesso.getIdPonto()+ ";Falha ao reconectar!");
                    System.out.println("Erro ao conectar antena");
                }

            }

            // thread para receber mensagens do servidor
            Recebedor r = new Recebedor(socket.getInputStream(), pontoAcesso,socket,controle,controleGrupo);
            new Thread(r).start();
            
            

            // lê msgs do teclado e manda pro servidor
            TestaConexaoTAG testaConexaoTAG = new TestaConexaoTAG(pontoAcesso, this);
            Thread t1 = new Thread(testaConexaoTAG);
            t1.start();
            
            
            
                    

            //testaConexaoTAG.setSocket(saida);
            pontoAcesso.setConectado(true);

            TelaInstance.getServidor().distribuiMensagem("###112;" + pontoAcesso.getIdPonto());

            //saida.close();
            // teclado.close();
            // cliente.close();
        } catch (Exception ex) {
            System.out.println("Erro ao conectar tag ");
            System.out.println(ex.getMessage());
        }
    }

    public  Socket getSocket() {
        return socket;
    }

    public PrintStream getSaida() {
        return saida;
    }
    public void setControle(Controle controle) {
        
         this.controle = controle;
    }
    
}
