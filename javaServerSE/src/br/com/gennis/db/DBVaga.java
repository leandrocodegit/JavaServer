/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.db;

import br.com.gennis.dados.Cadastros;
import br.com.gennis.dados.ConexaoDB;
import br.com.gennis.dados.Veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class DBVaga extends ConexaoDB{
    
    
    
    private final String SELECT_CADASTRO_ID_SERIAL = "SELECT C.IDCAD,C.FUNCAO,C.AVISOS_MORADOR,V.PLACA,V.MODELO,V.COR,"
            + " V.BLOQUEIO_VEICULO ,C.NOME,C.TIPO,C.CODIGO,C.BLOQUEIO_CADASTRO, C.FOTO, UC.UNIDADEFK FROM\n" +
"                VEICULOS  V , CADASTROS C,  UNIDADE_CADASTRO UC WHERE V.IDCADFK = C.IDCAD \n" +
"				AND C.IDCAD = (SELECT IDCADFK FROM DISPOSITIVOS WHERE SERIAL = ? ) \n" +
"				AND V.PLACA = (SELECT PLACA FROM DISPOSITIVOS WHERE SERIAL = ?) AND C.IDCAD = UC.IDCADFK"
            +                 " GROUP BY C.IDCAD,C.FUNCAO,C.AVISOS_MORADOR,V.PLACA,V.MODELO,V.COR,"
            + " V.BLOQUEIO_VEICULO ,C.NOME,C.TIPO,C.CODIGO,C.BLOQUEIO_CADASTRO, C.FOTO, UC.UNIDADEFK ";
    private Cadastros cadastros;
    private Veiculo veiculo;

    public Cadastros selectCadastroIDPorDispositivo(String serial) {
        Connection con = null;

        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_CADASTRO_ID_SERIAL);

            prepared.setString(1, serial);
            prepared.setString(2, serial);

            ResultSet resultSet = prepared.executeQuery();

            if (resultSet.next()) {

                veiculo = new Veiculo();
                cadastros = new Cadastros();

                //Dados da pessoa
                cadastros.setDocumento(resultSet.getString("IDCAD"));
                cadastros.setNome(resultSet.getString("NOME"));
                cadastros.setTipo(resultSet.getString("TIPO"));
                cadastros.setAvisos(resultSet.getBoolean("AVISOS_MORADOR"));                
                cadastros.setBloqueioPessoa(resultSet.getBoolean("BLOQUEIO_CADASTRO"));
                cadastros.setFoto(resultSet.getBytes("FOTO"));
                cadastros.setPrefixoUnidade(resultSet.getString("UNIDADEFK"));

               

                //Dados do veiculo
                veiculo.setPlaca(resultSet.getString("PLACA"));
                veiculo.setMarca(resultSet.getString("MODELO"));
                veiculo.setCor(resultSet.getString("COR"));
                cadastros.setBloqueioVeiculo(resultSet.getBoolean("BLOQUEIO_VEICULO"));

                cadastros.setVeiculo(veiculo);
                
                
                cadastros.setGrupoUnidades(getGrupoListaUnidades(cadastros.getDocumento()));
                
                
               return cadastros;

            }
            
            else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            System.out.println("Diposit 62");
        } finally {
            closeConnnection(con);
        }

        return cadastros;
    }
    
    
    private String SELECT_UNIDADE_GRUPO = "SELECT UNIDADEFK FROM UNIDADE_CADASTRO WHERE IDCADFK = ? AND UNIDADEFK != 'N'";
    List<String> listaUnidade = new ArrayList<String>();

    public List<String> getGrupoListaUnidades(String idcad) {

        String unidade = "";

        listaUnidade = new ArrayList<String>();
        Connection con = null;

        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_UNIDADE_GRUPO);
            prepared.setString(1, idcad);
            ResultSet resultSet = prepared.executeQuery();

            while (resultSet.next()) {

                listaUnidade.add(resultSet.getString("UNIDADEFK"));

            }

        } catch (Exception e) {
            System.out.println("Cadastros 811");
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }

        return listaUnidade;
    }
    
}
