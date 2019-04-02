/**
 * A Condition node.
 * This acts as a guard and will succeed or fail immediately based on a board predicate, without moving to the 'RUNNING' state.
 * @param conditionFunction The condition function. 
 */
function Condition(conditionFunction) {
    /**
     * The node state.
     */
    let state = NodeState.READY;
   
    /**
     * Update the node and get whether the node state has changed.
     * @param board The board.
     * @returns Whether the state of this node has changed as part of the update.
     */
    this.update = function(board) {
        // Get the pre-update node state.
        const initialState = state;

        // Call the condition function to determine the state of this node.
        state = !!(board[conditionFunction]()) ? NodeState.SUCCEEDED : NodeState.FAILED;

        // Return whether the state of this node has changed.
        return state !== initialState;
    };

    /**
     * Gets the state of the node.
     */
    this.getState = () => state;

    /**
     * Reset the state of the node.
     */
    this.reset = () => {
        // Reset the state of this node.
        state = NodeState.READY;

        // Reset the child node.
        child.reset();
    };
};