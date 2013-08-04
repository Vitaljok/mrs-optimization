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
package phd.mrs.heuristic.object;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

/**
 *
 * @author Vitaljok
 */

public class ComponentGroup {    
    List<Component> components = new ArrayList<>();
    String groupId;
    String comment;

    public ComponentGroup(String groupId, String comment) {
        this.groupId = groupId;
        this.comment = comment;
    }

    public ComponentGroup() {
    }        

    @XmlIDREF
    @XmlElement(name = "component")
    public List<Component> getComponents() {
        return components;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }        
}
