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
package phd.mrs.heuristic.mission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import phd.mrs.heuristic.object.Agent;

/**
 * Area coverage mission, which imply that agent moves upon defined area and
 * performs an operation by its work device.
 *
 * @author Vitaljok
 */
@XmlRootElement
@Entity
public class DummyMission extends Mission {    
    
    @XmlElement
    @ElementCollection
    List<String> families = new ArrayList<>();

    public DummyMission() {
    }

    public DummyMission(String... families) {
        super();
        this.families.addAll(Arrays.asList(families));
    }

    @Override
    public Double getAgentPerformance(Agent agent) {

        for (String fam : families) {
            if (agent.getComponentMap().containsKey(fam)) {
                return 1d;
            }
        }

        return 0d;
    }

    @Override
    public Double getAmountOfWork() {
        return 1d;
    }
}
