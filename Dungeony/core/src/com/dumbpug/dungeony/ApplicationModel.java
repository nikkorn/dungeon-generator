package com.dumbpug.dungeony;

import com.dumbpug.dungeony.characterselection.PlayerDetails;
import java.util.ArrayList;

/**
 * The application model used to share data across application state.
 */
public class ApplicationModel {
    /**
     * The details of players created at the time of character selection.
     */
    private ArrayList<PlayerDetails> playerDetails = new ArrayList<PlayerDetails>();

    /**
     * Gets the details of players created at the time of character selection.
     * @return The details of players created at the time of character selection.
     */
    public ArrayList<PlayerDetails> getPlayerDetails() {
        return playerDetails;
    }

    /**
     * Sets the details of players created at the time of character selection.
     * @param playerDetails The details of players created at the time of character selection
     */
    public void setPlayerDetails(ArrayList<PlayerDetails> playerDetails) {
        this.playerDetails = playerDetails;
    }
}
