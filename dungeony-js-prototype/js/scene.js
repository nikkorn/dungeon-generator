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

    // Get the walkable tiles.
    const walkables = dungeon
        .filter(tile => tile.type !== TILE.WALL)
        .map((tile) => ({
            x: tile.x,
            y: tile.y,
            node: new AStarNode(tile.x, tile.y),
            isWaypoint: tile.entity && tile.entity.id === "waypoint"
        }));

    // Find the enemy path waypoints.
    const waypoints = walkables
        .filter(walkable => walkable.isWaypoint);

    // Create the enemies.
    const enemies = dungeon
        .filter(tile => tile.entity && tile.entity.id === "enemy")
        .map(tile => new Enemy(tile.x * TILE_SIZE, tile.y * TILE_SIZE, walkables));

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

        if (keysDown[KEY_CODE.W]) yPlayerOffset -= PLAYER_MOVEMENT;
        if (keysDown[KEY_CODE.S]) yPlayerOffset += PLAYER_MOVEMENT;
        if (keysDown[KEY_CODE.A]) xPlayerOffset -= PLAYER_MOVEMENT;
        if (keysDown[KEY_CODE.D]) xPlayerOffset += PLAYER_MOVEMENT;

        handleCharacterMovement(player, xPlayerOffset, yPlayerOffset);
    };

    /**
     * Handle the enemy movement.
     */
    function handleEnemyMovement() {
        for (const enemy of enemies) {
            // Can the enemy see the player?
            const canEnemySeePlayer = false;

            // The enemy movement offset.
            let enemyOffsetX = 0;
            let enemyOffsetY = 0;

            // How the enemy behaves depends on its current state.
            switch (enemy.getState(canEnemySeePlayer)) {
                case EnemyState.PATROLLING:
                    // Get the next tile in the enemy patrol.
                    const targetTile = enemy.getNextPatrolTileNode();

                    // There is nothing to do if we do not have a tile to move to.
                    if (!targetTile) {
                        continue;
                    }

                    // Get the x/y position of the target tile origin.
                    const tileOriginX = (targetTile.getX() * TILE_SIZE) + (TILE_SIZE / 2);
                    const tileOriginY = (targetTile.getY() * TILE_SIZE) + (TILE_SIZE / 2);

                    // Get the x/y position of the enemy origin.
                    const enemyOriginX = enemy.getX() + (enemy.getSize() / 2);
                    const enemyOriginY = enemy.getY() + (enemy.getSize() / 2);

                    // Move to the centre of the tile on the x axis.
                    if (enemyOriginX < tileOriginX) {
                        if (enemyOriginX + ENEMY_MOVEMENT > tileOriginX) {
                            enemyOffsetX = tileOriginX - enemyOriginX;
                        } else {
                            enemyOffsetX = ENEMY_MOVEMENT;
                        }
                    } else if (enemyOriginX > tileOriginX) {
                        if (enemyOriginX - ENEMY_MOVEMENT < tileOriginX) {
                            enemyOffsetX = -(enemyOriginX - tileOriginX);
                        } else {
                            enemyOffsetX = -ENEMY_MOVEMENT;
                        }
                    }

                    // Move to the centre of the tile on the y axis.
                    if (enemyOriginY < tileOriginY) {
                        if (enemyOriginY + ENEMY_MOVEMENT > tileOriginY) {
                            enemyOffsetY = tileOriginY - enemyOriginY;
                        } else {
                            enemyOffsetY = ENEMY_MOVEMENT;
                        }
                    } else if (enemyOriginY > tileOriginY) {
                        if (enemyOriginY - ENEMY_MOVEMENT < tileOriginY) {
                            enemyOffsetY = -(enemyOriginX - tileOriginY);
                        } else {
                            enemyOffsetY = -ENEMY_MOVEMENT;
                        }
                    }
                    break;

                case EnemyState.FOLLOWING_PLAYER:
                    // TODO Check whether player is close enough to follow. If not then do nothing.

                    // Follow the player at only half the player speed.
                    // TODO Add PLAYER_MOVEMENT and slower ENEMY_MOVEMENT.
                    enemyOffsetX = player.getX() > enemy.getX() ? ENEMY_MOVEMENT : -ENEMY_MOVEMENT;
                    enemyOffsetY = player.getY() > enemy.getY() ? ENEMY_MOVEMENT : -ENEMY_MOVEMENT;
                    break;
            }

            // Move the enemy.
            handleCharacterMovement(enemy, enemyOffsetX, enemyOffsetY);
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
            context.fillRect(((waypoint.x * TILE_SIZE) + xOffset) * zoomMultiplier, ((waypoint.y * TILE_SIZE) + yOffset) * zoomMultiplier, TILE_SIZE * zoomMultiplier, TILE_SIZE * zoomMultiplier);
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