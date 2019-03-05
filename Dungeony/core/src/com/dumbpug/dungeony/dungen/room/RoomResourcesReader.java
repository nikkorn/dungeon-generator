package com.dumbpug.dungeony.dungen.room;

import java.io.File;
import java.util.ArrayList;

/**
 * Reads rooms resources from disk.
 */
public class RoomResourcesReader {
	/**
	 * The room file extension.
	 */
	private static final String ROOM_FILE_EXTENSION = ".room.json";
	/**
	 * The room group file extension.
	 */
	private static final String ROOM_GROUP_FILE_EXTENSION = ".group.json";
	
	/**
	 * Get all room resources within the given directory.
	 * @param resourceDirectoryPath The path of the directory holding the rooms resources.
	 * @resources All room resources within the given directory.
	 */
	public static RoomResources getResources(String resourceDirectoryPath) {
		// Create the file which represents the room resource directory.
		File roomResourceDirectory = new File(resourceDirectoryPath);

		// The file must exist and be a directory for us to continue.
		if (!roomResourceDirectory.exists() || !roomResourceDirectory.isDirectory()) {
			throw new RuntimeException("room resource directory '" + roomResourceDirectory.getAbsolutePath() + "' does not exist or is a file");
		}
		
		// Get all of the room resource files.
		ArrayList<File> roomResourceFiles = RoomResourcesReader.findFilesWithExtension(roomResourceDirectory, ROOM_FILE_EXTENSION);
		
		// Get all of the room group resource files.
		ArrayList<File> roomGroupResourceFiles = RoomResourcesReader.findFilesWithExtension(roomResourceDirectory, ROOM_GROUP_FILE_EXTENSION);
			
		// Return the room resources.
		return new RoomResources(null, null);
	}
	
	/**
	 * Find all files recursively nested within the directory with the given extension.
	 * @param roomResourceDirectory The directory containing room resources.
	 * @param extension The room resource file extension.
	 * @return All files nested within the directory with the given extension.
	 */
	private static ArrayList<File> findFilesWithExtension(File roomResourceDirectory, String extension) {
		// Create a list to hold all the matching files.
		ArrayList<File> found = new ArrayList<File>();
		
		findFilesInDirectoryWithExtension(roomResourceDirectory, extension, found);
		
		// Return the list holding all the matching files.
		return found;
	}
	
	/**
	 * Find all files recursively nested within the directory with the given extension.
	 * @param directory The current directory.
	 * @param extension The room resource file extension.
	 * @param found The list of found files.
	 */
	private static void findFilesInDirectoryWithExtension(File directory, String extension, ArrayList<File> found) {
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				findFilesInDirectoryWithExtension(file, extension, found);
			} else {
				if (file.getName().endsWith(extension)) {
					found.add(file);
				}
			}
		}
	}
}
