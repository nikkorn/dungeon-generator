/**
 * Generate a dungeon!.
 * @param options The user-defined options.
 * @param patterns The patterns to apply.
 */
function generate(options, patterns) {
	/**
	 * The default options.
	 */
	const defaultOptions = {
		dungeonSize: 50,
		maxRoomSize: 10,
		minRoomSize: 5,
		roomBuffer: 1,
		roomCount: 10,
		corridorWidth: 1,
		attempts: 1000
	};

	/**
	 * Get a random item form an array. 
	 * @param array The array of items.
	 */
	function getRandomItem(array) {
		// We cant pick a random item from an empty array.
		if (!array.length) {
			return null;
		}

		// Return a random item.
		return array[Math.floor(Math.random() * array.length)];
	}

	/**
	 * Represents a lotto draw.
	 */
	function Lotto() {
		/**
		 * The participants
		 */
		this.participants = [];

		/**
		 * Add a participant.
		 * @param participant The participant.
		 * @param tickets The number of tickets held by the participant.
		 */
		this.add = function(participant, tickets) {
			this.participants.push({ participant, tickets });
			return this;
		};

		/**
		 * Draw a winning participant.
		 * @returns A winning participant.
		 */
		this.draw = function() {
			// We cannot do anything if there are no participants.
			if (!this.participants.length) {
				throw "cannot draw a lotto winner when there are no participants";
			}

			const pickable = [];

			this.participants.forEach(({ participant, tickets }) => {
				for (let ticketCount = 0; ticketCount < tickets; ticketCount++) {
					pickable.push(participant);
				}
			});

			return getRandomItem(pickable);
		};
	}

	/**
	 * Creates a new instance of Spaces.
	 * @param dungeonSize The width/height of the dungeon.
	 */
	function Spaces(dungeonSize) {
		/** The space values. */
		this.values = {},

		/**
		 * Set the space at x/y with width/height.
		 */
		this.set = function (type, x, y, width, height, details) {
			for (var posX = x; posX < (x + width); posX++) {
				for (var posY = y; posY < (y + height); posY++) {
					this.values[posX + "-" + posY] = {
						type,
						x: posX,
						y: posY,
						details: details || null
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
			return isOutOfBounds ? { type: "OOB", x, y, details: null } : this.values[x + "-" + y] || { type: "WALL", x, y, details: null };
		},

		/**
		 * Get a list of all spaces.
		 */
		this.getAll = function () {
			return Object.values(this.values);
		}
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
	 * @param options The user-defined options.
	 * @param rooms The generated rooms.
	 * @param spaces The dungeon spaces.
	 */
	function generateCorridors(options, rooms, spaces) {
		var previous = null;

		var drawVerticalCorridor = function (x, minY, maxY) {
			for (var y = minY; y <= maxY; y++) {
				spaces.set("CORRIDOR", x, y, options.corridorWidth, options.corridorWidth);
			}
		};

		var drawHorizontalCorridor = function (y, minX, maxX) {
			for (var x = minX; x <= maxX; x++) {
				spaces.set("CORRIDOR", x, y, options.corridorWidth, options.corridorWidth);
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
		// Create an array of frozen spaces. Any frozen spaces cannot be considered as part of pattern applications.
		const frozenSpaces = [];

		// Helper function to set a frozen space.
		const freezeSpace = (x, y) => frozenSpaces.push(x + "-" + y);

		// Helper function to get whether a space is frozen.
		const isSpaceFrozen = (x, y) => frozenSpaces.indexOf(x + "-" + y) !== -1;

		// Gets whether a pattern matches the specified space.
		const doMatchesApplyToSpace = (matches, x, y) => {
			for (var i = 0; i < matches.length; i++) {
				const match   = matches[i];
				const offsetX = match[0];
				const offsetY = match[1];
				const types   = match[2].split(",");

				// Out pattern cannot overlap any frozen spaces.
				if (isSpaceFrozen(x + offsetX, y + offsetY)) {
					return false;
				} 

				// Out pattern cannot overlap any incompatible spaces.
				if (types.indexOf(spaces.get(x + offsetX, y + offsetY).type) === -1) {
					return false;
				}
			}

			// Our pattern matched!
			return true;
		};

		// Gets a space at which the specified pattern can be applied, or null if no space exists.
		const findMatchingSpace = (pattern) => {
			// If the pattern has a 'matches' array then we are not dealing with multiple options.
			if (pattern.matches) {
				// The list of all x/y spaces which match the pattern.
				const matchingSpaces = [];

				// Check this pattern against every space in the dungeon.
				for (var x = 0; x < options.dungeonSize; x++) {
					for (var y = 0; y < options.dungeonSize; y++) {
						// Check whether the pattern matches the current space, and check whether we should apply it based on chance.
						if (doMatchesApplyToSpace(pattern.matches || [], x, y)) {
							matchingSpaces.push({ 
								x, 
								y, 
								applies: pattern.applies || [] 
							});
						}
					}
				}

				// Return a randomly picked matching space, or null if no matching spaces were found.
				return getRandomItem(matchingSpaces);
			} 
			// If the pattern has an 'options' array then we have to pick one.
			else if (pattern.options) {
				const matchGroups = {};

				// Find all options that match the current space and split them into groups defined by the ticket count of the matched pattern.
				pattern.options.forEach((option) => {
					// The list of all x/y spaces which match the option pattern.
					const matchingSpaces = [];

					// Check this pattern against every space in the dungeon.
					for (var x = 0; x < options.dungeonSize; x++) {
						for (var y = 0; y < options.dungeonSize; y++) {
							// Check whether the pattern matches the current space, and check whether we should apply it based on chance.
							if (doMatchesApplyToSpace(option.matches || [], x, y)) {
								matchingSpaces.push({ 
									x, 
									y, 
									applies: option.applies || [] 
								});
							}
						}
					}

					if (matchingSpaces.length) {
						// Get the ticket count for this option, or default to one.
						const tickets = option.tickets || 1;

						// Make sure that we have a group of matches for the ticket count.
						matchGroups[tickets] = matchGroups[tickets] || [];

						matchGroups[tickets].push.apply(matchGroups[tickets], matchingSpaces);
					}
				});

				// Create a lotto with which to pick a random group based on the ticket counts of all groups.
				const matchGroupsLotto = new Lotto();

				// If we have no matching groups then there were no matching patterns at all, return null.
				if (!Object.keys(matchGroups).length) {
					return null;
				}

				Object.keys(matchGroups).forEach((tickets) => matchGroupsLotto.add(matchGroups[tickets], tickets));

				// Pick a random group based on the ticket counts and return a random item from the winning group.
				return getRandomItem(matchGroupsLotto.draw());
			} else {
				throw "pattern '" + pattern.name + "' must have 'matches' or 'options' defined";
			}
		};

		for (var i = 0; i < patterns.length; i++) {
			// Get the current pattern.
			const pattern = patterns[i];

			// Get the min/max/chance values and default them to something sensible.
			const minimum = pattern.min || 0;
			const maximum = pattern.max || 1;
			const chance  = pattern.chance || 1;

			// Get the option that defines if we are freezing any spaces on pattern application.
			// A frozen space cannot be overwritten by any other pattern applications. 
			// The options are:
			//  - matched      : Any spaces included in the matched array will be frozen.
			//  - set          : Any spaces set as part of the pattern application will be frozen.
			//  - both         : Any spaces set as part of the pattern application and any included in the matched array will be frozen.
			const freeze = pattern.freeze || "any";

			// If there is a chance that the pattern should not be applied we should check now and skip the pattern if the chance fails.
			if (chance < 1 && Math.random() <= pattern.chance) {
				continue;
			}

			// Pick how many patterns we are going to apply based on the pattern min/max values.
			const count = Math.floor(Math.random() * (maximum - minimum + 1)) + minimum;

			// Apply the pattern randomly as many times as we need. 
			for (var p = 0; p < count; p++) {
				// Get a space at which we can apply the pattern.
				const matchingSpace = findMatchingSpace(pattern);

				// Check whether there was no matching spaces at which we could apply the current pattern.
				if (!matchingSpace) {
					// We have run out of places to apply the pattern, but did we at least meet the minimum number of applications?
					if (p < minimum) {
						// We still haven't met the minimum number of applications so we cannot continue.
						throw "could not apply pattern'" + pattern.name + "' the minimum number of times required";
					} else {
						// We cannot make any more applications, but we have at least already met the minimum.
						break;
					}
				}

				// Apply the current pattern to the matching space.
				matchingSpace.applies.forEach(([xOffset, yOffset, type, details]) => {
					// Get the absolute x/y of the space we are trying to set.
					const x = matchingSpace.x + xOffset;
					const y = matchingSpace.y + yOffset;

					// Check to make sure that the space we are trying to set isn't already frozen.
					if (isSpaceFrozen(x, y)) {
						return;
					}

					spaces.set(type, x, y, 1, 1, details);

					// The pattern we just applied may require that any set spaces be frozen.
					if (freeze === "set" || freeze === "both") {
						freezeSpace(x, y);
					}
				});

				// The pattern we just applied may require that any matched spaces be frozen.
				if (freeze === "matched" || freeze === "both") {
					pattern.matches.forEach(([xOffset, yOffset]) => freezeSpace(matchingSpace.x + xOffset, matchingSpace.y + yOffset));
				}
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
			return target.type !== "WALL" && target.type !== "OOB";
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
						spaces.set("WALL", x, y, 1, 1);
					}
				}
			}
		}
	}

	// Apply the default required options to the user-defined options.
	options = { ...defaultOptions, ...options };

	// Create a new instance of Spaces.
	const spaces = new Spaces(options.dungeonSize);

	// Generate the rooms.
	const rooms = generateRooms(options);

	// Generate and draw some corridors between our rooms.
	generateCorridors(options, rooms, spaces);

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
