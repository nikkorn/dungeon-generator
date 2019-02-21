
/**
 * Process entity nodes.
 * @param nodes The entity nodes.
 */
function processEntities(nodes) {
    // Recursively process the entities for a node.
    const processEntitiesForNode = (node, entities) => {
        if (node.id) {
            // Handle single entity.
            entities.push({
                id: node.id,
                x: node.x,
                y: node.y,
                direction: node.direction
            });
        } else if (node.entities) {
            // Handle multiple nodes.
            node.entities.forEach(childNode => processEntitiesForNode(childNode, entities));
        } else if (node.participants) {
            // Create a lotto with which to find a winning participating node.
			const lotto = new Lotto();
                    
            // Add each participant to the lotto.
            node.participants.forEach(participant => lotto.add(participant, participant.tickets || 0));

            // Process the winning node.
            processEntitiesForNode(lotto.draw(), entities);
        } else {
            // This node represents the option to not create an entity.
        }
    };

    // Store the entities that were successfully generated.
    const generated = [];

    nodes.forEach(node => processEntitiesForNode(node, generated));

    // Return the generated entities.
    return generated;
}