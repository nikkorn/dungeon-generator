package com.dumbpug.mistreevous.guard;

import com.dumbpug.mistreevous.IBoard;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A WHILE guard which is satisfied as long as the given condition remains true.
 */
public class While extends Guard {
    /**
     * Creates a new instance of the While class.
     * @param method The board method.
     */
    public While(String method) {
        super(method);
    }

    @Override
    public boolean isSatisfied(IBoard board) {
        try {
            // Attempt to get the relevant board method.
            Method method = board.getClass().getMethod(this.method);

            // Attempt to call the guard method and return the result.
            return (boolean)method.invoke(board);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("cannot call WHILE method '" + this.method + "' as it is not defined in the blackboard", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Type getType() {
        return Type.WHILE;
    }
}
