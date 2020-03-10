package com.dumbpug.mistreevous.node;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;
import com.dumbpug.mistreevous.decorator.Decorators;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A Condition leaf node.
 * This will succeed or fail immediately based on a board predicate, without moving to the 'RUNNING' state.
 */
public class Condition extends Node {
    /**
     * The condition name.
     */
    private String condition;

    /**
     * Creates a new instance of the Condition class.
     * @param decorators The node decorators.
     * @param condition The condition name.
     */
    public Condition(Decorators decorators, String condition) {
        super(decorators);
        this.condition = condition;
    }

    @Override
    public void onUpdate(IBoard board) {
        try {
            // Attempt to get the relevant board method.
            Method method = board.getClass().getMethod(this.condition);

            // Attempt to call the condition method and grab the result.
            boolean result = (boolean)method.invoke(board);

            // The result of calling the condition method will determine the resulting node state.
            this.setState(result ? State.SUCCEEDED : State.FAILED);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("cannot call CONDITION method '" + this.condition + "' as it is not defined in the blackboard", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.CONDITION;
    }
}
