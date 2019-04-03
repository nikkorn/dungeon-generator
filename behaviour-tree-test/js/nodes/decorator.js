/**
 * A Decorator node.
 * This node wraps a single child and will mutate the state of the child state.
 * @param uid The unique node it.
 * @param decoratorFunction The decorator function.
 * @param child The child node. 
 */
function Decorator(uid, decoratorFunction, child) {
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

        // We cannot update this decorator node if there is no decorator function to invoke on the blackboard.
        if (typeof board[decoratorFunction] !== "function") {
            throw `cannot update decorator node as function '${decoratorFunction}' is not defined in the blackboard`;
        }

        // Sets the state of this node to match that of its child state and calls the decorator function to potentially modify it.
        const callDecoratorFunction = () => {
             // Set the state of this node to match the state of the child one. This will remain
            // the state of this decorator node unless it is modified via the decorator function.
            state = child.getState();

            // Call the decorator function, passing success/fail/resume/reset callbacks that the user can call to succeed/fail/resume/reset the child.
            board[decoratorFunction](
                () => state = NodeState.SUCCEEDED, 
                () => state = NodeState.FAILED,
                () => state = NodeState.RUNNING,
                () => state = NodeState.READY,
                this._convertNodeStateToString(state)
            );

            // If the state is 'READY' then reset the child now.
            if (state === NodeState.READY) {
                child.reset();
            }
        };

        // An action node should be updated until it fails or succeeds.
        if (child.getState() === NodeState.READY || child.getState() === NodeState.RUNNING) {
            // Get the actual state of the child.
            const initialChildState = child.getState();

            // Update the child.
            child.update(board);

            // If the state of the child has changed then we should call our decorator function to potentially mutate the state of this node.
            if (child.getState() !== initialChildState) {
                callDecoratorFunction();
            }
        } else {
            // If this node is still in the READY or RUNNING state even though the child one isn't, then 
            // we should call the decorator function as we may still want to modify the state of this node.
            callDecoratorFunction();
        }

        // Return whether the state of this node has changed.
        return state !== initialState;
    };

    /**
     * Gets the state of the node.
     */
    this.getState = () => state;

    /**
     * Gets the name of the node.
     */
    this.getName = () => decoratorFunction;

    /**
     * Gets the state of the node.
     */
    this.getChildren = () => [child];

    /**
     * Gets the type of the node.
     */
    this.getType = () => "decorator";

    /**
     * Gets the unique id of the node.
     */
    this.getUid = () => uid;

    /**
     * Reset the state of the node.
     */
    this.reset = () => {
        // Reset the state of this node.
        state = NodeState.READY;

        // Reset the child node.
        child.reset();
    };

    /**
     * A function to convert a node state to a string.
     * @param nodeState The node state.
     * @returns The node state as a string.
     */
    this._convertNodeStateToString = (nodeState) => {
        switch (nodeState) {
            case NodeState.RUNNING:
                return "running";
            case NodeState.SUCCEEDED:
                return "succeeded";
            case NodeState.FAILED:
                return "failed";
            default:
                return "ready";
        }
    };
};