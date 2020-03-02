package com.dumbpug.mistreevous;

import com.dumbpug.mistreevous.nodes.Node;
import com.dumbpug.mistreevous.nodes.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * A behaviour tree.
 */
public class BehaviourTree {
    /**
     * The root tree node.
     */
    private Root root;

    /**
     * Creates a new instance of the BehaviourTree class.
     * @param definition The tree definition.
     * @param board The board.
     */
    public BehaviourTree(String definition, IBoard board) {
        // Get the definition as a list of tokens.
        Tokens tokens = parseDefinitionTokens(definition);

        try {
            // Attempt to convert our tokens into a tree of nodes.
            this.root = createRootNode(tokens);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create tree instance", e);
        }
    }

    /**
     * Parse a tree definition into a list of tokens.
     * @param definition The tree definition.
     * @return A list of tokens.
     */
    private Tokens parseDefinitionTokens(String definition) {
        definition = definition.replace("(", " ( ");
        definition = definition.replace(")", " ) ");
        definition = definition.replace("{", " { ");
        definition = definition.replace("}", " } ");
        definition = definition.replace("[", " [ ");
        definition = definition.replace("]", " ] ");
        definition = definition.replace(",", " , ");

        ArrayList<String> tokens = new ArrayList<String>();

        for(String token : Arrays.asList(definition.split(" "))) {
            if (!token.trim().isEmpty()) {
                tokens.add(token.trim());
            }
        }

        return new Tokens(tokens);
    }

    /**
     * Create and populate the root tree node.
     * @param tokens The tokens representing the entire tree.
     * @return The root tree node.
     */
    private Root createRootNode(Tokens tokens) {
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
}
