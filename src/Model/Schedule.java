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
public class Schedule {

    private String time;
    private String week;
    private String room;
    private String classID;

    public Schedule() {
    }

    public Schedule(String time, String week, String room, String classID) {
        this.time = time;
        this.week = week;
        this.room = room;
        this.classID = classID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }


    public void addSchedule() throws SQLException {
        String query = "insert into list_schedule values (?,?,?,?)";
        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, classID);
        stmt.setString(2, time);
        stmt.setString(3, week);
        stmt.setString(4, room);

        stmt.executeUpdate();
        conn.close();

    }

    public ArrayList<Schedule> getListScheduleByClassID(String classID) throws SQLException{
        ArrayList<Schedule> list = new ArrayList<>();
        String queryStr = "select * from list_schedule where Ma_lop = '"+classID+"';";

        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(queryStr);
        ResultSet rs = stmt.executeQuery();

        //Check empty result
        while (rs.next()) {
            String timeStudy = rs.getString("Thoi_gian");
            String weekStudy = rs.getString("Tuan_hoc");
            String roomStudy = rs.getString("Phong_hoc");
            String classId = rs.getString("Ma_lop");

            list.add(new Schedule(timeStudy, weekStudy, roomStudy, classId));

        }
        conn.close();

        return list;
    }
    
     public ArrayList<Schedule> getListScheduleBySubjectID(String subjectID, String semester) throws SQLException{
        ArrayList<Schedule> list = new ArrayList<>();
        String queryStr = "select * from list_schedule where Ma_lop in (select distinct Ma_lop from list_class where Ma_hp = '"+subjectID+"' and Hoc_ky = '" +semester+"');";
        Connection conn = ConnectToDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(queryStr);
        ResultSet rs = stmt.executeQuery();

        //Check empty result
        while (rs.next()) {
            String timeStudy = rs.getString("Thoi_gian");
            String weekStudy = rs.getString("Tuan_hoc");
            String roomStudy = rs.getString("Phong_hoc");
            String classId = rs.getString("Ma_lop");

            list.add(new Schedule(timeStudy, weekStudy, roomStudy, classId));

        }
        conn.close();

        return list;
    }
    
    public ArrayList<Schedule> getListSchedule(ArrayList<String> list, String classID){
        ArrayList<Schedule> listSchedules = new ArrayList<>();
        if(list.size() > 0){
            for(int i = 0; i < list.size(); i = i + 3){
                listSchedules.add(new Schedule(list.get(i), list.get(i+1), list.get(i+2), classID));
            }
        }
        
        return listSchedules;
    }
    

}
