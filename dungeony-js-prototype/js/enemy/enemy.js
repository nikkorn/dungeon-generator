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
function Enemy(x, y, waypoints, walkables) {
    /**
     * The enemy position.
     */
    this.x = x;
    this.y = y;
    /**
     * The current patrol path.
     */
    const currentPatrolPath = {
        // The target waypoint, reaching it completes the current node path.
        target: waypoints[0],

        // The path of nodes to follow, if null then it has not been calculated yet.
        path: null
    };

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

        // The enemy is patrolling.   
        return EnemyState.PATROLLING;
    };

    /**
     * Get the next patrol tile.
     */
    this.getNextPatrolTile = function() {
        // Get the x/y tile positions of the enemy.
        const enemyTileX = getTilePosition(this.x + (this.getSize() / 2));
        const enemyTileY = getTilePosition(this.y + (this.getSize() / 2));
        
        // TODO Check whether the enemy has reached the target, if so then find a new target and calculate a path.

        // TODO Calculate a patrol path if:
        //     - The currentPatrolPath.path property is null.
        //     - The enemy tile position is not within a 1 tile radius of the last (closest) node in the current path.
        //       This may have been caused by the enemy getting distracted or following the player.

        // TODO If the player position is completely within (no edges outside at all) the tile that is the last node in the path then pop the current node off of the current path.

        // TODO Return the last node in the path array.
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