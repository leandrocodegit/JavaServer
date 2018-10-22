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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author LEANDRO
 */
public class DBCadastro extends ConexaoDB{
 
     private Date date = new Date();
    private SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
    
    private final String SELECT_CADASTRO_ID = "SELECT * FROM CADASTROS C , UNIDADE_CADASTRO UC, PREDIOS P \n"
            + "           WHERE C.IDCAD = ? AND C.IDCAD = UC.IDCADFK AND P.TORRE = UC.TORREFK GROUP BY UC.IDCADFK ASC LIMIT 1";

    public Cadastros selectCadastroID(String serial) {
        Connection con = null;

        Cadastros cad = null;
        
        String idcad = getCodicoCadastro(serial);
        
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_CADASTRO_ID);

            prepared.setString(1, idcad);

            ResultSet resultSet = prepared.executeQuery();

            if (resultSet.next()) {

                cad = new Cadastros();
                cad.setDocumento(resultSet.getString("IDCAD"));
                cad.setCodigo(resultSet.getInt("CODIGO_CADASTRO"));
                cad.setNome(resultSet.getString("NOME"));
                cad.setVinculo(resultSet.getString("VINCULO"));
                cad.setTipo(resultSet.getString("TIPO"));
                cad.setBloqueioPessoa(resultSet.getBoolean("BLOQUEIO_CADASTRO"));
                cad.setPermiteVisitas(resultSet.getBoolean("PERMITE_VISITAS"));
                cad.setPrefixoUnidade(resultSet.getString("UNIDADEFK"));
                cad.setFuncao(resultSet.getString("FUNCAO"));
                cad.setAvisos(resultSet.getBoolean("AVISOS_MORADOR"));
                cad.setEntrada(resultSet.getBoolean("ENTRADA"));

                Torre torre = new Torre();
                torre.setNome(resultSet.getString("NOME_TORRE"));
                torre.setTorre(resultSet.getString("TORRE"));
                cad.setTorre(torre);
                
                cad.setVeiculo(getVeiculo(serial));

                cad.setGrupoUnidades(getGrupoListaUnidades(idcad));

            }
        } catch (Exception e) {
            System.out.println("Erro");
            System.out.println(e);
        } finally {
            closeConnnection(con);
        }

        return cad;
    }
    
    
     List<String> listaUnidade = new ArrayList<String>();
    private String SELECT_UNIDADE_GRUPO = "SELECT UNIDADEFK FROM UNIDADE_CADASTRO WHERE IDCADFK = ? AND UNIDADEFK != 'N'";
    public List<String> getGrupoListaUnidades(String idcad) {

        String unidade = "";

        listaUnidade.removeAll(listaUnidade);
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
    
    
    private String SELECT_VEICULOS_PLACA = "SELECT * FROM VEICULOS WHERE  PLACA = ( SELECT PLACA FROM DISPOSITIVOS WHERE SERIAL = ? )";

    public Veiculo getVeiculo(String serial) {

        Veiculo veiculo = new Veiculo();

        Connection con = null;

        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_VEICULOS_PLACA);
            prepared.setString(1, serial);
            ResultSet resultSet = prepared.executeQuery();

            if (resultSet.next()) {

               

                veiculo.setPlaca(resultSet.getString("PLACA"));
                veiculo.setIdCadastro(resultSet.getString("IDCADFK"));
                veiculo.setBloqueio(resultSet.getBoolean("BLOQUEIO_VEICULO"));
                veiculo.setCor(resultSet.getString("COR"));
                veiculo.setMarca(resultSet.getString("MODELO"));
                veiculo.setMotocilcleta(resultSet.getBoolean("MOTOCICLETA"));
                veiculo.setUnidade(resultSet.getString("PREFIXO_UNIDADE"));
                veiculo.setVaga(resultSet.getString("VAGA"));

                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }

        return veiculo;
    }
    
    
    private final String SELECT_ID_CADASTRO = "SELECT IDCADFK FROM DISPOSITIVOS WHERE SERIAL = ?";

    public String getCodicoCadastro(String serial) {
        Connection con = null;
        String cod = "";
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_ID_CADASTRO);
            prepared.setString(1, serial);

            ResultSet resultSet = prepared.executeQuery();

            if (resultSet.next()) {

                cod = resultSet.getString("IDCADFK");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }

        return cod;
    }

    
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
    
    
        private String horaAtual() {

        date = new Date();

        return f.format(date);
    }
        
        
        
        
       

}
