/**
 * Enumeration of enemy states.
 */
const EnemyState = {
    "IDLE": 0,
    "PATROLLING": 1,
    "LOOKING_AROUND": 2,
    "FOLLOWING_PLAYER": 3
};

/**
 * Represents an in-game enemy.
 * @param {*} x The x position of the enemy.
 * @param {*} y The y position of the enemy.
 * @param {*} waypoints The waypoints visitable by the enemy.
 */
function Enemy(x, y, waypoints) {
    /**
     * The enemy position.
     */
    this.x = x;
    this.y = y;
    /**
     * The waypoint that the enemy is walking to, just make it the first available one for now.
     */
    const targetWaypoint = waypoints[0];

    /**
     * Move the player.
     */
    this.move = function(xOffset, yOffset) {
        this.x += xOffset;
        this.y += yOffset;
    };

    /**
     * Get the state of the player.
     */
    this.getState = function(canSeePlayer) {
        // If the enemy can see the player then the enemy will follow them.
        if (canSeePlayer) {
            return EnemyState.FOLLOWING_PLAYER;
        }

        // The enemy is just patrolling.
        return EnemyState.PATROLLING;
    };

    /**
     * Get the target waypoint that the enemy is trying to reach.
     */
    this.getTargetWaypoint = function() {
        return targetWaypoint;
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