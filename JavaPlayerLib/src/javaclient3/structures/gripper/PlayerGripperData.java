/*
 *  Player Java Client 3 - PlayerGripperData.java
 *  Copyright (C) 2006 Radu Bogdan Rusu
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * $Id: PlayerGripperData.java 125M 2011-03-24 02:24:05Z (local) $
 *
 */
package javaclient3.structures.gripper;

import javaclient3.structures.*;

/**
 * Data: state (PLAYER_GRIPPER_DATA_STATE)
 * The gripper interface returns the current state of the gripper and
 * information on a potential object in the gripper.
 * State may be PLAYER_GRIPPER_STATE_OPEN, PLAYER_GRIPPER_STATE_CLOSED,
 * PLAYER_GRIPPER_STATE_MOVING or PLAYER_GRIPPER_STATE_ERROR.
 * Beams provides information on how far into the gripper an object is.
 * For most grippers, this will be a bit mask, with each bit representing
 * whether a beam has been interrupted or not.
 * Stored provides the number of currently stored objects.
 *
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerGripperData implements PlayerConstants {

    // The current gripper state
    private int state;
    // The current gripper break-beam state
    private int beams;
    // The number of currently-stored objects
    private int stored;

    /**
     * @return  The current gripper state:
     *      1 - open
     *      2 - closed
     *      3 - moving
     */
    public synchronized int getState() {
        return this.state;
    }

    /**
     * @param newState  The current gripper state (unsigned byte)
     *          <ul>
     *              <li>bit 0: Paddles open
     *              <li>bit 1: Paddles closed
     *              <li>bit 2: Paddles moving
     *              <li>bit 3: Paddles error
     *              <li>bit 4: Lift is up
     *              <li>bit 5: Lift is down
     *              <li>bit 6: Lift is moving
     *              <li>bit 7: Lift error
     *          </ul>
     */
    public synchronized void setState(int newState) {
        this.state = newState;
    }

    /**
     * @return  The current gripper breakbeam state (unsigned byte):      *
     * <ul>
     *              <li>bit 0: Gripper limit reached</li>
     *              <li>bit 1: Lift limit reached</li>
     *              <li>bit 2: Outer beam obstructed</li>
     *              <li>bit 3: Inner beam obstructed</li>
     *              <li>bit 4: Left paddle open</li>
     *              <li>bit 5: Right paddle open</li>
     * </ul>
     */
    public synchronized int getBeams() {
        return this.beams;
    }

    /**
     * @param newBeams  The current gripper breakbeam state
     */
    public synchronized void setBeams(int newBeams) {
        this.beams = newBeams;
    }

    /**
     * @return  The number of currently-stored objects
     */
    public synchronized int getStored() {
        return this.stored;
    }

    /**
     * @param newStored  The number of currently-stored objects
     */
    public synchronized void setStored(int newStored) {
        this.stored = newStored;
    }
}
