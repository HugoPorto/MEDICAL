/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.controller;

import static com.lowagie.text.pdf.PdfFileSpecification.url;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 */
public class Conexao {

    // metodo responsavel por estabelecer  a conexao com o banco infox
    java.sql.Connection conexao = null;

    // metodo responsavel por estabelecer  a conexao com o banco 
    public static Connection conector() {
        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String bd = "consultorio";
        String login = "root";
        String password = "12345";
        String url = "jdbc:mysql://localhost:3306/" + bd + "?serverTimezone=UTC";

        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, login, password);
            return conexao;

        } catch (Exception e) {
            return null; // caso ocorra algum erro de coneccao ele mostrara no console
        }
    }

    public Conexao getConexao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void desconectar() {
        try {
            System.out.println("Fechando Conexao");
            conexao.close();
        } catch (Exception ex) {
        }
    }
}
