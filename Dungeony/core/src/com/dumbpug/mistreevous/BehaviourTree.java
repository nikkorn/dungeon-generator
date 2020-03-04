package com.dumbpug.mistreevous;

import com.dumbpug.mistreevous.builder.TreeBuilder;
import com.dumbpug.mistreevous.node.Root;
import java.util.ArrayList;
import java.util.Arrays;

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
            // Attempt to convert our tokens into a tree of node.
            this.root = TreeBuilder.createTree(tokens);
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
}
