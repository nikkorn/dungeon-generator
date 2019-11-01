/**
 * Create a canvas and return the context.
 */
function getCanvasContext() 
{
    // Create the canvas.
    var canvas = document.createElement("canvas");

    // Style the canvas.
    canvas.width            = CANVAS_SIZE;
    canvas.height           = CANVAS_SIZE;
    canvas.style.background = colours.grey;

    // Append the canvas to the page.
    document.getElementById("dungeony-container").appendChild(canvas);

    // Return the canvas context.
    return canvas.getContext("2d");
};

// Get the level to use from the user.
const level = levels[prompt("Please select a level", "tester")];

// Create the game player based on level data.
const player = new Player(level.player)

// Create the wall objects based on level data.
const walls = level.walls.map(wall => new Wall(wall));

// Create the enemies based on level data.
const enemies = level.enemies.map(enemy => new Enemy(enemy));

// All game entities.
const entities = [player, ...enemies, ...walls];

// Listen for pressed keys.
var keys         = {};
window.onkeyup   = function(e) { keys[e.keyCode] = false; }
window.onkeydown = function(e) { keys[e.keyCode] = true; }

// Create a canvas and get its context.
const context = getCanvasContext();

/**
 * Handle the player movement.
 */
function handlePlayerMovement() 
{
    const playerVelocity = player.getVelocity();

    // Apply gravity to the player velocity.
    playerVelocity.y += GRAVITY;

    if (keys[KEY_CODE.W]) playerVelocity.y -= CHARACTER_MOVEMENT * 4;
    if (keys[KEY_CODE.A]) playerVelocity.x -= CHARACTER_MAX_MOVEMENT;
    if (keys[KEY_CODE.D]) playerVelocity.x += CHARACTER_MAX_MOVEMENT;

    playerVelocity.clamp(CHARACTER_MAX_MOVEMENT);

    handleCharacterMovement(player, playerVelocity.x, playerVelocity.y);

    playerVelocity.x = 0;
};

/**
 * Handle the enemy movement.
 */
function handleEnemyMovement() 
{
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
                const enemyVelocity = enemy.getVelocity();

                // Apply gravity to the enemy velocity.
                enemyVelocity.y += GRAVITY;

                // Try to move the enemy towards the player.
                if (player.getX() < enemy.getX()) {
                    enemyVelocity.x -= CHARACTER_MOVEMENT * 0.7
                } else {
                    enemyVelocity.x += CHARACTER_MOVEMENT * 0.7
                }

                enemyVelocity.clamp(CHARACTER_MAX_MOVEMENT);

                handleCharacterMovement(enemy, enemyVelocity.x, enemyVelocity.y);
                break;
        }
    }
};

/**
 * Handle movement for a character.
 */
function handleCharacterMovement(character, xCharacterOffset, yCharacterOffset) 
{
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
 */
function draw() 
{
    // Clear the canvas context.
    context.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

    // Draw the player.
    context.fillStyle = colours.purple;
    context.fillRect(player.getX(), player.getY(), player.getSize(), player.getSize());

    // Draw every enemy.
    context.fillStyle = colours.red;
    for (const enemy of enemies) {
        context.fillRect(enemy.getX(), enemy.getY(), enemy.getSize(), enemy.getSize());
    }

    // Draw every wall.
    context.fillStyle = colours.blue;
    for (const wall of walls) {
        context.fillRect(wall.getX(), wall.getY(), wall.getSize(), wall.getSize());
    }
};

/**
 * The game loop.
 */
function loop() 
{
    // Handle the player movement.
    handlePlayerMovement();

    // Handle the enemy movement
    handleEnemyMovement();

    // Draw the game.
    draw();
};

setInterval(() => loop(), 16.6);