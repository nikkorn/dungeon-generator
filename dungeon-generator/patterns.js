/** Patterns to match on areas in the dungeon. */
const dungeon_patterns = [];

// A level entry.
dungeon_patterns.push({
  name: "level-entry",
  chance: 0.1,
  min: 1,
  max: 1,
  matches: [
    [0, 0, "ROOM"],
    [1, 0, "ROOM"],
    [2, 0, "ROOM"],
    [3, 0, "ROOM"],
    [4, 0, "ROOM"],
    [0, 1, "ROOM"],
    [1, 1, "ROOM"],
    [2, 1, "ROOM"],
    [3, 1, "ROOM"],
    [4, 1, "ROOM"],
    [0, 2, "ROOM"],
    [1, 2, "ROOM"],
    [2, 2, "ROOM"],
    [3, 2, "ROOM"],
    [4, 2, "ROOM"],
    [0, 3, "ROOM"],
    [1, 3, "ROOM"],
    [2, 3, "ROOM"],
    [3, 3, "ROOM"],
    [4, 3, "ROOM"]
  ],
  onMatch: function (x, y, setSpace) {
    setSpace("WALL", x + 1, y + 1);
    setSpace("WALL", x + 2, y + 1);
    setSpace("WALL", x + 3, y + 1);
    setSpace("WALL", x + 1, y + 2);
    setSpace("ENTRY_DOOR", x + 2, y + 2);
    setSpace("WALL", x + 3, y + 2);
  }
});

// A level exit.
dungeon_patterns.push({
  name: "level-exit",
  chance: 0.1,
  min: 1,
  max: 1,
  matches: [
    [-1, 0, "WALL"],
    [0, 0, "WALL"],
    [1, 0, "WALL"],
    [-1, -1, "WALL"],
    [0, -1, "WALL"],
    [1, -1, "WALL"],
    [-1, 1, "ROOM"],
    [0, 1, "ROOM"],
    [1, 1, "ROOM"]
  ],
  onMatch: function (x, y, setSpace) {
    setSpace("EXIT_DOOR", x, y);
  }
});

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