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

// Generate a new dungeon!
const dungeon = new Generator().generate();

// Listen for pressed keys.
var keys         = {};
window.onkeyup   = function(e) { keys[e.keyCode] = false; }
window.onkeydown = function(e) { keys[e.keyCode] = true; }

// Create a canvas and get its context.
const context = getCanvasContext();

// Create the game scene.
const scene = new Scene(dungeon, keys);

// Create the game loop.
setInterval(() => {
    // Tick the scene.
    scene.tick();

    // Draw the scene.
    scene.draw(context);
}, 16.6);