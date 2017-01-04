package com.microsoft.azuresample.sqlkeyvault.model;

import com.microsoft.azuresample.sqlkeyvault.Utils.SqlServerHelper;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vazvadsk on 2016-12-05.
 */
public class ToDoDAO {
    public List<ToDo> query(){
        List<ToDo> ret = new ArrayList<ToDo>();
        try {
            SQLServerConnection conn = SqlServerHelper.GetConnection();
            try (PreparedStatement selectStatement = conn.prepareStatement(
                    "SELECT Id, Note, MySecretNote FROM ToDo"))
            {
                ResultSet rs = selectStatement.executeQuery();
                while(rs.next()) {
                    ret.add(new ToDo(
                            rs.getInt("Id"),
                            rs.getString("Note"),
                            rs.getString("MySecretNote")
                    ));
                }
                rs.close();
            }finally {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("ERROR: cannot connect to SQL Server.");
            e.printStackTrace();
        }
        return ret;
    }

    public ToDo create(ToDo item){

        try {
            SQLServerConnection conn = SqlServerHelper.GetConnection();
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO ToDo(Note, MySecretNote) VALUES(?,?)"))
            {
                stmt.setString(1, item.getNote());
                stmt.setString(2, item.getMySecretNote());
                stmt.executeUpdate();
            }finally {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("ERROR: cannot connect to SQL Server.");
            e.printStackTrace();
        }

        return item;
    }

    public String test(){
        String ret = "";
        try {
            SQLServerConnection conn = SqlServerHelper.GetConnection();
            try (PreparedStatement selectStatement = conn.prepareStatement(
                    "SELECT Id, Note, MySecretNote FROM ToDo"))
            {
                ResultSet rs = selectStatement.executeQuery();
                while(rs.next()) {
                    ret += rs.getString("Note") + ", " + rs.getString("MySecretNote")+", ";
                }
                rs.close();
            }finally {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("ERROR: cannot connect to SQL Server.");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            ret = sw.toString();
        }
        return ret;
    }
}
