/**
 * The collection of all generatable rooms.
 */
const rooms = [];

/**
 * The collection of all room groups.
 * A room group defines characteristics for all rooms that share that group.
 */
const roomGroups = [];

/**
 * The spawn room that is always generated at the centre of the dungeon, there will always be only one.
 */
rooms.push(
    {
        name: "spawn",
        min: 1,
        max: 1,
        cells: [
            {
                position: { x: 0, y: 0 },
                entities: [
                    { id: "spawn_panel", x: 1,  y: 1 },
                    { id: "spawn_panel", x: 1,  y: 3 },
                    { id: "spawn_panel", x: 3,  y: 1 },
                    { id: "spawn_panel", x: 3,  y: 3 }
                ]
            }
        ]
    }
);

/**
 * A simple empty 3*1 room accessible from the east side.
 */
rooms.push(
    {
        name: "empty_3*1_room",
        max: 3,
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.EAST,
                door: DOOR.NO_KEY,
                entities: []
            },
            {
                position: { x: -1, y: 0 },
                entities: [
                    {
                        participants: [
                            {
                                tickets: 1
                            },
                            {
                                tickets: 1, 
                                entities: [
                                    { 
                                        id: "zombie", 
                                        x: 1,  
                                        y: 1 
                                    },
                                    { 
                                        id: "zombie", 
                                        x: 3,  
                                        y: 3 
                                    }
                                ]
                            },
                            {
                                tickets: 1, 
                                entities: [
                                    { 
                                        id: "zombie_strong", 
                                        x: 1,  
                                        y: 1 
                                    }
                                ]
                            }
                        ]
                    }
                ]
            },
            {
                position: { x: -2, y: 0 },
                blocked: [],
                entities: []
            }
        ]
    }
);

/**
 * A simple empty 2*1 room accessible from the west side.
 */
rooms.push(
    {
        name: "empty_2*1_room_west",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.WEST,
                door: DOOR.NO_KEY,
                entities: []
            },
            {
                position: { x: 1, y: 0 },
                entities: []
            }
        ]
    }
);

/**
 * A simple empty 2*1 room accessible from the east side.
 */
rooms.push(
    {
        name: "empty_2*1_room_east",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.EAST,
                door: DOOR.NO_KEY,
                entities: []
            },
            {
                position: { x: 1, y: 0 },
                entities: []
            }
        ]
    }
);

/**
 * A simple empty 1*1 room accessible from the west side.
 */
rooms.push(
    {
        name: "empty_1*1_room_west",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.WEST,
                door: DOOR.NO_KEY,
                entities: []
            }
        ]
    }
);

/**
 * A simple empty 1*1 room accessible from the east side.
 */
rooms.push(
    {
        name: "empty_1*1_room_east",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.EAST,
                door: DOOR.NO_KEY,
                entities: []
            }
        ]
    }
);

/**
 * An ultimate treasure 1*1 room accessible only from the east side.
 * A room with a five key door that guarantees a chest.
 */
rooms.push(
    {
        name: "ultimate_treasure_east",
        max: 1,
        chance: 0.5,
        depth: {
            minimum: 4
        },
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.EAST,
                door: DOOR.FIVE_KEYS,
                blocked: [DIRECTION.NORTH, DIRECTION.SOUTH, DIRECTION.WEST],
                entities: [
                    {
                        participants: [
                            {
                                tickets: 1, 
                                x: 2,
                                y: 2,
                                id: "ultra_rare_chest", 
                                direction: DIRECTION.EAST 
                            },
                            {
                                tickets: 3, 
                                x: 2,
                                y: 2,
                                id: "rare_chest", 
                                direction: DIRECTION.EAST 
                            }
                        ]
                    }
                ]
            }
        ]
    }
);

/**
 * An ultimate treasure or strong enemy 1*1 room accessible only from the east side.
 */
