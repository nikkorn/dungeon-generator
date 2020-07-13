package com.dumbpug.dungeony.game.item;

/**
 * An in-game item.
 */
public class Item {
    /**
     * The item type.
     */
    private ItemType type;
    /**
     * The item condition.
     */
    private Condition condition = Condition.NA;

    /**
     * Creates a new instance of the Item class.
     * @param type The item type.
     */
    public Item(ItemType type) {
        this.type = type;
    }

    /**
     * Creates a new instance of the Item class.
     * @param type The item type.
     * @param condition The item condition.
     */
    public Item(ItemType type, Condition condition) {
        this.type      = type;
        this.condition = condition;
    }

    /**
     * Gets the type of the item.
     * @return The type of the item.
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Gets the condition of the item.
     * @return The condition of the item.
     */
    public Condition getCondition() {
        return condition;
    }
}
