package com.dumbpug.mistreevous.decorator;

import com.dumbpug.mistreevous.IBoard;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A STEP decorator which defines a blackboard function to call when the decorated node is updated.
 */
public class Step extends Decorator {
    /**
     * Creates a new instance of the Step class.
     * @param method The board method.
     */
    public Step(String method) {
        super(method);
    }

    /**
     * Call the relevant board method.
     * @param board The board.
     */
    public void call(IBoard board) {
        try {
            // Attempt to get the relevant board method.
            Method method = board.getClass().getMethod(this.method);

            // Attempt to invoke the board method.
            method.invoke(board);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("cannot call STEP decorator method '" + this.method + "' as it is not defined in the blackboard", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Type getType() {
        return Type.STEP;
    }
}