rooms.push(
    {
        name: "ultimate_trick_or_treat_east",
        max: 1,
        chance: 0.5,
        depth: {
            minimum: 4
        },
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.EAST,
                door: DOOR.FOUR_KEYS,
                blocked: [DIRECTION.NORTH, DIRECTION.SOUTH, DIRECTION.WEST],
                entities: [
                    {
                        participants: [
                            {
                                tickets: 1, 
                                x: 2,
                                y: 2,
                                id: "ultra_rare_chest", 
                                direction: DIRECTION.EAST 
                            },
                            {
                                tickets: 1, 
                                entities: [
                                    { 
                                        id: "ghost_strong", 
                                        x: 1,  
                                        y: 1 
                                    },
                                    { 
                                        id: "ghost_strong", 
                                        x: 3,  
                                        y: 3 
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    }
);

/**
 * A simple empty 1*3 room accessible from the south side.
 */
rooms.push(
    {
        name: "empty_1*3_room",
        max: 3,
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.SOUTH,
                door: DOOR.THREE_KEYS,
                entities: [
                    {
                        participants: [
                            {
                                tickets: 1
                            },
                            {
                                tickets: 1, 
                                entities: [
                                    { 
                                        id: "bat", 
                                        x: 1,  
                                        y: 1 
                                    },
                                    { 
                                        id: "bat", 
                                        x: 3,  
                                        y: 3 
                                    }
                                ]
                            }
                        ]
                    }
                ]
            },
            {
                position: { x: 0, y: 1 },
                entities: [
                    {
                        participants: [
                            {
                                tickets: 3
                            },
                            {
                                tickets: 1, 
                                entities: [
                                    { 
                                        id: "bat_strong", 
                                        x: 2,  
                                        y: 2 
                                    }
                                ]
                            }
                        ]
                    }
                ]
            },
            {
                position: { x: 0, y: 2 },
                blocked: [],
                entities: [
                    {
                        participants: [
                            {
                                tickets: 1
                            },
                            {
                                tickets: 1, 
                                entities: [
                                    { 
                                        id: "bat", 
                                        x: 1,  
                                        y: 1 
                                    },
                                    { 
                                        id: "bat", 
                                        x: 3,  
                                        y: 3 
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    }
);

/**
 * An L shaped room accessible from the south side.
 */
rooms.push(
    {
        name: "l_shape_south_room",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.SOUTH,
                door: DOOR.THREE_KEYS,
                entities: []
            },
            {
                position: { x: 0, y: 1 },
                entities: [
                    {
                        participants: [
                            {
                                tickets: 1
                            },
                            {
                                tickets: 1, 
                                entities: [
                                    { 
                                        id: "bat", 
                                        x: 0,  
                                        y: 0 
                                    },
                                    { 
                                        id: "bat", 
                                        x: 4,  
                                        y: 4 
                                    }
                                ]
                            }
                        ]
                    }
                ]
            },
            {
                position: { x: 1, y: 1 },
                entities: []
            }
        ]
    }
);

/**
 * An L shaped room accessible from the north side.
 */
rooms.push(
    {
        name: "l_shape_north_room",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.NORTH,
                door: DOOR.THREE_KEYS,
                entities: []
            },
            {
                position: { x: 0, y: -1 },
                entities: [
                    {
                        tickets: 1
                    },
                    {
                        tickets: 1, 
                        entities: [
                            { 
                                id: "bat", 
                                x: 0,  
                                y: 0 
                            },
                            { 
                                id: "bat", 
                                x: 4,  
                                y: 4 
                            }
                        ]
                    }
                ]
            },
            {
                position: { x: -1, y: -1 },
                entities: []
            }
        ]
    }
);

/**
 * An L shaped room accessible from the north side.
 */
rooms.push(
    {
        name: "sideways_hat_room",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.NORTH,
                door: DOOR.THREE_KEYS,
                entities: []
            },
            {
                position: { x: 0, y: -1 },
                entities: []
            },
            {
                position: { x: 1, y: -1 },
                entities: [
                    {
                        participants: [
                            {
                                tickets: 5,
                                entities: [
                                    { 
                                        id: "pot", 
                                        x: 4,  
                                        y: 0 
                                    },
                                    { 
                                        id: "pot", 
                                        x: 4,  
                                        y: 1 
                                    },
                                    { 
                                        id: "pot", 
                                        x: 4,  
                                        y: 3 
                                    },
                                    { 
                                        id: "pot", 
                                        x: 4,  
                                        y: 4
                                    }
                                ]
                            },
                            {
                                tickets: 1,
                                entities: [
                                    { 
                                        id: "pot", 
                                        x: 4,  
                                        y: 0 
                                    },
                                    { 
                                        id: "common_chest", 
                                        x: 2, 
                                        y: 2, 
                                        direction: DIRECTION.WEST 
                                    },
                                    { 
                                        id: "pot", 
                                        x: 4,  
                                        y: 4 
                                    }
                                ]
                            },
                            {
                                tickets: 2
                            }
                        ]
                    }
                ]
            },
            {
                position: { x: 0, y: -2 },
                entities: []
            }
        ]
    }
);

/**
 * A big ring room accessible from the north
 */
rooms.push(
    {
        name: "big_ring_room_north",
        min: 1,
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.NORTH,
                door: DOOR.THREE_KEYS,
                entities: []
            },
            {
                position: { x: -1, y: 0 },
                entities: []
            },
            {
                position: { x: 1, y: 0 },
                entities: []
            },
            {
                position: { x: -1, y: -1 },
                entities: []
            },
            {
                position: { x: 1, y: -1 },
                entities: []
            },
            {
                position: { x: -1, y: -2 },
                entities: []
            },
            {
                position: { x: 0, y: -2 },
                entities: []
            },
            {
                position: { x: 1, y: -2 },
                entities: []
            }
        ]
    }
);

/**
 * A big cross room accessible from the north
 */
rooms.push(
    {
        name: "cross_room_north",
        max: 1,
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.NORTH,
                door: DOOR.THREE_KEYS,
                entities: []
            },
            {
                position: { x: -1, y: -1 },
                blocked: [DIRECTION.NORTH, DIRECTION.SOUTH],
                entities: []
            },
            {
                position: { x: 0, y: -1 },
                entities: []
            },
            {
                position: { x: 1, y: -1 },
                blocked: [DIRECTION.NORTH, DIRECTION.SOUTH],
                entities: []
            },
            {
                position: { x: 0, y: -2 },
                entities: []
            }
        ]
    }
);

/**
 * A big cross room accessible from the south
 */
rooms.push(
    {
        name: "cross_room_south",
        max: 1,
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.SOUTH,
                blocked: [DIRECTION.EAST, DIRECTION.WEST],
                door: DOOR.THREE_KEYS,
                entities: []
            },
            {
                position: { x: -1, y: 1 },
                entities: []
            },
            {
                position: { x: 0, y: 1 },
                entities: []
            },
            {
                position: { x: 1, y: 1 },
                entities: []
            },
            {
                position: { x: 0, y: 2 },
                blocked: [DIRECTION.EAST, DIRECTION.WEST],
                entities: []
            }
        ]
    }
);

