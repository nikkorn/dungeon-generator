package com.dumbpug.dungeony.game.inventory;

import java.util.ArrayList;

/**
 * Represents a character inventory with slots that can be populated with items and quantities.
 */
public class Inventory {
    /**
     * The item slots in the inventory.
     */
    private ArrayList<Slot> slots = new ArrayList<Slot>();
    /**
     * The amount of gold held
     */
    private int gold = 0;

    /**
     * Creates a new instance of the Inventory class.
     * @param slots The number of available slots in the inventory.
     */
    public Inventory(int slots) {
        // Create our slots.
        for (int slotIndex = 0; slotIndex < slots; slotIndex++) {
            this.slots.add(new Slot());
        }
    }

    /**
     * Gets the slot at the given index.
     * @param index The slot index.
     * @return The slot at the given index.
     */
    public Slot getSlot(int index) {
        if (index < 0 || index >= this.slots.size()) {
            throw new RuntimeException("invalid inventory slot index: " + index);
        }

        return this.slots.get(index);
    }

    /**
     * Gets the amount of gold in the inventory.
     * @return The amount of gold in the inventory.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Sets the amount of gold in the inventory.
     * @param gold The amount of gold in the inventory.
     */
    public void setGold(int gold) {
        this.gold = gold;
    }
}
