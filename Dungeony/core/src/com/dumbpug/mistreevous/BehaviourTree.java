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
     * The board.
     */
    private IBoard board;

    /**
     * Creates a new instance of the BehaviourTree class.
     * @param definition The tree definition.
     * @param board The board.
     */
    public BehaviourTree(String definition, IBoard board) {
        // Grab a reference to the board.
        this.board = board;

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
     * Gets whether the tree is in the running state.
     * @returns Whether the tree is in the running state.
     */
    public boolean isRunning() {
        return this.root.is(State.RUNNING);
    }

    /**
     * Gets whether the tree has moved to a completed state.
     * @returns Whether the tree has moved to a completed state.
     */
    public boolean isComplete() {
        return this.root.is(State.SUCCEEDED) || this.root.is(State.FAILED);
    }

    /**
     * Step the tree.
     */
    public void step() {
        // If the root node has already been stepped to completion then we need to reset it.
        if (this.root.is(State.SUCCEEDED) || this.root.is(State.FAILED)) {
            this.root.reset();
        }

        try {
            // Update the root node, which will subsequently update the entire tree.
            this.root.update(this.board);
        } catch (Exception e) {
            throw new RuntimeException("error stepping tree", e);
        }
    }

    /**
     * Reset the tree.
     */
    public void reset() {
        this.root.reset();
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
