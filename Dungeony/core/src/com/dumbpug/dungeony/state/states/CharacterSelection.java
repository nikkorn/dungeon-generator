package com.dumbpug.dungeony.state.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.ApplicationModel;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.PlayerIdentifier;
import com.dumbpug.dungeony.state.State;

/**
 * The 'character selection' state of the application.
 */
public class CharacterSelection extends State {
    /**
     * The application model used to share data across application state.
     */
    private ApplicationModel applicationModel;

    /**
     * Creates a new instance of the CharacterSelection class.
     * @param applicationModel The application model used to share data across application state
     */
    public CharacterSelection(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    @Override
    public void onEntry(State state) {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void update() {
        // For now, just add a single player.
        this.applicationModel.getPlayerDetails().add(new PlayerDetails(PlayerIdentifier.PLAYER_1));

        // Go straight to the game state state for now.
        this.changeState("GAME");
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public String getStateName() {
        return "CHARACTER_SELECTION";
    }
}
