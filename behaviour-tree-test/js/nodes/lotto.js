/**
 * A LOTTO node.
 * A winning child is picked on the initial update of this node, based on ticket weighting.
 * The state of this node will match the state of the winning child.
 * @param uid The unique node it.
 * @param tickets The child node tickets
 * @param children The child nodes. 
 */
function Lotto(uid, tickets, children) {
    /**
     * The node state.
     */
    let state = NodeState.READY;

    /**
     * The winning child node.
     */
    let winningChild;
   
    /**
     * Update the node and get whether the node state has changed.
     * @param board The board.
     * @returns Whether the state of this node has changed as part of the update.
     */
    this.update = function(board) {
        // Get the pre-update node state.
        const initialState = state;

        // If this node is in the READY state then we need to pick a winning child node.
        if (state === NodeState.READY) {
            // TODO Randomly pick a child based on ticket weighting.
            winningChild = children[0];
        }

        // If the winning child has never been updated or is running then we will need to update it now.
        if (winningChild.getState() === NodeState.READY || winningChild.getState() === NodeState.RUNNING) {
            winningChild.update(board);
        }

        // The state of the lotto node is the state of its winning child.
        state = winningChild.getState();

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
    this.getName = () =>  tickets.length ? `LOTTO [${ tickets.join(",") }]` : "LOTTO";

    /**
     * Gets the state of the node.
     */
    this.getChildren = () => children;

    /**
     * Gets the type of the node.
     */
    this.getType = () => "lotto";

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

        // Reset each child node.
        children.forEach((child) => child.reset());
    };
};