/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.acura;

import br.com.gennis.controle.Controle;
import br.com.gennis.controle.ControleGrupo;
import br.com.gennis.dados.Acesso;
import br.com.gennis.dados.Cadastros;
import br.com.gennis.dados.DBAcessos;
import br.com.gennis.dados.DBCadastro;
import br.com.gennis.controle.PontoAcesso;
import br.com.gennis.servidor.TelaInstance;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class RecebedorTAG implements Runnable {

    private InputStream cliente;
    public static Socket socketLinear;
    private Cadastros cadastros = new Cadastros();
    PontoAcesso pontoAcesso;
    private DBCadastro dBCadastro = new DBCadastro();
    private Controle controle;
    private ControleGrupo controleGrupo;

    private DBAcessos dbAcessos = new DBAcessos();
    private Acesso acesso = new Acesso();

    public RecebedorTAG(InputStream cliente, PontoAcesso pontoAcesso, Controle controle, ControleGrupo controleGrupo) {
        this.cliente = cliente;
        this.pontoAcesso = pontoAcesso;
        this.controle = controle;
        this.controleGrupo = controleGrupo;

    }

    public static int convert(int n) {
        return Integer.parseInt(String.valueOf(n), 10);
    }

    @Override
    public void run() {

        Scanner s = new Scanner(this.cliente);

        // recebe msgs do servidor e imprime na tela
        while (s.hasNextLine()) {

            //System.out.println(s.nextLine());
            String serial = s.nextLine();

            try {

                if (serial.length() > 20) {

                    System.out.println("");
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++ Nova chamada  +++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("ID TAG1 - " + serial);

                    if (pontoAcesso.isFluxo() || pontoAcesso.isGrupoFluxo()) {

                        boolean registra = controle.entrada(serial, pontoAcesso.getTempoFluxo());
                        boolean registraGupo = controleGrupo.entrada(serial, pontoAcesso.getTempoFluxo());
                        if (registra || pontoAcesso.isGrupoFluxo()) {

                            if (pontoAcesso.isGrupoFluxo()) {

                                if (registraGupo) {

                                    if (!pontoAcesso.isFluxo() || registra) {
                                        TelaInstance.getServidor().distribuiMensagem("###000$;" + pontoAcesso.getNome() + ";" + pontoAcesso.getTipo() + ";"
                                                + pontoAcesso.getIdPonto() + ";" + serial.substring(2) + ";" + serial + ";0;");
                                        System.out.println("-------------> Autorizado pelo grupo");
                                    }
                                } else {
                                    System.out.println("-------------> Não autorizado pelo grupo");
                                }

                            } else {

                                System.out.println("-------------> Registra " + registra + " --------------->   " + pontoAcesso.getNome());
                                System.out.println("-------------> Autorizado pelo pareamento");

                                TelaInstance.getServidor().distribuiMensagem("###000$;" + pontoAcesso.getNome() + ";" + pontoAcesso.getTipo() + ";"
                                        + pontoAcesso.getIdPonto() + ";" + serial.substring(2) + ";" + serial + ";0;");
                            }

                        }

                        controle.imprimir(pontoAcesso.getNome());

                        System.out.println("++++++++++++++++++++++++++++++++++++++++++ Fim chamada  ++++++++++++++++++++++++++++++++++++++++++++");
                        System.out.println("");

                    }
                }

                // System.out.println("ID TAG2 - " + controle.toString());
                //System.out.println("Res " + bufResp[0]);
            } catch (Exception ex) {

                // Cliente.conectado = false;
                System.out.println(ex);
            }

            // unidade = unidade + blocos[bufResp[0]];
        }
    }

}
