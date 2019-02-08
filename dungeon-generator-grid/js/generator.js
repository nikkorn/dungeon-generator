/**
 * The dungeon generator.
 */
function Generator() {
	/**
	 * A dictionary mapping dungeon cell x/y positional keys to the cells that occupy them.
	 */
	this.cells = {};
	/**
	 * A dictionary mapping room id's to the count of times they have been added to the dungeon.
	 */
	this.roomCounts = {};

	/**
	 * Generate!
	 */
	this.generate = function () {
		// Clear the cells dictionary and the dictionary storing added room information.
		this.cells      = {};
		this.addedRooms = {};

		// Add the spawn room to the center of the dungeon, this should always be a success.
		addRoom(0, 0, getRoom("spawn"));

		// Keep track of the number of times we have attempted to add a room and failed.
		let roomGenerationFailureCount = 0;

		// While we need to populate our dungeon with rooms find a room and bolt it on.
		while (getRoomCount() < MAX_ROOMS_COUNT && roomGenerationFailureCount < MAX_ROOM_GENERATE_RETRY) {

			// TODO Find all available anchors and pick any random one as A.

			// TODO Get all rooms as X where the entrance matches the direction of A.

			// TODO MAYBE Randomly pick a room rarity and filter X by that rarity.

			// TODO Pick rooms randomly from X until R is found where carRoomBeGenerated(R).

			// TODO Call addRoom(aX, aY, R) where aX and aY are the x/y positions of A.
		}

		// TODO Check we have a valid dungeon by checking that:
		// - Any rooms that have a minimum count have been added at least that many times.
		// - The total number of rooms generated exceeds MIN_ROOMS_COUNT. 

		// TODO Populate and return a collection of tiles based on the dungeon cells.
	};

	/**
	 * Find all available anchors.
	 */
	this.findAvailableAnchors = function() {

	};

	/**
	 * Gets whether the room can be generated at the specified position.
	 * @param {*} x The dungeon cell x position at which to place the room entrance cell.
	 * @param {*} y The dungeon cell y position at which to place the room entrance cell.
	 * @param {*} room The room to check.
	 * @returns Whether the room can be generated at the position.
	 */
	this.carRoomBeGenerated = function(room, x, y) {
		// TODO Find the room group that the room is in (if there is one) and return false
		// if the number of rooms that belong to the same category and that have already been
		// generated meet the max for the group.

		// TODO If the room has a max then return false if the count has already been met.

		// TODO Return false 

		// The room can be generated
		return true;
	};

	/**
	  * Add a room to the dungeon as the cells that the room occupies.
	 * @param {*} x The dungeon cell x position at which to place the room entrance cell.
	 * @param {*} y The dungeon cell y position at which to place the room entrance cell.
	 * @param {*} room The room to add.
	 */
	this.addRoom = function (x, y, room) {
		// Keep a count of any rooms that we add.
		this.roomCounts[room.id] = (this.roomCounts[room.id] || 0) + 1;

		// Add each room cell to the dungeon.
		for  (const cellInfo of room.cells) {
			// Create a cell instance with an absolute cell position, rather than the relative room one.
			const cell = new Cell(x + cellInfo.position.x, y + cellInfo.position.y, room.id, cellInfo.door, cellInfo.entrance);

			// Add the cell to the dungeon!
			this.cells[cell.getKey()] = cell;
		}
	};

	/**
	 * Get the total number of rooms that have been added to the dungeon.
	 */
	this.getRoomCount = function() {
		return Object.values(this.addedRooms).reduce((total, roomCount) => total + roomCount, 0);
	};
}



















/**
 * Generate a number of rooms in the dungeon.
 */
