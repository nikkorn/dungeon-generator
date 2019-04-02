/**
 * An Action node.
 * This represents an immediate or ongoing state of behaviour.
 * @param actionFunction The action function. 
 */
function Condition(actionFunction) {
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

        // An action node should be updated until it fails or succeeds.
        if (child.getState() === NodeState.READY || child.getState() === NodeState.RUNNING) {
             // Call the action function to determine the state of this node.
            state = board[actionFunction]();
        }

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