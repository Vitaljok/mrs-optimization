/*
 *  Player Java Client 3 - PlayerBBox.java
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
 * $Id: PlayerBbox2d.java 90 2010-05-02 18:09:04Z corot $
 *
 */
package javaclient3.structures;

/**
 * A rectangular bounding box, used to define the size of an object.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerBbox2d {
    private double sw;        // Width  [m]
    private double sl;        // Length [m]


    /**
     * @return Width [m]
     */
    public synchronized double getSw () {
        return this.sw;
    }

    /**
     * @param newSw Width [m]
     */
    public synchronized void setSw (double newSw) {
        this.sw = newSw;
    }

    /**
     * @return Length [m]
     */
    public synchronized double getSl () {
        return this.sl;
    }

    /**
     * @param newSl Length [m]
     */
    public synchronized void setSl (double newSl) {
        this.sl = newSl;
    }

}