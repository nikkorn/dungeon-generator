function Player({ x, y }) {
    /**
     * The position.
     */
    this.x = x;
    this.y = y;
    /**
     * The velocity.
     */
    this.velocity = {
        x: 0,
        y: 0,
        clamp: function (max) {
            if (this.x > max) {
                this.x = max;
            } else if (this.x < -max) {
                this.x = -max;
            }
            if (this.y > max) {
                this.y = max;
            } else if (this.y < -max) {
                this.y = -max;
            }
        }
    };

    /**
     * Move the player.
     */
    this.move = function (xOffset, yOffset) {
        this.x += xOffset;
        this.y += yOffset;
    };

    /**
     * Gets the x position.
     */
    this.getX = function () {
        return this.x;
    };

    /**
     * Gets the y position.
     */
    this.getY = function () {
        return this.y;
    };

    /**
     * Gets the velocity.
     */
    this.getVelocity = function () {
        return this.velocity;
    };

    /**
     * Gets the size.
     */
    this.getSize = function () {
        return CHARACTER_SIZE;
    };
};