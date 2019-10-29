const defaultOptions = {
	dungeonSize: 50,
	maxRoomSize: 10,
	minRoomSize: 5,
	roomBuffer: 1,
	roomCount: 10,
	attempts: 1000
};

/**
 * Creates a new instance of Spaces.
 * @param dungeonSize The width/height of the dungeon.
 */
function Spaces(dungeonSize) {
	/** The space values. */
	this.values = {},

	/**
	 * Set the space at x/y.
	 */
	this.set = function (type, x, y, width, height) {
		for (var posX = x; posX < (x + (width || 1)); posX++) {
			for (var posY = y; posY < (y + (height || 1)); posY++) {
				this.values[posX + "-" + posY] = {
					type,
					x: posX,
					y: posY
				};
			}
		}
	},

	/**
	 * Get the space at x/y.
	 */
	this.get = function (x, y) {
		// Is this position outside the dungeon area?
		const isOutOfBounds = x < 0 || x >= dungeonSize || y < 0 || y >= dungeonSize;
		// If this position is out of bounds then return 'OOB'. Otherwise, return the space type.
		return isOutOfBounds ? { type: "OOB", x, y } : this.values[x + "-" + y] || { type: "WALL", x, y };
	},

	/**
	 * Get a list of all spaces.
	 */
	this.getAll = function () {
		return Object.values(this.values);
	}
}

/**
 * Generate a dungeon!
 * @param options The user-defined options.
 */
function generate(options, patterns) {
	// Apply the default required options to the user-defined options.
	options = { ...defaultOptions, ...options };

	// Create a new instance of Spaces.
	const spaces = new Spaces(options.dungeonSize);

	// Generate the rooms.
	var rooms = generateRooms(options);

	// Generate and draw some corridors between our rooms.
	generateCorridors(rooms, spaces);

	// Apply the room tiles.
	for (var i = 0; i < rooms.length; i++) {
		spaces.set("ROOM", rooms[i].x, rooms[i].y, rooms[i].width, rooms[i].height);
	}

	// Go over every space in the dungeon area and compare a series of patterns to the position.
	applyPatterns(options, spaces, patterns);

	// Determine which walls are actually reachable (not surrounded by other walls) and set them.
	setReachableWalls(options, spaces);

	return {
		tiles: spaces.getAll(),
		size: options.dungeonSize
	};
}

/**
 * Generate a number of rooms in the dungeon.
 * @param options The user-defined options.
 */
function generateRooms(options) {
	// The generated rooms.
	var rooms = [];

	// The number of times we have tried to generate a room.
	var attempt = 0;

	// Gets whether a room is within the bounds of the dungeon area.
	const roomIsWithinDungeonBounds = (room) => {
		var min                = 1;
		var max                = options.dungeonSize - 2;
		var inVerticalBounds   = room.y >= min && (room.y + room.height) <= max;
		var inHorizontalBounds = room.x >= min && (room.x + room.width) <= max;
		// Return whether the entire room is withing the horizontal and vertical dungeon bounds.
		return inVerticalBounds && inHorizontalBounds;
	};

	// Gets whether the specified room interacts with an other rooms.
	const roomHasOverlaps = (room) => {
		for (var i = 0; i < rooms.length; i++) {
			var a = rooms[i];
			var b = room;

			// Check for an overlap on each index independently.
			const overlapOnX = a.x < (b.x + b.width + options.roomBuffer) && (a.x + a.width + options.roomBuffer) > b.x;
			const overlapOnY = a.y < (b.y + b.height + options.roomBuffer) && (a.y + a.height + options.roomBuffer) > b.y;

			// Check for an overlap on both axis.
			if (overlapOnX && overlapOnY) {
				// There was an overlap!
				return true;
			}
		}
		// There were no overlaps.
		return false;
	};

	// Generate as many rooms as we need.
	while (rooms.length < options.roomCount && attempt++ < options.attempts) {
		// Create a randomly sized/positioned room.
		var room = {
			x: Math.floor(Math.random() * options.dungeonSize),
			y: Math.floor(Math.random() * options.dungeonSize),
			width: Math.floor((Math.random() * (options.maxRoomSize - options.minRoomSize)) + options.minRoomSize),
			height: Math.floor((Math.random() * (options.maxRoomSize - options.minRoomSize)) + options.minRoomSize)
		};

		// Set the room centre.
		room.centre = {
			x: room.x + Math.floor(room.width / 2),
			y: room.y + Math.floor(room.height / 2)
		};

		// Check that the room is within the bounds of the dungeon
		// area and that it does not overlap an existing room.
		if (roomIsWithinDungeonBounds(room) && !roomHasOverlaps(room)) {
			rooms.push(room);
		}
	}
	// Return the created rooms.
	return rooms;
}

