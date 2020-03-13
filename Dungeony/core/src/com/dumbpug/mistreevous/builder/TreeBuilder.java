package com.dumbpug.mistreevous.builder;

import com.dumbpug.mistreevous.Tokens;
import com.dumbpug.mistreevous.decorator.Decorator;
import com.dumbpug.mistreevous.decorator.Decorators;
import com.dumbpug.mistreevous.guard.GuardPath;
import com.dumbpug.mistreevous.node.Action;
import com.dumbpug.mistreevous.node.Composite;
import com.dumbpug.mistreevous.node.Condition;
import com.dumbpug.mistreevous.node.Node;
import com.dumbpug.mistreevous.node.Repeat;
import com.dumbpug.mistreevous.node.Root;
import com.dumbpug.mistreevous.node.Selector;
import com.dumbpug.mistreevous.node.Sequence;
import com.dumbpug.mistreevous.node.Wait;
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

        ArrayList<Node> children    = null;
        Decorators decorators       = null;
        ArrayList<String> arguments = null;

        // We should keep processing the raw tokens until we run out of them.
        while (tokens.hasNext()) {
            // Grab the next token.
            String token = tokens.next().toUpperCase();

            // Process the next token.
            switch (token) {
                case "ROOT":
                    // Create the list of children for the root node.
                    children = new ArrayList<Node>();

                    // Try to pick any decorators off of the token stack.
                    decorators = getDecorators(tokens);

                    // Create and add the root node to the stack.
                    tree.peek().add(new Root(decorators, children));

                    // The next token we expect is a '{' which represents the start of children.
                    tokens.pop("{");

                    // The new tree scope is that of the new root node children.
                    tree.push(children);
                    break;


                case "SEQUENCE":
                    // Create the list of children for the sequence node.
                    children = new ArrayList<Node>();

                    // Try to pick any decorators off of the token stack.
                    decorators = getDecorators(tokens);

                    // Create and add the sequence node to the stack.
                    tree.peek().add(new Sequence(decorators, children));

                    // The next token we expect is a '{' which represents the start of children.
                    tokens.pop("{");

                    // The new tree scope is that of the new sequence node children.
                    tree.push(children);
                    break;


                case "SELECTOR":
                    // Create the list of children for the selector node.
                    children = new ArrayList<Node>();

                    // Try to pick any decorators off of the token stack.
                    decorators = getDecorators(tokens);

                    // Create and add the selector node to the stack.
                    tree.peek().add(new Selector(decorators, children));

                    // The next token we expect is a '{' which represents the start of children.
                    tokens.pop("{");

                    // The new tree scope is that of the new selector node children.
                    tree.push(children);
                    break;


                case "REPEAT":
                    // We may have two optional arguments defined.
                    arguments = getArguments(tokens, new IArgumentValidator() {
                        @Override
                        public void validate(String argument, int index) {
                            // Each repeat node argument must be a valid integer number.
                            Integer.parseInt(argument);
                        }
                    });

                    // Up to two arguments can be defined.
                    if (arguments.size() > 2) {
                        throw new RuntimeException("invalid number of repeat node arguments defined");
                    }

                    // Get the optional iteration and max iteration arguments.
                    Integer iterations    = arguments.size() > 0 ? Integer.parseInt(arguments.get(0)) : null;
                    Integer maxIterations = arguments.size() > 1 ? Integer.parseInt(arguments.get(1)) : null;

                    // Try to pick any decorators off of the token stack.
                    decorators = getDecorators(tokens);

                    // Create the list of children for the repeat node.
                    children = new ArrayList<Node>();

                    // Create and add the repeat node to the stack.
                    tree.peek().add(new Repeat(decorators, children, iterations, maxIterations));

                    // The next token we expect is a '{' which represents the start of children.
                    tokens.pop("{");

                    // The new tree scope is that of the new repeat node children.
                    tree.push(children);
                    break;


                case "ACTION":
                    // We must have arguments defined, as we require an action name argument.
                    arguments = getArguments(tokens, null);

                    // Only a single argument should be defined, the action name.
                    if (arguments.size() != 1) {
                        throw new RuntimeException("expected single action name argument");
                    }

                    // Get the action name.
                    String actionName = arguments.get(0);

                    // Try to pick any decorators off of the token stack.
                    decorators = getDecorators(tokens);

                    // Create and add the action node to the stack.
                    tree.peek().add(new Action(decorators, actionName));
                    break;


                case "CONDITION":
                    // We must have arguments defined, as we require a condition name argument.
                    arguments = getArguments(tokens, null);

                    // Only a single argument should be defined, the condition name.
                    if (arguments.size() != 1) {
                        throw new RuntimeException("expected single condition name argument");
                    }

                    // Get the condition name.
                    String conditionName = arguments.get(0);

                    // Try to pick any decorators off of the token stack.
                    decorators = getDecorators(tokens);

                    // Create and add the condition node to the stack.
                    tree.peek().add(new Condition(decorators, conditionName));
                    break;


                case "WAIT":
                    // We must have arguments defined, as we require at least a single duration defined.
                    arguments = getArguments(tokens, new IArgumentValidator() {
                        @Override
                        public void validate(String argument, int index) {
                            // Each wait node argument must be a valid long number.
                            Long.parseLong(argument);
                        }
                    });

                    // Either 1 or 2 arguments should be defined.
                    if (arguments.size() < 1 || arguments.size() > 2) {
                        throw new RuntimeException("invalid number of wait node duration arguments defined");
                    }

                    // Get the wait duration and optional longest duration.
                    long duration        = Long.parseLong(arguments.get(0));
                    Long longestDuration = arguments.size() > 1 ? Long.parseLong(arguments.get(1)) : null;

                    // Try to pick any decorators off of the token stack.
                    decorators = getDecorators(tokens);

                    // Create and add the wait node to the stack.
                    tree.peek().add(new Wait(decorators, duration, longestDuration));
                    break;


                case "}":
                    // The '}' character closes the current scope.
                    tree.pop();
                    break;


                default:
                    throw new RuntimeException("unknown token: " + token);
            }
        }

        // Get the root node at the base of out tree.
        Root root = (Root)tree.peek().get(0);

        // Validate our root node, and subsequently all child nodes in the tree.
        validateNode(root);

        // Apply guard paths to all nodes from the root.
        applyLeafNodeGuardPaths(root);

        // Return the root tree node.
        return root;
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
        for (int tokenIndex = 0; tokenIndex < argumentListTokens.size(); tokenIndex++) {
            // Get the current token.
            String token = argumentListTokens.get(tokenIndex);

            // Get whether the next token should be an argument or separating comma.
            boolean isExpectingArgument = (tokenIndex % 2) == 0;

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

    /**
     * Attempt to parse a number of decorators from a tokens queue.
     * Any decorators will be defined using the syntax "name[arg1,arg2,arg3]".
     * @param tokens The tokens queue.
     * @return The node decorators.
     */
    public static Decorators getDecorators(Tokens tokens) {
        // Create a list to hold the decorators we find.
        ArrayList<Decorator> decorators = new ArrayList<Decorator>();

        // Attempt to get the first decorator.
        Decorator next = DecoratorBuilder.createDecorator(tokens);

        // Keep trying to parse decorators from the tokens queue.
        while (next != null) {
            // Add the decorator we found to the list of decorators.
            decorators.add(next);

            // Attempt to get the next decorator.
            next = DecoratorBuilder.createDecorator(tokens);
        }

        return new Decorators(decorators);
    }

    /**
     * Apply guard paths for every leaf node in the behaviour tree.
     * @param root The root node.
     */
    public static void applyLeafNodeGuardPaths(Root root) {
        for (ArrayList<Node> path : getNodePaths(root)) {
            // Each node in the current path will have to be assigned a guard path, working from the root outwards.
            for (int depth = 0; depth < path.size(); depth++) {
                // Get the node in the path at the current depth.
                Node currentNode = path.get(depth);

                // The node may already have been assigned a guard path, if so just skip it.
                if (currentNode.hasGuardPath()) {
                    continue;
                }

                // Create the path of nodes to the current node.
                ArrayList<Node> nodes = new ArrayList<Node>(path.subList(0, depth + 1));

                // Assign the guard path to the current node.
                currentNode.setGuardPath(new GuardPath(nodes));
            }
        }
    }

    /**
     * Gets a multi-dimensional list of root->leaf node paths.
     * @param root The root node.
     * @returns A multi-dimensional list of root->leaf node paths.
     */
    public static ArrayList<ArrayList<Node>> getNodePaths(Root root) {
        ArrayList<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();

        // Find all leaf node paths, starting from the root.
        findLeafNodes(paths, new ArrayList<Node>(), root);

        return paths;
    }

    /**
     * Collects all leaf nodes in the given node.
     * @param paths All node paths.
     * @param path The current node path.
     * @param node The current node.
     */
    private static void findLeafNodes(ArrayList<ArrayList<Node>> paths, ArrayList<Node> path, Node node) {
        // Add the current node to the path.
        path.add(node);

        // Check whether the current node is a leaf node.
        if (node.isLeafNode()) {
            paths.add(path);
        } else {
            for (Node child : ((Composite)node).getChildren()) {
                findLeafNodes(paths, path, child);
            }
        }
    }

    /**
     * Validate the specified node and any child nodes recursively.
     * @param node The node to validate.
     */
    public static void validateNode(Node node) {
        // Validate the node.
        node.validate();

        // Validate any child nodes.
        if (!node.isLeafNode()) {
            for (Node child : ((Composite)node).getChildren()) {
                validateNode(child);
            }
        }
    }
}
