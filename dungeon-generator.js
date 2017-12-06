const spaceSize        = 10;
const dungeonSize      = 600;
const maxRoomSize      = 15;
const minRoomSize      = 5;
const roomCount        = 10;
const maxTry           = 1000;
const dungeonSpaceSize = dungeonSize / spaceSize;

/** The space types. */
const space = {
    room: { colour: "#ffffff", type: "ROOM" },
    corridor: { colour: "#8acce2", type: "CORRIDOR" }, 
    door: { colour: "#9e46ce", type: "DOOR" },
    pillar: { colour: "#000000", type: "PILLAR" },
    wall: { colour: "#000000", type: "WALL" },
    enemy: { colour: "#bf1428", type: "ENEMY" },
    pickup: { colour: "#f49542", type: "PICKUP" },
    entrance: { colour: "#71f442", type: "ENTRANCE" },
    exit: { colour: "#6316e0", type: "EXIT" }
};

/** The x/y position to space type mappings. */
let spaces = {};

/**
 * Generate a dungeon!
 */
function generate()
{
  // Clear the spaces.
  spaces = {};

  // Clear the dungeon SVG
  document.getElementById('dungeon').innerHTML = "<rect width=\"600\" height=\"600\" style=\"fill:rgb(0,0,0)\" />";

  // Generate the rooms.
  var rooms = generateRooms();

  // Generate and draw some corridors between our rooms.
  generateCorridors(rooms);

  // Draw the rooms.
  drawRooms(rooms);

  // Go over every space in the dungeon area and compare a series of patterns to the position.
  applyPatterns();
}

/**
 * Generate a number of rooms in the dungeon.
 */
function generateRooms()
{
  var rooms = [];
  var tryNo = 0;

  while (rooms.length < roomCount && tryNo++ < maxTry)
  {
    // Create a randomly sized/positioned room.
    var room = {
      x : Math.floor(Math.random() * 60),
      y : Math.floor(Math.random() * 60),
      width : Math.floor((Math.random() * (maxRoomSize - minRoomSize)) + minRoomSize),
      height : Math.floor((Math.random() * (maxRoomSize - minRoomSize)) + minRoomSize)
    };

    // Set the room centre.
    room.centre = {
      x: room.x + Math.floor(room.width / 2),
      y: room.y + Math.floor(room.height / 2)
    };

    // Check that the room is within the bounds of the dungeon
    // area and that it does not overlap an existing room.
    if (roomIsWithinDungeonBounds(room) && !overlaps(room, rooms))
    {
      rooms.push(room);
    }
  }

  return rooms;
}

/**
 * Draw a collection of rooms.
 */
function drawRooms(rooms)
{
  for (var i = 0; i < rooms.length; i++)
  {
    setSpace(space.room, rooms[i].x, rooms[i].y, rooms[i].width, rooms[i].height);
  }
}

/**
 * Generate a number of corridors beteen rooms in the dungeon.
 */
function generateCorridors(rooms)
{
  var previous = null;

  var drawVerticalCorridor = function (x, minY, maxY)
  {
    for (var y = minY; y <= maxY; y++)
    {
      setSpace(space.corridor, x, y);
    }
  };

  var drawHorizontalCorridor = function (y, minX, maxX)
  {
    for (var x = minX; x <= maxX; x++)
    {
      setSpace(space.corridor, x, y);
    }
  };

  for (var i = 0; i < rooms.length; i++)
  {
    // Get the centre of the current room.
    var current = rooms[i].centre;

    // Do we have two rooms to connect?
    if (previous != null)
    {
      // Are we going vertically or horizontally first? Flip a coin.
      if (Math.random() >= 0.5)
      {
        drawVerticalCorridor(current.x, Math.min(current.y, previous.y), Math.max(current.y, previous.y));
        drawHorizontalCorridor(previous.y, Math.min(current.x, previous.x), Math.max(current.x, previous.x));
      }
      else
      {
        drawHorizontalCorridor(current.y, Math.min(current.x, previous.x), Math.max(current.x, previous.x));
        drawVerticalCorridor(previous.x, Math.min(current.y, previous.y), Math.max(current.y, previous.y));
      }
    }

    previous = current;
  }
}

/**
 * Go over every space in the dungeon area and compare a series of patterns to the position.
 */
