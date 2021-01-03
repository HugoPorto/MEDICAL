package me.medical.utils;

import java.awt.Color;


public class ValidaEmail {
    
    public boolean emailValido(String texto){
        if ((texto.contains("@")) &&
               (texto.contains(".")) &&
               (!texto.contains(" "))) {
 
                    String usuario = new String(texto.substring(0,
                    texto.lastIndexOf('@')));
 
                    String dominio = new String(texto.substring(texto.lastIndexOf
                    ('@') + 1, texto.length()));
 
                    if ((usuario.length() >=1) && (!usuario.contains("@")) &&
                    (dominio.contains(".")) && (!dominio.contains("@")) && (dominio.indexOf(".") >=
                    1) && (dominio.lastIndexOf(".") < dominio.length() - 1)) {
                        
                        return true;
 
                    } 
                    else {
 
                         return false;
                
 
                    }
 
         } 
         else {
            
            return false;       
         }
       
   }
}