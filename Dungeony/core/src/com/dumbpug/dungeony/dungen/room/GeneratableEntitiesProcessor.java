package com.dumbpug.dungeony.dungen.room;

import java.util.ArrayList;
import java.util.Random;
import org.json.JSONArray;

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
	public static ArrayList<Entity> process(JSONArray generatable, Random random) {
		return new ArrayList<Entity>();
	}
}
