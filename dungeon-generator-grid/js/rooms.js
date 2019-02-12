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
                contents: []
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
                door: DOOR.THREE_KEYS,
                contents: []
            },
            {
                position: { x: -1, y: 0 },
                contents: []
            },
            {
                position: { x: -2, y: 0 },
                blocked: [],
                contents: []
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
                door: DOOR.THREE_KEYS,
                contents: []
            },
            {
                position: { x: 1, y: 0 },
                contents: []
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
                door: DOOR.THREE_KEYS,
                contents: []
            },
            {
                position: { x: 1, y: 0 },
                contents: []
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
                door: DOOR.THREE_KEYS,
                contents: []
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
                door: DOOR.THREE_KEYS,
                contents: []
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
                contents: []
            },
            {
                position: { x: 0, y: 1 },
                contents: []
            },
            {
                position: { x: 0, y: 2 },
                blocked: [],
                contents: []
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
                contents: []
            },
            {
                position: { x: 0, y: 1 },
                contents: []
            },
            {
                position: { x: 1, y: 1 },
                contents: []
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
                contents: []
            },
            {
                position: { x: 0, y: -1 },
                contents: []
            },
            {
                position: { x: -1, y: -1 },
                contents: []
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
                contents: []
            },
            {
                position: { x: 0, y: -1 },
                contents: []
            },
            {
                position: { x: 1, y: -1 },
                contents: []
            },
            {
                position: { x: 0, y: -2 },
                contents: []
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
                contents: []
            },
            {
                position: { x: -1, y: 0 },
                contents: []
            },
            {
                position: { x: 1, y: 0 },
                contents: []
            },
            {
                position: { x: -1, y: -1 },
                contents: []
            },
            {
                position: { x: 1, y: -1 },
                contents: []
            },
            {
                position: { x: -1, y: -2 },
                contents: []
            },
            {
                position: { x: 0, y: -2 },
                contents: []
            },
            {
                position: { x: 1, y: -2 },
                contents: []
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
        rooms: ["shop_east", "shop_west"]
    }
);

/**
 * A shop with its door to the east.
 */
rooms.push(
    {
        name: "shop_east",
        range: { min: 2 , max: 10 },
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.EAST,
                door: DOOR.SHOP,
                contents: []
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
        range: { min: 2 , max: 10 },
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: DIRECTION.WEST,
                door: DOOR.SHOP,
                contents: []
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