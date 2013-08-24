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
package lv.llu.rembot.ctr.player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import lv.llu.rembot.ctr.utils.Debug;

/**
 *
 * @author Vitaljok
 */
public class PlayerConfig {

    String name;
    String description;
    List<PlayerDriver> drivers = new ArrayList<>();

    public PlayerConfig(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public PlayerConfig() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PlayerDriver> getDrivers() {
        return drivers;
    }

    public void writeToFile(String fileName) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            for (PlayerDriver driver : drivers) {
                out.write(driver.toString());
                out.newLine();
            }
            
            out.flush();
        } catch (IOException ex) {
            Debug.log.log(Level.SEVERE, "Error writing Player config to file", ex);
        }
    }
    
    public static PlayerConfig getDefaultCorobotConfig() {        
        PlayerConfig config = new PlayerConfig("Corobot (default)", "Default Player config used on Corobot.");
        config.getDrivers().add(new PlayerDriver("dummy driver"));
        
        
        return config;
    }
}
