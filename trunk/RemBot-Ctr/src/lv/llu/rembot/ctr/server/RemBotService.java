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

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Vitaljok
 */
public interface RemBotService extends Remote {

    /**
     * Shut down RemBot service on remote host
     *
     * @throws RemoteException
     */
    public void shutDown() throws RemoteException;

    /**
     * Ping server to check connection.
     *
     * @throws RemoteException
     */
    public void ping() throws RemoteException;
}
