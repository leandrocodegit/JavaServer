/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.dados;


import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LEANDRO
 */
public class ConexaoDB implements DbConnectSQL{
    
    
    

   @Override
   public Connection getConnection() throws SQLException {

       
       
       try { 
           Class.forName("com.mysql.jdbc.Driver");
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
       
       
       
        String url = "jdbc:mysql://127.0.0.1/DBLOCALCONTROLE?autoReconnect=true&useSSL=false"; 
        //url = "jdbc:mysql://187.3.129.131:9988/DBLOCALCONTROLE";
       
        String usuario = "root";
        String senha = "admin";
        //String senha = "[senha]";

        Connection con = null;
        con = DriverManager.getConnection(url, usuario, senha);
        return con;
    }

   @Override
    public void closeConnnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    


}
