// Create a test enemy behaviour tree definition.

// TERMINOLOGY

// CONTEXT
// A context represents state for a node and any children node in the tree.

// Moving to a new node in the tree will create a brand new context object for the node.
// This context will be passed in to every 'test()', 'onEntry()', 'onUpdate()' and 'onExit()' call for that node.
// The context is thrown away when a node stops being visited (is no longer active).
// Each context object will have a 'getParentContext()' function on it which will return the context of the parent node.

// STATE
// An external object that is passed throughout the tree to be used much in the same way as context objects.
// The state is never destroyed.

const behaviourTreeDefinition = [
    {
        type: "branch",
        nodes: [
            {
                type: "node",
                name: "patrolling",
                test: (context, state) => !state.canSeePlayer(),
                onEntry: (context, state) => console.log("Starting Patrol!"),
                onUpdate: (context, state) => {},
                onExit: (context, state) => console.log("Stopping Patrol!")
            },
            {
                type: "cycle",
                name: "spotted_player",
                nodes: [
                    {
                        type: "node",
                        name: "act_suprised",
                        test: (context, state) => context.hasJustSpottedPlayer(),
                        onEntry: (context, state) => console.log("Starting acting surprised after seeing player!"),
                        onUpdate: (context, state) => {},
                        onExit: (context, state) => console.log("Stopping acting surprised after seeing player!")
                    },
                    {
                        type: "branch",
                        name: "following_player",
                        nodes: [
                            {
                                type: "node",
                                name: "pursue_player",
                                test: (context, state) => !context.isPlayerInAttackDistance(),
                                onEntry: (context, state) => console.log("Walking towards the player!"),
                                onUpdate: (context, state) => {},
                                onExit: (context, state) => console.log("Stopped walking towards the player!")
                            },
                            {
                                type: "cycle",
                                name: "attacking_player_cycle",
                                nodes: [
                                    {
                                        type: "node",
                                        name: "attack_player",
                                        test: (context, state) => !state.isPlayerInAttackDistance(),
                                        onEntry: (context, state) => console.log("Walking towards the player!"),
                                        onUpdate: (context, state) => {
                                            // Hit the player super hard!
                                            state.attackPlayer();

                                            // TODO Set cool down on node state.
                                        },
                                        onExit: (context, state) => console.log("Stopped walking towards the player!")
                                    },
                                    {
                                        type: "node",
                                        name: "attack_cool_down_wait",
                                        test: (context, state) => !state.isPlayerInAttackDistance(),
                                        onEntry: (context, state) => console.log("Taking a break from attacking!"),
                                        onUpdate: (context, state) => {},
                                        onExit: (context, state) => console.log("Stopped walking towards the player!")
                                    }
                                ],
                                test: (context, state) => !context.isPlayerInAttackDistance(),
                                onEntry: (context, state) => console.log("Walking towards the player!"),
                                onUpdate: (context, state) => {},
                                onExit: (context, state) => console.log("Stopped walking towards the player!")
                            }

                        ],
                        test: (context, state) => context.canFollowPlayer(),
                        onEntry: (context, state) => console.log("Starting to follow the player!"),
                        onUpdate: (context, state) => {},
                        onExit: (context, state) => {}
                    },
                    {
                        type: "node",
                        name: "lost_player",
                        test: (context, state) => context.hasJustLostPlayer(),
                        onEntry: (context, state) => console.log("Just lost the player!"),
                        onUpdate: (context, state) => {},
                        onExit: (context, state) => {}
                    }
                ],
                test: (context, state) => state.canSeePlayer(),
                onEntry: (context, state) => console.log("Starting the spotted player cycle!"),
                onUpdate: (context, state) => {},
                onExit: (context, state) => console.log("Stopping the spotted player cycle!")
            }
        ]
    }
];