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
        id: "spawn",
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
        id: "empty_3*1_room",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: Direction.EAST,
                door: Door.THREE_KEYS,
                contents: []
            },
            {
                position: { x: -1, y: 0 },
                contents: []
            },
            {
                position: { x: -2, y: 0 },
                blocked: [
                    Direction.EAST
                ],
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
        id: "shop_east",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: Direction.EAST,
                door: Door.SHOP,
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
        id: "shop_west",
        cells: [
            {
                position: { x: 0, y: 0 },
                entrance: Direction.WEST,
                door: Door.SHOP,
                contents: []
            }
        ]
    }
);

/**
 * Get a room by its id.
 * @param {*} id The room id.
 */
function getRoom(id)
{
    for (const room of rooms) {
        if (room.id === id) {
            // We found the room.
            return room;
        }
    }

    // We did not find the room.
    return null;
} 