/**
 * Represents a dungeon anchor.
 * @param {*} x The x cell position.
 * @param {*} y The y cell position.
 * @param {*} direction The direction at which to join an occupied cell.
 */
function Anchor(x, y, direction) {
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
}