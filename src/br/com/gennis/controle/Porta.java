/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.controle;

/**
 *
 * @author LEANDRO
 */
public class Porta {

    private int IDPorta;
    private String ponto;
    private String controladora;
    private int idControladora;
    private int rele;
    private int tipo;
    private int idPonto;
    private int tempo;
    private boolean ativo;

    public int getIDPorta() {
        return IDPorta;
    }

    public void setIDPorta(int IDPorta) {
        this.IDPorta = IDPorta;
    }

    public String getPonto() {
        return ponto;
    }

    public void setPonto(String ponto) {
        this.ponto = ponto;
    }

    public String getControladora() {
        return controladora;
    }

    public void setControladora(String controladora) {
        this.controladora = controladora;
    }

    public int getIdControladora() {
        return idControladora;
    }

    public void setIdControladora(int idControladora) {
        this.idControladora = idControladora;
    }

    public int getRele() {
        return rele;
    }

    public void setRele(int rele) {
        this.rele = rele;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getIdPonto() {
        return idPonto;
    }

    public void setIdPonto(int idPonto) {
        this.idPonto = idPonto;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

   
    
    

}
