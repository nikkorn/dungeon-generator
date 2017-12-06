/** Patterns to match on areas in the dungeon. */
const patterns = [
  {
    name: "room-door-below",
    chance: 1,
    matches: [
      [1, 0, "WALL"],
      [0, 0, "CORRIDOR"],
      [-1, 0, "WALL"],
      [0, -1, "ROOM"]
    ],
    onMatch: function (x, y) {
      setSpace(space.door, x, y);
    }
  },
  {
    name: "room-door-above",
    chance: 1,
    matches: [
      [1, 0, "WALL"],
      [0, 0, "CORRIDOR"],
      [-1, 0, "WALL"],
      [0, 1, "ROOM"]
    ],
    onMatch: function (x, y) {
      setSpace(space.door, x, y);
    }
  },
  {
    name: "room-door-left",
    chance: 1,
    matches: [
      [0, 1, "WALL"],
      [0, 0, "CORRIDOR"],
      [0, -1, "WALL"],
      [-1, 0, "ROOM"]
    ],
    onMatch: function (x, y) {
      setSpace(space.door, x, y);
    }
  },
  {
    name: "room-door-right",
    chance: 1,
    matches: [
      [0, 1, "WALL"],
      [0, 0, "CORRIDOR"],
      [0, -1, "WALL"],
      [1, 0, "ROOM"]
    ],
    onMatch: function (x, y) {
      setSpace(space.door, x, y);
    }
  }
];

// Add vertical corridor.
patterns.push({
    name: "vertical-pillar-corridor",
    chance: 0.1,
    matches: [
      [0, 2, "CORRIDOR"],
      [0, 1, "CORRIDOR"],
      [0, 0, "CORRIDOR"],
      [0, -1, "CORRIDOR"],
      [0, -2, "CORRIDOR"],
      [-1, 2, "WALL"],
      [-1, 1, "WALL"],
      [-1, 0, "WALL"],
      [-1, -1, "WALL"],
      [-1, -2, "WALL"],
      [1, 2, "WALL"],
      [1, 1, "WALL"],
      [1, 0, "WALL"],
      [1, -1, "WALL"],
      [1, -2, "WALL"]
    ],
    onMatch: function (x, y) {
      setSpace(space.room, x - 1, y - 2, 3, 5);
      setSpace(space.pillar, x, y + 1);
      setSpace(space.pillar, x, y - 1);
      // Maybe sneak an enemy between the pillars.
      if (Math.random() <= 0.15) {
          setSpace(space.enemy, x, y);
      }
    }
});

// Add basic enemy.
patterns.push({
    name: "enemy",
    chance: 0.03,
    matches: [
      [0, 1, "ROOM"],
      [0, 0, "ROOM"],
      [0, -1, "ROOM"],
      [-1, 1, "ROOM"],
      [-1, 0, "ROOM"],
      [-1, -1, "ROOM"],
      [1, 1, "ROOM"],
      [1, 0, "ROOM"],
      [1, -1, "ROOM"]
    ],
    onMatch: function (x, y) {
      setSpace(space.enemy, x, y);
    }
});

// Add pickups (west of rooms).
patterns.push({
    name: "pickup",
    chance: 0.03,
    matches: [
      [0, 1, "WALL"],
      [0, 0, "WALL"],
      [0, -1, "WALL"],
      [1, 0, "ROOM"]
    ],
    onMatch: function (x, y) {
      setSpace(space.pickup, x, y);
    }
});

// Add pickups (east of rooms).
patterns.push({
    name: "pickup",
    chance: 0.03,
    matches: [
      [0, 1, "WALL"],
      [0, 0, "WALL"],
      [0, -1, "WALL"],
      [-1, 0, "ROOM"]
    ],
    onMatch: function (x, y) {
      setSpace(space.pickup, x, y);
    }
});