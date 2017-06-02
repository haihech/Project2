/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author haibk
 */
public class ProcessData {

    ArrayList<String> listInfoClass;
    ArrayList<String> listInfoSchedule;
    int local = -1;

    public ProcessData() {
    }

    public ProcessData(String semester, String classID, String subjectID, String srcHtml) {
        listInfoClass = new ArrayList<>();
        listInfoSchedule = new ArrayList<>();

        getDataClass(srcHtml);
    }

    public void getDataClass(String srcContent) {

        String[] s1 = srcContent.split("</td><td class");
        int size = s1.length;
        String classID[] = s1[0].split(">");
        listInfoClass.add(classID[80]);
        if (size <= 10) {
            for (int i = 1; i < size - 1; i++) {
                if (i < 8) {
                    String[] s2 = s1[i].split(">");
                    listInfoClass.add(s2[1]);
                } else if (i == 8) {
                    String[] School_Date = s1[8].split(">");
                    String[] School = School_Date[1].split("<");
                    listInfoClass.add(School[0]);
                }

            }

        } else {
            for (int i = 1; i < size - 2; i++) {
                if (i < 8) {
                    String[] s2 = s1[i].split(">");
                    listInfoClass.add(s2[1]);
                } else if (i == 8) {
                    String[] School_Date = s1[8].split(">");
                    String[] School = School_Date[1].split("<");

                    listInfoClass.add(School[0]);
                    listInfoSchedule.add(School_Date[59]);
                } else if (i > 8) {
                    if ((i - 2) % 3 != 0) {
                        String[] s2 = s1[i].split(">");
                        listInfoSchedule.add(s2[1]);
                    } else {
                        String[] s2 = s1[i].split(">");
                        listInfoSchedule.add(s2[5]);
                    }
                }

            }
        }

        ConvertData cd = new ConvertData();

        String className = cd.convertToTV(listInfoClass.get(2));
        String note = cd.convertToTV(listInfoClass.get(3));
        String classType = cd.convertToTV(listInfoClass.get(5));
        String sch = cd.convertToTV(listInfoClass.get(8));

        listInfoClass.remove(2);
        listInfoClass.remove(2);
        listInfoClass.remove(3);
        listInfoClass.remove(5);

        listInfoClass.add(2, className);
        listInfoClass.add(3, note);
        listInfoClass.add(5, classType);
        listInfoClass.add(8, sch);

    }

    public ArrayList<String> getDataStudent(ArrayList<String> listHtml) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> listStu = new ArrayList<>();
        int size = listHtml.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String t = listHtml.get(i);
                String[] s = t.split("t<td class");

                for (int k = 2; k < s.length - 2; k++) {
                    String[] s1 = s[k].split("</td>");
                    for (int j = 0; j < s1.length - 1; j++) {
                        String[] s2 = s1[j].split(">");
                        list.add(s2[1]);
                    }
                }
            }
        }
        ConvertData cd = new ConvertData();
        for (int i = 0; i < list.size(); i++) {
            String convert = cd.convertToTV(list.get(i));
            listStu.add(convert);
        }

        return listStu;
    }

    public void getDataStudentRegister(String srcContent) {

    }

    public void print() {
        for (int i = 0; i < listInfoClass.size(); i++) {
            System.out.println(listInfoClass.get(i));
        }

        for (int i = 0; i < listInfoSchedule.size(); i++) {
            System.out.println(listInfoSchedule.get(i));
        }
    }

    public ArrayList<String> getListInfoClass() {
        return listInfoClass;
    }

    public ArrayList<String> getListInfoSchedule() {
        return listInfoSchedule;
    }

    public int getLocal() {
        return local;
    }

    public String getListClassID(String srcContent, String classID, int index) {
        ArrayList<String> listSubjectID = new ArrayList<>();

        String[] s1 = srcContent.split("MainContent_gvClassList_DXKVInput");
        String subjectID[] = s1[1].split("&#39;");

        for (int i = 1; i < subjectID.length; i++) {
            if (i % 2 == 1) {
                listSubjectID.add(subjectID[i]);
            }
        }

        if (listSubjectID.size() == 1) {

        } else {
            for (int i = 0; i < listSubjectID.size(); i++) {
                if (listSubjectID.get(i).compareTo(classID) == 0) {
                    local = i;
                    break;
                }
            }
            if (local == -1) {
                listSubjectID.remove(index);
                listSubjectID.add(index, classID);
            } else {
                listSubjectID.remove(local);
                listSubjectID.add(index, classID);
            }
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("%5B'");
        int size = listSubjectID.size();
        for (int i = 0; i < size - 1; i++) {
            stringBuffer.append(listSubjectID.get(i) + "'%2C'");
        }
        stringBuffer.append(listSubjectID.get(size - 1) + "'%5D");

        return stringBuffer.toString();
    }

    public String getNewListClassID(String srcContent, String classID) {
        ArrayList<String> listSubjectID = new ArrayList<>();

        String[] s1 = srcContent.split("MainContent_gvClassList_DXKVInput");
        for(int i = 0; i< s1.length; i++){
            System.out.println(s1[i]);
        }
        String subjectID[] = s1[1].split("&#39;");

        for (int i = 1; i < subjectID.length; i++) {
            if (i % 2 == 1) {
                listSubjectID.add(subjectID[i]);
            }
        }

        if (listSubjectID.size() == 1) {
            local = 0;
        } else {
            for (int i = 0; i < listSubjectID.size(); i++) {
                if (listSubjectID.get(i).compareTo(classID) == 0) {
                    local = i;
                    break;
                }
            }

        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("%5B'");
        int size = listSubjectID.size();
        for (int i = 0; i < size - 1; i++) {
            stringBuffer.append(listSubjectID.get(i) + "'%2C'");
        }
        stringBuffer.append(listSubjectID.get(size - 1) + "'%5D");

        return stringBuffer.toString();
    }

}
