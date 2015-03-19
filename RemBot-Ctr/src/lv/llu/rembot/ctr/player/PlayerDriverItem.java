/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.llu.rembot.ctr.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Vitaljok
 */
public class PlayerDriverItem {

    String name;
    List<String> values = new ArrayList<>();
    Boolean isList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerDriverItem(String name, String value) {
        this.name = name;
        this.isList = false;
        this.values.add(value);
    }

    public PlayerDriverItem(String name, String value, Boolean isList) {
        this.name = name;
        this.isList = isList;
        this.values.add(value);
    }

    public PlayerDriverItem(String name, String... values) {
        this.name = name;
        this.isList = true;
        this.values.addAll(Arrays.asList(values));
    }

    public List<String> getValues() {
        return values;
    }
    
    public Boolean getIsList() {
        return isList;
    }

    public void setIsList(Boolean isList) {
        this.isList = isList;
    }

    
    
    @Override
    public String toString() {
        if (isList) {
            String res = "";
            for (String v : values) {
                res += " \"" + v + "\" ";
            }
            return name + " [" + res + "]";
        } else {
            return name + " \"" + values.get(0) + "\"";
        }
    }
}
