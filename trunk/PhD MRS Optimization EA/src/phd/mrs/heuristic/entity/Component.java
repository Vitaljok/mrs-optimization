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

import java.text.MessageFormat;
import java.util.Properties;

/**
 *
 * @author Vitaljok
 */
public class Component{

    private String clazz;
    private String name;
    private Component parent;
    Properties properties = new Properties();

    protected Component() {
    }

    public Component(String clazz, String name, Component parent) {
        this.clazz = clazz;
        this.name = name;
        this.parent = parent;
    }

    public Properties getProperties() {
        return properties;
    }

    public Double getDoubleProperty(String key) {

        try {
            return Double.valueOf(this.getProperties().getProperty(key));
        } catch (Exception ex) {
            throw new RuntimeException(
                    MessageFormat.format("Component \"{0}\" does not have valid \"{1}\" property!",
                    this.getClazz(),
                    key));
        }
    }

    public Double getDoubleProperty(String key, Double defaultValue) {
        try {
            return this.getDoubleProperty(key);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Component getParent() {
        return parent;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "[" + clazz + "]";
    }
}
