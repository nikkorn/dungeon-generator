package com.dumbpug.dungeony.dungen.room;

import java.util.ArrayList;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import com.dumbpug.dungeony.dungen.Direction;
import com.dumbpug.dungeony.dungen.Position;
import com.dumbpug.dungeony.dungen.tile.Entity;
import com.dumbpug.dungeony.lotto.Lotto;

/**
 * Processes and creates cell entities.
 */
public class GeneratableEntitiesProcessor {
	
	/**
	 * Generate actual entities based on generatable entity information. 
	 * @param generatable The information regarding the generatable entities.
	 * @param random The RNG to use in resolving entity participants.
	 * @return Entities based on generatable entity information. 
	 */
	public static ArrayList<PositionedEntity> process(JSONArray generatable, Random random) {
		// Create a list to store the generated entities.
		ArrayList<PositionedEntity> entities = new ArrayList<PositionedEntity>();
		
		for (int generatableIndex = 0; generatableIndex < generatable.length(); generatableIndex++) {
			processEntitiesForNode(generatable.getJSONObject(generatableIndex), entities, random);
		}
		
		// Return all of the generated entities.
		return entities;
	}
	
	/**
	 * Recursively process the entities for a node.
	 * @param node The node to process.
	 * @param entities The generated entities list.
	 * @param random The RNG to use in picking winning participants.
	 */
	private static void processEntitiesForNode(JSONObject node, ArrayList<PositionedEntity> entities, Random random) {
		if (node.has("id")) {
			// If we have an id then the node represents an entity to generate.
			String id = node.getString("id");
			
			// Get the facing direction of the entity. This is optional.
			Direction facingDirection = node.has("direction") ? getDirection(node.getString("direction")) : Direction.UNKNOWN;
			
			// Get the absolute entity position.
			Position position = new Position(node.getInt("x"), node.getInt("y"));
			
			// Add the positioned entity.
			entities.add(new PositionedEntity(new Entity(id, facingDirection), position));
		} else if (node.has("entities")) {
			// If we have an 'entities' array then we should process each one.
			JSONArray entitiesJSONArray = node.getJSONArray("entities");
			
			// Process each one individually.
			for (int entityIndex = 0; entityIndex < entitiesJSONArray.length(); entityIndex++) {
				processEntitiesForNode(entitiesJSONArray.getJSONObject(entityIndex), entities, random);
			}
		} else if (node.has("participants")) {
			// If we have a 'participants' array then we will be randomly selecting a winning entity.
			JSONArray participantsJSONArray = node.getJSONArray("participants");
			
			// There is nothing to do if there are no participants.
			if (participantsJSONArray.length() == 0) {
				return;
			}
			
			// Create a lotto with which to find a winning participating node.
			Lotto<JSONObject> lotto = new Lotto<JSONObject>(random);
			
			// Process each participant individually.
			for (int participantIndex = 0; participantIndex < participantsJSONArray.length(); participantIndex++) {
				// Get the participant.
				JSONObject participant = participantsJSONArray.getJSONObject(participantIndex);
				
				// Get the participants ticket count.
				int tickets = participant.has("tickets") ? participant.getInt("tickets") : 0;
				
				// Add the participant to the lotto.
				lotto.add(participant, tickets);
			}
			
			// Process the winning node.
			processEntitiesForNode(lotto.draw(), entities, random);
		}
	}
	
	/**
	 * Get the Direction enum value based on its string value.
	 * @param direction The string value.
	 * @return The Direction enum value based on its string value.
	 */
	private static Direction getDirection(String direction) {
		return Direction.valueOf(direction.toUpperCase());
	}
}
