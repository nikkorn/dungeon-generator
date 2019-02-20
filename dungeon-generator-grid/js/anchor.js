/**
 * Represents a dungeon cell anchor.
 * @param x The x cell position.
 * @param y The y cell position.
 * @param direction The direction at which to join an occupied cell.
 * @param depth The depth of the room that the anchor is attached to.
 */
function Anchor(x, y, direction, depth) {

    /**
     * Get the direction at which to join an occupied cell.
     */
    this.getJoinDirection = () => direction;

    /**
     * Gets the x cell position.
     */
    this.getX = () => x;

    /**
     * Gets the y cell position.
     */
    this.getY = () => y;

    /**
     * Get the depth of the room that the anchor is attached to.
     */
    this.getDepth = () => depth;

    /**
     * Get whether this anchor's depth is within the specified depth range.
     * @param range The depth range.
     * @returns Whether this anchor's depth is within the specified depth range.
     */
    this.isWithinRange = (range) => {
        // Check whether this anchor depth is below the minimum.
        if (typeof range.minimum === "number" && depth < range.minimum) {
            return false;
        }

        // Check whether this anchor depth is below the minimum.
        if (typeof range.maximum === "number" && depth > range.maximum) {
            return false;
        }

        // The anchor depth is within the specified depth range.
        return true;
    };
}