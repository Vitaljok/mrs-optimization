/*
 * Copyright (C) 2012 Vitaljok
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
package phd.mrs.heuristic.object.config;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Vitaljok
 */
@Embeddable
public class AbstractCoefs implements Serializable {
    public double b0;
    public double b1;
    public double b2;
    public double k;

    public AbstractCoefs() {
    }

    public AbstractCoefs(double b0, double b1, double b2, double k) {
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.k = k;
    }
    
}
