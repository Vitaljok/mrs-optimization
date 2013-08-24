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
package lv.llu.rembot.ctr.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lv.llu.rembot.ctr.player.PlayerConfig;
import lv.llu.rembot.ctr.utils.Debug;

/**
 *
 * @author Vitaljok
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(factoryMethod = "createServerConfig")
public final class ServerConfig {

    static private final String CONFIG_FILE_NAME = "config.xml";
    static private final ServerConfig INSTANCE;
    @XmlElement
    private Boolean autoStartPlayer;
    private List<PlayerConfig> playerConfig = new ArrayList<>();
    @XmlElement
    private Boolean autoStartVLan;
    private List<String> vLanParams = new ArrayList<>();
    @XmlElement
    private Integer rmiRegistryPort;
    @XmlElement
    private String rmiBindName;

    private ServerConfig() {        
    }

    private void initDefaultInstance() {
        ServerConfig config = getInstance();
        config.autoStartPlayer = true;
        config.playerConfig.add(PlayerConfig.getDefaultCorobotConfig());
        config.autoStartVLan = false;
        config.rmiRegistryPort = 36667;
        config.rmiBindName = "lv/llu/RemBotService";
    }

    public static ServerConfig getInstance() {
        return INSTANCE;
    }

    static {        
        INSTANCE = new ServerConfig();
        INSTANCE.initDefaultInstance();
        Debug.log.info("Reloading server configuration");
        INSTANCE.loadFromXml();
        INSTANCE.saveToXml();
    }

    @SuppressWarnings("unused")
    private static ServerConfig createServerConfig() {
        return getInstance();
    }

    public void saveToXml() {
        Debug.log.config("Saving server configuration to XML");
        try {
            JAXBContext xml = JAXBContext.newInstance(ServerConfig.class);
            Marshaller marsh = xml.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marsh.marshal(this, new File(CONFIG_FILE_NAME));
        } catch (JAXBException ex) {
            Debug.log.severe("Error saving server config to file");
        }
    }

    public void loadFromXml() {
        try {
            Debug.log.config("Loading server configuration from XML");
            JAXBContext xml = JAXBContext.newInstance(ServerConfig.class);
            Unmarshaller unmarsh = xml.createUnmarshaller();
            unmarsh.unmarshal(new File(ServerConfig.CONFIG_FILE_NAME));
        } catch (JAXBException ex) {
            Debug.log.warning("Failed loading config from XML, using default.");
        }
    }

    public Boolean getAutoStartPlayer() {
        return autoStartPlayer;
    }

    public void setAutoStartPlayer(Boolean autoStartPlayer) {
        this.autoStartPlayer = autoStartPlayer;
    }

    public List<PlayerConfig> getPlayerConfig() {
        return playerConfig;
    }

    public Boolean getAutoStartVLan() {
        return autoStartVLan;
    }

    public void setAutoStartVLan(Boolean autoStartVLan) {
        this.autoStartVLan = autoStartVLan;
    }

    public List<String> getvLanParams() {
        return vLanParams;
    }

    public Integer getRmiPort() {
        return rmiRegistryPort;
    }

    public void setRmiPort(Integer rmiPort) {
        this.rmiRegistryPort = rmiPort;
    }

    public String getRmiBindName() {
        return rmiBindName;
    }

    public void setRmiBindName(String rmiBindName) {
        this.rmiBindName = rmiBindName;
    }
}
