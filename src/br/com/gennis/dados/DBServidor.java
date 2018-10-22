/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author LEANDRO
 */
public class DBServidor extends ConexaoDB{
    
    private String SELECT_MAC = "SELECT * FROM SERVIDOR_LISTA ";
    private List<MACCliente> listaMac = new LinkedList<>();
     
     public List<MACCliente> selectPrefixoUnidade(String unidade,String predio) {
        Connection con = null;

        String unidadePre = "";
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_MAC);

            

            ResultSet resultSet = prepared.executeQuery();
            

            while (resultSet.next()) {
                
                MACCliente macCliente = new MACCliente();
                
                macCliente.setMac(resultSet.getString("MAC"));
                macCliente.setAtivo(resultSet.getBoolean("ATIVO"));
                
                listaMac.add(macCliente);
                
            }
         
        } catch (Exception e) {
            System.out.println("Erro 174");
            System.out.println(e);
        } finally {
            closeConnnection(con);
        }

        return listaMac;
    }
     
     private String SELECT_MAC_ATIVO = "SELECT * FROM SERVIDOR_LISTA WHERE MAC = ? AND ATIVO = TRUE";
     
     public boolean verificaMACAtivo(String mac) {
        Connection con = null;

        boolean ativo = false;
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_MAC_ATIVO);

            prepared.setString(1, mac);

            ResultSet resultSet = prepared.executeQuery();
            

            if (resultSet.next()) {

                
                ativo = true;
                return ativo;
                

            }
            else{
                ativo = false;
                return ativo;
            }
        } catch (Exception e) {
            System.out.println("Erro 174");
            System.out.println(e);
        } finally {
            closeConnnection(con);
        }

        return ativo;
    }
    
}
