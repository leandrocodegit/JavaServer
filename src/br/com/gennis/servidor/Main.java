/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.servidor;

import br.com.gennis.acura.AntenaTAG;
import br.com.gennis.acura.Device;
import br.com.gennis.db.DBPontoAcesso;
import br.com.gennis.controle.PontoAcesso;
import br.com.gennis.controle.Controle;
import br.com.gennis.controle.ControleGrupo;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.gennis.perfil.DBConfiguracao;
import br.com.gennis.perfil.SingletonPerfil;

/**
 *
 * @author LEANDRO
 */
public class Main {

    public static void main(String[] args) {

        /* too late ! */
        System.setProperty("java.awt.headless", "false");
        /* ---> prints false */

        try {
            // TelaInstance.getServidor().executa();

            //Thread t4 = new Thread(TelaInstance.getServidorModulo());
            //t4.start();
            DBConfiguracao dbConfiguracao = new DBConfiguracao();
            dbConfiguracao.getControleHorario();
            SingletonPerfil.setPerfilSistema(dbConfiguracao.getPerfil());

            //TelaInstanceModulo.getTelaServidor().setVisible(true);
            DBPontoAcesso dbPontoAcesso = new DBPontoAcesso();

            List<PontoAcesso> lista = dbPontoAcesso.getPontos(false);
            List<PontoAcesso> listaLinear = new ArrayList<>();

            Servidor servidor = new Servidor(9988);

            TelaInstance.setServidor(servidor);

            Thread t12 = new Thread(servidor);

            t12.start();

            List<Device> lisDevice = new ArrayList<>();
            ControleGrupo controleGrupo1 = new ControleGrupo();

            for (int i = 0; i < lista.size(); i++) {

                
                if (lista.get(i).getTipo() == 1) {
                    AntenaTAG a1 = new AntenaTAG(lista.get(i),controleGrupo1);

                    lisDevice.add(a1);

                    Thread t5 = new Thread(a1);
                    t5.start();

                }

                if (lista.get(i).getTipo() == 2) {

                    listaLinear.add(lista.get(i));
                    
                    Controle controle = new Controle(true);

                    Cliente a1 = new Cliente(lista.get(i),controle,controleGrupo1);

                    Thread t5 = new Thread(a1);
                    t5.start();

                    continue;

                }

            }

            lista = dbPontoAcesso.getPontos(true);
            System.out.println(lista.size());
            ControleGrupo controleGrupo2 = new ControleGrupo();

            for (int i = 0; i < lista.size(); i++) {

                
                
                int par = lista.get(i).getPar();
                AntenaTAG a1 = null;
                PontoAcesso pontoAcesso1 = lista.get(i);
                Controle controle = new Controle(false);
                controle.setTempoFluxo(pontoAcesso1.getTempoFluxo());

                controle.setP1(pontoAcesso1.getNome());
                a1 = new AntenaTAG(pontoAcesso1, controle,controleGrupo2);
                Thread tPrincipal = new Thread(a1);
                tPrincipal.start();

                for (int j = 0; j < lista.size(); j++) {
                    System.out.println(i);
                    if (lista.get(j).getIdPonto() == par) {

                        PontoAcesso pontoAcesso2 = lista.get(j);
                        a1 = new AntenaTAG(pontoAcesso2, controle,controleGrupo2);
                        Thread tPar = new Thread(a1);
                        tPar.start();

                        controle.setP2(pontoAcesso2.getNome());

                        controle.imprimir("PAREADO ------------------> ");

                        lista.remove(pontoAcesso1);
                        i--;
                        System.out.println("Remove " + pontoAcesso1.getNome());
                        lista.remove(pontoAcesso2);
                        System.out.println("Remove " + pontoAcesso2.getNome());

                        System.out.println("Tamanho da lista = " + lista.size());
                        break;

                    }
                }

                System.out.println("Tamanho da lista final = " + lista.size());

            }

            ServidorGuarita servidorGuarita = new ServidorGuarita(37777, listaLinear);
            Thread t14 = new Thread(servidorGuarita);
            t14.start();

            /*
             PontoAcesso entESQ = new PontoAcesso();
             PontoAcesso entDIR = new PontoAcesso();
             PontoAcesso saiESQ = new PontoAcesso();
             PontoAcesso saiDIR = new PontoAcesso();
             PontoAcesso entMANGA = new PontoAcesso();
             PontoAcesso saiMANGA = new PontoAcesso();

             entMANGA.setNome("ENTRADA MANGABEIRAS");
             entMANGA.setIp("191.17.143.57");
             entMANGA.setPorta(9980);
             entMANGA.setTipo(2);
            
             saiMANGA.setNome("SAÍDA MANGABEIRAS");
             saiMANGA.setIp("191.17.143.57");
             saiMANGA.setPorta(9981);
             saiMANGA.setTipo(2);
            
             AntenaTAG a1 = new AntenaTAG(entMANGA);
             Thread t5 = new Thread(a1);

             t5.start();

             AntenaTAG a2 = new AntenaTAG(saiMANGA);
             Thread t6 = new Thread(a2);

             t6.start();
            
                    
             entDIR.setNome("ENTRADA TUPI DIR");
             entDIR.setIp("191.17.143.57");
             entDIR.setPorta(9982);
             entDIR.setTipo(2);

            

             entESQ.setNome("ENTRADA TUPI ESQ");
             entESQ.setIp("191.17.143.57");
             entESQ.setPorta(9983);
             entESQ.setTipo(2);
            
            
             AntenaTAG a3 = new AntenaTAG(entDIR);
             Thread t7 = new Thread(a3);

             t7.start();

             AntenaTAG a4 = new AntenaTAG(entESQ);
             Thread t8 = new Thread(a4);

             t8.start();
                    
             saiDIR.setNome("SAÍDA TUPI DIR");
             saiDIR.setIp("191.17.143.57");
             saiDIR.setPorta(9984);
             saiDIR.setTipo(2);

            

             saiESQ.setNome("SAÍDA TUPI ESQ");
             saiESQ.setIp("191.17.143.57");
             saiESQ.setPorta(9985);
             saiESQ.setTipo(2);
            

             AntenaTAG a5 = new AntenaTAG(saiDIR);
             Thread t9 = new Thread(a5);

             t9.start();

             AntenaTAG a6 = new AntenaTAG(saiESQ);
             Thread t10 = new Thread(a6);

             t10.start();
             */
            // TelaInstance.getTelaServidor().setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
