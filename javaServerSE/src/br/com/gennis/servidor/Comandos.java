/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.servidor;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author LEANDRO
 */
public class Comandos {
    
    
    
    public void importarDados(Socket socket){
        
         try{
           DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            
           byte[] ok =  {(byte) 0x00, (byte)0x46 , (byte)0x46};
            
           
           Thread.sleep(2000);
                System.out.println("Comando enviado");
            
            
            
            
            dout.write(ok);

        } catch (Exception ex) {
             System.out.println("Erro");
        }
        
        
    }
    
    public void atualizaReceptores(Socket socket){
        
         try{
           DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            
           byte[] ok =  {(byte) 0x00, (byte)0x1D , (byte)0x1D};
            
           
           Thread.sleep(2000);
                System.out.println("Comando enviado");
            
            
            
            
            dout.write(ok);

        } catch (Exception ex) {
             System.out.println("Erro");
        }
        
        
    }
    
}
