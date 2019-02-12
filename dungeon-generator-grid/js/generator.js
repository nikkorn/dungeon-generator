/**
 * The dungeon generator.
 */
function Generator() {
	/**
	 * A dictionary mapping dungeon cell x/y positional keys to the cells that occupy them.
	 */
	this.cells = {};
	/**
	 * A dictionary mapping room names to the count of times they have been added to the dungeon.
	 */
	this.roomCounts = {};

	/**
	 * Generate!
	 */
	this.generate = function () {
		// A function which attempts to generate a dungeon, returning a success status and the dungeon tiles if successful.
		const attempt = function() {
			// Clear the cells dictionary and the dictionary storing added room information.
			this.cells      = {};
			this.addedRooms = {};

			// Add the spawn room to the center of the dungeon, this should always be a success.
			this.addRoom(0, 0, getRoom("spawn"), 0);

			// Keep track of the number of times we have attempted to add a room and failed.
			let roomGenerationFailureCount = 0;

			// While we need to populate our dungeon with rooms find a room and bolt it on.
			while (this.getRoomCount() < MAX_ROOMS_COUNT && roomGenerationFailureCount < MAX_ROOM_GENERATE_RETRY) {
				// Find all available anchors and pick any random one.
				const anchor = getRandomItem(this.findAvailableAnchors());

				// Get all rooms where the entrance matches the direction of the anchor.
				const attachableRooms = rooms.filter(room => getRoomEntranceDirection(room) === anchor.getJoinDirection()); 

				// TODO MAYBE Randomly pick a room rarity and filter X by that rarity.

				// Randomly pick a generatable room definition.
				const generatableRoom = attachableRooms.find(room => this.canRoomBeGenerated(room, anchor));

				// Generate a room if we have a valid generatable room definition.
				if (generatableRoom) {
					this.addRoom(anchor.getX(), anchor.getY(), generatableRoom, anchor.getDepth());
				} else {
					roomGenerationFailureCount++;
				}
			}

			// We failed to generate the dungeon if we didn't meet the minimum number of rooms.
			if (this.getRoomCount() < MIN_ROOMS_COUNT) {
				return { success: false };
			}

			// TODO Check we have a valid dungeon by checking that:
			// - Any rooms that have a minimum count have been added at least that many times.
			// - The total number of rooms generated exceeds MIN_ROOMS_COUNT. 

			// TODO Populate and return a collection of tiles based on the dungeon cells.
			return { success: true, tiles: this.convertCellsToTiles() };
		}

		// Keep track of the number of times we have attempted to create the dungeon and failed.
		let dungeonGenerationFailureCount = 0;

		// Keep trying to generate the dungeon until we hit the attempt limit.
		while (dungeonGenerationFailureCount < MAX_DUNGEON_GENERATE_RETRY) {
			const dungeonGenerationAttempt = attempt.call(this);

			// If we succeeded then just return the generated tiles.
			if (dungeonGenerationAttempt.success) {
				return dungeonGenerationAttempt.tiles;
			}

			dungeonGenerationFailureCount++;
		}

		// We completely failed to generate a valid dungeon!
		throw "reached dungeon generation attempt limit!";
	};

	/**
	 * Get the cell at the x/y position, or undefined if a cell does not exist at the position.
	 * @param x The x position. 
	 * @param y The y position.
	 * @returns The cell at the x/y position, or undefined if a cell does not exist at the position. 
	 */
	this.getCellAt = function(x, y) {
		return this.cells[getPositionKey(x, y)];
	};

	/**
	 * Find all available anchors.
	 */
	this.findAvailableAnchors = function() {
		// Create an array to store all of the available anchors.
		const anchors = [];

		// A function to get whether an x/y cell position is free.
		const isCellPositionFree = (x, y) => !this.cells[getPositionKey(x, y)];

		// Iterate over all existing cells and get all adjacent cell positions as anchors that are not blocked.
		for (const cell of Object.values(this.cells)) {
			// Get the position of the cell.
			const cellX = cell.getX();
			const cellY = cell.getY(); 

			// Get the depth of the next room.
			const nextRoomDepth = cell.getDepth() + 1;

			// Can we create an anchor above?
			if (isCellPositionFree(cellX, cellY + 1) && cell.isJoinableAt(DIRECTION.NORTH)) {
				anchors.push(new Anchor(cellX, cellY + 1, DIRECTION.SOUTH, nextRoomDepth));
			}

			// Can we create an anchor below?
			if (isCellPositionFree(cellX, cellY - 1) && cell.isJoinableAt(DIRECTION.SOUTH)) {
				anchors.push(new Anchor(cellX, cellY - 1, DIRECTION.NORTH, nextRoomDepth));
			}

			// Can we create an anchor to the left?
			if (isCellPositionFree(cellX - 1, cellY) && cell.isJoinableAt(DIRECTION.WEST)) {
				anchors.push(new Anchor(cellX - 1, cellY, DIRECTION.EAST, nextRoomDepth));
			}

			// Can we create an anchor to the right?
			if (isCellPositionFree(cellX + 1, cellY) && cell.isJoinableAt(DIRECTION.EAST)) {
				anchors.push(new Anchor(cellX + 1, cellY, DIRECTION.WEST, nextRoomDepth));
			}
		}

		// Return all of the anchors that were found.
		return anchors;
	};

	/**
	 * Gets whether the room can be generated at the specified anchor.
	 * @param room The room to check.
	 * @param anchor The anchor.
	 * @returns Whether the room can be generated at the anchor.
	 */
	this.canRoomBeGenerated = function(room, anchor) {
		// Find the room group that the room is in (if there is one).
		const roomGroup = roomGroups.find(group => group.rooms.includes(room.name));

		// Return false if the number of rooms that belong to the same category 
		// and that have already been generated meet the max for the group.
		if (roomGroup && roomGroup.max) {
			// Get the total number of times that rooms in the same group have been generated.
			const roomGroupGenerationCount = roomGroup.rooms
				.map(roomId => this.roomCounts[roomId] || 0)
				.reduce((total, roomCount) => total + roomCount, 0);

			// Have we met the group max?
			if (roomGroupGenerationCount >= roomGroup.max) {
				return false;
			}
		}

		// If the room has a max then return false if the count has already been met.
		if (room.max && (this.roomCounts[room.name] || 0) >= room.max) {
			return false;
		}

		// Check to make sure that all of the cell positions that will be taken up by the room are available.
		for (const cell of room.cells) {
			if (this.cells[getPositionKey(cell.position.x + anchor.getX(), cell.position.y + anchor.getY())]) {
				// The cell position is taken!
				return false;
			}
		}

		// The room can be generated!
		return true;
	};

	/**
	  * Add a room to the dungeon as the cells that the room occupies.
	 * @param x The dungeon cell x position at which to place the room entrance cell.
	 * @param y The dungeon cell y position at which to place the room entrance cell.
	 * @param room The room to add.
	 * @param depth The depth of the room into the dungeon.
	 */
	this.addRoom = function (x, y, room, depth) {
		// Keep a count of any rooms that we add.
		this.roomCounts[room.name] = (this.roomCounts[room.name] || 0) + 1;

		// Create a unique room id to represent a generated room instance.
		const uniqueRoomId = getPositionKey(x, y);

		// Add each room cell to the dungeon.
		for (const cellInfo of room.cells) {
			// Create a cell instance with an absolute cell position, rather than the relative room one.
			const cell = new Cell(x + cellInfo.position.x, y + cellInfo.position.y, depth, uniqueRoomId, room.name, cellInfo.blocked, cellInfo.door, cellInfo.entrance);

			// Add the cell to the dungeon!
			this.cells[getCellKey(cell)] = cell;
		}
	};

	/**
	 * Convert the dungeon cells into an array of dungeon tiles.
	 */
	this.convertCellsToTiles = function() {
		const tiles = {};

		// Convert each cell into a bunch of tiles.
		Object.values(this.cells).forEach(cell => {
			// Get the x/y position of the bottom left tile in the cell.
			const tileXMin = cell.getX() * (CELL_TILE_SIZE + 1);
			const tileYMin = cell.getY() * (CELL_TILE_SIZE + 1);
			const tileXMax = tileXMin + CELL_TILE_SIZE;
			const tileYMax = tileYMin + CELL_TILE_SIZE;

			// Create a tile for each tile position in the cell.
			for (let tileX = tileXMin; tileX < tileXMax; tileX++) {
				for (let tileY = tileYMin; tileY < tileYMax; tileY++) {
					tiles[getPositionKey(tileX, tileY)] = { 
						x: tileX, 
						y: tileY, 
						roomName: cell.getRoomName(),
						depth: cell.getDepth(),
						type: TILE.ROOM
					};
				}
			}

			// Are we generating a door for the cell?
			if (cell.getDoor()) {
				// Get the door type.
				const door = cell.getDoor();
				// Get the direction at which to generate the door.
				const doorDirection = cell.getDoorDirection();
				// Helper function to get the door position.
				const getDoorPosition = () => {
					// Where we place the door tile depends on its direction.
					switch (doorDirection) {
						case DIRECTION.NORTH:
							return { x: tileXMin + CELL_DOOR_OFFSET, y: tileYMax };
						case DIRECTION.SOUTH:
							return { x: tileXMin + CELL_DOOR_OFFSET, y: tileYMin - 1 };
						case DIRECTION.EAST:
							return { x: tileXMax, y: tileYMin + CELL_DOOR_OFFSET };
						case DIRECTION.WEST:
							return { x: tileXMin - 1, y: tileYMin + CELL_DOOR_OFFSET };
					}
				};
				// Get the door position.
				const doorPosition = getDoorPosition();
				// Create the door tile at the correct position.
				tiles[getPositionKey(doorPosition.x, doorPosition.y)] = { 
					x: doorPosition.x, 
					y: doorPosition.y, 
					doorType: door,
					doorDirection: doorDirection,
					roomId: cell.getRoomId(),
					roomName: cell.getRoomName(),
					depth: cell.getDepth(),
					type: TILE.ROOM
				};
			}

			// Is the cell above this one in the same room?
			if (this.areRoomCellsBridged(cell.getX(), cell.getY(), DIRECTION.NORTH)) {
				// Create a tile for each tile position in the cell.
				for (let tileX = tileXMin; tileX < tileXMax; tileX++) {
					tiles[getPositionKey(tileX, tileYMax)] = { 
						x: tileX, 
						y: tileYMax, 
						roomName: cell.getRoomName(),
						depth: cell.getDepth(),
						type: TILE.ROOM
					};
				}
			}

			// Is the cell above this one in the same room?
			if (this.areRoomCellsBridged(cell.getX(), cell.getY(), DIRECTION.SOUTH)) {
				// Create a tile for each tile position in the cell.
				for (let tileX = tileXMin; tileX < tileXMax; tileX++) {
					tiles[getPositionKey(tileX, tileYMin - 1)] = { 
						x: tileX, 
						y: tileYMin - 1, 
						roomName: cell.getRoomName(),
						depth: cell.getDepth(),
						type: TILE.ROOM
					};
				}
			}

			// Is the cell to the left of this one in the same room?
			if (this.areRoomCellsBridged(cell.getX(), cell.getY(), DIRECTION.WEST)) {
				// Create a tile for each tile position in the cell.
				for (let tileY = tileYMin; tileY < tileYMax; tileY++) {
					tiles[getPositionKey(tileXMin - 1, tileY)] = { 
						x: tileXMin - 1, 
						y: tileY, 
						roomName: cell.getRoomName(),
						depth: cell.getDepth(),
						type: TILE.ROOM
					};
				}
			}

			// Is the cell to the right of this one in the same room?
			if (this.areRoomCellsBridged(cell.getX(), cell.getY(), DIRECTION.EAST)) {
				// Create a tile for each tile position in the cell.
				for (let tileY = tileYMin; tileY < tileYMax; tileY++) {
					tiles[getPositionKey(tileXMax, tileY)] = { 
						x: tileXMax, 
						y: tileY, 
						roomName: cell.getRoomName(),
						depth: cell.getDepth(),
						type: TILE.ROOM
					};
				}
			}
		});

		// TODO Fill in the reachable wall tiles.

		return Object.values(tiles);
	}

	/**
	 * Gets whether the cell at the x/y position and the cell in the specified direction are in the same room.
	 * @param x The initial cell x position.
	 * @param y The initial cell y position.
	 */
	this.areRoomCellsBridged = function(x, y, direction) {
		// Get the initial cell.
		const initialCell = this.cells[getPositionKey(x, y)];

		// If the cell is not a room cell then there will definitely not be a bridge.
		if (!initialCell) {
			return false;
		}

		// Helper function to get the target cell position based on direction.
		const getTargetCellPosition = () => {
			switch (direction) {
				case DIRECTION.NORTH:
					return { x, y: y + 1 };
				case DIRECTION.SOUTH:
					return { x, y: y - 1 };
				case DIRECTION.EAST:
					return { x: x + 1, y };
				case DIRECTION.WEST:
					return { x: x - 1, y };
			}
		};

		// Get the target cell position.
		const targetCellPosition = getTargetCellPosition();

		// Get the target cell.
		const targetCell = this.cells[getPositionKey(targetCellPosition.x, targetCellPosition.y)];

		// Are the cells within the same room instance.
		return targetCell && targetCell.getRoomId() === initialCell.getRoomId();
	}

	/**
	 * Get the total number of rooms that have been added to the dungeon.
	 */
	this.getRoomCount = function() {
		return Object.values(this.roomCounts).reduce((total, roomCount) => total + roomCount, 0);
	};
}