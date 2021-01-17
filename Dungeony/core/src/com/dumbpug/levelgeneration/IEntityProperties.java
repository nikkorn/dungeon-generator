package com.dumbpug.levelgeneration;

/**
 * Represents an immutable collection of entity properties.
 */
public interface IEntityProperties {
    /**
     * Gets whether the properties collection contains a property with the given name.
     * @param name The property name.
     * @return Whether the properties collection contains a property with the given name.
     */
    boolean has(String name);

    /**
     * Gets the value of the property with the given name as a string.
     * @param name The property name.
     * @return The value of the property with the given name as a string.
     */
    String getString(String name);

    /**
     * Gets the value of the property with the given name as a string, or the default value if the property does not exist.
     * @param name The property name.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property with the given name as a string, or the default value if the property does not exist.
     */
    String getString(String name, String defaultValue);
    /**
     * Gets the value of the property with the given name as an integer.
     * @param name The property name.
     * @return The value of the property with the given name as an integer.
     */
    int getInt(String name);

    /**
     * Gets the value of the property with the given name as an integer, or the default value if the property does not exist.
     * @param name The property name.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property with the given name as an integer, or the default value if the property does not exist.
     */
    int getInt(String name, int defaultValue);

    /**
     * Gets the value of the property with the given name as a float.
     * @param name The property name.
     * @return The value of the property with the given name as a float.
     */
    float getFloat(String name);

    /**
     * Gets the value of the property with the given name as a float, or the default value if the property does not exist.
     * @param name The property name.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property with the given name as a float, or the default value if the property does not exist.
     */
    float getFloat(String name, float defaultValue);

    /**
     * Gets the value of the property with the given name as a boolean.
     * @param name The property name.
     * @return The value of the property with the given name as a boolean.
     */
    boolean getBoolean(String name);

    /**
     * Gets the value of the property with the given name as a boolean, or the default value if the property does not exist.
     * @param name The property name.
     * @param defaultValue The default value to return if the property does not exist.
     * @return The value of the property with the given name as a boolean, or the default value if the property does not exist.
     */
    boolean getBoolean(String name, boolean defaultValue);
}
