/**
 * @param nodes The array of all walkable nodes, including the origin and target nodes.
 * @param origin The origin node.
 * @param target The target node.
 */
function findPath(nodes, origin, target) {

    // The map of all open nodes.
    const openNodes = {};

    // The map of all closed nodes.
    const closedNodes = {};

    // Create the result object to return;
    const result = {
        isPathBlocked: true,
        path: []
    };

    // Helper function to get a unique key based on an x/y position.
    const getKey = (x, y) => x + "_" + y;

    // Helper function to find the open node with the lowest score.
    const getOpenNodeWithLowestScore = () => Object.values(openNodes).sort((a, b) => a.getScore() - b.getScore())[0];

    // Create a node position lookup to speed up finding adjacent nodes.
    const nodeLookup = {};
    nodes.forEach((node) => nodeLookup[node.getKey()] = node);

    // Helper function to find the nodes adjacent to the specified one.
    const getAdjacentNodes = (currentNode) => [
        [currentNode.getX(), currentNode.getY() + 1],
        [currentNode.getX(), currentNode.getY() - 1],
        [currentNode.getX() + 1, currentNode.getY()],
        [currentNode.getX() - 1, currentNode.getY()]
    ].map(([x, y]) => getKey(x, y))
        .map((key) => nodeLookup[key])
        .filter((node) => node);

    // The list of all nodes MUST include both the origin and target node.
    if (nodes.indexOf(origin) === -1 || nodes.indexOf(target) === -1) {
        throw "The nodes array must include both the origin and target node."
    }

    // Set the heuristic for the origin and target nodes.
    origin.setHeuristic(target);
    target.setHeuristic(target);

    // Firstly, add the origin node to the open node map.
    openNodes[origin.getKey()] = origin;

    // Keep looking for a valid path.
    do {
        // Get the open node with the lowest score.
        const currentNode = getOpenNodeWithLowestScore();
        
        // Add the current node to the closed map and remove it from the open one.
        closedNodes[currentNode.getKey()] = currentNode;
        delete openNodes[currentNode.getKey()];

        // If we added the destination to the closed map, we've found a path!
        if (closedNodes[target.getKey()]) {
            // The path is not blocked as we managed to reach the target node.
            result.isPathBlocked = false;
            // Stop looking for a path.
            break;
        }

        // Get all walkable nodes adjacent to the current one and iterate over them.
        for (const adjacentNode of getAdjacentNodes(currentNode)) {
            // Ignore the adjacent node if it is already in the closed map.
            if (closedNodes[adjacentNode.getKey()]) {
                continue;
            }

            // If the current node is not already in the open map...
            if (!openNodes[adjacentNode.getKey()]) {
                // ...Compute its score based on the adjacent nodes movement cost ...
                adjacentNode.setAccumulatedMovementCost(currentNode.getAccumulatedMovementCost() + adjacentNode.getMovementCost());

                // ...And compute its heuristic...
                adjacentNode.setHeuristic(target);

                // ...Set its parent...
                adjacentNode.setParent(currentNode);

                // ...And add it to the open map.
                openNodes[adjacentNode.getKey()] = adjacentNode;
            } else {
                // ...Test if using the current G score make the adjacentNode F score
                // lower, if yes update the parent because it means it is a better path.
                if (currentNode.getAccumulatedMovementCost() + adjacentNode.getHeuristic() < adjacentNode.getScore()) {
                    adjacentNode.setParent(currentNode);
                }
            }
        }
    } while (Object.keys(openNodes).length);

    // Work backwards from the goal node to work out the sequence of directional
    // movements required to reach the goal from the original position.
    let currentNode = target;

    // Collect the nodes that the path is composed of.
    while (currentNode.getParent() !== null) {
        result.path.push(currentNode);

        currentNode = currentNode.getParent();
    }

    return result;
}