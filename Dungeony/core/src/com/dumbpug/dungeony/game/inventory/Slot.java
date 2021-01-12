package com.dumbpug.dungeony.game.inventory;

import com.dumbpug.dungeony.game.item.Item;

/**
 * Represents a slot in an inventory that can hold a quantity of an item.
 */
public class Slot {
    /**
     * The item in the slot.
     */
    private Item item = null;
    /**
     * The quantity of the item.
     */
    private int quantity = 0;

    /**
     * Gets the item in the slot, or null if empty.
     * @return The item in the slot, or null if empty.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the item in the slot, or null if empty.
     * @param item The item in the slot, or null if empty.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Gets the quantity of the item.
     * @return The quantity of the item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item.
     * @param quantity The quantity of the item.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
