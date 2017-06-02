/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.RequestClient;
import Model.Server;

/**
 *
 * @author haibk
 */
public class ServerController {

    public void startServer(int port) {
        Server server = new Server();
        server.startServer(port);
    }

    public int getMaxIDRequest(){
        RequestClient rc = new RequestClient();
        return rc.getMaxIDRequest();
    }
    
    public String getRequest(){
        RequestClient rc = new RequestClient();
        return rc.getRequest();
    }
    
}
