/** Patterns to match on areas in the dungeon. */
const dungeon_patterns = [];

// A level entry.
dungeon_patterns.push({
  name: "level-entry",
  min: 1,
  max: 1,
  freeze: "matched",
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
    [4, 3, "ROOM"],
    [0, 4, "ROOM"],
    [1, 4, "ROOM"],
    [2, 4, "ROOM"],
    [3, 4, "ROOM"],
    [4, 4, "ROOM"]
  ],
  apply: [
    [1, 1, "WALL"],
    [2, 1, "WALL"],
    [3, 1, "WALL"],
    [1, 2, "WALL"],
    [2, 2, "ENTRY_DOOR"],
    [3, 2, "WALL"]
  ]
});

// A level exit.
dungeon_patterns.push({
  name: "level-exit",
  min: 1,
  max: 1,
  freeze: "matched",
  matches: [
    [-1, 0, "WALL"],
    [0, 0, "WALL"],
    [1, 0, "WALL"],
    [-1, -1, "WALL"],
    [0, -1, "WALL"],
    [1, -1, "WALL"],
    [-1, 1, "ROOM"],
    [0, 1, "ROOM"],
    [1, 1, "ROOM"],
    [-1, 2, "ROOM"],
    [0, 2, "ROOM"],
    [1, 2, "ROOM"]
  ],
  apply: [
    [0, 0, "EXIT_DOOR"]
  ]
});

// Add chest surrounded by enemies.
dungeon_patterns.push({
    name: "guarded-chest",
    min: 3,
    max: 3,
    freeze: "matched",
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
      [4, 3, "ROOM"],
      [0, 4, "ROOM"],
      [1, 4, "ROOM"],
      [2, 4, "ROOM"],
      [3, 4, "ROOM"],
      [4, 4, "ROOM"]
      
    ],
    apply: [
      [2, 2, "CHEST"],
      [0, 4, "ENEMY"],
      [4, 4, "ENEMY"],
      [4, 0, "ENEMY"],
      [0, 0, "ENEMY"]
    ]
});

// A couple of enemies.
dungeon_patterns.push({
  name: "enemy-pair-1",
  min: 3,
  max: 8,
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
    [4, 3, "ROOM"],
    [0, 4, "ROOM"],
    [1, 4, "ROOM"],
    [2, 4, "ROOM"],
    [3, 4, "ROOM"],
    [4, 4, "ROOM"]
    
  ],
  apply: [
    [1, 1, "ENEMY"],
    [3, 3, "ENEMY"]
  ]
});

// A couple of enemies.
dungeon_patterns.push({
  name: "enemy-pair-2",
  min: 3,
  max: 8,
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
    [4, 3, "ROOM"],
    [0, 4, "ROOM"],
    [1, 4, "ROOM"],
    [2, 4, "ROOM"],
    [3, 4, "ROOM"],
    [4, 4, "ROOM"]
    
  ],
  apply: [
    [1, 3, "ENEMY"],
    [3, 1, "ENEMY"]
  ]
});

// A strong enemy.
dungeon_patterns.push({
  name: "strong-enemy",
  min: 1,
  max: 3,
  matches: [
    [0, 0, "ROOM"],
    [1, 0, "ROOM"],
    [2, 0, "ROOM"],
    [0, 1, "ROOM"],
    [1, 1, "ROOM"],
    [2, 1, "ROOM"],
    [0, 2, "ROOM"],
    [1, 2, "ROOM"],
    [2, 2, "ROOM"]
  ],
  apply: [
    [1, 1, "ENEMY_STRONG"]
  ]
});