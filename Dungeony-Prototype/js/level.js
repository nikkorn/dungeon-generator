/**
 * The level data.
 */
const levels = {
    "tester": {
        player: { x: 2, y: 4 },
        walls: [
            { x: 7, y: 5 },
            { x: 7, y: 6 },
            { x: 7, y: 7 },
            { x: 7, y: 8 },
            { x: 7, y: 9 },
            { x: 7, y: 9 }
        ],
        enemies: [
            { 
                x: 50, 
                y: 10,
                type: ENEMY_TYPE.WALKER,
                movements: [
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: 1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 },
                    { x: -1, y: 0 }
                ]
            },
            { 
                x: 100, 
                y: 10,
                type: ENEMY_TYPE.FOLLOWER
            }
        ]
    }
};