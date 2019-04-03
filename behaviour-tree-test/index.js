const definitionTextArea = document.getElementById("definition-text-area");
const resultTextArea     = document.getElementById("result-text-area");
const blackboardTextArea = document.getElementById("blackboard-text-area");
const treeViewWrapper    = document.getElementById("tree-view-wrapper");

/**
 * The behaviour tree.
 */
let behaviourTree;

/**
 * The behaviour tree blackboard.
 */
let blackboard;

// Set a test definition.
definitionTextArea.innerHTML =
`ROOT {
    SELECTOR {
        SEQUENCE {
            CONDITION:PlayerIsInView
            ACTION:ShoutAtPlayer
            SEQUENCE {
                CONDITION:PlayerIsInAttackDistance
                ACTION:AttackPlayer
                ACTION:Wait
            }
            ACTION:FollowPlayer
        }
        SEQUENCE {
            ACTION:WalkToPatrolDestination
            ACTION:Wait
        }
    }
}`;

// Set a test blackboard in the blackboard text area.
blackboardTextArea.innerHTML = "PlayerIsInView: () => true";

/**
 * Handles definition updates.
 */
function onDefinitionUpdate() {
    // Clear away the existing tree view.
    treeViewWrapper.innerHTML = "";

    // Create the blackboard.
    // In this page the blackboard will be kept up-to-date with changes made to the blackboard text area. 
    blackboard = {};

    // Do the initial blackboard update.
    onBlackboardUpdate();

    try {
        // Try to create the behaviour tree.
        behaviourTree = new BehaviourTree(definitionTextArea.value, blackboard);

        // We created the behaviour tree without an issue.
        resultTextArea.innerHTML             = "OK";
        resultTextArea.style.backgroundColor = "#d6f9d4";
    } catch (exception) {
        // There was an error creating the behaviour tree!
        behaviourTree = null;

        // Show the exception on the page.
        resultTextArea.innerHTML             = exception;
        resultTextArea.style.backgroundColor = "#fcc2c2";

        // There is nothing left to do.
        return;
    }

    const nodes = [];

    const processNode = (node, parentUid) => {
        // Push the current node into the nodes array.
        nodes.push({ 
            id: node.uid,
            type: node.type, 
            caption: node.caption(), 
            parent: parentUid 
        });

        // Process each of the nodes children.
        (node.children || []).forEach((child) => processNode(child, node.uid));
    };

    // Convert the nested AST node structure into an array of nodes with which to build the tree view.
    processNode(behaviourTree.getRootASTNode(), null);

    // Build the tree view.
    buildTreeView(nodes);
};

/**
 * Update the behaviour tree blackboard to match the blackboard defined in the editor.
 */
function onBlackboardUpdate() {
    // Create the blackboard proxy.
    const blackboardProxy = eval('({' + blackboardTextArea.value + '})');

    // Update the blackboard.
    for (var key in blackboardProxy) {
        if (blackboardProxy.hasOwnProperty(key)) {
            blackboard[key] = blackboardProxy[key];
        }
    }
};

/**
 * Handles clicks on the 'tick' button.
 */
function onTickButtonPressed() {
    // If there is no behaviour tree then there is nothing to do here.
    if (!behaviourTree) {
        return;
    }

    // Update the BT blackboard.
    onBlackboardUpdate();

    // Update the behaviour tree.
    // TODO Eventually replace with call like 'behaviourTree.update();'
    behaviourTree.getRootBTNode().update(blackboard);
};

/**
 * Handles clicks on the 'reset' button.
 */
function onResetButtonPressed() {
    // Do the definition update.
    onDefinitionUpdate();
};

/**
 * Build the tree view.
 */
function buildTreeView(nodes) {
    // Build the tree view.
    var options = {
        data: nodes,
        nodeIdField: "id",
        nodeNameField: "caption",
        nodeTypeField: "type",
        nodeParentField: "parent",
        definition: {
            default: {
                tooltip: function (node) { return node.item.caption },
                template: (node) => `<div class='tree-view-node'>
                <div class='tree-view-icon tree-view-icon-${node.item.type}'>
                <img src="resources/${node.item.type}.png">
                </div>
                <div><p class='tree-view-caption' style="margin:0px;">${node.item.caption}</p></div>
                </div>`
            }
        },
        line: {
            type: "angled",
            thickness: 2,
            colour: "#a3a1a1",
            cap: "round"
        },
        layout: {
            rootNodeOrientation: "vertical",
            direction: "horizontal"
        }
    };

    // Create the behaviour tree view.
    new Workflo(treeViewWrapper, options);
};

// Do the initial definition update.
onDefinitionUpdate();