/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.acura;


import br.com.gennis.controle.Porta;
import br.com.gennis.dados.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Leandro Code
 */
public class DBPortas extends ConexaoDB{
    
    
    private final String SELECT_PORTAS = "SELECT * FROM PORTAS P, PONTO_ACESSO PA WHERE P.IDPONTOFK = PA.IDPONTO";
    private List<Porta> lista = new LinkedList<Porta>();

public List<Porta> selectListaPortas() {
         
        lista.removeAll(lista);
        
        Connection con = null;

        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_PORTAS);
            ResultSet resultSet = prepared.executeQuery();

            while (resultSet.next()) {
                
                Porta portas = new Porta();
                
                               
                portas.setPonto(resultSet.getString("NOME"));
                portas.setIdPonto(resultSet.getInt("IDPONTOFK"));
                portas.setIdControladora(resultSet.getInt("IDCNTRLFK"));
                portas.setRele(resultSet.getInt("RELE"));                  
                portas.setControladora(resultSet.getString("CONTROLADORA"));
                portas.setTipo(resultSet.getInt("TIPO")); 
                portas.setTempo(resultSet.getInt("TEMPO"));
                portas.setAtivo(resultSet.getBoolean("ATIVO"));
                
               lista.add(portas);
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }

        return lista;
    }

private final String ATUALIZA_PORTA = "UPDATE PORTAS SET IDCNTRLFK= ?, RELE = ? , CONTROLADORA = ?, TIPO = ?,TEMPO = ?"
        + " ATIVO = ? WHERE IDPONTOFK = ?";
    public void atualizaPorta(Porta portas) {
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(ATUALIZA_PORTA);
            prepared.setInt(1,portas.getIdControladora());
            prepared.setInt(2,portas.getRele());
            prepared.setString(3,portas.getControladora());
            prepared.setInt(4,portas.getTipo());
            prepared.setInt(5,portas.getTempo());
            prepared.setBoolean(6,portas.isAtivo());
            prepared.setInt(7, portas.getIdPonto());
          
           prepared.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro 202 !\n Falha ao inserir novo registro.");
        } finally {
            closeConnnection(con);
        }
       ;
    }
    
}
