
function BehaviourTree(definition, board) {

     /**
     * Node factories.
     */
    const NodeWrappingBehaviour = { "NONE": 0, "SINGLE": 1, "MULTIPLE": 2 };

    /**
     * Node factories.
     */
    const NodeFactories = {
        "ROOT": () => ({ 
            type: "ROOT", 
            children: [], 
            wrappingBehaviour: NodeWrappingBehaviour.SINGLE 
        }),
        "SELECTOR": () => ({ 
            type: "SELECTOR", 
            children: [], 
            wrappingBehaviour: NodeWrappingBehaviour.MULTIPLE
        }),
        "SEQUENCE": () => ({ 
            type: "SEQUENCE", 
            children: [], 
            wrappingBehaviour: NodeWrappingBehaviour.MULTIPLE
        }),
        "CONDITION": () => ({ 
            type: "CONDITION", 
            wrappingBehaviour: NodeWrappingBehaviour.NONE, 
            function: "" 
        }),
        "DECORATOR": () => ({ 
            type: "DECORATOR", 
            wrappingBehaviour: NodeWrappingBehaviour.SINGLE, 
            name: "" 
        })
    };

    /**
     * BehaviourTree init logic.
     */
    this.init = function() {
        console.log("Making something out of: " + definition);
        // Convert the definition into some tokens.
        const tokens = this.getDefinitionTokens();

        // TODO Create AST from tokens.
    };

    this.getDefinitionTokens = function() {
        // Firstly, create a copy of the raw definition.
        let cleansedDefinition = definition;

        // Add some space around '[', ']' and ',' so that they can be plucked out easier as individual tokens.
        cleansedDefinition = cleansedDefinition.replace(/\]/g, " ] ");
        cleansedDefinition = cleansedDefinition.replace(/\[/g, " [ ");
        cleansedDefinition = cleansedDefinition.replace(/\,/g, " , ");
        cleansedDefinition = cleansedDefinition.replace(/\:/g, " : ");

        // Split the definition into raw token form.
        const rawTokens = cleansedDefinition.replace(/\s+/g, " ").trim().split(" ");

        console.log(rawTokens);
    };

    // Call BehaviourTree init logic.
    this.init();
};