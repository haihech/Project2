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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haibk
 */
public class Student {
    private String classID;
    private String studentID;
    private String name;
    private String className;
    private String status;
    private String version;
    
    public Student(){
        
    }

    public Student(String classID, String studentID, String name, String className, String status, String version) {
        this.classID = classID;
        this.studentID = studentID;
        this.name = name;
        this.className = className;
        this.status = status;
        this.version = version;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public void addStudent() {
        try {
            String query = "insert into list_student values (?,?,?,?,?,?)";
            Connection conn = ConnectToDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, classID);
            stmt.setString(2, studentID);
            stmt.setString(3, name);
            stmt.setString(4, className);
            stmt.setString(5, status);
            stmt.setString(6, version);
            
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Student> getListStudentByClassID(String version, String classID) throws SQLException {
        ArrayList<Student> list = new ArrayList<>();
        String query = "select * from list_student where Phien_ban = '" + version + "' and Ma_lop = '" + classID + "';";
        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String studentId = rs.getString("Ma_SV");
            String studentName = rs.getString("Ho_ten");
            String nameClass = rs.getString("Lop");
            String statusRes = rs.getString("TT_dangky");

            list.add(new Student(classID, studentId, studentName, nameClass, statusRes, version));
        }
        conn.close();

        return list;
    }
    
    public ArrayList<Student> getListStudentBySubjectID(String version, String subjectID, String semester ) throws SQLException {
        ArrayList<Student> list = new ArrayList<>();
        String query = "select * from list_student where Phien_ban = '"+version+"' and Ma_lop in (select distinct Ma_lop from list_class where Ma_hp = '"+subjectID+"' and Hoc_ky = '" +semester+"');";

        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String classId = rs.getString("Ma_lop");
            String studentId = rs.getString("Ma_SV");
            String studentName = rs.getString("Ho_ten");
            String nameClass = rs.getString("Lop");
            String statusRes = rs.getString("TT_dangky");

            list.add(new Student(classId, studentId, studentName, nameClass, statusRes, version));
        }
        conn.close();

        return list;
    }
    
    public ArrayList<Student> getListStudent(ArrayList<String> list, String classID){
        ArrayList<Student> listStudents = new ArrayList<>();
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i = i + 4){
                listStudents.add(new Student(classID, list.get(i), list.get(i+1), list.get(i+2), list.get(i+3), "3"));
            }
        }
        
        return listStudents;
    }
}
