package com.dumbpug.mistreevous.builder;

/**
 * A functional interface for validating a raw node argument.
 */
public interface IArgumentValidator {
    /**
     * Validate the node argument at the given index.
     * @param argument The argument.
     * @param index The argument index.
     */
    void validate(String argument, int index);
}
