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
package phd.mrs.heuristic.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import phd.mrs.heuristic.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class Project implements Serializable {

    private String name;
    private List<Component> components = new ArrayList<Component>();
    private List<Agent> agents = new ArrayList<Agent>();
    private Map<Component, List<Component>> reqMap = new HashMap<Component, List<Component>>();

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Component, List<Component>> getCompMap() {
        return reqMap;
    }

    public List<Component> getComponents() {
        return components;
    }

    public Map<Component, List<Component>> getReqMap() {
        return reqMap;
    }

    public void addReq(Component orig, Component ref) {
        if (!this.reqMap.containsKey(orig)) {
            this.reqMap.put(orig, new ArrayList<Component>());
        }

        this.reqMap.get(orig).add(ref);
    }

    public List<Agent> getAgents() {
        return agents;
    }

    private boolean isAgentValid(Agent agent) {
        for (Component comp : agent.getComponents()) {
            List<Component> refList = reqMap.get(comp);
            
            if (refList != null) {
                if (!agent.getComponents().containsAll(refList)) {
                    return false;
                }
            }
        }       
        
        return true;
    }

    public Integer generateAgents() {
        Integer compNum = this.getComponents().size();
        Debug.log.info("Generating agents: " + (1 << compNum));
        
        int inv = 0;

        for (int i = 1; i <= (1 << compNum); i++) {
            String pattern = String.format("%" + compNum + "s",
                    Integer.toBinaryString(i)).replaceAll("\\s", "0");

            Agent agent = new Agent();

            for (int j = 0; j < compNum; j++) {
                if (pattern.charAt(compNum - j - 1) == '1') {
                    agent.getComponents().add(this.getComponents().get(j));
                }
            }
           
            if (this.isAgentValid(agent)) {
                this.getAgents().add(agent);
            } else {
                inv++;
            }             
        }
        
        Debug.log.info("Skipped invalid agents: " + inv);

        return 0;
    }
}
