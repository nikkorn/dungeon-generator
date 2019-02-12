/**
 * Get a unique key for an x/y position.
 * @param x The x position. 
 * @param y The y position.
 */
function getPositionKey(x, y) {
    return `${x}_${y}`;
}

/**
 * Get the unique key for a cell.
 * @param cell The cell. 
 */
function getCellKey(cell) {
    return getPositionKey(cell.getX(), cell.getY());
}

/**
 * Get a random item form an array. 
 * @param items Th array of items.
 */
function getRandomItem(items) {
    // We cant pick a random item from an empty array.
    if (!items.length) {
        return undefined;
    }

    // Return a random item.
    return items[Math.floor(Math.random() * items.length)]; 
}

/**
 * Randomly shuffle the input array.
 * @param array The array to shuffle.
 */
function shuffle(array) {
    var currentIndex = array.length, temporaryValue, randomIndex;
  
    // While there remain elements to shuffle...
    while (0 !== currentIndex) {
  
      // Pick a remaining element...
      randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex -= 1;
  
      // And swap it with the current element.
      temporaryValue = array[currentIndex];
      array[currentIndex] = array[randomIndex];
      array[randomIndex] = temporaryValue;
    }
  
    return array;
  }