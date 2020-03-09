package com.dumbpug.mistreevous.test;

import com.dumbpug.mistreevous.IBoard;
import com.dumbpug.mistreevous.State;

public class TestBoard implements IBoard {
    public State someThing() {
        return State.SUCCEEDED;
    }
}