package com.dumbpug.levelgeneration;

/**
 * A property of a tile-positioned entity.
 */
public class EntityProperty {
    /**
     * The property name.
     */
    private String name;
    /**
     * The property value.
     */
    private String value;

    /**
     * Creates a new instance of the EntityProperty class.
     * @param name The property name.
     * @param value The property value.
     */
    public EntityProperty(String name, String value) {
        this.name  = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
