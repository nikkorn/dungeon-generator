/**
 * Represents a dungeon cell.
 * @param x The x position.
 * @param y The y position.
 * @param roomId The id of the room that the cell is part of.
 * @param blocked The directions from which the cell is blocked (cannot be liked to another room).
 * @param door The door that is used to access the cell (if this cell is a room entrance cell).
 * @param doorDirection The direction at which an entrance door is placed.
 */
function Cell(x, y, roomId, blocked, door, doorDirection) {
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
    /**s
     * The id of the room that the cell is part of.
     */
    this.roomId = roomId;
    /**
     * The directions from which the cell is blocked (cannot be liked to another room).
     */
    this.blocked = blocked;

    /**
     * Get the x position of the cell.
     */
    this.getX = function() {
        return this.x;
    };

    /**
     * Get the y position of the cell.
     */
    this.getY = function() {
        return this.y;
    };

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
     * Get whether the cell is joinable at the particular direction.
     * @param direction The direction.
     */
    this.isJoinableAt = function(direction) {
        return !this.blocked.includes(direction);
    };
}