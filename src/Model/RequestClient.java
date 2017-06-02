/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haibk
 */
public class RequestClient {

    String requestClient;
    
    public  RequestClient(){}
    
    public RequestClient(String requestClient) {
        this.requestClient = requestClient;
    }
    
    public String changeRequestClient() {
        String[] requestArr = requestClient.split(" ");
        int size = requestArr.length;
        if (size == 3) {
            String str = " - Yêu cầu: Lấy dữ liệu của lớp có mã lớp = " + requestArr[1] +"\n";
            if (requestArr[2].compareTo("1") == 0) {
                return str + " ở thời điểm bắt đầu đăng ký";
            }
            if (requestArr[2].compareTo("2") == 0) {
                return str + " ở thời điểm điều chỉnh đăng ký";
            } else {
                return str + " ở thời điểm kết thúc đăng ký";
            }

        } else if (size == 4) {
            String str = " - Yêu cầu : Lấy dữ liệu các lớp của môn học có mã = " + requestArr[1] +"\n";
            if (requestArr[2].compareTo("1") == 0) {
                return str + " ở thời điểm bắt đầu đăng ký của học kỳ " + requestArr[3];
            }
            if (requestArr[2].compareTo("2") == 0) {
                return str + " ở thời điểm điều chỉnh đăng ký của học kỳ " + requestArr[3];
            } else {
                return str + " ở thời điểm kết thúc đăng ký của học kỳ " + requestArr[3];
            }
        }
        return "Không có yêu cầu";
    }
    
    public void addRequest() {
        try {
            String query = "insert into list_request(Yeu_cau, Trang_thai) values (?,?)";
            Connection conn = ConnectToDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, changeRequestClient());
            stmt.setString(2, "Đã hoàn thành");
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RequestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getRequest(){
        String reqClient = null;
        try {
            
            String query = "SELECT Yeu_cau, Trang_thai FROM project2.list_request where id in (select max(id) from list_request);";
            Connection conn = ConnectToDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String request = rs.getString("Yeu_cau");
                String status = rs.getString("Trang_thai");
                reqClient = request +"\n        * "+ status;
            }
            
            conn.close();
           
        } catch (SQLException ex) {
            Logger.getLogger(RequestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
         return reqClient;
    }
    
    public int getMaxIDRequest(){
        int max = 0;
        try {
            String query = "select max(id) as id from list_request;";
            Connection conn = ConnectToDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                max = rs.getInt("id");
            }
            conn.close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(RequestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return max;
    }
}
