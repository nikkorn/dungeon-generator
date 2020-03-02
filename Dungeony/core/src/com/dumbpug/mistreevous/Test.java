package com.dumbpug.mistreevous;

public class Test {
    public static void main(String[] args) {
        String definition = "root{action[act]}";

        BehaviourTree tree = new BehaviourTree(definition, null);
    }
}