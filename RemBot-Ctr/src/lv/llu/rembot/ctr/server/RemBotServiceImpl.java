/*
 * Copyright (C) 2013 Vitaljok
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package lv.llu.rembot.ctr.server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import lv.llu.rembot.ctr.RemBotCtr;
import lv.llu.rembot.ctr.utils.Debug;

public class RemBotServiceImpl implements RemBotService {

    public RemBotServiceImpl() {
    }   
    private Registry registry;

    public void start() {        
        try {
            registry = LocateRegistry.createRegistry(ServerConfig.getInstance().getRmiPort());
            Remote stub = UnicastRemoteObject.exportObject(this, 0);
            registry.bind(ServerConfig.getInstance().getRmiBindName(), stub);

            Debug.log.log(Level.INFO, "Bound to {0} @{1}",
                    new Object[]{
                ServerConfig.getInstance().getRmiBindName(),
                ServerConfig.getInstance().getRmiPort()});

        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(RemBotCtr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        try {
            registry.unbind(ServerConfig.getInstance().getRmiBindName());
            UnicastRemoteObject.unexportObject(this, true);
            UnicastRemoteObject.unexportObject(registry, true);
            Debug.log.log(Level.INFO, "Unbound from {0} @{1}",
                    new Object[]{
                ServerConfig.getInstance().getRmiBindName(),
                ServerConfig.getInstance().getRmiPort()});
        } catch (RemoteException | NotBoundException ex) {
            Debug.log.severe("Failed stopping service");
        }
    }

    @Override
    public void shutDown() throws RemoteException {
        this.stop();
    }

    @Override
    public void ping() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
