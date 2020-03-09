package com.dumbpug.mistreevous.decorator;

import com.dumbpug.mistreevous.IBoard;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An EXIT decorator which defines a blackboard function to call when the
 * decorated node is updated and moves to a finished state or is aborted.
 */
public class Exit extends Decorator {
    /**
     * Creates a new instance of the Exit class.
     * @param method The board method.
     */
    public Exit(String method) {
        super(method);
    }

    /**
     * Call the relevant board method.
     * @param board The board.
     * @param isSuccess Whether the decorated node was left with a success state.
     * @param isAborted Whether the decorated node was aborted.
     */
    public void call(IBoard board, boolean isSuccess, boolean isAborted) {
        try {
            // Attempt to get the relevant board method.
            Method method = board.getClass().getMethod(this.method, boolean.class, boolean.class);

            // Attempt to invoke the board method.
            method.invoke(board, isSuccess, isAborted);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("cannot call EXIT decorator method '" + this.method + "' as it is not defined in the blackboard", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Type getType() {
        return Type.EXIT;
    }
}