package com.dumbpug.levelgeneration;

import java.util.Collection;
import java.util.HashMap;

/**
 * A tile-positioned entity.
 */
public class EntityDefinition {
    /**
     * The entity name.
     */
    private String name;
    /**
     * The entity properties.
     */
    private HashMap<String, EntityProperty> properties = new HashMap<String, EntityProperty>();

    /**
     * Creates a new instance of the EntityDefinition class.
     * @param name The name of the entity.
     */
    public EntityDefinition(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the entity.
     * @return The name of the entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Set a property for the entity.
     * @param name The property name.
     * @param value The property value.
     */
    public void setProperty(String name, String value) {
        properties.put(name, new EntityProperty(name, value));
    }

    /**
     * Get the value of the specified property, or null if no property exists.
     * @param name The property name.
     * @return The value of the specified property, or null if no property exists.
     */
    public String getProperty(String name) {
        return properties.containsKey(name) ? properties.get(name).getValue() : null;
    }

    /**
     * Gets the collection of all entity properties.
     * @return The collection of all entity properties.
     */
    public Collection<EntityProperty> getProperties() {
        return properties.values();
    }
}