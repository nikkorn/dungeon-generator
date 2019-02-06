/**
 * Enumeration of game state types.
 */
const SceneState = { "ACTIVE": 0, "WON": 1, "LOST": 2 };

/**
 * The game scene.
 * @param level The level details.
 */
function Scene(level) {
    /**
     * The level tiles.
     */
    this.tiles = level.tiles;
    /**
     * The level blocks.
     */
    this.blocks = level.blocks.map((definition) => new Block(definition));

    /**
     * Slide all blocks of a colour one space in a direction.
     * @param colour The block colour.
     * @param direction The direction to slide the blocks of the specified colour.
     */
    this.slide = function(colour, direction) {

        // A function which returns whether a block can freely be pushed in the specified direction.
        const canPushBlock = (block) => {
            // TODO Swap out for logic that checks whether the space the block is being pushed to contains a wall or other block.
            return true;
        };

        // Get all boxes of the specified colour, sorted based on its position relating to the 
        // direction we are moving blocks, and push them if they are not blocked.
        this.blocks
            .filter(block => block.getColour() === colour)
            .sort((blockA, blockB) => {
                // How we sort the blocks depends entirely on the direction that the blocks will be moved.
                switch (direction) {
                    case DIRECTION.UP:
                        return blockA.getY() > blockB.getY();

                    case DIRECTION.DOWN:
                        return blockA.getY() < blockB.getY();

                    case DIRECTION.LEFT:
                        return blockA.getX() > blockB.getX();

                    case DIRECTION.RIGHT:
                        return blockA.getX() < blockB.getX();

                    default:
                        throw "unexpected direction";
                }
            })
            .filter(canPushBlock)
            .forEach((block) => block.push(direction));

        // TODO Get sorted list of all blocks based on the slide direction. E.G. If sliding all blocks right then start with the right-most block.
        console.log(`Slide all ${colour} blocks in the ${direction} direction!`);
    };

    /**
     * Get the state of the scene.
     * @returns The scene state.
     */
    this.getState = function() {
        // TODO Check if game was lost by either:
        //    - Block stuck in hole with differing colours. 
        //    - Block falls out of the scene by sliding onto an empty tile.

        // TODO Check if game was won by all blocks being on corresponding colour pad/hole.

        // We have not won or lost yet, so the scene is still active.
        return SceneState.ACTIVE;
    };

    /**
     * Draw the scene to the specified canvas context.
     * @param context The canvas context.
     */
    this.draw = function(context) {
        // Clear the canvas context.
        context.clearRect(0, 0, canvasSize, canvasSize);

        // Draw every tile.
        for (var tile of this.tiles) {
            context.fillStyle = colours.purple;
            context.fillRect(tile.x * tileSize, tile.y * tileSize, tileSize, tileSize);
        }

        // Draw every block.
        for (var block of this.blocks) {
            context.fillStyle = colours.blue;
            context.fillRect(block.getX() * tileSize, block.getY() * tileSize, tileSize, tileSize);
        }
    };
};