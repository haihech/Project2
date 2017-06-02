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
public class ConvertData {
    ArrayList<String> listStr;
    ArrayList<String> listConVert;
    
    public ConvertData(){
        listStr = new ArrayList<>();
        listStr.add("&#224;");
        listStr.add("&#225;");
        listStr.add("&#226;");
        listStr.add("&#227;");
        listStr.add("&#232;");
        listStr.add("&#233;");
        listStr.add("&#234;");
        listStr.add("&#236;");
        listStr.add("&#237;");
        listStr.add("&#242;");
        listStr.add("&#243;");
        listStr.add("&#244;");
        listStr.add("&#245;");
        listStr.add("&#249;");
        listStr.add("&#250;");
        listStr.add("&#253;");
        listStr.add("&#259;");
        listStr.add("&nbsp;");
        listStr.add("&amp;");

        listConVert = new ArrayList<>();
        listConVert.add("à");
        listConVert.add("á");
        listConVert.add("â");
        listConVert.add("ã");
        listConVert.add("è");
        listConVert.add("é");
        listConVert.add("ê");
        listConVert.add("ì");
        listConVert.add("í");
        listConVert.add("ò");
        listConVert.add("ó");
        listConVert.add("ô");
        listConVert.add("õ");
        listConVert.add("ù");
        listConVert.add("ú");
        listConVert.add("ý");
        listConVert.add("ă");
        listConVert.add("No data");
        listConVert.add("&");
    }
    
    public String convertToTV(String str) {
        for (int i = 0; i < listStr.size(); i++) {
            String text = listStr.get(i);
            if (str.contains(text)) {
                str = str.replaceAll(text, listConVert.get(i));
            }
        }
        return str;
    }
}
