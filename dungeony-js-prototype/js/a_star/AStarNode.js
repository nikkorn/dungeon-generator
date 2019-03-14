/**
 * @param x The x position.
 * @param y The y position.
 * @param movementCost The movement cost.
 */
function AStarNode(x, y, movementCost) {
    /**
     * The heuristic.
     */
    this.heuristic = 0;

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
     * Get the node heuristic.
     */
    this.getHeuristic = () => this.heuristic;

    /**
     * Set the node heuristic based on the path target.
     */
    this.setHeuristic = (target) => this.heuristic = Math.abs(target.getX() - x) + Math.abs(target.getY() - y);

    /**
     * Get the unique key for the node. 
     */
    this.getKey() = () => x + "_" + y;
}