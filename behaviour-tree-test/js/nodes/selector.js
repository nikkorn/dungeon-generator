/**
 * A SELECTOR node.
 * The child nodes are executed in sequence until one succeeds or all fail.
 * @param children The child nodes. 
 */
function Selector(children) {
    /**
     * The node state.
     */
    let state = NodeState.READY;
   
    /**
     * Update the node and get whether the node state has changed.
     * @returns Whether the state of this node has changed as part of the update.
     */
    this.update = function() {
        // Get the pre-update node state.
        const initialState = state;

        // Iterate over all of the children of this node.
        for (const child of children) {
            // If the child has never been updated then we will need to do this now.
            if (child.getState() === NodeState.READY) {
                child.update();
            }

            // If the current child has a state of 'SUCCEEDED' then this node is also a 'SUCCEEDED' node.
            if (child.getState() === NodeState.SUCCEEDED) {
                // This node is a 'SUCCEEDED' node.
                state = NodeState.SUCCEEDED;

                // There is no need to check the rest of the selector nodes.
                return state !== initialState;
            }

            // If the current child has a state of 'FAILED' then we should move on to the next child.
            if (child.getState() === NodeState.FAILED) {
                // Find out if the current child is the last one in the selector.
                // If it is then this sequence node has also failed.
                if (children.indexOf(child) === children.length - 1) {
                    // This node is a 'FAILED' node.
                    state = NodeState.FAILED;

                    // There is no need to check the rest of the selector as we have completed it.
                    return state !== initialState;
                } else {
                    // The child node failed, try the next one.
                    continue;
                }
            }

            // The node should be in the 'RUNNING' state.
            if (child.getState() === NodeState.RUNNING) {
                // This node is a 'RUNNING' node.
                state = NodeState.RUNNING;

                // There is no need to check the rest of the selector as the current child is still running.
                return state !== initialState;
            }

            // The child node was not in an expected state.
            throw "Error: child node was not in an expected state.";
        }
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

        // Reset each child node.
        children.forEach((child) => child.reset());
    };
};