/**
 * Add a room group for the shop rooms so that only one is generated.
 */
roomGroups.push(
    {
        name: "shop",
        min: 1,
        max: 1,
        depth: {
            minimum: 2,
            maximum: 4
        },
        rooms: ["shop_east", "shop_west", "shop_north", "shop_south"]
    }
);

/**
 * A shop with its door to the east.
 */
rooms.push(
    {
        name: "shop_east",
        category: "shop",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.EAST,
                door: DOOR.SHOP,
                blocked: [DIRECTION.NORTH, DIRECTION.SOUTH, DIRECTION.WEST],
                entities: []
            }
        ]
    }
);

/**
 * A shop with its door to the west.
 */
rooms.push(
    {
        name: "shop_west",
        category: "shop",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.WEST,
                door: DOOR.SHOP,
                blocked: [DIRECTION.NORTH, DIRECTION.SOUTH, DIRECTION.EAST],
                entities: []
            }
        ]
    }
);

/**
 * A shop with its door to the north.
 */
rooms.push(
    {
        name: "shop_north",
        category: "shop",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.NORTH,
                door: DOOR.SHOP,
                blocked: [DIRECTION.SOUTH, DIRECTION.WEST, DIRECTION.EAST],
                entities: []
            }
        ]
    }
);

/**
 * A shop with its door to the south.
 */
rooms.push(
    {
        name: "shop_south",
        category: "shop",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.SOUTH,
                door: DOOR.SHOP,
                blocked: [DIRECTION.NORTH, DIRECTION.WEST, DIRECTION.EAST],
                entities: []
            }
        ]
    }
);


/**
 * A big cross boss room accessible from the north.
 */
rooms.push(
    {
        name: "boss_room_north",
        category: "boss",
        depth: {
            minimum: 2,
            maximum: 4
        },
        max: 1,
        min: 1,
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.NORTH,
                door: DOOR.BOSS,
                blocked: [DIRECTION.WEST, DIRECTION.EAST],
                entities: []
            },
            {
                position: { x: -1, y: -1 },
                blocked: [DIRECTION.WEST, DIRECTION.NORTH, DIRECTION.SOUTH],
                entities: []
            },
            {
                position: { x: 0, y: -1 },
                entities: [
                    { 
                        id: "boss", 
                        x: 2,  
                        y: 2, 
                        direction: DIRECTION.NORTH 
                    }
                ]
            },
            {
                position: { x: 1, y: -1 },
                blocked: [DIRECTION.EAST, DIRECTION.NORTH, DIRECTION.SOUTH],
                entities: []
            },
            {
                position: { x: 0, y: -2 },
                blocked: [DIRECTION.EAST, DIRECTION.WEST, DIRECTION.SOUTH],
                entities: []
            }
        ]
    }
);

/**
 * Get a room by its name.
 * @param name The room name.
 */
function getRoom(name)
{
    for (const room of rooms) {
        if (room.name === name) {
            // We found the room.
            return room;
        }
    }

    // We did not find the room.
    return null;
}

/**
 * Get the entrance direction for the specifid room. 
 * @param room The room. 
 */
function getRoomEntranceDirection(room) {
    return (room.cells.find(cell => typeof cell.entrance === "number") || {}).entrance;
}