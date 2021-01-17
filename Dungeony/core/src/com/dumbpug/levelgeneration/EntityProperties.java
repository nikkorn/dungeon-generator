package com.dumbpug.levelgeneration;

import java.util.HashMap;

/**
 * A collection of entity properties.
 */
public class EntityProperties implements IEntityProperties {
    /**
     * The entity property mapping of uppercase names to stringy values.
     */
    private HashMap<String, String> properties = new HashMap<String, String>();

    /**
     * Gets whether the properties collection contains a property with the given name.
     * @param name The property name.
     * @return Whether the properties collection contains a property with the given name.
     */
    public boolean has(String name) {
        return this.properties.containsKey(name.toUpperCase());
    }

    /**
     * Gets the value of the property with the given name as a string.
     * @param name The property name.
     * @return The value of the property with the given name as a string.
     */
    public String getString(String name) {
        // No default value has been provided so throw an exception.
        if (!this.has(name)) {
            throw new RuntimeException("EntityProperties does not contain property '" + name + "'");
        }

        return this.properties.get(name.toUpperCase());

    }

    /**
     * Gets the value of the property with the given name as a string, or the default value if the property does not exist.
     * @param name The property name.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property with the given name as a string, or the default value if the property does not exist.
     */
    public String getString(String name, String defaultValue) {
        // No property exists with the given name so just return the default value specified.
        if (!this.has(name)) {
            return defaultValue;
        }

        return this.properties.get(name.toUpperCase());
    }

    /**
     * Gets the value of the property with the given name as an integer.
     * @param name The property name.
     * @return The value of the property with the given name as an integer.
     */
    public int getInt(String name) {
        // No default value has been provided so throw an exception.
        if (!this.has(name)) {
            throw new RuntimeException("EntityProperties does not contain property '" + name + "'");
        }

        try {
            return Integer.parseInt(this.properties.get(name.toUpperCase()));
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Cannot convert entity property '" + name + "' value of '" + this.properties.get(name.toUpperCase()) + "' to an integer");
        }
    }

    /**
     * Gets the value of the property with the given name as an integer, or the default value if the property does not exist.
     * @param name The property name.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property with the given name as an integer, or the default value if the property does not exist.
     */
    public int getInt(String name, int defaultValue) {
        // No property exists with the given name so just return the default value specified.
        if (!this.has(name)) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(this.properties.get(name.toUpperCase()));
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Cannot convert entity property '" + name + "' value of '" + this.properties.get(name.toUpperCase()) + "' to an integer");
        }
    }

    /**
     * Gets the value of the property with the given name as a float.
     * @param name The property name.
     * @return The value of the property with the given name as a float.
     */
    public float getFloat(String name) {
        // No default value has been provided so throw an exception.
        if (!this.has(name)) {
            throw new RuntimeException("EntityProperties does not contain property '" + name + "'");
        }

        try {
            return Float.parseFloat(this.properties.get(name.toUpperCase()));
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Cannot convert entity property '" + name + "' value of '" + this.properties.get(name.toUpperCase()) + "' to a float");
        }
    }

    /**
     * Gets the value of the property with the given name as a float, or the default value if the property does not exist.
     * @param name The property name.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property with the given name as a float, or the default value if the property does not exist.
     */
    public float getFloat(String name, float defaultValue) {
        // No property exists with the given name so just return the default value specified.
        if (!this.has(name)) {
            return defaultValue;
        }

        try {
            return Float.parseFloat(this.properties.get(name.toUpperCase()));
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Cannot convert entity property '" + name + "' value of '" + this.properties.get(name.toUpperCase()) + "' to a float");
        }
    }

    /**
     * Gets the value of the property with the given name as a boolean.
     * @param name The property name.
     * @return The value of the property with the given name as a boolean.
     */
    public boolean getBoolean(String name) {
        // No default value has been provided so throw an exception.
        if (!this.has(name)) {
            throw new RuntimeException("EntityProperties does not contain property '" + name + "'");
        }

        return Boolean.parseBoolean(this.properties.get(name.toUpperCase()));
    }

    /**
     * Gets the value of the property with the given name as a boolean, or the default value if the property does not exist.
     * @param name The property name.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property with the given name as a boolean, or the default value if the property does not exist.
     */
    public boolean getBoolean(String name, boolean defaultValue) {
        // No property exists with the given name so just return the default value specified.
        if (!this.has(name)) {
            return defaultValue;
        }

        return Boolean.parseBoolean(this.properties.get(name.toUpperCase()));
    }

    /**
     * Add the entity property to the entity properties collection.
     * @param name The property name.
     * @param value The property value as a string.
     */
    public void add(String name, String value) {
        this.properties.put(name.toUpperCase(), value);
    }
}
