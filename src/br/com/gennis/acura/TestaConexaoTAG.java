/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.acura;

import br.com.gennis.controle.PontoAcesso;
import br.com.gennis.servidor.TelaInstance;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author ADMIN
 */
public class TestaConexaoTAG implements Runnable {

    
    private PontoAcesso pontoAcesso;
    private Device device;
   

   
        

    public TestaConexaoTAG(PontoAcesso pontoAcesso, Device device) {
        this.pontoAcesso = pontoAcesso;
        this.device = device;
        
        
        
    }

    public void run() {

        while (true) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {

            }

            if (device.getSocket() != null) {

                device.getSaida().println("ack");

               // System.out.println(antenaTAG.getSaida().checkError() + "  " +  pontoAcesso.getNome() + "  " + pontoAcesso.getId() + "  " + (socket == antenaTAG.getSaida()));

                while (device.getSaida().checkError() && pontoAcesso.isConectado()) {

                    try {
                    device.getSaida().close();
                    device.getSocket().close();
                    pontoAcesso.setConectado(false);
                   
                       
                       device.conectar();

                        break;
                    } catch (Exception ex) {
                        System.out.println("Verificar conexao");
                    }

                }
                
                try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        
                    }
                
                if (pontoAcesso.isConectado()) {
                    TelaInstance.getServidor().distribuiMensagem("###112;" + pontoAcesso.getIdPonto());

                }

               // TelaInstance.getServidor().distribuiMensagem("###112;" + pontoAcesso.getId());
            }

        }
    }

}
