/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Breke
 *
 */
public class UpperCaseField extends PlainDocument {

    private int iMaxLength;

    public UpperCaseField(int tamanho) {
        super();
        iMaxLength = tamanho;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

        if (str == null) {
            return;
        }

        if (iMaxLength <= 0) { // aceitará qualquer nº de caracteres
            super.insertString(offs, str, a);
            return;
        }

        int ilen = (getLength() + str.length());
        char[] upper = str.toCharArray();

        for (int i = 0; i < upper.length; i++) {
            upper[i] = Character.toUpperCase(upper[i]);
        }

        if (ilen <= iMaxLength) { 
            super.insertString(offs, new String(upper), a); 
        }
        
    }
}
