/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author haibk
 */
public class ConnectToDB {

    public static Connection conn = null;

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "abcdef");
           
        } catch (SQLException e) {
            System.out.println("Ket noi bi loi");
        }
        return conn;
    }

    public void closeDB() {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
            }
        }

    }

}
