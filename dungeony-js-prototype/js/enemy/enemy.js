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
 * @param {*} walkables The walkable tiles.
 */
function Enemy(x, y, walkables) {
    /**
     * The enemy position.
     */
    this.x = x;
    this.y = y;
    /**
     * The current patrol path.
     */
    let currentPatrolPath = [];

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
     * Get the next patrol tile node.
     */
    this.getNextPatrolTileNode = function() {
        // If there is currently no path then we should create one to a random waypoint.
        if (currentPatrolPath.length === 0) {
            this.generatePatrolPath();
        }

        // Helper function which gets whether the enemy is on the specified tile node.
        const isAtNode = (node) => this.getTileX() === node.getX() && this.getTileY() === node.getY();

        // Get the last node in the patrol path, the one the enemy should be closest to.
        const lastPathNode = currentPatrolPath[currentPatrolPath.length - 1];

        // Has the play reached the last node and most likely closest node in the path?
        if (isAtNode(lastPathNode)) {
            // Get rid of the last node from the path as we have reached it.
            currentPatrolPath.pop();
        } else {
            // TODO Check whether the enemy is too far away from the last path node.
            // If so then we must have wandered off and will have to calculate a new path.
        }

        // If there are nodes left then we have not reached the end of the path and should return the current node we are aiming for.
        if (currentPatrolPath.length) {
            return currentPatrolPath[currentPatrolPath.length - 1];
        }
    };

    /**
     * Generate a new patrol path.
     */
    this.generatePatrolPath = function() {
        // Find the walkable tile at the waypoint position.
        const startTile = walkables.find((walkable) => walkable.x === this.getTileX() && walkable.y === this.getTileY());

        // Get a random waypoint to walk to.
        const waypoints = walkables.filter((walkable) => walkable.isWaypoint);
        const targetWaypoint = waypoints[Math.floor(Math.random() * waypoints.length)]; 

        // Find the walkable tile at the current enemy position.
        const endTile = walkables.find((walkable) => walkable.x === targetWaypoint.x && walkable.y === targetWaypoint.y);

        // Calculate the path!
        currentPatrolPath = findPath(walkables.map((walkable) => walkable.node), startTile.node, endTile.node).path;
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
     * Gets the tile x position.
     */
    this.getTileX = function() {
        return getTilePosition(this.x + (this.getSize() / 2));
    };

    /**
     * Gets the y position.
     */
    this.getTileY = function() {
        return getTilePosition(this.y + (this.getSize() / 2));
    };

    /**
     * Gets the size.
     */
    this.getSize = function() {
        return CHARACTER_SIZE;
    };
};