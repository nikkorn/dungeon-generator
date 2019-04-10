/**
 * A REPEAT node.
 * The node has a single child which can have:
 * -- A number of iterations for which to repeat the child node.
 * -- A condition function that determines whether to repeat the update of the child node.
 * -- An infinite repeat loop if neither an iteration count or a condition function is defined.
 * The REPEAT node will stop and have a 'FAILED' state if its child is ever in a 'FAILED' state after an update.
 * The REPEAT node will attempt to move on to the next iteration if its child is ever in a 'SUCCEEDED' state.
 * @param uid The unique node it.
 * @param iterations The number of iterations to repeat the child node, or the minimum number of iterations if maximumIterations is defined.
 * @param maximumIterations The maximum number of iterations to repeat the child node.
 * @param conditionFunction The name of the condition function that determines whether to repeat the update of the child node.
 * @param child The child node. 
 */
function Repeat(uid, iterations, maximumIterations, conditionFunction, child) {
    /**
     * The node state.
     */
    let state = Mistreevous.State.READY;

    /**
     * The condition function name.
     */
    let conditionFunctionName = null;

    /**
     * The number of target iterations to make.
     */
    let targetIterationCount = null;

    /**
     * The current iteration count.
     */
    let currentIterationCount = 0;

    /**
     * Update the node and get whether the node state has changed.
     * @param board The board.
     * @returns Whether the state of this node has changed as part of the update.
     */
    this.update = function(board) {
        // Get the pre-update node state.
        const initialState = state;

        // If this node is in the READY state then we need to reset the iteration count and determine which method we will use as a repeat condition.
        if (state === Mistreevous.State.READY) {
            // Reset the current iteration count.
            currentIterationCount = 0;

            // Reset the child node.
            child.reset();

            // Are we dealing with a set number of iterations or a condition function?
            if (typeof iterations !== "undefined") {
                // If we have maximumIterations defined then we will want a random iteration count bounded by iterations and maximumIterations.
                targetIterationCount = (typeof maximumIterations !== "undefined") ? 
                    Math.floor(Math.random() * (maximumIterations - iterations + 1) + iterations) : 
                    iterations;
            } else if (typeof conditionFunction === "string") {
                // We are dealing wil a condition function.
                conditionFunctionName = conditionFunction;
            }

            // The node is now running until we finish iteration.
            state = Mistreevous.State.RUNNING;
        }

        // TODO Fix the following!

        //  If the child is in the 'READY' state but we cannot iterate then this node has succeeded!
        if (child.getState() === Mistreevous.State.READY && !this._canIterate()) {
            // The node has succeeded
            state = Mistreevous.State.SUCCEEDED;

            // Return whether the state of this node has changed.
            return state !== initialState;
        }

        if (child.getState() === Mistreevous.State.READY || child.getState() === Mistreevous.State.RUNNING) {
            // Update the child.
            child.update(board);
        } 
        
        if (child.getState() === Mistreevous.State.SUCCEEDED) {

        } else {
            // The child must have failed, meaning that this node has failed.
            state = Mistreevous.State.FAILED;
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
    this.getName = () => "REPEAT";

    /**
     * Gets the state of the node.
     */
    this.getChildren = () => [child];

    /**
     * Gets the type of the node.
     */
    this.getType = () => "repeat";

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

    /**
     * Gets whether an iteration can be made.
     * @returns Whether an iteration can be made.
     */
    this._canIterate = () => {
        if (targetIterationCount !== null) {
            // We can iterate as long as we have not reached our target iteration count.
            return currentIterationCount < targetIterationCount;
        } else if (conditionFunctionName !== null) {
            // Call the condition function to determine whether we can iterate.
            if (typeof board[conditionFunctionName] === "function") {
                return !!(board[conditionFunctionName]());
            } else {
                throw `cannot update repeat node as condition function '${conditionFunction}' is not defined in the blackboard`;
            }
        }

        // If neither an iteration count or a condition function were defined then we can iterate indefinitely.
        return true;
    };
};