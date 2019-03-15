/**
 * @param x The x position.
 * @param y The y position.
 * @param movementCost The movement cost.
 */
function AStarNode(x, y, movementCost = 1) {
    /**
     * The heuristic.
     */
    this.heuristic = 0;

    /**
	 * The movement cost from the start node to this node. This will normally just
	 * be incremented by 1 per node, but this can be made higher in order to
	 * decrease the appeal of following a path with this node.
	 */
    this.accumulatedMovementCost = 0;
    
    /**
     * The node parent.
     */
    this.parent = null;

    /**
     * Gets the x cell position.
     */
    this.getX = () => x;

    /**
     * Gets the y cell position.
     */
    this.getY = () => y;

    /**
     * Get the movement cost.
     */
    this.getMovementCost = () => movementCost;

    /**
     * Get the accumulated movement cost.
     */
    this.getAccumulatedMovementCost = () => this.accumulatedMovementCost;

    /**
     * Set the accumulated movement cost.
     */
    this.setAccumulatedMovementCost = (cost) => this.accumulatedMovementCost = cost;

    /**
     * Get the node heuristic.
     */
    this.getHeuristic = () => this.heuristic;

    /**
     * Set the node heuristic based on the path target.
     */
    this.setHeuristic = (target) => this.heuristic = Math.abs(target.getX() - x) + Math.abs(target.getY() - y);

     /**
     * Get the node parent.
     */
    this.getParent = () => this.parent;

    /**
     * Set the node parent.
     */
    this.setParent = (parent) => this.parent = parent;

    /**
	 * Get the score for this node.
	 */
	this.getScore = () => this.accumulatedMovementCost + this.heuristic;

    /**
     * Get the unique key for the node. 
     */
    this.getKey = () => x + "_" + y;
}