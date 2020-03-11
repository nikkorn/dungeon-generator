package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorators;

/**
 * A WAIT node.
 * The state of this node will change to SUCCEEDED after a duration of time.
 */
public class Wait extends Node {
    /**
     * The duration that this node will wait to succeed in milliseconds, or the earliest if longestDuration is defined.
     */
    private long duration;
    /**
     * The longest possible duration in milliseconds that this node will wait to succeed.
     */
    private Long longestDuration;
    /**
     * The time in milliseconds at which this node was first updated.
     */
    private Long initialUpdateTime = null;

    /**
     * Creates a new instance of the Wait class.
     * @param decorators The node decorators.
     * @param duration The condition name.
     */
    public Wait(Decorators decorators, long duration, Long longestDuration) {
        super(decorators);
        this.duration = duration;
        this.longestDuration = longestDuration;
    }

    @Override
    public void onUpdate(IBoard board) {
        // If this node is in the READY state then we need to set the initial update time.
        if (this.is(State.READY)) {
            // Set the initial update time.
            initialUpdateTime = System.currentTimeMillis();

            // If a longestDuration value was defined then we will be randomly picking a duration between the
            // shortest and longest duration. If it was not defined, then we will be just using the duration.
            duration = (long)(longestDuration != null ? Math.floor(Math.random() * (longestDuration - duration + 1) + duration) : duration);

            // The node is now running until we finish waiting.
            this.setState(State.RUNNING);
        }

        // Have we waited long enough?
        if (System.currentTimeMillis() >= (initialUpdateTime + duration)) {
            // We have finished waiting!
            this.setState(State.SUCCEEDED);
        }
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.WAIT;
    }
}
