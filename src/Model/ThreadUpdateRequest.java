/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.ServerForm;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haibk
 */
public class ThreadUpdateRequest implements Runnable {

    ServerForm serverForm;

    public ThreadUpdateRequest(ServerForm serverForm) {
        this.serverForm = serverForm;
    }

    @Override
    public void run() {
        RequestClient rc = new RequestClient();
        int maxID = rc.getMaxIDRequest();
        while (true) {

            try {
                Thread.sleep(2000);
                int max = rc.getMaxIDRequest();
                if (max > maxID) {
                    maxID = max;
                    serverForm.setTextRequest(rc.getRequest());
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerForm.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
