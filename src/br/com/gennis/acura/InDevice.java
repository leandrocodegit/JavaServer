/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.acura;

import br.com.gennis.controle.Controle;
import br.com.gennis.controle.Fluxo;
import br.com.gennis.controle.PontoAcesso;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface InDevice {

    public void conectar();
    public Socket getSocket();
    public PrintStream getSaida();
    public void setControle(Controle controle);
    public PontoAcesso getPontoAcesso();

}
