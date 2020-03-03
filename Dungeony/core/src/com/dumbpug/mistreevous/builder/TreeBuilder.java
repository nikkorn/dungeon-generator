package com.dumbpug.mistreevous.builder;

import com.dumbpug.mistreevous.Tokens;
import com.dumbpug.mistreevous.nodes.Action;
import com.dumbpug.mistreevous.nodes.Node;
import com.dumbpug.mistreevous.nodes.Root;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Tree creator.
 */
public class TreeBuilder {
    /**
     * Create the tree based on definition tokens.
     * @param tokens The definition tokens.
     * @return The tree based on definition tokens.
     */
    public static Root createTree(Tokens tokens) {
        // There must be at least 3 tokens for the tree definition to be valid. 'ROOT', '{' and '}'.
        if (tokens.size() < 3) {
            throw new RuntimeException("invalid token count");
        }

        // We should have a matching number of '{' and '}' tokens. If not, then there are scopes that have not been properly closed.
        if (tokens.countOf("{") != tokens.countOf("}")) {
            throw new RuntimeException("scope character mismatch");
        }

        // Create the tree stack.
        Stack<ArrayList<Node>> tree = new Stack<ArrayList<Node>>();
        tree.add(new ArrayList<Node>());

        // We should keep processing the raw tokens until we run out of them.
        while (tokens.hasNext()) {
            // Grab the next token.
            String token = tokens.next().toUpperCase();

            // Process the next token.
            switch (token) {
                case "ROOT":
                    // Create the list of children for the root node.
                    ArrayList<Node> children = new ArrayList<Node>();

                    // Create and add the root node to the stack.
                    tree.peek().add(new Root(children));

                    // TODO Try to pick any decorators off of the token stack.

                    // The next token we expect is a '{' which represents the start of children.
                    tokens.pop("{");

                    // The new tree scope is that of the new root nodes children.
                    tree.push(children);
                    break;

                case "ACTION":
                    // We must have arguments defined, as we require an action name argument.
                    ArrayList<String> arguments = getArguments(tokens, null);

                    // Only a single argument should be defined, the action name.
                    if (arguments.size() != 1) {
                        throw new RuntimeException("expected single action name argument");
                    }

                    // TODO Try to pick any decorators off of the token stack.

                    // Get the action name.
                    String actionName = arguments.get(0);

                    // Create and add the action node to the stack.
                    tree.peek().add(new Action(actionName));
                    break;

                case "}":
                    // The '}' character closes the current scope.
                    tree.pop();
                    break;

                default:
                    throw new RuntimeException("unknown token: " + token);
            }
        }

        // Return the root tree node at the base of our tree.
        return (Root)tree.peek().get(0);
    }

    /**
     * Attempt to parse a number of arguments from a tokens queue.
     * Any node arguments will be defined using the syntax "[arg1,arg2,arg3]".
     * @param tokens The tokens queue.
     * @param validator The argument validator.
     * @return any number of arguments from a tokens queue.
     */
    public static ArrayList<String> getArguments(Tokens tokens, IArgumentValidator validator) {
        // Any node arguments will be defined using the syntax "[arg1,arg2,arg3]".
        if (!tokens.peek().equals("[")) {
            return new ArrayList<String>();
        }

        // Get rid of the opening "[".
        tokens.pop("[");

        // Create a list to hold argument and ',' tokens.
        ArrayList<String> argumentListTokens = new ArrayList<String>();

        // Grab all tokens between the '[' and ']'.
        while (tokens.hasNext() && !tokens.peek().equals("]")) {
            argumentListTokens.add(tokens.next());
        }

        // Create a list to hold argument tokens.
        ArrayList<String> argumentTokens = new ArrayList<String>();

        // Process all the tokens between the '[' and ']'.
        for (String token : argumentListTokens) {
            // Get whether the next token should be an argument or separating comma.
            boolean isExpectingArgument = (argumentTokens.size() % 2) == 0;

            // If the current token should be an actual argument then validate it, otherwise it should be a ',' token.
            if (isExpectingArgument) {
                // The current token should be an argument, we should validate it if a validator was provided.
                // The validator is responsible for throwing an exception if the argument validation fails.
                if (validator != null) {
                    validator.validate(token, argumentTokens.size());
                }

                // This is a valid argument!
                argumentTokens.add(token);
            } else {
                // The current token needs to be a ',' or our arguments list is malformed.
                if (!token.equals(",")) {
                    throw new RuntimeException("invalid argument list, expected ',' or ']' but got : " + token);
                }
            }
        }

        // The arguments list should terminate with a ']' token.
        tokens.pop("]");

        // Return the argument list.
        return argumentTokens;
    }
}
