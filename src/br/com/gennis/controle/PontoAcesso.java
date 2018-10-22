/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.controle;

/**
 *
 * @author ADMIN
 */
public class PontoAcesso {

    private int idPonto;
    private String nome;
    private String ip;
    private int portaTCP;
    private boolean bloqueio;
    private boolean ativo;
    private int sentido;
    private int tipo;
    private int par;
    private boolean fluxo;
    private boolean abrirPorta;
    private boolean associado;
    private boolean conectado;
    private Porta porta;
    private int idDipositivo;
    private long tempoFluxo;
    private boolean grupoFluxo;

    public int getIdPonto() {
        return idPonto;
    }

    public void setIdPonto(int idPonto) {
        this.idPonto = idPonto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPortaTCP() {
        return portaTCP;
    }

    public void setPortaTCP(int portaTCP) {
        this.portaTCP = portaTCP;
    }

    public boolean isBloqueio() {
        return bloqueio;
    }

    public void setBloqueio(boolean bloqueio) {
        this.bloqueio = bloqueio;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getSentido() {
        return sentido;
    }

    public void setSentido(int sentido) {
        this.sentido = sentido;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public boolean isFluxo() {
        return fluxo;
    }

    public void setFluxo(boolean fluxo) {
        this.fluxo = fluxo;
    }

    public boolean isAbrirPorta() {
        return abrirPorta;
    }

    public void setAbrirPorta(boolean abrirPorta) {
        this.abrirPorta = abrirPorta;
    }

    public boolean isAssociado() {
        return associado;
    }

    public void setAssociado(boolean associado) {
        this.associado = associado;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public Porta getPorta() {
        return porta;
    }

    public void setPorta(Porta porta) {
        this.porta = porta;
    }

    public int getIdDipositivo() {
        return idDipositivo;
    }

    public void setIdDipositivo(int idDipositivo) {
        this.idDipositivo = idDipositivo;
    }

    public long getTempoFluxo() {
        return tempoFluxo;
    }

    public void setTempoFluxo(long tempoFluxo) {
        this.tempoFluxo = tempoFluxo;
    }

    public boolean isGrupoFluxo() {
        return grupoFluxo;
    }

    public void setGrupoFluxo(boolean grupoFluxo) {
        this.grupoFluxo = grupoFluxo;
    }
    
    
   
    
    
}
