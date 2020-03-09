package com.dumbpug.mistreevous.decorator;

import com.dumbpug.mistreevous.IBoard;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An ENTRY decorator which defines a blackboard function to call
 * when the decorated node is updated and moves out of running state.
 */
public class Entry extends Decorator {
    /**
     * Creates a new instance of the Entry class.
     * @param method The board method.
     */
    public Entry(String method) {
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
            throw new RuntimeException("cannot call ENTRY decorator method '" + this.method + "' as it is not defined in the blackboard", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Type getType() {
        return Type.ENTRY;
    }
}
