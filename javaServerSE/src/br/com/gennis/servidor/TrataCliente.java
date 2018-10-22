/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.servidor;


import br.com.gennis.dados.DBAcessos;
import java.io.InputStream;
import java.util.Scanner;
import br.com.gennis.perfil.SingletonPerfil;

/**
 *
 * @author LEANDRO
 */
public class TrataCliente implements Runnable {

    private InputStream cliente;
    private Servidor servidor;
    private DBAcessos dbAcessos = new DBAcessos();

    public TrataCliente(InputStream cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        // quando chegar uma msg, distribui pra todos
        Scanner s = new Scanner(this.cliente);
        while (s.hasNextLine()) {

            String msg = s.nextLine();
            
            
            
            //Recebe o endereço MAC e IP para validar conexao com servidor;
            if (msg.contains("###66551")) {

                String dados[] = msg.split(";");

                TelaInstance.getTrataSessao().adicionarMAC(dados[1], dados[2]);
                continue;

            }

            //Remove um dispositivo do LINEAR
            if (msg.contains("###110+")) {

                TelaInstance.getServidorGuarita().apagarDispositivo(msg);
                continue;
            }

            if (msg.contains("###002$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###003$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }

            //Adiciona um dipositivo ao LINEAR
            if (msg.contains("###111+")) {

                TelaInstance.getServidorGuarita().cadastrarControle(msg);
                continue;
            }
            //Atualiza receptores do LINEAR
            if (msg.contains("###112+")) {

                TelaInstance.getServidorGuarita().atualizarReceptor();
                continue;
            }

            if (msg.contains("###999@cmd")) {

                
                TelaInstance.getServidorGuarita().importarDispositivos();
                continue;
            }
            if (msg.contains("###172+")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###271$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###272$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###273$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###222$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###223$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###281$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###283$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###284$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }

            if (msg.contains("###285$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###282$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            //Entrada autorizada
            if (msg.contains("###278$")) {

                TelaInstance.getServidor().distribuiMensagem(msg);

                dbAcessos.atualizarSituacao("AUTORIZADO", msg.substring(7, msg.length()));
                TelaInstance.getServidorGuarita().acionarSaida();
                continue;
            }

            if (msg.contains("solentack")) {

                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }

            if (msg.contains("alterack")) {

                String dados[] = msg.split(";");

                dbAcessos.atualizarEntrada(dados[1], dados[2], dados[3]);
                TelaInstance.getServidor().distribuiMensagem(msg);
                continue;
            }
            if (msg.contains("###200")) {

                TelaInstance.getServidorGuarita().acionarSaida();
                continue;
            }
            if (msg.contains("###003$")) {
                

                dbAcessos.atualizarSituacao("CANCELADO", msg.substring(7, msg.length()));
                continue;
            }

            if (msg.contains("###678$")) {

                SingletonPerfil.restar();

                continue;
            }

        }
        s.close();
    }
}
