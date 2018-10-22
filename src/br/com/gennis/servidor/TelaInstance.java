/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.servidor;

import br.com.gennis.dados.TrataSessao;



/**
 *
 * @author LEANDRO
 */
public class TelaInstance {
    
    
    private static Servidor servidor;
    private static ServidorGuarita servidorGuarita;
    
    private static TrataSessao trataSessao = new TrataSessao();

    public static TrataSessao getTrataSessao() {
        return trataSessao;
    }

    public static void setServidor(Servidor servidor) {
        TelaInstance.servidor = servidor;
    }
    
    
    public static Servidor getServidor() {
        
        return servidor;
    }

    public static ServidorGuarita getServidorGuarita() {
        return servidorGuarita;
    }

    public static void setServidorGuarita(ServidorGuarita servidorGuarita) {
        TelaInstance.servidorGuarita = servidorGuarita;
    }
    
    

   
    
   
   

   
    
}
