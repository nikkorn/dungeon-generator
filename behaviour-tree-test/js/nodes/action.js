/**
 * An Action node.
 * This represents an immediate or ongoing state of behaviour.
 * @param uid The unique node it.
 * @param actionFunction The action function.
 */
function Action(uid, actionFunction) {
    /**
     * The node state.
     */
    let state = Mistreevous.State.READY;
   
    /**
     * Update the node and get whether the node state has changed.
     * @param board The board.
     * @returns Whether the state of this node has changed as part of the update.
     */
    this.update = function(board) {
        // Get the pre-update node state.
        const initialState = state;

        // An action node should be updated until it fails or succeeds.
        if (state === Mistreevous.State.READY || state === Mistreevous.State.RUNNING) {
            // Call the action function to determine the state of this node, but it must exist in the blackboard.
            if (typeof board[actionFunction] === "function") {
                // Set the state to running, it will stay in this state until either the success or fail callback is invoked via the action call.
                state = Mistreevous.State.RUNNING;

                // Call the action function, passing success/fail callbacks that the user can call to succeed/fail the action.
                board[actionFunction](() => state = Mistreevous.State.SUCCEEDED, () => state = Mistreevous.State.FAILED);
            } else {
                throw `cannot update action node as function '${actionFunction}' is not defined in the blackboard`;
            }
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
    this.getName = () => actionFunction;

    /**
     * Gets the state of the node.
     */
    this.getChildren = () => null;

    /**
     * Gets the type of the node.
     */
    this.getType = () => "action";

    /**
     * Gets the unique id of the node.
     */
    this.getUid = () => uid;

    /**
     * Reset the state of the node.
     */
    this.reset = () => {
        // Reset the state of this node.
        state = Mistreevous.State.READY;

        // Reset the child node.
        child.reset();
    };
};