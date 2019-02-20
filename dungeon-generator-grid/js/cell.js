/**
 * Represents a dungeon cell.
 * @param x The x position.
 * @param y The y position.
 * @param depth The depth of the cell into the dungeon.
 * @param roomId The id of the room that the cell is part of.
 * @param roomName The name of the room that the cell is part of.
 * @param roomCategory The category of the room that the cell is part of.
 * @param blocked The directions from which the cell is blocked (cannot be liked to another room).
 * @param door The door that is used to access the cell (if this cell is a room entrance cell).
 * @param doorDirection The direction at which an entrance door is placed.
 * @param entities The entities that will (potentially) be generated for the cell. 
 */
function Cell(x, y, depth, roomId, roomName, roomCategory, blocked, door, doorDirection, entities) {

    /**
     * Get the x position of the cell.
     */
    this.getX = () => x;

    /**
     * Get the y position of the cell.
     */
    this.getY = () => y;

    /**
     * Get the id of the room that the cell is part of.
     */
    this.getRoomId = () => roomId;
    /**
     * Get the name of the room that the cell is part of.
     */
    this.getRoomName = () => roomName;

    /**
     * Get the category of the room that the cell is part of.
     */
    this.getRoomCategory = () => roomCategory;

    /**
     * Get the door that is used to access the cell (if this cell is a room entrance cell).
     */
    this.getDoor = () => door;

    /**
     * Get the direction at which an entrance door is placed.
     */
    this.getDoorDirection = () => doorDirection;

    /**
     * Get the depth of the cell into the dungeon.
     */
    this.getDepth = () => depth;

    /**
     * Get the entities of the cell.
     */
    this.getEntities = () => entities || [];

    /**
     * Get whether the cell is joinable at the particular direction.
     * @param direction The direction.
     */
    this.isJoinableAt = (direction) => !(blocked || []).includes(direction);
}