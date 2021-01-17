package com.dumbpug.levelgeneration;

/**
 * A tile-positioned entity.
 */
public class EntityDefinition {
    /**
     * The entity name.
     */
    private String name;
    /**
     * The entity properties collection.
     */
    private EntityProperties properties = new EntityProperties();
    /**
     * The entity offset defining the entity position relative to the parent tile position.
     */
    private EntityOffset offset = EntityOffset.CENTRE;

    /**
     * Creates a new instance of the EntityDefinition class.
     * @param name The name of the entity.
     */
    public EntityDefinition(String name) {
        this(name, EntityOffset.CENTRE);
    }

    /**
     * Creates a new instance of the EntityDefinition class.
     * @param name The name of the entity.
     * @param offset The entity offset defining the entity position relative to the parent tile position.
     */
    public EntityDefinition(String name, EntityOffset offset) {
        this.name   = name;
        this.offset = offset;
    }

    /**
     * Gets the name of the entity.
     * @return The name of the entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the collection of all entity properties.
     * @return The collection of all entity properties.
     */
    public IEntityProperties getProperties() {
        return properties;
    }

    /**
     * Sets an entity property value.
     * @param name The property name.
     * @param value The property value.
     */
    public void setProperty(String name, String value) {
        this.properties.add(name, value);
    }

    /**
     * Gets the entity offset.
     * @return The entity offset.
     */
    public EntityOffset getOffset() {
        return offset;
    }
}
