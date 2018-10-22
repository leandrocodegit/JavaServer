/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.dados;

import java.io.PrintStream;
import java.net.Socket;


public class Conexoes {
    
    private String tipo;
    private String idConexao;
    private String enderecoIP;
    private String portaTCP;
    private PrintStream printStream;
    private Socket socket;
    
      public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdConexao() {
        return idConexao;
    }

    public void setIdConexao(String idConexao) {
        this.idConexao = idConexao;
    }

    public String getEnderecoIP() {
        return enderecoIP;
    }

    public void setEnderecoIP(String enderecoIp) {
        this.enderecoIP = enderecoIp;
    }

    public String getPortaTCP() {
        return portaTCP;
    }

    public void setPortaTCP(String portaTCP) {
        this.portaTCP = portaTCP;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    
    
}
