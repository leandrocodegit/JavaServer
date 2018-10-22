/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.perfil;

import br.com.gennis.servidor.Main;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;


/**
 *
 * @author LEANDRO
 */
public class SingletonPerfil {

    private static PerfilSistema perfilSistema = new PerfilSistema();
    private static PeriodoADM periodoADM = new PeriodoADM();

    public static PerfilSistema getPerfilSistema() {

        return perfilSistema;
    }

    public static void setPerfilSistema(PerfilSistema perfilSistema) {
        SingletonPerfil.perfilSistema = perfilSistema;
    }

    public static PeriodoADM getPeriodoADM() {
        return periodoADM;
    }
    
    public static void restar() {

        try {
            Class cls = Main.class;
            ProtectionDomain pDomain = cls.getProtectionDomain();
            CodeSource cSource = pDomain.getCodeSource();
            URL loc = cSource.getLocation();
            
            String comando = "java -jar " + loc.toString().substring(6);
                   
            Process Processo = Runtime.getRuntime().exec(comando);
        } catch (IOException MensagemdeErro) {
            System.out.println(MensagemdeErro);
        }
        System.exit(0);
    }

  

}
