/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haibk
 */
public class ThreadServer implements Runnable {

    Socket sock;
    String requestClient;

    public ThreadServer(Socket socket) {
        this.sock = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        try {
            String fileName = "D:/project2.sql";
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            while ((requestClient = in.readLine()) != null) {
                RequestClient reClient = new RequestClient(requestClient);
                reClient.addRequest();

                ProcessRequestClient rc = new ProcessRequestClient();
                rc.processRequestClient(requestClient);

                SetDataFile sdf = new SetDataFile(rc.getListClasses(), rc.getListSchedules(), rc.getListStudents());

                OutputStream os = sock.getOutputStream();
                DataOutputStream dout = new DataOutputStream(os);
                byte[] buffer = new byte[1024];
                int len;
                int data = 0;
                FileInputStream fis = new FileInputStream(file);
                
                while ((len = fis.read(buffer)) > 0) {
                    data++;
                }
                
                fis.close();
                fis = new FileInputStream(file);
                dout.write(data);
                dout.flush();
                len = 0;
                for (; data > 0; data--) {
                    len = fis.read(buffer);
                    os.write(buffer, 0, len);
                }
                os.flush();


            }

        } catch (IOException | SQLException ex) {
            try {
                sock.close();
            } catch (IOException ex1) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex1);
            }

        }

    }

}
