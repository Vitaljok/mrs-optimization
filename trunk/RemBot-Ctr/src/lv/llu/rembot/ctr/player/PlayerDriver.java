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
public class PlayerDriver {

    private Boolean enabled = true;
    private List<PlayerDriverItem> items = new ArrayList<>();

    private PlayerDriver() {
    }

    public PlayerDriver(String name) {
        this.items.add(new PlayerDriverItem("name", name));
    }

    @Override
    public String toString() {
        String res = "";
        res += "driver (\n";

        for (PlayerDriverItem item : this.items) {
            res += "  " + item.toString() + "\n";
        }

        res += ")\n";
        return res;
    }

    public List<PlayerDriverItem> getItems() {
        return items;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
