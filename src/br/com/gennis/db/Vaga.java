/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.db;

/**
 *
 * @author ADMIN
 */
public class Vaga {
    
   private String vaga;
   private String unidade;
   private String torre;
   private boolean ocupada;
   private String dataAcesso;
   private String idcadFK;

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public String getDataAcesso() {
        return dataAcesso;
    }

    public void setDataAcesso(String dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

    public String getIdcadFK() {
        return idcadFK;
    }

    public void setIdcadFK(String idcadFK) {
        this.idcadFK = idcadFK;
    }
   
   
   
    
    
}
