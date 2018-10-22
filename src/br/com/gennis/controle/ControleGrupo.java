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
public class ControleGrupo {

    private Device pontoAcesso1;
    private Device pontoAcesso2;
    private Map<String, String> entrada = new HashMap<String, String>();
    private List<Fluxo> acesso = new LinkedList<>();
    private long tempoFluxo;

    public ControleGrupo() {

    }

    public long getTempoFluxo() {
        return tempoFluxo;
    }

    public void setTempoFluxo(long tempoFluxo) {
        this.tempoFluxo = tempoFluxo;
    }

    synchronized public boolean entrada(String serial, long tempoEvento) {

        Fluxo fluxo = null;

        boolean registra = false;
        
        

        Calendar c = Calendar.getInstance();
        long tempo = c.getTimeInMillis();

        if (acesso.isEmpty()) {
            fluxo = new Fluxo();

            fluxo.setSerial(serial);
            fluxo.setEntrada(tempo);

           // System.out.println("+++++++  Novo fluxo init  " + fluxo.getEntrada() + " " + serial);
            acesso.add(fluxo);
            registra = true;

        } else {

            boolean adicionar = true;

            for (int i = 0; i < acesso.size(); i++) {

                if (acesso.get(i).getSerial().contains(serial)) {

                   // System.out.println("+++++++  Fluxo detectado " + serial + " " + registra);
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
               // System.out.println("HORA 1 = " + c.getTime());
              //  System.out.println("++++++++  Novo fluxo  " + fluxo.getEntrada() + " " + serial);
              //  System.out.println("Tamanho da lista = " + acesso.size());
                limparLista(tempo);
                acesso.add(fluxo);

            }
        }
        System.out.println("-------------> Verificando grupo             ++++++++++++++++ " + registra);

        return registra;
    }

    private boolean verificaFluxo(Fluxo fluxo,long tempoEvento) {

        boolean valida = false;
        Calendar c = Calendar.getInstance();
        long tempo = c.getTimeInMillis();

      //  System.out.println("Tempo estimado = " + (tempo - fluxo.getEntrada()));

        if (tempo - fluxo.getEntrada() < tempoEvento) {
            valida = false;

        } else {

          //  System.out.println("HORA 2 = " + c.getTime());
          //  System.out.println("+++++++  Atualiza fluxo  " + fluxo.getEntrada() + " " + fluxo.getSerial());
            valida = true;

        }

        fluxo.setEntrada(tempo);

        return valida;
    }

    private void limparLista(long tempo) {

        if (acesso.size() > 5) {
            if (tempo - acesso.get(acesso.size() - 3).getEntrada() > 20000 && tempo - acesso.get(acesso.size() - 2).getEntrada() > 20000 && tempo - acesso.get(acesso.size() - 1).getEntrada() > 20000);

          //  System.out.println("T1 = " + (tempo - (acesso.size() - 2)) / 100);
          //  System.out.println("T2 = " + (tempo - (acesso.size() - 3) / 100));
          //  System.out.println("T3 = " + (tempo - (acesso.size() - 4)) / 100);

            if (tempo - acesso.get(0).getEntrada() > 20000) {

                acesso.removeAll(acesso);

            }

        }

    }

}
