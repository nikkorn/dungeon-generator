function draw(tiles, colours, tileSize) {
	// Clear the dungeon SVG
	document.getElementById('dungeon').innerHTML = "<rect width=\"600\" height=\"600\" style=\"fill:rgb(0,0,0)\" />";

	tiles.forEach((tile) => drawTile(tileSize, colours[tile.type], tile.x, tile.y));
}

/**
 * Draw a space in the dungeon.
 */
function drawTile(tileSize, colour, x, y) {
	var rect = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	rect.setAttributeNS(null, 'x', x * tileSize);
	rect.setAttributeNS(null, 'y', y * tileSize);
	rect.setAttributeNS(null, 'height', tileSize);
	rect.setAttributeNS(null, 'width', tileSize);
	rect.setAttributeNS(null, 'fill', colour);
	document.getElementById('dungeon').appendChild(rect);
};