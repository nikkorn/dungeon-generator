package com.dumbpug.mistreevous.builder;

import com.dumbpug.mistreevous.Tokens;
import com.dumbpug.mistreevous.decorator.Decorator;
import com.dumbpug.mistreevous.decorator.Entry;
import com.dumbpug.mistreevous.decorator.Exit;
import com.dumbpug.mistreevous.decorator.Step;
import com.dumbpug.mistreevous.guard.Until;
import com.dumbpug.mistreevous.guard.While;
import java.util.ArrayList;

/**
 * Builder of decorator instances.
 */
public class DecoratorBuilder {
    /**
     * Attempt to pull a decorator from a token queue.
     * @param tokens The array of remaining tokens.
     * @returns A decorator if available, or null if not.
     */
    public static Decorator createDecorator(Tokens tokens) {
        // If there are no tokens then we cannot have a decorator.
        if (!tokens.hasNext()) {
            return null;
        }

        Decorator.Type decoratorType = null;
        try {
            // Attempt to parse the next token as a decorator type.
            decoratorType = Decorator.Type.valueOf(tokens.peek().toUpperCase());
        } catch (IllegalArgumentException e) {
            // The next token is not a valid decorator type.
            return null;
        }

        // Swallow the decorator identifier token.
        tokens.next();

        // Get the decorator arguments.
        ArrayList<String> arguments = TreeBuilder.getArguments(tokens, null);

        // A decorator should only ever have a single argument, a reference to a board method.
        if (arguments.size() != 1) {
            throw new RuntimeException("expected parameter for decorator: " + decoratorType);
        }

        // Get the board method reference argument.
        String method = arguments.get(0);

        // Create the decorator instance based on its type.
        switch (decoratorType) {
            case ENTRY:
                return new Entry(method);
            case STEP:
                return new Step(method);
            case EXIT:
                return new Exit(method);
            case WHILE:
                return new While(method);
            case UNTIL:
                return new Until(method);
            default:
                // We do not have a way to create a decorator instance for this type.
                throw new RuntimeException("cannot create decorator instance for type: " + decoratorType);
        }
    }
}
