/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.connection;

import java.sql.*;

/**
 *
 * @author Folha
 */
public class Main {

    static String bd = "consultorio";
    static String login = "root";
    static String password = "12345";
    static String url = "jdbc:mysql://localhost:3306/"+bd+"?serverTimezone=UTC";
    
    public Connection conexao = null;
    
    public static void main(String[] args) throws SQLException {
        
        String className = "com.mysql.cj.jdbc.Driver";

        try {
            
            Class.forName(className);
             if (DriverManager.getConnection(url,login,password) != null){
                System.out.println("Conexao a base de dados "+bd+" OK\n");
             }
        } catch (ClassNotFoundException ex) {
            System.out.println("Falhou!");
        }
    }
}
