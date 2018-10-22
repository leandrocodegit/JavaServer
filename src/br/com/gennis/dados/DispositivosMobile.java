/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.dados;
/**
 *
 * @author LEANDRO
 */
public class DispositivosMobile {
    
    
    private String localizador;
    private String documento;
    private String id;
    private String nome;
    private String unidade;
    private String tipo;
    private String placa;
    private String senha;    
    private boolean bloqueioPessoa;
    private boolean bloqueioVeiculo;
    private boolean moto;
    private String modeloVeiculo;

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public boolean isMoto() {
        return moto;
    }

    public void setMoto(boolean moto) {
        this.moto = moto;
    }
    
    
    

    public boolean isBloqueioPessoa() {
        return bloqueioPessoa;
    }

    public void setBloqueioPessoa(boolean bloqueioPessoa) {
        this.bloqueioPessoa = bloqueioPessoa;
    }

    public boolean isBloqueioVeiculo() {
        return bloqueioVeiculo;
    }

    public void setBloqueioVeiculo(boolean bloqueioVeiculo) {
        this.bloqueioVeiculo = bloqueioVeiculo;
    }
    
    

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

   
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

 
    
    
    
}
