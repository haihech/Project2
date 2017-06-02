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
import java.util.ArrayList;

/**
 * 
 * @author haibk
 */
public class Class {

    private String classID;
    private String subjectID;
    private String subjectName;
    private String note;
    private String classType;
    private String status;
    private int maxRegister;
    private int numRegister;
    private String school;
    private String semester;
    private String version;

    public Class() {
    }

    public Class(String classID, String subjectID, String subjectName, String note, String classType, String status, int maxRegister, int numRegister, String school, String semester, String version) {
        this.classID = classID;
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.note = note;
        this.classType = classType;
        this.status = status;
        this.maxRegister = maxRegister;
        this.numRegister = numRegister;
        this.school = school;
        this.semester = semester;
        this.version = version;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaxRegister() {
        return maxRegister;
    }

    public void setMaxRegister(int maxRegister) {
        this.maxRegister = maxRegister;
    }

    public int getNumRegister() {
        return numRegister;
    }

    public void setNumRegister(int numRegister) {
        this.numRegister = numRegister;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void addClass() throws SQLException {
        String query = "insert into list_class values (?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, classID);
        stmt.setString(2, subjectID);
        stmt.setString(3, subjectName);
        stmt.setString(4, note);
        stmt.setString(5, classType);
        stmt.setString(6, status);
        stmt.setInt(7, maxRegister);
        stmt.setInt(8, numRegister);
        stmt.setString(9, school);
        stmt.setString(10, semester);
        stmt.setString(11, version);

        stmt.executeUpdate();
        conn.close();
    }

    public ArrayList<Class> getListClassByVersion(String version) throws SQLException {
        ArrayList<Class> listClass = new ArrayList<>();
        String query = "select * from list_class where Phien_ban ='" + version + "';";
        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String classId = rs.getString("Ma_lop");
            String subjectId = rs.getString("Ma_hp");
            String name = rs.getString("Ten_lop");
            String notes = rs.getString("Ghi_chu");
            String type = rs.getString("Loai_lop");
            String sta = rs.getString("Trang_thai");
            int max = rs.getInt("Max_dk");
            int number = rs.getInt("Da_dk");
            String schoolStr = rs.getString("Vien");
            String semes = rs.getString("Hoc_ky");

            listClass.add(new Class(classId, subjectId, name, notes, type, sta, max, number, schoolStr, semes, version));
        }
        conn.close();

        return listClass;
    }

    public Class getClass(String version, String classID) throws SQLException {

        String query = "select * from list_class where Phien_ban ='" + version + "' and Ma_lop = '" + classID + "';";
        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String subjectId = rs.getString("Ma_hp");
            String name = rs.getString("Ten_lop");
            String notes = rs.getString("Ghi_chu");
            String type = rs.getString("Loai_lop");
            String sta = rs.getString("Trang_thai");
            int max = rs.getInt("Max_dk");
            int number = rs.getInt("Da_dk");
            String schoolStr = rs.getString("Vien");
            String semes = rs.getString("Hoc_ky");

            return new Class(classID, subjectId, name, notes, type, sta, max, number, schoolStr, semes, version);
        }
        conn.close();

        return null;
    }

    public void setClass(ArrayList<String> listInfo, String semester) {
        setClassID(listInfo.get(0));
        setSubjectID(listInfo.get(1));
        setSubjectName(listInfo.get(2));
        setNote(listInfo.get(3));
        setClassType(listInfo.get(4));
        setStatus(listInfo.get(5));
        setMaxRegister(Integer.parseInt(listInfo.get(6)));
        setNumRegister(Integer.parseInt(listInfo.get(7)));
        setSchool(listInfo.get(8));
        setSemester(semester);
        setVersion("3");

    }
    
    public boolean checkClassID(String classID) throws SQLException {
        boolean check = false;
        String query = "select * from list_class where Ma_lop ='" +classID+"' and Phien_ban = '3';";
        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            check = true;
        }
        return check;
    }
    
    public ArrayList<Class> getListClassBySubjectID(String subjectID, String version, String semester) throws SQLException{
        ArrayList<Class> listClass = new ArrayList<>();
        
        String queryStr = "select * from list_class where Ma_hp = '" + subjectID + "' and Phien_ban = '" + version +"' and Hoc_ky = '" +semester+"';";
        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(queryStr);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String classId = rs.getString("Ma_lop");
            String name = rs.getString("Ten_lop");
            String notes = rs.getString("Ghi_chu");
            String type = rs.getString("Loai_lop");
            String sta = rs.getString("Trang_thai");
            int max = rs.getInt("Max_dk");
            int number = rs.getInt("Da_dk");
            String schoolStr = rs.getString("Vien");
            String semes = rs.getString("Hoc_ky");

            listClass.add(new Class(classId, subjectID, name, notes, type, sta, max, number, schoolStr, semes, version));
        }
        conn.close();

        return listClass;
        
    }
    
}
