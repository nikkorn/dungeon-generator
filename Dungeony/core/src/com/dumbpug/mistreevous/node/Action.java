package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorators;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An Action leaf node.
 * This represents an immediate or ongoing state of behaviour.
 */
public class Action extends Node {
    /**
     * The action name.
     */
    private String actionName;

    /**
     * Creates a new instance of the Action class.
     * @param decorators The node decorators.
     * @param actionName The action name.
     */
    public Action(Decorators decorators, String actionName) {
        super(decorators);
        this.actionName = actionName;
    }

    @Override
    public void onUpdate(IBoard board) {
        try {
            // Attempt to get the relevant board method.
            Method method = board.getClass().getMethod(this.actionName);

            // Attempt to call the action method and grab the node state as the result.
            State result = (State)method.invoke(board);

            // Check to make sure that the value returned from the action call was a valid state.
            if (result == State.SUCCEEDED || result == State.FAILED || result == State.RUNNING) {
                // The result of calling the action method will be the new state of the node.
                this.setState(result);
            } else {
                throw new RuntimeException("the ACTION method '" + this.actionName + "' must return a node state of SUCCEEDED, FAILED or RUNNING");
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("cannot call ACTION method '" + this.actionName + "' as it is not defined in the blackboard", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ACTION;
    }
}
