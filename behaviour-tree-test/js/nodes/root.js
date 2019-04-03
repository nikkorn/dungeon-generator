/**
 * A Root node.
 * The root node will have a single child.
 * @param child The child node. 
 */
function Root(child) {
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

        // If the child has never been updated or is running then we will need to update it now.
        if (child.getState() === NodeState.READY || child.getState() === NodeState.RUNNING) {
            try {
                child.update(board);
            } catch (exception) {
                throw `TreeUpdateError: ${exception}`;
            }
        }

        // The state of the root node is the state of its child.
        state = child.getState();

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