function generateRooms() {
	// The generated rooms.
	var rooms = [];

	// The number of times we have tried to generate a room.
	var tryNo = 0;

	// Generate as many rooms as we need.
	while (rooms.length < roomCount && tryNo++ < maxTry) {
		// Create a randomly sized/positioned room.
		var room = {
			x: Math.floor(Math.random() * 60),
			y: Math.floor(Math.random() * 60),
			width: Math.floor((Math.random() * (maxRoomSize - minRoomSize)) + minRoomSize),
			height: Math.floor((Math.random() * (maxRoomSize - minRoomSize)) + minRoomSize)
		};

		// Set the room centre.
		room.centre = {
			x: room.x + Math.floor(room.width / 2),
			y: room.y + Math.floor(room.height / 2)
		};

		// Check that the room is within the bounds of the dungeon
		// area and that it does not overlap an existing room.
		if (roomIsWithinDungeonBounds(room) && !overlaps(room, rooms)) {
			rooms.push(room);
		}
	}
	// Return the created rooms.
	return rooms;
}

/**
 * Draw a collection of rooms.
 */
function drawRooms(rooms) {
	for (var i = 0; i < rooms.length; i++) {
		setSpace(space.room, rooms[i].x, rooms[i].y, rooms[i].width, rooms[i].height);
	}
}

/**
 * Generate a number of corridors beteen rooms in the dungeon.
 */
