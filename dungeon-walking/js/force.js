function Force({ x, y, value, isDone }) {
    /**
     * The direction.
     */
    this.x = x;
    this.y = y;
    /**
     * The force.
     */
    this.value = value;

    this.apply = function(velocity) {
        velocity.x += this.x;
        velocity.y += this.y;
    };
};