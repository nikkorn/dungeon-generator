package com.dumbpug.mistreevous.test;

import com.dumbpug.mistreevous.BehaviourTree;
import com.dumbpug.mistreevous.decorator.Step;

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        //test.createTreeTest();
        //test.reflectiveInvokeTest();
        //test.enemyTest();
        //test.waitTest();
        test.guardTest();
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

    public void enemyTest() {
        String definition = "root{sequence{condition[isHungry]action[roar]action[scream]}}";

        BehaviourTree tree = new BehaviourTree(definition, new TestEnemy());

        tree.step();
        tree.step();
        tree.step();
    }

    public void waitTest() {
        String definition = "root{sequence{wait[1000]action[roar]wait[1000]action[scream]wait[1000]action[roar]wait[1000]action[scream]}}";

        BehaviourTree tree = new BehaviourTree(definition, new TestEnemy());

        while (!tree.isComplete()) {
            tree.step();
        }
    }

    public void guardTest() {
        String definition = "root{ repeat until[isHungry] { sequence{ wait[500] action[roar] } } }";

        BehaviourTree tree = new BehaviourTree(definition, new TestEnemy());

        while (!tree.isComplete()) {
            tree.step();
        }
    }
}