function generateCorridors(rooms) {
	var previous = null;

	var drawVerticalCorridor = function (x, minY, maxY) {
		for (var y = minY; y <= maxY; y++) {
			setSpace(space.corridor, x, y);
		}
	};

	var drawHorizontalCorridor = function (y, minX, maxX) {
		for (var x = minX; x <= maxX; x++) {
			setSpace(space.corridor, x, y);
		}
	};

	for (var i = 0; i < rooms.length; i++) {
		// Get the centre of the current room.
		var current = rooms[i].centre;

		// Do we have two rooms to connect?
		if (previous != null) {
			// Are we going vertically or horizontally first? Flip a coin.
			if (Math.random() >= 0.5) {
				drawVerticalCorridor(current.x, Math.min(current.y, previous.y), Math.max(current.y, previous.y));
				drawHorizontalCorridor(previous.y, Math.min(current.x, previous.x), Math.max(current.x, previous.x));
			}
			else {
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
function applyPatterns() {
	for (var i = 0; i < patterns.length; i++) {
		// Get the current pattern.
		const pattern = patterns[i];

		// Check if the pattern has a min/max property, if so then we are only 
		// matching N times, where N >= min and N <= max. Otherwise, if there is
		// only a chance value then we randomly choose whether to apply it to
		// each matching space in turn.
		if (pattern.min && pattern.max) {
			// Pick how many patterns we are going to apply based on the pattern min/max values.
			const pick = Math.floor(Math.random() * pattern.max) + pattern.min;
			// A function used to find all matches for the current pattern and applies a random one.
			const matchAndApplyPattern = function () {
				// The list of all spaces which match the pattern.
				let matchingSpaces = [];
				// Check this pattern against every space in the dungeon.
				for (var x = 0; x < dungeonSpaceSize; x++) {
					for (var y = 0; y < dungeonSpaceSize; y++) {
						// Check whether the pattern matches the current space, and check whether we should apply it based on chance.
						if (doesPatternMatchSpace(pattern, x, y)) {
							matchingSpaces.push({ x, y });
						}
					}
				}
				// If there were no matching spaces then we cannot apply the pattern.
				if (matchingSpaces.length == 0) {
					console.log("no space matches pattern: " + pattern.name);
				}
				// Pick a random matching space ...
				const matchingSpace = matchingSpaces[Math.floor(Math.random() * matchingSpaces.length)];
				// ... And apply the pattern.
				pattern.onMatch(matchingSpace.x, matchingSpace.y);
			};
			// Apply the pattern randomly as many times as we need. 
			for (var p = 0; p < pick; p++) {
				matchAndApplyPattern();
			}
		}
		else if (pattern.chance) {
			// Check this pattern against every space in the dungeon.
			for (var x = 0; x < dungeonSpaceSize; x++) {
				for (var y = 0; y < dungeonSpaceSize; y++) {
					// Check whether the pattern matches the current space, and check whether we should apply it based on chance.
					if (doesPatternMatchSpace(pattern, x, y) && Math.random() <= pattern.chance) {
						// The pattern matched the current space.
						pattern.onMatch(x, y);
					}
				}
			}
		}
		else {
			console.log("need to specify chance or min/max value for pattern: " + pattern.name);
		}
	}
}

/**
 * Check a pattern matches the specified space.
 */
function doesPatternMatchSpace(pattern, x, y) {
	for (var i = 0; i < pattern.matches.length; i++) {
		const match = pattern.matches[i];
		const offsetX = match[0];
		const offsetY = match[1];
		const types = match[2].split(",");

		if (types.indexOf(getSpace(x + offsetX, y + offsetY)) === -1) {
			return false;
		}
	}

	// Our pattern matched!
	return true;
}

/**
 * Gets whether the specified room interacts with an other rooms.
 * @param room The room to be generated.
 * @param rooms The existing rooms.
 */
function overlaps(room, rooms) {
	for (var i = 0; i < rooms.length; i++) {
		var a = rooms[i];
		var b = room;

		// Check for an overlap on each index independently.
		const overlapOnX = a.x < (b.x + b.width + roomBuffer) && (a.x + a.width + roomBuffer) > b.x;
		const overlapOnY = a.y < (b.y + b.height + roomBuffer) && (a.y + a.height + roomBuffer) > b.y;

		// Check for an overlap on both axis.
		if (overlapOnX && overlapOnY) {
			// There was an overlap!
			return true;
		}
	}
	// There were no overlaps.
	return false;
};

/**
 * Find all reachable walls.
 * These are walls which are reachable by the player.
 */
function findReachableWalls() {
	// Helper function to determine whether the space at 
	// the specified position is a wall or unreachable.
	const isReachable = function (x, y) {
		const target = getSpace(x, y);
		return target !== space.wall.type && target !== space.reachableWall.type && target !== "OOB";
	};

	// Find any walls which have anything other than walls or the dungeon edge on each side.
	for (var x = 0; x < dungeonSpaceSize; x++) {
		for (var y = 0; y < dungeonSpaceSize; y++) {
			// Get the type of the current space.
			const spaceType = getSpace(x, y);

			// Is the current space a wall?
			if (spaceType === space.wall.type) {
				if (isReachable(x + 1, y) || isReachable(x - 1, y) || isReachable(x, y + 1) || isReachable(x, y - 1)) {
					// This wall is reachable by entities within the dungeon! Set the reachable wall.
					setSpace(space.reachableWall, x, y);
				}
			}
		}
	}
}

/**
 * Gets whether a room is within the bounds of the dungeon area.
 */
function roomIsWithinDungeonBounds(room) {
	var min = 1;
	var max = (dungeonSize / spaceSize) - 2;
	var inVerticalBounds = room.y >= min && (room.y + room.height) <= max;
	var inHorizontalBounds = room.x >= min && (room.x + room.width) <= max;
	// Return whether the entire room is withing the horizontal and vertical dungeon bounds.
	return inVerticalBounds && inHorizontalBounds;
};

/**
 * Set a space in the dungeon.
 */
function setSpace(type, x, y, width, height) {
	for (var posX = x; posX < (x + (width || 1)); posX++) {
		for (var posY = y; posY < (y + (height || 1)); posY++) {
			// Add the space to our space mappings.
			spaces[posX + "-" + posY] = type.type;
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
function getSpace(x, y) {
	// Is this position outside the dungeon area?
	const isOutOfBounds = x < 0 || x >= dungeonSpaceSize || y < 0 || y >= dungeonSpaceSize;
	// If this position is out of bounds then return 'OOB'. Otherwise, return the space type.
	return isOutOfBounds ? "OOB" : spaces[x + "-" + y] || space.wall.type;
};