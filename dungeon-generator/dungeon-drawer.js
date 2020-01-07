function draw(result, colours, tileSize) {
	// Clear the dungeon SVG
	document.getElementById('dungeon').style.width  = result.size * tileSize;
	document.getElementById('dungeon').style.height = result.size * tileSize;
	document.getElementById('dungeon').innerHTML    = `<rect width=${result.size * tileSize} height=${result.size * tileSize} style=\"fill:rgb(0,24,94)\" />`;

	result.tiles.forEach((tile) => drawTile(tileSize, colours[tile.type], tile.type, tile.x, tile.y, tile.details && JSON.stringify(tile.details)));
}

/**
 * Draw a space in the dungeon.
 */
function drawTile(tileSize, colour, type, x, y, details) {
	var rect = document.createElementNS("http://www.w3.org/2000/svg", 'rect');
	rect.setAttributeNS(null, 'x', x * tileSize);
	rect.setAttributeNS(null, 'y', y * tileSize);
	rect.setAttributeNS(null, 'height', tileSize);
	rect.setAttributeNS(null, 'width', tileSize);
	rect.setAttributeNS(null, 'fill', colour);
	rect.innerHTML = `<title>X: ${x}\nY: ${y}\nTYPE: ${type} ${details ? "\nDETAILS: " + details : ""}</title>`;
	document.getElementById('dungeon').appendChild(rect);
};