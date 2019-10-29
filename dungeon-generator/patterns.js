/** Patterns to match on areas in the dungeon. */
const dungeon_patterns = [];

// Add vertical corridor.
dungeon_patterns.push({
    name: "vertical-pillar-corridor",
    chance: 0.1,
    matches: [
	    [-2, 3, "WALL"],
      [-2, 2, "WALL"],
      [-2, 1, "WALL"],
      [-2, 0, "WALL"],
      [-2, -1, "WALL"],
      [-2, -2, "WALL"],
	    [-2, -3, "WALL"],
	    [-1, 3, "WALL"],
      [-1, 2, "WALL"],
      [-1, 1, "WALL"],
      [-1, 0, "WALL"],
      [-1, -1, "WALL"],
      [-1, -2, "WALL"],
	    [-1, -3, "WALL"],
	    [0, 3, "CORRIDOR"],
      [0, 2, "CORRIDOR"],
      [0, 1, "CORRIDOR"],
      [0, 0, "CORRIDOR"],
      [0, -1, "CORRIDOR"],
      [0, -2, "CORRIDOR"],
	    [0, -3, "CORRIDOR"],
	    [1, 3, "WALL"],
      [1, 2, "WALL"],
      [1, 1, "WALL"],
      [1, 0, "WALL"],
      [1, -1, "WALL"],
      [1, -2, "WALL"],
	    [1, 3, "WALL"],
	    [2, 3, "WALL"],
      [2, 2, "WALL"],
      [2, 1, "WALL"],
      [2, 0, "WALL"],
      [2, -1, "WALL"],
      [2, -2, "WALL"],
	    [2, 3, "WALL"]
    ],
    onMatch: function (x, y, setSpace) {
      setSpace("ROOM", x - 1, y - 2, 3, 5);
      setSpace("PILLAR", x, y + 1);
      setSpace("PILLAR", x, y - 1);
    }
});