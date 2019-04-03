/**
 * The behaviour tree.
 * @param definition The behaviour tree definition.
 * @param board The behaviour tree board.
 */
function BehaviourTree(definition, board) {

     /**
     * Node factories.
     */
    const ASTNodeWrappingBehaviour = { "NONE": 0, "SINGLE": 1, "MULTIPLE": 2 };

    /**
     * Node factories.
     */
    const ASTNodeFactories = {
        "ROOT": () => ({ 
            uid: getUid(),
            caption: () => "ROOT",
            type: "root", 
            children: [], 
            wrappingBehaviour: ASTNodeWrappingBehaviour.SINGLE,
            createNodeInstance: function () { 
                return new Root(this.children[0].createNodeInstance());
            }
        }),
        "SELECTOR": () => ({
            uid: getUid(),
            caption: () => "SELECTOR",
            type: "selector", 
            children: [], 
            wrappingBehaviour: ASTNodeWrappingBehaviour.MULTIPLE,
            createNodeInstance: function () { 
                return new Selector(this.children.map((child) => child.createNodeInstance()));
            }
        }),
        "SEQUENCE": () => ({
            uid: getUid(),
            caption: () => "SEQUENCE",
            type: "sequence", 
            children: [], 
            wrappingBehaviour: ASTNodeWrappingBehaviour.MULTIPLE,
            createNodeInstance: function () { 
                return new Sequence(this.children.map((child) => child.createNodeInstance()));
            }
        }),
        "CONDITION": () => ({
            uid: getUid(),
            caption: function() { return this["function"]; },
            type: "condition", 
            wrappingBehaviour: ASTNodeWrappingBehaviour.NONE, 
            function: "",
            createNodeInstance: function () { 
                return new Condition(this["function"]);
            }
        }),
        "DECORATOR": () => ({
            uid: getUid(),
            caption: () => "DECORATOR",
            type: "decorator", 
            children: [], 
            wrappingBehaviour: ASTNodeWrappingBehaviour.SINGLE, 
            name: "" 
        }),
        "ACTION": () => ({
            uid: getUid(),
            caption: function() { return this["function"]; },
            type: "action", 
            wrappingBehaviour: ASTNodeWrappingBehaviour.NONE, 
            function: "",
            arguments: [],
            createNodeInstance: function () {
                return new Action(this["function"]);
            }
        })
    };

    /**
     * The root AST node.
     */
    let rootASTNode;

    /**
     * The root BT tree node.
     */
    let rootBTNode;

    /**
     * Generate a Uid. 
     */
    const getUid = () => {
        var S4 = function() {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        };
        return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
    }

    /**
     * BehaviourTree init logic.
     */
    this.init = function() {
        // Convert the definition into some tokens.
        const tokens = this.parseDefinition();

        // Create the BT AST from tokens.
        rootASTNode = this.createRootASTNode(tokens);

        // Convert the AST to our actual tree.
        rootBTNode = rootASTNode.createNodeInstance();
    };

    /**
     * Get the root AST node.
     */
    this.getRootASTNode = () => rootASTNode;

    /**
     * Get the root BT node.
     */
    this.getRootBTNode = () => rootBTNode;

    /**
     * Parse the BT tree definition into an array of raw tokens.
     */
    this.parseDefinition = function() {
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
    this.createRootASTNode = function(tokens) {
        // There must be at least 3 tokens for the tree definition to be valid. 'ROOT', '{' and '}'.
        if (tokens.length < 3) {
            throw "ParseError: Invalid token count";
        }

        // The first token MUST be our 'ROOT' token.
        if (tokens[0] !== "ROOT") {
            throw "ParseError: Initial node must be the 'ROOT' node";
        }

        // We should have a matching number of '{' and '}' tokens. If not, then there are scopes that have not been properly closed.
        if (tokens.filter((token) => token === "{").length !== tokens.filter((token) => token === "}").length) {
            throw "ParseError: Scope character mismatch";
        }

        // Helper function to pop the next raw token off of the stack and throw an error if it wasn't the expected one.
        const popAndCheck = (expected) => {
            // Get and remove the next token.
            const popped = tokens.shift();

            // Was it the expected token?
            if (popped !== expected) {
                throw "ParseError: Unexpected token found on the stack. Expected '" + expected + "' but got '" + popped + "'"; 
            }
        };

        // Throw the 'ROOT' and opening '{' token away.
        popAndCheck("ROOT");
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
            switch (token) {
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

                case "DECORATOR":
                    // TODO ...
                    break;

                case "ACTION":
                    // Create a ACTION AST node.
                    node = ASTNodeFactories.ACTION();

                    // Push the ACTION node into the current scope.
                    stack[stack.length-1].push(node);

                    // A ':' character splits the 'ACTION' token and the target function name token.
                    popAndCheck(":");

                    // The next token should be the name of the action function. 
                    node.function = tokens.shift();
                    break;

                case "}": 
                    // The '}' character closes the current scope.
                    stack.pop();
                    break;

                default:
                    throw "ParseError: Unexpected token: " + token
            }
        }

        // Return the root BT AST node.
        return rootASTNode;
    };

    // Call BehaviourTree init logic.
    this.init();
};