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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vitaljok
 */
public class PlayerConfigImpl extends PlayerConfig {

    List<PlayerDriver> drivers = new ArrayList<>();

    public PlayerConfigImpl(String name) {
        this.name = name;
    }

    public PlayerConfigImpl(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public PlayerConfigImpl() {
    }

    public List<PlayerDriver> getDrivers() {
        return drivers;
    }

    public static PlayerConfigImpl getDefaultCorobotConfig() {
        PlayerConfigImpl config = new PlayerConfigImpl("Corobot (default)", "Default Player config used on Corobot.");

        PlayerDriver driver1 = new PlayerDriver("stage");
        driver1.getItems().add(new PlayerDriverItem("plugin", "stageplugin"));
        driver1.getItems().add(new PlayerDriverItem("worldfile", "mrs_grass.world"));
        driver1.getItems().add(new PlayerDriverItem("usegui", "1"));
        driver1.getItems().add(new PlayerDriverItem("provides", "6650:simulation:0", true));
        config.getDrivers().add(driver1);

        PlayerDriver driver2 = new PlayerDriver("stage");
        driver2.getItems().add(new PlayerDriverItem("model", "rob1"));
        driver2.getItems().add(new PlayerDriverItem("provides", "6665:position2d:0", "6665:ranger:0", "6665:fiducial:0", "6665:blobfinder:0"));
        config.getDrivers().add(driver2);

        return config;
    }

    @Override
    public String getConfigText() {
        String res = "";
        for (PlayerDriver driver : drivers) {
            if (driver.isEnabled()) {
                res += driver.toString() + "\n";
                
            }
        }
        
        return res;
    }
}
