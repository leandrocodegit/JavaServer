/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gennis.perfil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfSQL {

    
    
     public void salvar (String ip)
    {
        
        //String endereco = JOptionPane.showInputDialog ("Digite o endereco do arquivos");
        //arquivo para ser lido
        File arquivo1 = new File ("F:\\Portaria4\\bin\\conf.txt");//cria o file do arquivo informado
        //arquivo para escrita
        
       
        
        try
        {
            arquivo1.createNewFile ();//arquivo criado
           
           /*ESCREVER*/
            FileWriter fileW = new FileWriter (arquivo1);//arquivo para escrita
            BufferedWriter buffW = new BufferedWriter (fileW);
            //enquanto tiver leitura
            
                
               
                    buffW.write (ip);//Leia um arquivo e Escreva no outro
                   
            
           
            buffW.close ();
        } catch (IOException io)
        {
        }
    }
     
     
     
}
