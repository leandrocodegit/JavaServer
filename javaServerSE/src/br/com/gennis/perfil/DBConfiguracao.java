/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.perfil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author LEANDRO
 */
public class DBConfiguracao extends br.com.gennis.dados.ConexaoDB {

    private String SELECT_CONF = "SELECT * FROM CONFIGURACAO";
    private PerfilSistema perfilSistema = new PerfilSistema();
    

    public PerfilSistema getPerfil() {

        Connection con = null;

        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_CONF);

            ResultSet resultSet = prepared.executeQuery();

            if (resultSet.next()) {

                perfilSistema.setPortaTCP(resultSet.getInt("PORTATCP"));
                perfilSistema.setPortaTCPModulo(resultSet.getInt("PORTA_MODULO"));
                perfilSistema.setIpModulo(resultSet.getString("IP_MODULO"));

            }

        } catch (SQLException e) {
            System.out.println("Crachas 217");
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }

        return perfilSistema;
    }

    private String ATUALIZA_REDE = "UPDATE CONFIGURACAO SET IP = ?, PORTATCP = ?";

    public void atualizarConfRede(PerfilSistema perfilSistema) {

        Connection con = null;
        PreparedStatement prepared;

        try {

            con = getConnection();
            prepared = con.prepareStatement(ATUALIZA_REDE);

            prepared.setString(1, perfilSistema.getIp());
            prepared.setInt(2, perfilSistema.getPortaTCP());

            prepared.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Crachas 248");
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }

    }
    private String ATUALIZA_CONF = "UPDATE CONFIGURACAO SET  ENTRADA_SAIDA = ? , MODO_EVENTO = ? , TEMPO_FUNCIONARIO = ?, "
            + "SITUACAO_UNIDADE = ?, TEMPO_CRACHA = ?,SAIDA_VISITANTE = ?";

    public void atualizarConfiguracao(PerfilSistema perfilSistema) {

        Connection con = null;
        PreparedStatement prepared;

        try {

            con = getConnection();
            prepared = con.prepareStatement(ATUALIZA_CONF);

            prepared.setBoolean(1, perfilSistema.isEntradaSaida());
            prepared.setBoolean(2, perfilSistema.isModoEvento());
            prepared.setInt(3, perfilSistema.getTempoFuncionario());
            prepared.setBoolean(4, perfilSistema.isSituacaoUnidade());
            prepared.setInt(5, perfilSistema.getTempoCracha());
            prepared.setBoolean(6, perfilSistema.isSaidaVisitante());

            prepared.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Crachas 248");
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }

    }

    private String SELECT_PERIODO_ADM = "SELECT * FROM CONFIGURACAO";
    private PeriodoADM periodoADM = new PeriodoADM();

    public PerfilSistema getAcessoADM() {

        Connection con = null;

        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_CONF);

            ResultSet resultSet = prepared.executeQuery();

            if (resultSet.next()) {

                /*
                 periodoADM.setModoEvento(resultSet.getBoolean("MODO_EVENTO"));
                 periodoADM.setEntradaSaida(resultSet.getBoolean("ENTRADA_SAIDA"));
                 periodoADM.setTempoFuncionario(resultSet.getInt("TEMPO_FUNCIONARIO"));
                 periodoADM.setTempoCracha(resultSet.getInt("TEMPO_CRACHA"));
                 */
            }

        } catch (SQLException e) {
            System.out.println("Crachas 217");
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }

        return perfilSistema;
    }

    private String ATUALIZA_PERIODO_ADM = "UPDATE PERIODO_ADM SET  UTEIS_INICIO = ? , UTEIS_FIM = ? , SAB_INICIO = ?, "
            + "SAB_FIM = ?, CONTROLE_HORARIO = ?";

    public void atualizarPeriodoADM(PeriodoADM PeriodoADM) {

        Connection con = null;
        PreparedStatement prepared;

        try {

            con = getConnection();
            prepared = con.prepareStatement(ATUALIZA_PERIODO_ADM);

            prepared.setString(1, PeriodoADM.getUteisInicio());
            prepared.setString(2, PeriodoADM.getUteisFim());
            prepared.setString(3, PeriodoADM.getSabInicio());
            prepared.setString(4, PeriodoADM.getSabFim());
            prepared.setBoolean(5, PeriodoADM.isControleHorarioADM());

            prepared.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Crachas 248");
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }

    }

    private final String SELECT_CONTROLE_HORARIO = "SELECT UTEIS_INICIO,UTEIS_FIM,SAB_INICIO,SAB_FIM,CONTROLE_HORARIO, (UTEIS_INICIO <= current_time()) AS UI,\n"
            + "         (UTEIS_FIM    >= current_time())  AS UF,\n"
            + "          (SAB_INICIO <= current_time()) AS SI,\n"
            + "         (SAB_FIM    >= current_time())  AS SF, (SELECT count(ID)  AS MES FROM feriados WHERE DIA = EXTRACT(DAY FROM CURDATE()) \n"
            + "          AND MES = EXTRACT(MONTH FROM CURDATE())) AS FERIADO     \n"
            + "            FROM PERIODO_ADM WHERE ID = 1";

    public void getControleHorario() {
        Connection con = null;

        Calendar c = Calendar.getInstance();
        int diaHoje = c.get(Calendar.DAY_OF_WEEK);

        try {
            con = getConnection();
            PreparedStatement prepared = con.prepareStatement(SELECT_CONTROLE_HORARIO);
            ResultSet resultSet = prepared.executeQuery();

            if (resultSet.next()) {

                SingletonPerfil.getPeriodoADM().setUteisInicio(resultSet.getString("UTEIS_INICIO"));
                SingletonPerfil.getPeriodoADM().setUteisFim(resultSet.getString("UTEIS_FIM"));
                SingletonPerfil.getPeriodoADM().setSabInicio(resultSet.getString("SAB_INICIO"));
                SingletonPerfil.getPeriodoADM().setSabFim(resultSet.getString("SAB_FIM"));
                SingletonPerfil.getPeriodoADM().setControleHorarioADM(resultSet.getBoolean("CONTROLE_HORARIO"));
                SingletonPerfil.getPeriodoADM().setFeriado(resultSet.getBoolean("FERIADO"));

                boolean ui = resultSet.getBoolean("UI");
                boolean uf = resultSet.getBoolean("UF");
                boolean si = resultSet.getBoolean("SI");
                boolean sf = resultSet.getBoolean("SF");

                if (ui && uf) {
                    SingletonPerfil.getPeriodoADM().setDiaAutorizado(true);
                } else {
                    SingletonPerfil.getPeriodoADM().setDiaAutorizado(false);
                }
                

                if (diaHoje == 7) {

                    if (si && sf) {
                        SingletonPerfil.getPeriodoADM().setDiaAutorizado(true);
                    } else {
                        SingletonPerfil.getPeriodoADM().setDiaAutorizado(false);
                    }

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnnection(con);
        }

    }
    
    public static void main(String[] args) {
        
        System.out.println(SingletonPerfil.getPerfilSistema().getIp());
        System.out.println(SingletonPerfil.getPerfilSistema().getIpModulo());
        
    }

}
