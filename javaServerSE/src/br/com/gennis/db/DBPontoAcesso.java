/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.db;


import br.com.gennis.dados.ConexaoDB;
import br.com.gennis.controle.PontoAcesso;
import br.com.gennis.controle.Porta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class DBPontoAcesso extends ConexaoDB{
    
    
private String SELECT_PONTOS = "SELECT * FROM DBLOCALCONTROLE.PONTO_ACESSO PA, DBLOCALCONTROLE.PORTAS P WHERE PA.IDPONTO = P.IDPONTOFK "
        + "AND PA.ATIVO = 1 AND PA.FLUXO = ? GROUP BY PA.IDPONTO ";
    private List<PontoAcesso> listaPontos = new ArrayList();

    public List<PontoAcesso> getPontos(boolean isFluxo) {
        Connection con = null;

        listaPontos.removeAll(listaPontos);
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_PONTOS);
            prepared.setBoolean(1, isFluxo);
            ResultSet resultSet = prepared.executeQuery();

            while (resultSet.next()) {

                PontoAcesso pontoAcesso = new PontoAcesso();
                Porta porta = new Porta();

                pontoAcesso.setIdPonto(resultSet.getInt("IDPONTO"));
                pontoAcesso.setIdDipositivo(resultSet.getInt("IDDISPOSITIVO"));
                pontoAcesso.setTipo(resultSet.getInt("TIPO"));
                pontoAcesso.setSentido(resultSet.getInt("SENTIDO"));
                pontoAcesso.setPar(resultSet.getInt("PAR"));
                pontoAcesso.setPortaTCP(resultSet.getInt("PORTATCP"));
                pontoAcesso.setNome(resultSet.getString("NOME"));
                pontoAcesso.setIp(resultSet.getString("IP"));
                pontoAcesso.setAtivo(resultSet.getBoolean("ATIVO"));
                pontoAcesso.setFluxo(resultSet.getBoolean("FLUXO"));
                pontoAcesso.setAbrirPorta(resultSet.getBoolean("ABREPORTA"));
                pontoAcesso.setTempoFluxo(resultSet.getLong("TEMPOFLUXO"));
                pontoAcesso.setGrupoFluxo(resultSet.getBoolean("GRUPO_FLUXO"));
               // pontoAcesso.setTempoFluxo(resultSet.getLong("TEMPOGRUPO"));

                porta.setIdPonto(resultSet.getInt("IDPONTO"));
                porta.setIdControladora(resultSet.getInt("IDCNTRLFK"));
                porta.setRele(resultSet.getInt("RELE"));
                porta.setControladora(resultSet.getString("CONTROLADORA"));
                porta.setTipo(resultSet.getInt("TIPO"));
                porta.setTempo(resultSet.getInt("TEMPO"));
                porta.setAtivo(resultSet.getBoolean("ATIVO"));

                pontoAcesso.setPorta(porta);

                listaPontos.add(pontoAcesso);

            }
        } catch (Exception e) {
            System.out.println("Erro 174");
            System.out.println(e);
        } finally {
            closeConnnection(con);
        }

        return listaPontos;
    }
     
     
     private String VERIFICA_DISPOSITIVO_ATIVO = "SELECT ATIVO FROM PONTO_ACESSO WHERE IP = ?";
     
      public boolean isDispositivoAtivo(String ip) {
        Connection con = null;

        boolean ativo = false;
        
        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(VERIFICA_DISPOSITIVO_ATIVO);
            prepared.setString(1, ip);
            ResultSet resultSet = prepared.executeQuery();
            

            if (resultSet.next()) {

              ativo = resultSet.getBoolean("ATIVO");
                
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
