package com.dumbpug.dungeony.state.states;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.ApplicationModel;
import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.character.player.PlayerIdentifier;
import com.dumbpug.dungeony.game.character.player.PlayerType;
import com.dumbpug.dungeony.input.KeyboardInputProvider;
import com.dumbpug.dungeony.input.XboxControllerInputProvider;
import com.dumbpug.dungeony.state.State;

/**
 * The 'character selection' state of the application.
 */
public class CharacterSelection extends State {
    /**
     * The application model used to share data across application state.
     */
    private ApplicationModel applicationModel;

    // TODO Add an ExtendedViewport just for this.

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
        if (Controllers.getControllers().isEmpty()) {
            return;
        }

        XboxControllerInputProvider inputProvider = new XboxControllerInputProvider();

        Controller controller = Controllers.getControllers().get(0);
        controller.addListener(inputProvider);

        // For now, just add a single player and use keyboard input.
        this.applicationModel.getPlayerDetails().add(new PlayerDetails(PlayerIdentifier.PLAYER_1, PlayerType.CLOAK, inputProvider));

        // Go straight to the game state state for now.
        this.changeState("GAME");
    }

    @Override
    public void render() { }

    @Override
    public void onResize(int width, int height) { }

    @Override
    public String getStateName() {
        return "CHARACTER_SELECTION";
    }
}
