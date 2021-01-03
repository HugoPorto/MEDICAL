package me.medical.connection;

import java.sql.*;

public class Conectar {

    static String bd = "consultorio";
    static String login = "root";
    static String password = "12345";
    static String url = "jdbc:mysql://localhost:3306/" + bd + "?serverTimezone=UTC";
//    "jdbc:mysql://localhost:3306/" + bd + "?serverTimezone=UTC";

    public static PreparedStatement prepareStatement(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Connection CONEXAO = null;

    public Conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            CONEXAO = DriverManager.getConnection(url, login, password);
            if (CONEXAO != null) {
                System.out.println("Conexao a base de dados " + bd + " OK\n");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

//    public Connection getConnection(){
//        return CONEXAO;
//    }
    public void desconectar() {
        try {
            System.out.println("Fechando Conexao");
            CONEXAO.close();
        } catch (Exception ex) {
        }
    }
}
