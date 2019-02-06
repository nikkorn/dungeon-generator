function Enemy({ x, y, movements }) {
    /**
     * The enemy position.
     */
    this.x = x;
    this.y = y;
    /**
     * The enemy movements position.
     */
    this.movements = movements;
    /**
     * The current enemy movement index. 
     */
    this.currentMovementIndex = 0;

    /**
     * Move the player.
     */
    this.move = function(xOffset, yOffset) {
        this.x += xOffset;
        this.y += yOffset;
    };

    /**
     * Get the next enemy movement.
     */
    this.getNextMovement = function() {
        const movement = this.movements[this.currentMovementIndex];

        // Have we looped round to the initial movement?
        if (++this.currentMovementIndex >= this.movements.length) {
            this.currentMovementIndex = 0;
        }

        return movement;
    };

    /**
     * Gets the x position.
     */
    this.getX = function() {
        return this.x;
    };

    /**
     * Gets the y position.
     */
    this.getY = function() {
        return this.y;
    };

    /**
     * Gets the size.
     */
    this.getSize = function() {
        return CHARACTER_SIZE;
    };
};