function applyPatterns()
{
  for (var i = 0; i < patterns.length; i++)
  {
    // Get the current pattern.
    const pattern = patterns[i];

    // TODO Check if the pattern has a min/max property, if so then:
    //   - Ignore chance.
    //   - Find every pattern which matches the space. None of these can overlap!
    //   - Let x be a random number between min and max.
    //   - Pick x matching spaces and apply and call onMatch.
    if (pattern.min && pattern.max)
    {
      // The list of all spaces which match the pattern.
      let matchingSpaces = [];

       // Check this pattern against every space in the dungeon.
      for (var x = 0; x < dungeonSpaceSize; x++)
      {
        for (var y = 0; y < dungeonSpaceSize; y++)
        {
          // Check whether the pattern matches the current space, and check whether we should apply it based on chance.
          if (doesPatternMatchSpace(pattern, x, y))
          {
            // TODO Check for overlap!
            matchingSpaces.push({ x, y });
          }
        }
      } 

      // TODO Pick a random number between min and max, pick that many random matches and apply them.
    }
    else if (pattern.chance)
    {
      // Check this pattern against every space in the dungeon.
      for (var x = 0; x < dungeonSpaceSize; x++)
      {
        for (var y = 0; y < dungeonSpaceSize; y++)
        {
          // Check whether the pattern matches the current space, and check whether we should apply it based on chance.
          if (doesPatternMatchSpace(pattern, x, y) && Math.random() <= pattern.chance)
          {
            // The pattern matched the current space.
            pattern.onMatch(x, y);
          }
        }
      } 
    }
    else
    {
      console.log("need to specify chance or min/max value for pattern: " + pattern.name);
    }
  }
}

/**
 * Check a pattern matches the specified space.
 */
function doesPatternMatchSpace(pattern, x, y)
{
    for (var i = 0; i < pattern.matches.length; i++)
    {
      const match   = pattern.matches[i];
      const offsetX = match[0];
      const offsetY = match[1];
      const types   = match[2].split(",");

      if (types.indexOf(getSpace(x + offsetX, y + offsetY)) === -1)
      {
        return false;
      }
    }

    // Our pattern matched!
    return true;
}

/**
 * Gets whether the specified room interacts with an other rooms.
 */
function overlaps(room, rooms)
{
  for (var i = 0; i < rooms.length; i++)
  {
    var a = rooms[i];
    var b = room;
    // Check for an overlap.
    if(a.x < (b.x + b.width) && (a.x + a.width) > b.x && a.y < (b.y + b.height) && (a.y + a.height) > b.y) {
      // There was an overlap!
      return true;
    }
  }
  // There were no overlaps.
  return false;
};

/**
 * Gets whether a room is within the bounds of the dungeon area.
 */
function roomIsWithinDungeonBounds(room)
{
  var min                = 1;
  var max                = (dungeonSize / spaceSize) - 2;
  var inVerticalBounds   = room.y >= min && (room.y + room.height) <= max;
  var inHorizontalBounds = room.x >= min && (room.x + room.width) <= max;

  return inVerticalBounds && inHorizontalBounds;
};

/**
 * Set a space in the dungeon.
 */
function setSpace(type, x, y, width, height)
{
  for (var posX = x; posX < (x + (width || 1)); posX++)
  {
    for (var posY = y; posY < (y + (height || 1)); posY++)
    {
      // Add the space to our space mappings.
      spaces[posX+"-"+posY] = type.type;
      // Draw the space on the dungeon area SVG.
      var rect = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
      rect.setAttributeNS(null, 'x', posX * spaceSize);
      rect.setAttributeNS(null, 'y', posY * spaceSize);
      rect.setAttributeNS(null, 'height', 10);
      rect.setAttributeNS(null, 'width', 10);
      rect.setAttributeNS(null, 'fill', type.colour);
      document.getElementById('dungeon').appendChild(rect);
    }
  }
};

/**
 * Get a space in the dungeon.
 * @returns space type.
 */
function getSpace(x, y)
{
  // Is this position outside the dungeon area?
  const isOutOfBounds = x < 0 || x >= dungeonSpaceSize || y < 0 || y >= dungeonSpaceSize;
  // If this position is out of bounds then return 'OOB'. Otherwise, return the space type.
  return isOutOfBounds ? "OOB" : spaces[x+"-"+y] || space.wall.type;
};