/*
 * Copyright (C) 2011 Vitaljok
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
package phd.mrs.mission;

import java.util.Properties;

/**
 *
 * @author Vitaljok
 */
public class AreaCoverageMission extends AbstractMission{

    
    
    public AreaCoverageMission(Properties properties) {
        super(properties);
    }

    @Override
    public Double getTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
