function Enemy(x, y, type, movements) {
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
     * The enemy type.
     */
    this.type = type;

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
        if (!this.movements) {
            return;
        }

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

    /**
     * Gets the enemy type.
     */
    this.getType = function() {
        return this.type;
    };
};