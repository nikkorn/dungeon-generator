/**
 * Represents a dungeon anchor.
 * @param x The x cell position.
 * @param y The y cell position.
 * @param direction The direction at which to join an occupied cell.
 * @param depth The depth of the room that the anchor is attached to.
 */
function Anchor(x, y, direction, depth) {
    /**
     * The anchor position.
     */
    this.x = x;
    this.y = y;
    /**
     * The direction at which to join an occupied cell.
     */
    this.direction = direction;
    /**
     * The depth of the room that the anchor is attached to.
     */
    this.depth = depth;

    /**
     * Get the direction at which to join an occupied cell.
     */
    this.getJoinDirection = function() {
        return this.direction;
    };

    /**
     * Gets the x cell position.
     */
    this.getX = function() {
        return this.x;
    };

    /**
     * Gets the y cell position.
     */
    this.getY = function() {
        return this.y;
    };

    /**
     * Get the depth of the room that the anchor is attached to.
     */
    this.getDepth = function() {
        return this.depth;
    };
}