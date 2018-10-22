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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author LEANDRO
 */
public class DBAcessos extends ConexaoDB {

    private final String INSERT_ENTRADA = "INSERT INTO  ACESSOS (NOME,IDCAD,ENTRADA,ACESSO,UNIDADE,SITUACAO) VALUES (?,?,?,?,?,?)";
    
    public int inserirEntrada(Acesso acesso) {
        Connection con = null;
        int identificador = 0;

        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(INSERT_ENTRADA);

            prepared.setString(1, acesso.getNome());
            prepared.setString(2, acesso.getDocumento());
            prepared.setString(3, dataAtual());
            prepared.setString(4, acesso.getAcesso());
            prepared.setString(5, acesso.getUnidade());
            prepared.setString(6, acesso.getSituacao());

            prepared.execute();
            
            ResultSet resultSet = prepared.executeQuery("SELECT LAST_INSERT_ID()");
        if (resultSet.next()) {
            identificador = resultSet.getInt("LAST_INSERT_ID()");
            
        }
            
            
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Erro 202 !\n Falha ao inserir novo registro.");

            System.out.println("Erro no acesso");
            System.out.println(e);
        } finally {
            closeConnnection(con);
        }

        return identificador;
    }

    private SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date;

    private String dataAtual() {

        date = new Date();

        return f.format(date);
    }
    
    
     private final String ATUALIZA_ACESSO = "UPDATE ACESSOS SET SITUACAO = ? WHERE ID =?";

    public void atualizarSituacao(String situacao, String ID) {

        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(ATUALIZA_ACESSO);

            prepared.setString(1, situacao);
            prepared.setString(2, ID);

            prepared.execute();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro 202 !\n Falha ao inserir novo registro.");
        } finally {
            closeConnnection(con);
        }

    }
    
    private final String ATUALIZA_NOME_ACESSO = "UPDATE ACESSOS SET NOME = ?, IDCAD = ? WHERE ID =?";

    public void atualizarEntrada(String nome,String idcad, String ID) {

        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(ATUALIZA_NOME_ACESSO);

            prepared.setString(1, nome);
            prepared.setString(2, idcad);
            prepared.setString(3, ID);

            prepared.execute();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro 202 !\n Falha ao inserir novo registro.");
        } finally {
            closeConnnection(con);
        }

    }
}
