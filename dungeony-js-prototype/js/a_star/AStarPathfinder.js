/**
 * @param nodes The array of all walkable nodes, including the origin and target nodes.
 * @param origin The origin node.
 * @param target The target node.
 */
function findPath(nodes, origin, target) {

    // Helper function to get a unique key based on an x/y position.
    const getKey = (x, y) => x + "_" + y;

    // The map of all open nodes.
    const openNodes = {};

    // The map of all closed nodes.
    const closedNodes = {};

    // The list of all nodes MUST include both the origin and target node.
    if (nodes.indexOf(origin) === -1 || nodes.indexOf(target) === -1) {
        throw "The nodes array must include both the origin and target node."
    }

    // Set the heuristic for the origin and target nodes.
    origin.setHeuristic(target);
    target.setHeuristic(target);

    // Firstly, add the origin node to the open node map.
    openNodes.push(origin.getKey(), origin);

    // ...
}