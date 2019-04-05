(function() {
    "use strict";

    /**
     * The behaviour tree.
     * @param definition The behaviour tree definition.
     * @param board The behaviour tree board.
     */
	function BehaviourTree (definition, board) {
		
        /**
         * Generate a Uid.
         * @returns A random unique id.
         */
        const getUid = () => {
            var S4 = function() {
                return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
            };
            return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
        }

        /**
         * Node factories.
         */
        const ASTNodeFactories = {
            "ROOT": () => ({ 
                uid: getUid(),
                type: "root",
                children: [],
                validate: function () {
                    // A root node must have a single node.
                    if (this.children.length !== 1) {
                        throw "a root node must have a single child";
                    }
                },
                createNodeInstance: function () { 
                    return new Root(this.uid, this.children[0].createNodeInstance());
                }
            }),
            "SELECTOR": () => ({
                uid: getUid(),
                type: "selector",
                children: [],
                validate: function () {
                    // A selector node must have at least a single node.
                    if (this.children.length < 1) {
                        throw "a selector node must have at least a single child";
                    }
                },
                createNodeInstance: function () { 
                    return new Selector(this.uid, this.children.map((child) => child.createNodeInstance()));
                }
            }),
            "SEQUENCE": () => ({
                uid: getUid(),
                type: "sequence",
                children: [], 
                validate: function () {
                    // A sequence node must have at least a single node.
                    if (this.children.length < 1) {
                        throw "a sequence node must have at least a single child";
                    }
                },
                createNodeInstance: function () { 
                    return new Sequence(this.uid, this.children.map((child) => child.createNodeInstance()));
                }
            }),
            "CONDITION": () => ({
                uid: getUid(),
                type: "condition",
                function: "",
                validate: function () {},
                createNodeInstance: function () { 
                    return new Condition(this.uid, this["function"]);
                }
            }),
            "FLIP": () => ({
                uid: getUid(),
                type: "flip",
                children: [],
                validate: function () {
                    // A flip node must have a single node.
                    if (this.children.length !== 1) {
                        throw "a flip node must have a single child";
                    }
                },
                createNodeInstance: function () { 
                    return new Flip(this.uid, this.children[0].createNodeInstance());
                }
            }),
            "ACTION": () => ({
                uid: getUid(),
                type: "action",
                function: "",
                arguments: [],
                validate: function () {},
                createNodeInstance: function () {
                    return new Action(this.uid, this["function"]);
                }
            })
        };

        /**
         * The tree definition.
         */
        this._definition = definition;

        /**
         * The blackboard.
         */
        this._blackboard = board;

        /**
         * The root tree node.
         */
        this._rootNode;

        /**
         * BehaviourTree init logic.
         */
        this._init = function() {
            // Convert the definition into some tokens.
            const tokens = this._parseDefinition();

            // Try to create the behaviour tree AST from tokens, this could fail if the definition is invalid.
            let rootASTNode;
            try {
                rootASTNode = this._createRootASTNode(tokens);
            } catch (exception) {
                // There was an issue in trying to parse the tree definition.
                throw `TreeParseError: ${exception}`;
            }

            // Convert the AST to our actual tree.
            this._rootNode = rootASTNode.createNodeInstance();
        };

        /**
         * Parse the BT tree definition into an array of raw tokens.
         */
        this._parseDefinition = function() {
            // Firstly, create a copy of the raw definition.
            let cleansedDefinition = definition;

            // Add some space around '[', ']' and ',' so that they can be plucked out easier as individual tokens.
            cleansedDefinition = cleansedDefinition.replace(/\]/g, " ] ");
            cleansedDefinition = cleansedDefinition.replace(/\[/g, " [ ");
            cleansedDefinition = cleansedDefinition.replace(/\,/g, " , ");
            cleansedDefinition = cleansedDefinition.replace(/\:/g, " : ");

            // Split the definition into raw token form and return it.
            return cleansedDefinition.replace(/\s+/g, " ").trim().split(" ");
        };

        /**
         * Create a BT AST node based on the remaining tokens.
         * @param tokens The remaining tokens.
         */
        this._createRootASTNode = function(tokens) {
            // There must be at least 3 tokens for the tree definition to be valid. 'ROOT', '{' and '}'.
            if (tokens.length < 3) {
                throw "invalid token count";
            }

            // The first token MUST be our 'ROOT' token.
            if (tokens[0].toUpperCase() !== "ROOT") {
                throw "initial node must be the 'ROOT' node";
            }

            // We should have a matching number of '{' and '}' tokens. If not, then there are scopes that have not been properly closed.
            if (tokens.filter((token) => token === "{").length !== tokens.filter((token) => token === "}").length) {
                throw "scope character mismatch";
            }

            // Helper function to pop the next raw token off of the stack and throw an error if it wasn't the expected one.
            const popAndCheck = (expected) => {
                // Get and remove the next token.
                const popped = tokens.shift();

                // Was it the expected token?
                if (popped.toUpperCase() !== expected.toUpperCase()) {
                    throw "unexpected token found on the stack. Expected '" + expected + "' but got '" + popped + "'"; 
                }
            };

            // Throw the 'ROOT' and opening '{' token away.
            popAndCheck("root");
            popAndCheck("{");

            // Create the root node.
            const rootASTNode = ASTNodeFactories.ROOT();

            // Create a stack of node children arrays, with the root child array as the initial one.
            const stack = [rootASTNode.children];

            // We should keep processing the raw tokens until we run out of them.
            while (tokens.length) {
                // Grab the next token.
                const token = tokens.shift();

                let node;

                // How we create the next AST token depends on the current raw token value.
                switch (token.toUpperCase()) {
                    case "SELECTOR": 
                        // Create a SELECTOR AST node.
                        node = ASTNodeFactories.SELECTOR();

                        // Push the SELECTOR node into the current scope.
                        stack[stack.length-1].push(node);

                        popAndCheck("{");

                        // The new scope is that of the new SELECTOR nodes children.
                        stack.push(node.children);
                        break;

                    case "SEQUENCE":
                        // Create a SEQUENCE AST node.
                        node = ASTNodeFactories.SEQUENCE();

                        // Push the SEQUENCE node into the current scope.
                        stack[stack.length-1].push(node);

                        popAndCheck("{");

                        // The new scope is that of the new SEQUENCE nodes children.
                        stack.push(node.children);
                        break;

                    case "CONDITION": 
                        // Create a CONDITION AST node.
                        node = ASTNodeFactories.CONDITION();

                        // Push the CONDITION node into the current scope.
                        stack[stack.length-1].push(node);

                        // A ':' character splits the 'CONDITION' token and the target function name token.
                        popAndCheck(":");

                        // The next token should be the name of the condition function. 
                        node.function = tokens.shift();
                        break;

                    case "FLIP":
                        // Create a FLIP AST node.
                        node = ASTNodeFactories.FLIP();

                        // Push the Flip node into the current scope.
                        stack[stack.length-1].push(node);

                        popAndCheck("{");

                        // The new scope is that of the new FLIP nodes children.
                        stack.push(node.children);
                        break;

                    case "ACTION":
                        // Create a ACTION AST node.
                        node = ASTNodeFactories.ACTION();

                        // Push the ACTION node into the current scope.
                        stack[stack.length-1].push(node);

                        // A ':' character splits the 'ACTION' token and the target function name token.
                        popAndCheck(":");

                        // If the next token is a '}' then there is a missing action function token.
                        if (tokens[0] === "}") {
                            throw "missing action function";
                        }

                        // The next token should be the name of the action function. 
                        node.function = tokens.shift();
                        break;

                    case "}": 
                        // The '}' character closes the current scope.
                        stack.pop();
                        break;

                    default:
                        throw "unexpected token: " + token
                }
            }

            // Validate each of the nodes.
            const validateASTNode = (node) => {
                // Validate the node.
                node.validate();

                // Validate each child of the node.
                (node.children ||[]).forEach((child) => validateASTNode(child));
            };
            validateASTNode(rootASTNode);

            // Return the root BT AST node.
            return rootASTNode;
        };

        // Call BehaviourTree init logic.
        this._init();
    };
    
    /**
	 *  Get the root node.
	 */
	BehaviourTree.prototype.getRootNode = function () {
		return this._rootNode;
	};

	/**
	 * Step the tree.
	 */
	BehaviourTree.prototype.step = function () {
        try {
            this._rootNode.update(this._blackboard);
        } catch (exception) {
            throw `TreeStepError: ${exception}`;
        }
	};

	/**
	 * Reset the tree from the root.
	 */
	BehaviourTree.prototype.reset = function () {
        this._rootNode.reset();
	};

	// Export BehaviourTree.
	if (typeof module !== 'undefined' && typeof module.exports !== 'undefined') 
	{
		module.exports = BehaviourTree;
	}
	else 
	{
		if (typeof define === 'function' && define.amd) 
		{
			define([], function() 
			{
				return BehaviourTree;
			});
		}
		else 
		{
			window.BehaviourTree = BehaviourTree;
		}
	}
})();
