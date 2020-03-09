package com.dumbpug.mistreevous.test;

import com.dumbpug.mistreevous.BehaviourTree;
import com.dumbpug.mistreevous.decorator.Step;

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        test.createTreeTest();
        test.reflectiveInvokeTest();
    }

    public void createTreeTest() {
        String definition = "root{action[act]}";

        BehaviourTree tree = new BehaviourTree(definition, null);
    }

    public void reflectiveInvokeTest() {
        TestBoard testClass = new TestBoard();

        Step step = new Step("someThing");

        step.call(testClass);
    }
}