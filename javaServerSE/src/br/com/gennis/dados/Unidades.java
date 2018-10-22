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
public class Unidades {
    
    private String unidade;
    private String nome;
    private String predio;   
    private int numeroPessoas;
    private String prefixoUnidade;
    private int tipo;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

        
    public String getPrefixoUnidade() {
        return prefixoUnidade;
    }

    public void setPrefixoUnidade(String prefixoUnidade) {
        this.prefixoUnidade = prefixoUnidade;
    }
    
    

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }

    public void setNumeroPessoas(int numeroPessoas) {
        this.numeroPessoas = numeroPessoas;
    }
    
    
    
}
