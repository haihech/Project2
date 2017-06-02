/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Class;
import Model.ProcessData;
import Model.RequestServerSIS;
import Model.Schedule;
import Model.Student;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haibk
 */
public class AddInfoClassController {

    public AddInfoClassController() {
    }

    public void addInfoClass(String semester, String classID, String subjectID, String srcHtml) throws SQLException {
        Class classInfo = new Class();
        Schedule schedule = new Schedule();
        try {

            ProcessData processData = new ProcessData(semester, classID, subjectID, srcHtml);
            ArrayList<String> listInfoClass = processData.getListInfoClass();
            ArrayList<String> listInfoSchedule = processData.getListInfoSchedule();

            if (listInfoClass.size() <= 0) {
            } else {
                classInfo.setClass(listInfoClass, semester);
                classInfo.addClass();
            }

            ArrayList<Schedule> listSchedules = schedule.getListSchedule(listInfoSchedule, classID);
            int size = listSchedules.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    listSchedules.get(i).addSchedule();
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ProcessData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addInfoStudent(String classID, ArrayList<String> srcHtmlStudent) {
        Student s = new Student();
        try {

            ProcessData processData = new ProcessData();
            ArrayList<String> listInfoStudent = processData.getDataStudent(srcHtmlStudent);
            
            ArrayList<Student> listStudents = s.getListStudent(listInfoStudent, classID);
            int kt = listStudents.size();
            if (kt > 0) {
                for (int i = 0; i < kt; i++) {
                    listStudents.get(i).addStudent();
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ProcessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getContentHtml(String classID) throws Exception {
        RequestServerSIS requestServer = new RequestServerSIS();
        return requestServer.getContentClass(classID);
    }
    
    public ArrayList<String> getContenHtmlListStudent(String semester, String subjectID, String classID) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        RequestServerSIS requestServer = new RequestServerSIS();
        ProcessData pd = new ProcessData();

        String listIdHtml = requestServer.getListClassIDBySubjectID(semester, subjectID);
        int i = 0;
        while (!listIdHtml.contains(classID) && i < 8) {
            listIdHtml = requestServer.getListClassIDBySubjectID(semester, subjectID);
            i++;
        }

        String listID = pd.getNewListClassID(listIdHtml, classID);
        int local = pd.getLocal();

        Class classInfo = new Class();
        int numberRes = classInfo.getClass("3", classID).getNumRegister();

        list = requestServer.getListStudent(semester, classID, subjectID, listID, numberRes, local);
        String test = list.get(0);
        if (!(test.contains(classID) && !test.contains("No data to display"))) {
            list = new ArrayList<>();
        }

        return list;
    }
}
