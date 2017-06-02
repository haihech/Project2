/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author haibk
 */
public class ProcessRequestClient {

    ArrayList<Student> listStudents;
    ArrayList<Schedule> listSchedules;
    ArrayList<Class> listClasses;

    public ProcessRequestClient() {
        listClasses = new ArrayList<>();
        listSchedules = new ArrayList<>();
        listStudents = new ArrayList<>();
    }

    public void processRequestClient(String request) throws SQLException {
        String s[] = request.split(" ");

        int size = s.length;
        if (size == 3) {
            if (s[0].compareToIgnoreCase("Class") == 0) {
                Class classInfo = new Class();
                Class classRequest = classInfo.getClass(s[2], s[1]);
                if (classRequest == null) {
                } else {
                    listClasses.add(classRequest);
                    listStudents = new Student().getListStudentByClassID(s[2], s[1]);
                    listSchedules = new Schedule().getListScheduleByClassID(s[1]);
                }
            }
        } else if (size == 4) {
            if (s[0].compareToIgnoreCase("Subject") == 0) {
                String subjectID = s[1].toUpperCase();
                listClasses = new Class().getListClassBySubjectID(subjectID, s[2], s[3]);
                listSchedules = new Schedule().getListScheduleBySubjectID(subjectID, s[3]);
                listStudents = new Student().getListStudentBySubjectID(s[2], subjectID, s[3]);
            }
        }

    }

    public ArrayList<Student> getListStudents() {
        return listStudents;
    }

    public ArrayList<Schedule> getListSchedules() {
        return listSchedules;
    }

    public ArrayList<Class> getListClasses() {
        return listClasses;
    }

    

}
