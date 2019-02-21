
/**
 * Process entity nodes.
 * @param nodes The entity nodes.
 */
function processEntities(nodes) {
    // Recursively get the entities for a node.
    const getEntitiesForNode = (node, entities) => {
        if (node.id) {
            // TODO Handle single entity.
        } else if (node.entities) {
            // TODO Handle multiple entities.
        } else if (node.participants) {
            // TODO Get a winning entity.
        }
    };

    // Store the entities that were successfully generated.
    const generated = [];

    nodes.forEach(node => getEntitiesForNode(node, generated));

    // Return the generated entities.
    return generated;
}