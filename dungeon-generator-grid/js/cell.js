/**
 * Represents a dungeon cell.
 * @param {*} x The x position.
 * @param {*} y The y position.
 * @param {*} roomId The id of the room that the cell is part of.
 * @param {*} door The door that is used to access the cell (if this cell is a room entrance cell).
 * @param {*} doorDirection The direction at which an entrance door is placed.
 */
function Cell(x, y, roomId, door, doorDirection) {
    /**
     * The cell position.
     */
    this.x = x;
    this.y = y;
    /**
     * The door that is used to access the cell (if this cell is a room entrance cell).
     */
    this.door = door;
    /**
     * The direction at which an entrance door is placed.
     */
    this.doorDirection = doorDirection;
    /**
     * The id of the room that the cell is part of.
     */
    this.roomId = roomId;

    /**
     * Get the id of the room that the cell is part of.
     */
    this.getRoomId = function() {
        return this.roomId;
    };

    /**
     * Get the door that is used to access the cell (if this cell is a room entrance cell).
     */
    this.getDoor = function() {
        return this.door;
    };

    /**
     * Get the direction at which an entrance door is placed.
     */
    this.getDoorDirection = function() {
        return this.doorDirection;
    };

    /**
     * Get the unique key for the cell.
     */
    this.getKey = function() {
        return `${this.x}_${this.y}`;
    };
}