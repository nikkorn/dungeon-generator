package com.dumbpug.dungeony.dungen;

/**
 * Utility methods.
 */
public class Utility {

	/**
	 * Get the first argument that is not null.
	 * @param params The parameters.
	 * @return The first argument that is not null.
	 */
	public static <T> T coalesce(T... params) {
	    for (T param : params)
	        if (param != null)
	            return param;
	    return null;
	}
}
