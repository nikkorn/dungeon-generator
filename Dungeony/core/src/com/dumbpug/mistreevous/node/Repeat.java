package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorators;
import java.util.ArrayList;

/**
 * A REPEAT node.
 * The node has a single child which can have:
 * -- A number of iterations for which to repeat the child node.
 * -- An infinite repeat loop if neither an iteration count or a condition function is defined.
 * The REPEAT node will stop and have a 'FAILED' state if its child is ever in a 'FAILED' state after an update.
 * The REPEAT node will attempt to move on to the next iteration if its child is ever in a 'SUCCEEDED' state.
 */
public class Repeat extends Composite {
    /**
     * The number of iterations to repeat the child node, or the minimum number of iterations if maximumIterations is defined.
     */
    private Integer iterations;
    /**
     * The maximum number of iterations to repeat the child node.
     */
    private Integer maximumIterations;
    /**
     * The number of target iterations to make.
     */
    private Integer targetIterationCount = null;
    /**
     * The current iteration count.
     */
    private int currentIterationCount = 0;

    /**
     * Creates a new instance of the Repeat class.
     * @param decorators The node decorators.
     * @param children   The child node of this composite node.
     * @param iterations The number of iterations to repeat the child node, or the minimum number of iterations if maximumIterations is defined.
     * @param maximumIterations The maximum number of iterations to repeat the child node.
     */
    public Repeat(Decorators decorators, ArrayList<Node> children, Integer iterations, Integer maximumIterations) {
        super(decorators, children);
        this.iterations        = iterations;
        this.maximumIterations = maximumIterations;
    }

    @Override
    public void onUpdate(IBoard board) {
        // Get the child node.
        Node child = this.getChildren().get(0);

        // If this node is in the READY state then we need to reset the child and the target iteration count.
        if (this.is(State.READY)) {
            // Reset the child node.
            child.reset();

            // Set the target iteration count.
            this.setTargetIterationCount();
        }

        // Do a check to see if we can iterate. If we can then this node will move into the 'RUNNING' state.
        // If we cannot iterate then we have hit our target iteration count, which means that the node has succeeded.
        if (this.canIterate()) {
            // This node is in the running state and can do its initial iteration.
            this.setState(State.RUNNING);

            // We may have already completed an iteration, meaning that the child node will be in the SUCCEEDED state.
            // If this is the case then we will have to reset the child node now.
            if (child.is(State.SUCCEEDED)) {
                child.reset();
            }

            // Update the child of this node.
            child.update(board);

            // If the child moved into the FAILED state when we updated it then there is nothing left to do and this node has also failed.
            // If it has moved into the SUCCEEDED state then we have completed the current iteration.
            if (child.is(State.FAILED)) {
                // The child has failed, meaning that this node has failed.
                this.setState(State.FAILED);

                return;
            } else if (child.is(State.SUCCEEDED)) {
                // We have completed an iteration.
                currentIterationCount += 1;
            }
        } else {
            // This node is in the 'SUCCEEDED' state as we cannot iterate any more.
            this.setState(State.SUCCEEDED);
        }
    }

    @Override
    public void reset() {
        super.reset();

        // Reset the current iteration count.
        this.currentIterationCount = 0;
    }

    @Override
    public void validate() {
        // A repeat node must have a single child.
        if (this.getChildren().size() != 1) {
            throw new RuntimeException("A REPEAT node must have a single child node.");
        }
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.REPEAT;
    }

    /**
     * Sets the target iteration count.
     */
    private void setTargetIterationCount() {
        // Are we dealing with a finite number of iterations?
        if (this.iterations != null) {
            // If we have maximumIterations defined then we will want a random iteration count bounded by iterations and maximumIterations.
            if (this.maximumIterations != null) {
                this.targetIterationCount = (int)Math.floor(Math.random() * (maximumIterations - iterations + 1) + iterations);
            } else {
                this.targetIterationCount = iterations;
            }
        } else {
            targetIterationCount = null;
        }
    }

    /**
     * Gets whether an iteration can be made.
     * @returns Whether an iteration can be made.
     */
    private boolean canIterate() {
        if (targetIterationCount != null) {
            // We can iterate as long as we have not reached our target iteration count.
            return currentIterationCount < targetIterationCount;
        }

        // If neither an iteration count or a condition function were defined then we can iterate indefinitely.
        return true;
    }
}
