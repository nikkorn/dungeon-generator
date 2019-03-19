/**
 * The game scene.
 * @param dungeon The array of generated dungeon tiles.
 * @param keysDown An object containing key pressed states.
 */
function Scene(dungeon, keysDown) {
    // Create the game player and put them one tile into the spawn room.
    const player = new Player(TILE_SIZE * 3, TILE_SIZE * 3);

    // Create the wall objects based on level data.
    const walls = dungeon
        .filter(tile => tile.type === TILE.WALL)
        .map(wall => new Wall(wall));

    // Create an enemy to follow the player around the dungeon.
    const enemies = dungeon
        .filter(tile => tile.entity && tile.entity.id === "enemy")
        .map(tile => new Enemy(tile.x * TILE_SIZE, tile.y * TILE_SIZE, ENEMY_TYPE.FOLLOWER));

    // Create the enemy path waypoints.
    const waypoints = dungeon
        .filter(tile => tile.entity && tile.entity.id === "waypoint")
        .map(tile => new Waypoint(tile.x, tile.y));

    // Get the walkable tiles.
    const walkables = dungeon
        .filter(tile => tile.type !== TILE.WALL)
        .map((tile) => new AStarNode(tile.x, tile.y));

    // All game entities.
    const entities = [player, ...enemies, ...walls];

    /**
     * Tick the scene.
     */
    this.tick = function() {
        // Handle the player movement.
        handlePlayerMovement();

        // Handle the enemy movement
        handleEnemyMovement();
    };

    /**
     * Handle the player movement.
     */
    function handlePlayerMovement() {
        let xPlayerOffset = 0;
        let yPlayerOffset = 0;

        if (keysDown[KEY_CODE.W]) yPlayerOffset -= CHARACTER_MOVEMENT;
        if (keysDown[KEY_CODE.S]) yPlayerOffset += CHARACTER_MOVEMENT;
        if (keysDown[KEY_CODE.A]) xPlayerOffset -= CHARACTER_MOVEMENT;
        if (keysDown[KEY_CODE.D]) xPlayerOffset += CHARACTER_MOVEMENT;

        handleCharacterMovement(player, xPlayerOffset, yPlayerOffset);
    };

    /**
     * Handle the enemy movement.
     */
    function handleEnemyMovement() {
        for (const enemy of enemies) {
            // How the enemy behaves depends on its type.
            switch (enemy.getType()) {
                case ENEMY_TYPE.WALKER:
                    // Get the next enemy movement.
                    const nextEnemyMovement = enemy.getNextMovement();

                    // Try to move the enemy along his path.
                    handleCharacterMovement(enemy, nextEnemyMovement.x, nextEnemyMovement.y);
                    break;

                case ENEMY_TYPE.FOLLOWER:
                    // TODO Check whether player is close enough to follow. If not then do nothing.

                    // Follow the player at only half the player speed.
                    // TODO Add PLAYER_MOVEMENT and slower ENEMY_MOVEMENT.
                    const enemyOffsetX = player.getX() > enemy.getX() ? CHARACTER_MOVEMENT * 0.5 : CHARACTER_MOVEMENT * -0.5;
                    const enemyOffsetY = player.getY() > enemy.getY() ? CHARACTER_MOVEMENT * 0.5 : CHARACTER_MOVEMENT * -0.5;

                    // Try to move the enemy towards the player.
                    handleCharacterMovement(enemy, enemyOffsetX, enemyOffsetY);
                    break;
            }
        }
    };

    /**
     * Handle movement for a character.
     */
    function handleCharacterMovement(character, xCharacterOffset, yCharacterOffset) {
        let intersectsEntityOnXAxis = false;
        let intersectsEntityOnYAxis = false;

        // Check whether our player would intersect a wall if moving on the x axis.
        for (const entity of entities) {
            // Do nothing if the current entity is the character.
            if (entity === character) {
                continue;
            }

            if (doSquaresIntersect(character.getX() + xCharacterOffset, character.getY(), 
                character.getSize(), character.getSize(), entity.getX(), entity.getY(), entity.getSize(), entity.getSize())) {
                intersectsEntityOnXAxis = true;
                break;
            }
        }

        // Check whether our player would intersect a wall if moving on the y axis.
        for (const entity of entities) {
            // Do nothing if the current entity is the character.
            if (entity === character) {
                continue;
            }

            if (doSquaresIntersect(character.getX(), character.getY() + yCharacterOffset, 
                character.getSize(), character.getSize(), entity.getX(), entity.getY(), entity.getSize(), entity.getSize())) {
                intersectsEntityOnYAxis = true;
                break;
            }
        }

        if (!intersectsEntityOnXAxis) {
            character.move(xCharacterOffset, 0);
        }
        if (!intersectsEntityOnYAxis) {
            character.move(0, yCharacterOffset);
        }
    };

    /**
     * Draw the scene.
     * @param context The canvas context.
     */
    this.draw = function(context) {
        // Clear the canvas context.
        context.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

        const xOffset        = 1000 * CAMERA_ZOOM;
        const yOffset        = 1000 * CAMERA_ZOOM;
        const zoomMultiplier = 1 / CAMERA_ZOOM;

        // Draw the player.
        context.fillStyle = colours.purple;
        context.fillRect((player.getX() + xOffset) * zoomMultiplier, (player.getY() + yOffset) * zoomMultiplier, player.getSize() * zoomMultiplier, player.getSize() * zoomMultiplier);

        // Draw every enemy.
        context.fillStyle = colours.red;
        for (const enemy of enemies) {
            context.fillRect((enemy.getX() + xOffset) * zoomMultiplier, (enemy.getY() + yOffset) * zoomMultiplier, enemy.getSize() * zoomMultiplier, enemy.getSize() * zoomMultiplier);
        }

        // Draw every waypoint.
        context.fillStyle = colours.orange;
        for (const waypoint of waypoints) {
            context.fillRect((waypoint.getX() + xOffset) * zoomMultiplier, (waypoint.getY() + yOffset) * zoomMultiplier, waypoint.getSize() * zoomMultiplier, waypoint.getSize() * zoomMultiplier);
        }

        // TODO Remove!!!!!!!
        // Draw our test path.
        // context.fillStyle = colours.pink;
        // for (const point of aStarResult.path) {
        //     context.fillRect(((point.getX() * TILE_SIZE) + xOffset) * zoomMultiplier, ((point.getY() * TILE_SIZE) + yOffset) * zoomMultiplier, TILE_SIZE * zoomMultiplier, TILE_SIZE * zoomMultiplier);
        // }

        // Draw every wall.
        context.fillStyle = colours.blue;
        for (const wall of walls) {
            context.fillRect((wall.getX() + xOffset) * zoomMultiplier, (wall.getY() + yOffset) * zoomMultiplier, wall.getSize() * zoomMultiplier, wall.getSize() * zoomMultiplier);
        }
    };
};