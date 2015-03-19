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

/**
 *
 * @author Vitaljok
 */
public class PlayerConfigSimpleImpl extends PlayerConfig {

    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PlayerConfigSimpleImpl() {
    }

    public PlayerConfigSimpleImpl(String name, String description) {
        super(name, description);
    }

    public PlayerConfigSimpleImpl(String name) {
        super(name);
    }
    
    public static PlayerConfig getDefaultConfig() {
        PlayerConfigSimpleImpl config = new PlayerConfigSimpleImpl("Corobot (default)", "Default Player config used on Corobot.");

        config.setText(
                "driver (\n"
                + "  name \"stage\"\n"
                + "  provides [ \"6650:simulation:0\" ]\n"
                + "  plugin \"stageplugin\"\n"
                + "\n"
                + "  worldfile \"mrs_grass_simple.world\"\n"
                + "  \n"
                + "  usegui 1\n"
                + ")\n"
                + "\n"
                + "driver (\n"
                + "  name \"stage\"\n"
                + "  #                mobile base         laser           camera              cropper\n"
                + "  provides [ \"6661:position2d:0\" \"6661:ranger:0\" \"6661:blobfinder:0\" \"6661:fiducial:0\"]\n"
                + "  model \"rob_mower1\"\n"
                + ")");

        return config;

    }

    @Override
    public String getConfigText() {
        return this.text;
    }
}
