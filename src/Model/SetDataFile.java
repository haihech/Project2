/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haibk
 */
public class SetDataFile {

    String tblClass = "\n\n\n"
            + "CREATE TABLE IF NOT EXISTS `list_class` (\n"
            + "  `Ma_lop` varchar(20) NOT NULL,\n"
            + "  `Ma_hp` varchar(45) NOT NULL,\n"
            + "  `Ten_lop` varchar(100) NOT NULL,\n"
            + "  `Ghi_chu` varchar(100) NOT NULL,\n"
            + "  `Loai_lop` varchar(45) NOT NULL,\n"
            + "  `Trang_thai` varchar(45) NOT NULL,\n"
            + "  `Max_dk` int(11) NOT NULL,\n"
            + "  `Da_dk` int(11) NOT NULL,\n"
            + "  `Vien` varchar(45) NOT NULL,\n"
            + "  `Hoc_ky` varchar(15) NOT NULL,\n"
            + "  `Phien_ban` varchar(5) NOT NULL,\n"
            + "  PRIMARY KEY (`Ma_lop`,`Phien_ban`)\n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

    String tblSchedule = "CREATE TABLE IF NOT EXISTS `list_schedule` (\n"
            + "  `Ma_lop` varchar(20) NOT NULL,\n"
            + "  `Thoi_gian` varchar(45) NOT NULL,\n"
            + "  `Tuan_hoc` varchar(85) NOT NULL,\n"
            + "  `Phong_hoc` varchar(85) NOT NULL,\n"
            + "  PRIMARY KEY (`Ma_lop`,`Thoi_gian`),\n"
            + "  CONSTRAINT `fk_malop` FOREIGN KEY (`Ma_lop`) REFERENCES `list_class` (`Ma_lop`) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

    String tblStudent = "CREATE TABLE IF NOT EXISTS `list_student` (\n"
            + "  `Ma_lop` varchar(10) NOT NULL,\n"
            + "  `Ma_SV` varchar(10) NOT NULL,\n"
            + "  `Ho_ten` varchar(85) NOT NULL,\n"
            + "  `Lop` varchar(150) NOT NULL,\n"
            + "  `TT_dangky` varchar(45) NOT NULL,\n"
            + "  `Phien_ban` varchar(5) NOT NULL,\n"
            + "  PRIMARY KEY (`Ma_lop`,`Ma_SV`,`Phien_ban`),\n"
            + "  KEY `fk_Version_idx` (`Phien_ban`),\n"
            + "  CONSTRAINT `fk_Ma_lop` FOREIGN KEY (`Ma_lop`) REFERENCES `list_class` (`Ma_lop`) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

    public SetDataFile(ArrayList<Class> listClasses, ArrayList<Schedule> listSchedules, ArrayList<Student> listStudents) {
        try {
            setDataFile(listClasses, listSchedules, listStudents);
        } catch (IOException ex) {
            Logger.getLogger(SetDataFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDataFile(ArrayList<Class> listClasses, ArrayList<Schedule> listSchedules, ArrayList<Student> listStudents) throws IOException{

        String insertClass = "INSERT INTO `list_class` VALUES ";
        for (int i = 0; i < listClasses.size(); i++) {
            Class c = listClasses.get(i);
            String insert = "('" + c.getClassID() + "','" + c.getSubjectID() + "','" + c.getSubjectName() + "','" + c.getNote() + "','" + c.getClassType() + "','" + c.getStatus() + "'," + c.getMaxRegister() + "," + c.getNumRegister() + ",'" + c.getSchool() + "','" + c.getSemester() + "','" + c.getVersion() + "')";
            if (i < listClasses.size() - 1) {
                insertClass = insertClass.concat(insert + ",");
            } else {
                insertClass = insertClass.concat(insert + ";\n");
            }

        }

        String insertSchedule = "INSERT INTO `list_schedule` VALUES ";
        for (int i = 0; i < listSchedules.size(); i++) {
            Schedule s = listSchedules.get(i);
            String insert = "('" + s.getClassID() + "','" + s.getTime() + "','" + s.getWeek() + "','" + s.getRoom() + "')";
            if (i < listSchedules.size() - 1) {
                insertSchedule = insertSchedule.concat(insert + ",");
            } else {
                insertSchedule = insertSchedule.concat(insert + ";");
            }

        }

        String insertStudent = "INSERT INTO `list_student` VALUES ";
        for (int i = 0; i < listStudents.size(); i++) {
            Student s = listStudents.get(i);
            String insert = "('" + s.getClassID() + "','" + s.getStudentID() + "','" + s.getName() + "','" + s.getClassName() + "','" + s.getStatus() + "','" + s.getVersion() + "')";
            if (i < listStudents.size() - 1) {
                insertStudent = insertStudent.concat(insert + ",");
            } else {
                insertStudent = insertStudent.concat(insert + ";\n");
            }

        }

        String fileName = "D:/project2.sql";
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(tblClass);
        bw.flush();
        bw.write(tblSchedule);
        bw.flush();
        bw.write(tblStudent);
        bw.flush();
        if (listClasses.size() > 0) {
            bw.write(insertClass);
            bw.flush();
        }
        if (listStudents.size() > 0) {
            bw.write(insertStudent);
            bw.flush();
        }
        
        if (listSchedules.size() > 0) {
            bw.write(insertSchedule);
            bw.flush();
        }
        

    }
}
