/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.dados;

import br.com.gennis.servidor.TelaInstance;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author LEANDRO
 */
public class TrataSessao {
    
    private  DBServidor dbServidor = new DBServidor();
    private static List<MACCliente> listaMac = new LinkedList<>();
    
    public  boolean adicionarMAC(String mac, String ip){
        
        
        boolean ativo = dbServidor.verificaMACAtivo(mac);
        
        
        if(ativo){
        MACCliente macCliente = new MACCliente();
        
        macCliente.setIp(ip);
        macCliente.setMac(mac);
        System.out.println("Adicionado cliente");
        
        listaMac.add(macCliente);
        
        }
        
        
        return ativo;
        
        
    }
    
    
    public  boolean validarConexao(String ip) {
        
        for (int i = 0; i < listaMac.size(); i++) {
            
            
            if(listaMac.get(i).getIp().contains(ip)){
                
                return true;
                
            }
            
            
        }
        return false;
    }
}
