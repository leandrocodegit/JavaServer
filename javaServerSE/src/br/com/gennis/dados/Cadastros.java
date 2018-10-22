/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.dados;

import java.util.List;



/**
 *
 * @author LEANDRO
 */
public class Cadastros {

    private String documento;   
    private String localizador;
    private String nome;
    private String unidadeAcessada;
    private String vinculo;
    private String unidade;
    private String tipo;
    private int codigo;
    private String placa;
    private String modeloVeiculo;
    private boolean bloqueioPessoa;
    private boolean entrada;
    private boolean avisos;
    private boolean permiteVisitas;
    private boolean bloqueioVeiculo;
    private boolean todosVeiculos; 
    private boolean veiculoExterno;
    private boolean salvaDados;
    private boolean grupo;
    private String prefixoUnidade;    
    private String ultimaVisita;
    private String empresa;
    private String funcao;
    private String ct1 = "";
    private String ct2 = "";
    private byte [] foto;
    private Veiculo veiculo;
    private Unidades unidades;
    private Torre torre;
    private List<String> grupoUnidades;

    public List<String> getGrupoUnidades() {
        return grupoUnidades;
    }

    public void setGrupoUnidades(List<String> grupoUnidades) {
        this.grupoUnidades = grupoUnidades;
    }
    
    public Torre getTorre() {
        return torre;
    }

    public void setTorre(Torre torre) {
        this.torre = torre;
    }

    public boolean isGrupo() {
        return grupo;
    }

    public void setGrupo(boolean grupo) {
        this.grupo = grupo;
    }

    

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    
    
    public Unidades getUnidades() {
        return unidades;
    }

    public void setUnidades(Unidades unidades) {
        this.unidades = unidades;
    }

    public String getUnidadeAcessada() {
        return unidadeAcessada;
    }

    public void setUnidadeAcessada(String unidadeAcessada) {
        this.unidadeAcessada = unidadeAcessada;
    }
    

    

    public boolean isSalvaDados() {
        return salvaDados;
    }

    public void setSalvaDados(boolean salvaDados) {
        this.salvaDados = salvaDados;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }

    public boolean isVeiculoExterno() {
        return veiculoExterno;
    }

    public void setVeiculoExterno(boolean veiculoExterno) {
        this.veiculoExterno = veiculoExterno;
    }

    
    
    
    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public boolean isPermiteVisitas() {
        return permiteVisitas;
    }

    public void setPermiteVisitas(boolean permiteVisitas) {
        this.permiteVisitas = permiteVisitas;
    }

    public boolean isAvisos() {
        return avisos;
    }

    public void setAvisos(boolean avisos) {
        this.avisos = avisos;
    }

    
    
    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    
    
    
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
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
    

    public boolean isTodosVeiculos() {
        return todosVeiculos;
    }

    public void setTodosVeiculos(boolean todosVeiculos) {
        this.todosVeiculos = todosVeiculos;
    }

    public String getPrefixoUnidade() {
        return prefixoUnidade;
    }

    public void setPrefixoUnidade(String prefixoUnidade) {
        this.prefixoUnidade = prefixoUnidade;
    }

    public String getUltimaVisita() {
        return ultimaVisita;
    }

    public void setUltimaVisita(String ultimaVisita) {
        this.ultimaVisita = ultimaVisita;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getCt1() {
        return ct1;
    }

    public void setCt1(String ct1) {
        this.ct1 = ct1;
    }

    public String getCt2() {
        return ct2;
    }

    public void setCt2(String ct2) {
        this.ct2 = ct2;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    
   
   
}
