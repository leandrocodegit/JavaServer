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
public class PeriodoADM {
    
    
    
  private String uteisInicio;
  private String uteisFim;
  private String sabInicio;
  private String sabFim;
  private boolean diaAutorizado;
  private boolean feriado;
   private boolean controleHorarioADM;

    public String getUteisInicio() {
        return uteisInicio;
    }

    public void setUteisInicio(String uteisInicio) {
        this.uteisInicio = uteisInicio;
    }

    public String getUteisFim() {
        return uteisFim;
    }

    public void setUteisFim(String uteisFim) {
        this.uteisFim = uteisFim;
    }

    public String getSabInicio() {
        return sabInicio;
    }

    public void setSabInicio(String sabInicio) {
        this.sabInicio = sabInicio;
    }

    public String getSabFim() {
        return sabFim;
    }

    public void setSabFim(String sabFim) {
        this.sabFim = sabFim;
    }

    public boolean isDiaAutorizado() {
        return diaAutorizado;
    }

    public void setDiaAutorizado(boolean diaAutorizado) {
        this.diaAutorizado = diaAutorizado;
    }

    public boolean isFeriado() {
        return feriado;
    }

    public void setFeriado(boolean feriado) {
        this.feriado = feriado;
    }

    public boolean isControleHorarioADM() {
        return controleHorarioADM;
    }

    public void setControleHorarioADM(boolean controleHorarioADM) {
        this.controleHorarioADM = controleHorarioADM;
    }
  
  
  
    
}
