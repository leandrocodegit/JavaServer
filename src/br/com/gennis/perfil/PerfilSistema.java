/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.perfil;

/**
 *
 * @author LEANDRO
 */
public class PerfilSistema {
    
    
    private boolean modoEvento = false;
    private boolean situacaoUnidade = false;
    private boolean entradaSaida = false;
    private boolean saidaVisitante = false;
    private int tempoFuncionario;
    private int tempoCracha;
    private int portaTCP;
    private String ip;
    private int portaTCPModulo;
    private String ipModulo;
    private static String ipSQL;

    public int getPortaTCPModulo() {
        return portaTCPModulo;
    }

    public void setPortaTCPModulo(int portaTCPModulo) {
        this.portaTCPModulo = portaTCPModulo;
    }

    public String getIpModulo() {
        return ipModulo;
    }

    public void setIpModulo(String ipModulo) {
        this.ipModulo = ipModulo;
    }
    
    public String getIpSQL() {
        return ipSQL;
    }

    public void setIpSQL(String ipSQL) {
        this.ipSQL = ipSQL;
    }

    public boolean isSaidaVisitante() {
        return saidaVisitante;
    }

    public void setSaidaVisitante(boolean saidaVisitante) {
        this.saidaVisitante = saidaVisitante;
    }
    
      

    public int getPortaTCP() {
        return portaTCP;
    }

    public void setPortaTCP(int portaTCP) {
        this.portaTCP = portaTCP;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    

    public boolean isSituacaoUnidade() {
        return situacaoUnidade;
    }

    public void setSituacaoUnidade(boolean situacaoUnidade) {
        this.situacaoUnidade = situacaoUnidade;
    }

    
    public boolean isModoEvento() {
        return modoEvento;
    }

    public void setModoEvento(boolean modoEvento) {
        this.modoEvento = modoEvento;
    }

    public boolean isEntradaSaida() {
        return entradaSaida;
    }

    public void setEntradaSaida(boolean entradaSaida) {
        this.entradaSaida = entradaSaida;
    }

    public int getTempoFuncionario() {
        return tempoFuncionario;
    }

    public void setTempoFuncionario(int tempoFuncionario) {
        this.tempoFuncionario = tempoFuncionario;
    }

    public int getTempoCracha() {
        return tempoCracha;
    }

    public void setTempoCracha(int tempoCracha) {
        this.tempoCracha = tempoCracha;
    }

   
    
    
    
}
