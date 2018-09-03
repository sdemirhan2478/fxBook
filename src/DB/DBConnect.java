/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class DBConnect implements Serializable { 
    Connection conn;
    PreparedStatement pStmt; 

    public DBConnect () {
 
    }      

    public Connection getConn() { 
         try {
            Class.forName("org.sqlite.JDBC");
            this.conn =  DriverManager.getConnection("jdbc:sqlite:fxbook.s3db");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.conn;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PreparedStatement preparestmt (String pstmt) {
        try {
            return conn.prepareStatement(pstmt);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}           