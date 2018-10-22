/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.controle;

import br.com.gennis.acura.Device;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leandro Code
 */
public class Controle {

    private Device pontoAcesso1;
    private Device pontoAcesso2;
    private Map<String, String> entrada = new HashMap<String, String>();
    private List<Fluxo> acesso = new LinkedList<>();
    private String p1;
    private String p2;
    private long tempoFluxo;
    private boolean controleUnico;

    public Controle(boolean controleUnico) {
        this.controleUnico = controleUnico;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public long getTempoFluxo() {
        return tempoFluxo;
    }

    public void setTempoFluxo(long tempoFluxo) {
        this.tempoFluxo = tempoFluxo;
    }

    public void imprimir(String tex) {
      //  System.out.println(tex + "   -------> " + p1 + " + " + p2);
    }

    synchronized public boolean entrada(String serial,long tempoEvento) {

        Fluxo fluxo = null;

        boolean registra = false;
        
        System.out.println("-------------> Verificando controle          ++++++++++++++++");

        Calendar c = Calendar.getInstance();
        long tempo = c.getTimeInMillis();

        if (acesso.isEmpty()) {
            fluxo = new Fluxo();

            fluxo.setSerial(serial);
            fluxo.setEntrada(tempo);

          //  System.out.println("+++++++  Novo fluxo init  " + fluxo.getEntrada() + " " + serial);
            acesso.add(fluxo);
            registra = true;

        } else {

            boolean adicionar = true;

            for (int i = 0; i < acesso.size(); i++) {

                if (acesso.get(i).getSerial().contains(serial)) {

                 //   System.out.println("+++++++  Fluxo detectado " + serial + " " + registra);
                    registra = verificaFluxo(acesso.get(i),tempoEvento);
                    adicionar = false;
                    break;

                }

            }

            if (adicionar) {
                //acesso.removeAll(acesso);
                fluxo = new Fluxo();

                fluxo.setSerial(serial);
                fluxo.setEntrada(tempo);

                registra = true;
              //  System.out.println("HORA 1 = " + c.getTime());
              //  System.out.println("++++++++  Novo fluxo  " + fluxo.getEntrada() + " " + serial);
              //  System.out.println("Tamanho da lista = " + acesso.size());
                limparLista(tempo);
                acesso.add(fluxo);

            }
        }
        
        System.out.println("-------------> Verificando controle          ++++++++++++++++ " + registra);

        return registra;
    }

    private boolean verificaFluxo(Fluxo fluxo,long tempoEvento) {

        boolean valida = false;
        Calendar c = Calendar.getInstance();
        long tempo = c.getTimeInMillis();

       // System.out.println("Tempo estimado = " + (tempo - fluxo.getEntrada()));

        if (tempo - fluxo.getEntrada() < tempoEvento) {
            valida = false;

        } else {

          //  System.out.println("HORA 2 = " + c.getTime());
          //  System.out.println("+++++++  Atualiza fluxo  " + fluxo.getEntrada() + " " + fluxo.getSerial());
            valida = true;

            if (controleUnico) {

                fluxo.setEntrada(tempo);

            }

        }

        if (!controleUnico) {

            fluxo.setEntrada(tempo);

        }

        return valida;
    }

    private void limparLista(long tempo) {

        if (acesso.size() > 5) {
            if (tempo - acesso.get(acesso.size() - 3).getEntrada() > 20000 && tempo - acesso.get(acesso.size() - 2).getEntrada() > 20000 && tempo - acesso.get(acesso.size() - 1).getEntrada() > 20000);

          //  System.out.println("T1 = " + (tempo - (acesso.size() - 2)) / 100);
          //  System.out.println("T2 = " + (tempo - (acesso.size() - 3) / 100));
         //   System.out.println("T3 = " + (tempo - (acesso.size() - 4)) / 100);

            if (tempo - acesso.get(0).getEntrada() > 20000) {

                acesso.removeAll(acesso);

            }

        }

    }

}
