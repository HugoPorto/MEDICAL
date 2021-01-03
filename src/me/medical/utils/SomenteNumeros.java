
package me.medical.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.text.BadLocationException;

public class SomenteNumeros extends PlainDocument{

 //classe que permite a inclusão de apenas numeros em um jTextField
@Override
public void insertString(int offset,String str, javax.swing.text.AttributeSet attr)
throws BadLocationException{        
super.insertString(offset, str.toUpperCase().replaceAll("[^0-9|^ |^,.]",""), attr);
}
public void replace(int offset,String str, me.medical.utils.AtributeSet attr)
throws BadLocationException{
super.insertString(offset, str.toUpperCase().replaceAll("[^0-9|^ |^,.]",""), (AttributeSet) attr);
}
}