/**
 * Generate a number of corridors between rooms in the dungeon.
 * @param rooms The generated rooms.
 * @param spaces The dungeon spaces.
 */
function generateCorridors(rooms, spaces) {
	var previous = null;

	var drawVerticalCorridor = function (x, minY, maxY) {
		for (var y = minY; y <= maxY; y++) {
			spaces.set("CORRIDOR", x, y);
		}
	};

	var drawHorizontalCorridor = function (y, minX, maxX) {
		for (var x = minX; x <= maxX; x++) {
			spaces.set("CORRIDOR", x, y);
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
 * @param options The user-defined options.
 * @param spaces The dungeon spaces.
 * @param patterns The applicable patterns.
 */
function applyPatterns(options, spaces, patterns) {
	// Gets whether a pattern matches the specified space.
	const doesPatternMatchSpace = (pattern, x, y) => {
		for (var i = 0; i < pattern.matches.length; i++) {
			const match   = pattern.matches[i];
			const offsetX = match[0];
			const offsetY = match[1];
			const types   = match[2].split(",");

			if (types.indexOf(spaces.get(x + offsetX, y + offsetY).type) === -1) {
				return false;
			}
		}

		// Our pattern matched!
		return true;
	};

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
				for (var x = 0; x < options.dungeonSize; x++) {
					for (var y = 0; y < options.dungeonSize; y++) {
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
			for (var x = 0; x < options.dungeonSize; x++) {
				for (var y = 0; y < options.dungeonSize; y++) {
					// Check whether the pattern matches the current space, and check whether we should apply it based on chance.
					if (doesPatternMatchSpace(pattern, x, y) && Math.random() <= pattern.chance) {
						// The pattern matched the current space.
						pattern.onMatch(x, y, (...args) => spaces.set(...args));
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
 * Set all reachable wall positions.
 * @param options The user-defined options.
 * @param spaces The dungeon spaces.
 */
function setReachableWalls(options, spaces) {
	// Helper function to determine whether the space at 
	// the specified position is a wall or unreachable.
	const isReachable = function (x, y) {
		const target = spaces.get(x, y);
		return target.type !== "WALL" && target.type !== "REACHABLE-WALL" && target.type !== "OOB";
	};

	// Find any walls which have anything other than walls or the dungeon edge on each side.
	for (var x = 0; x < options.dungeonSize; x++) {
		for (var y = 0; y < options.dungeonSize; y++) {
			// Get the type of the current space.
			const space = spaces.get(x, y);

			// Is the current space a wall?
			if (space.type === "WALL") {
				if (
					isReachable(x - 1, y + 1) || 
					isReachable(x, y + 1) || 
					isReachable(x + 1, y + 1) || 
					isReachable(x - 1, y) ||
					isReachable(x + 1, y) || 
					isReachable(x - 1, y - 1) || 
					isReachable(x, y - 1) || 
					isReachable(x + 1, y - 1)
				) {
					// This wall is reachable by entities within the dungeon! Set the reachable wall.
					spaces.set("REACHABLE-WALL", x, y);
				}
			}
		}
	}
}