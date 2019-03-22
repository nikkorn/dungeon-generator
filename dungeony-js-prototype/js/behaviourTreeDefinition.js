// Create a test enemy behaviour tree definition.

// TERMINOLOGY

// BLACKNOARD
// An external object that is passed throughout the tree to hold blackboard and functions.
// The blackboard is never destroyed.

// TODO Add decorator nodes. A decorator node has a single child an can modify its behaviour (e.g. make it loop, invert its return type).

// Sequence and selector nodes have onEntry and onExit functions, while actions also have a onUpdate function from which we return either:
//    SUCCESS: The node was completed succesfully. If the node is one in a sequence then we should move on to the next, or if it is the last then the sequence node returns SUCCESS.
//    FAIL   : The node failed to complete. If the node is one in a sequence then do no process any following nodes and then the sequence node returns FAIL.
//    RESUME : The node is still active.

// TODO Add ability to register a node to be reused.

const behaviourTreeDefinition = [
    {
        type: "selector",
        nodes: [
            {
                type: "action",
                name: "patrolling",
                test: (blackboard) => !blackboard.canSeePlayer(),
                onEntry: (blackboard) => console.log("Starting Patrol!"),
                onUpdate: (blackboard) => {},
                onExit: (blackboard) => console.log("Stopping Patrol!")
            },
            {
                type: "sequence",
                name: "spotted_player",
                nodes: [
                    {
                        type: "action",
                        name: "act_suprised",
                        test: (blackboard) => blackboard.hasJustSpottedPlayer(),
                        onEntry: (blackboard) => console.log("Starting acting surprised after seeing player!"),
                        onUpdate: (blackboard) => {},
                        onExit: (blackboard) => console.log("Stopping acting surprised after seeing player!")
                    },
                    {
                        type: "selector",
                        name: "following_player",
                        nodes: [
                            {
                                type: "action",
                                name: "pursue_player",
                                test: (blackboard) => !blackboard.isPlayerInAttackDistance(),
                                onEntry: (blackboard) => console.log("Walking towards the player!"),
                                onUpdate: (blackboard) => {},
                                onExit: (blackboard) => console.log("Stopped walking towards the player!")
                            },
                            {
                                type: "sequence",
                                name: "attacking_player_cycle",
                                nodes: [
                                    {
                                        type: "action",
                                        name: "attack_player",
                                        test: (blackboard) => !blackboard.isPlayerInAttackDistance(),
                                        onEntry: (blackboard) => console.log("Walking towards the player!"),
                                        onUpdate: (blackboard) => {
                                            // Hit the player super hard!
                                            blackboard.attackPlayer();

                                            // TODO Set cool down on node blackboard.
                                        },
                                        onExit: (blackboard) => console.log("Stopped walking towards the player!")
                                    },
                                    {
                                        type: "action",
                                        name: "attack_cool_down_wait",
                                        test: (blackboard) => !blackboard.isPlayerInAttackDistance(),
                                        onEntry: (blackboard) => console.log("Taking a break from attacking!"),
                                        onUpdate: (blackboard) => {},
                                        onExit: (blackboard) => console.log("Stopped walking towards the player!")
                                    }
                                ],
                                test: (blackboard) => !blackboard.isPlayerInAttackDistance(),
                                onEntry: (blackboard) => console.log("Walking towards the player!"),
                                onExit: (blackboard) => console.log("Stopped walking towards the player!")
                            }

                        ],
                        test: (blackboard) => blackboard.canFollowPlayer(),
                        onEntry: (blackboard) => console.log("Starting to follow the player!"),
                        onExit: (blackboard) => {}
                    },
                    {
                        type: "action",
                        name: "lost_player",
                        test: (blackboard) => blackboard.hasJustLostPlayer(),
                        onEntry: (blackboard) => console.log("Just lost the player!"),
                        onUpdate: (blackboard) => {},
                        onExit: (blackboard) => {}
                    }
                ],
                test: (blackboard) => blackboard.canSeePlayer(),
                onEntry: (blackboard) => console.log("Starting the spotted player sequence!"),
                onExit: (blackboard) => console.log("Stopping the spotted player sequence!")
            }
        ]
    }
];