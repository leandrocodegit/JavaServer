/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author LEANDRO
 */
public class DBControleRF extends ConexaoDB {
    
    

    private final String INSERT_CONTROLERF = "INSERT INTO  DISPOSITIVOS "
            + "(SERIAL,IDCADFK,TIPO,BLOQUEIO,ASSOCIADO,PLACA,PREFIXO_UNIDADE) VALUES (?,?,?,?,?,?,?)";
    private final String VERIFICA_ASSOCIACAO = "SELECT IDCAD FROM CADASTROS C, UNIDADE_CADASTRO UC WHERE NOME LIKE ? \n" +
"     AND UC.UNIDADEFK = (SELECT PREFIXO_UNIDADE FROM PREFIXO_UNIDADES WHERE UNIDADE_FK = ? AND PREDIO_FK = ?) LIMIT 1";
    private final String INSERT_VEICULO = "INSERT INTO  VEICULOS "
            + "(IDCADFK,PLACA,MODELO,COR,MOTOCICLETA,BLOQUEIO_VEICULO,PREFIXO_UNIDADE) VALUES (?,?,?,?,?,?,?)";

    public void insertControleRF(ControleRF controleRF) {
        Connection con = null;
        boolean assoc = false;
        String idcad = "";
        String nome = "";

        if (controleRF.getNome().length() > 15) {
            nome = controleRF.getNome().substring(0, 15);

        } else {
            nome = controleRF.getNome();
        }

        System.out.println(controleRF.getUnidade() + controleRF.getTorre());
        nome = nome.trim();

        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(INSERT_CONTROLERF);
            
            

            PreparedStatement prepared2 = con.prepareStatement(VERIFICA_ASSOCIACAO);
            prepared2.setString(1, "%" + nome + "%");
            prepared2.setString(2, controleRF.getUnidade());
            prepared2.setString(3, controleRF.getTorre());

            
             

            

            ResultSet resultSet2 = prepared2.executeQuery();
            

            if (resultSet2.next()) {

              
                assoc = true;
                idcad = resultSet2.getString("IDCAD");

            } else {
                assoc = false;
            }

            prepared.setString(1, controleRF.getSerial());
            prepared.setString(2, idcad);
            prepared.setString(3, controleRF.getTipo());
            prepared.setBoolean(4, false);
            prepared.setBoolean(5, assoc);
            prepared.setString(6, controleRF.getPlaca());
            prepared.setString(7,selectPrefixoUnidade(controleRF.getUnidade(), controleRF.getTorre()));

            
            prepared.execute();

            boolean moto = false;

            if (controleRF.getMarca().contains("MOTO")) {
                moto = true;
            }

           

            PreparedStatement prepared3 = con.prepareStatement(INSERT_VEICULO);
            prepared3.setString(1, idcad);
            prepared3.setString(2, controleRF.getPlaca());
            prepared3.setString(3, controleRF.getMarca());
            prepared3.setString(4, controleRF.getCor());
            prepared3.setBoolean(5, moto);
            prepared3.setBoolean(6, false);
            prepared3.setString(7, controleRF.getUnidade() + controleRF.getTorre());
           
            if (!controleRF.getPlaca().contains("   ")) {
                prepared3.execute();
            }
           

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Erro 202 !\n Falha ao inserir novo registro.");

            System.out.println("114");
            System.out.println(e);
        } finally {
            closeConnnection(con);
        }

    }

    
    
    private String SELECT_PREFIXO = "SELECT * FROM PREFIXO_UNIDADES WHERE UNIDADE_FK = ? AND PREDIO_FK = ?";
     
     public String selectPrefixoUnidade(String unidade,String predio) {
        Connection con = null;

        String unidadePre = "";
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_PREFIXO);

            prepared.setString(1, unidade);
            prepared.setString(2, predio);

            ResultSet resultSet = prepared.executeQuery();
            

            if (resultSet.next()) {

                unidadePre = resultSet.getString("PREFIXO_UNIDADE");
                
                
                
                

            }
        } catch (Exception e) {
            System.out.println("Erro 174");
            System.out.println(e);
        } finally {
            closeConnnection(con);
        }

        return unidadePre;
    }
     
      private String DELETE_PORTA = "DELETE FROM DISPOSITIVOS WHERE SERIAL = ?";
      
     public void removeDispositivo(String serial) throws SQLException {
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(DELETE_PORTA);
            prepared.setString(1, serial);
           
            prepared.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }
        
    }
     


}
