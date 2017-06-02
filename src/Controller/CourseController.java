/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import Model.Class;
import Model.Schedule;
import Model.Student;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haibk
 */
public class CourseController {
    
    public CourseController(){
        
    }
    
    public boolean checkClassID (String classID){
        Class classInfo = new Class();
        try {
            return classInfo.checkClassID(classID);
        } catch (SQLException ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Class getClass(String version, String classID) throws SQLException {
        Class classInfo = new Class();
        return classInfo.getClass(version, classID);
    }
    
    public ArrayList<Schedule> getScheduleClass(String classID) throws SQLException {
        Schedule schedule = new Schedule();
        return schedule.getListScheduleByClassID(classID);
    }
    
    public ArrayList<Class> getListClass(String version, String subjectID, String semester) throws SQLException {
        Class classInfo = new Class();
        return classInfo.getListClassBySubjectID(subjectID, version, semester);
    }
    
    public ArrayList<Student> getListStudentByClassID(String version, String classID) throws SQLException {
        Student student = new Student();
        return student.getListStudentByClassID(version, classID);
    }
    
    public ArrayList<Student> getListStudentBySubjectID(String version, String subjectID, String semester) throws SQLException {
        Student student = new Student();
        return student.getListStudentBySubjectID(version, subjectID, semester);
    }
    
    public ArrayList<Schedule> getListScheduleBySubjectID(String subjectID, String semester) throws SQLException {
        Schedule schedule = new Schedule();
        return schedule.getListScheduleBySubjectID(subjectID, semester);
    }
}
