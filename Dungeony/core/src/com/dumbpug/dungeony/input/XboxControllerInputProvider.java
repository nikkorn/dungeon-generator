package com.dumbpug.dungeony.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.math.Vector3;
import com.dumbpug.dungeony.Constants;

import java.util.ArrayList;

/**
 * A provider for xbox controller input.
 */
public class XboxControllerInputProvider implements IPlayerInputProvider, ControllerListener {
    /**
     * The list of just pressed controls.
     */
    private ArrayList<Control> justPressedControls = new ArrayList<Control>();

    // TODO This class should take a Controller instance, rather that just lookup the first connected one.

    @Override
    public float getMovementAxisX() {
        float value = Controllers.getControllers().get(0).getAxis(Xbox.L_STICK_HORIZONTAL_AXIS);
        return (value < Constants.INPUT_CONTROLLER_AXIS_DEADZONE && value > -Constants.INPUT_CONTROLLER_AXIS_DEADZONE) ? 0 : value;
    }

    @Override
    public float getMovementAxisY() {
        float value =  -Controllers.getControllers().get(0).getAxis(Xbox.L_STICK_VERTICAL_AXIS);
        return (value < Constants.INPUT_CONTROLLER_AXIS_DEADZONE && value > -Constants.INPUT_CONTROLLER_AXIS_DEADZONE) ? 0 : value;
    }

    @Override
    public float getAimAxisX() {
        float value =  Controllers.getControllers().get(0).getAxis(Xbox.R_STICK_HORIZONTAL_AXIS);
        return (value < Constants.INPUT_CONTROLLER_AXIS_DEADZONE && value > -Constants.INPUT_CONTROLLER_AXIS_DEADZONE) ? 0 : value;
    }

    @Override
    public float getAimAxisY() {
        float value =  -Controllers.getControllers().get(0).getAxis(Xbox.R_STICK_VERTICAL_AXIS);
        return (value < Constants.INPUT_CONTROLLER_AXIS_DEADZONE && value > -Constants.INPUT_CONTROLLER_AXIS_DEADZONE) ? 0 : value;
    }

    @Override
    public boolean isControlPressed(Control control) {
        // TODO
        // We can only continue if there is a controller attached.
        if (Controllers.getControllers().size > 0) {
            // Get the first available controller.
            Controller controller = Controllers.getControllers().first();
            // Get the x axis of the left stick (movement).
            float leftXAxis = controller.getAxis(Ouya.AXIS_LEFT_X);
            // Determine which control we are checking the state of.
            switch (control) {
                case PRIMARY_ACTION:
                    return controller.getButton(Ouya.BUTTON_O);
                case SECONDARY_ACTION:
                    return controller.getButton(Ouya.BUTTON_R1);
                case LEFT:
                    // Whether we are moving left depends on the position of the stick.
                    return leftXAxis < -0.5;
                case RIGHT:
                    // Whether we are moving right depends on the position of the stick.
                    return leftXAxis > 0.5;
                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    public boolean isControlJustPressed(Control control) {
        // TODO
        // We only care about handing inputs where we care about acting upon single presses.
        switch (control) {
            case PRIMARY_ACTION:
            case SECONDARY_ACTION:
                return justPressedControls.contains(control);
            default:
                return this.isControlPressed(control);
        }
    }

    @Override
    public void reset() {
        // Forget about any buttons that were just pressed.
        justPressedControls.clear();
    }

    @Override
    public void connected(Controller controller) {}

    @Override
    public void disconnected(Controller controller) {}

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        // TODO
        // We only care about handing inputs where we care about acting upon single presses.
        switch (buttonCode) {
            case Ouya.BUTTON_O:
                this.justPressedControls.add(Control.PRIMARY_ACTION);
                return true;
            case Ouya.BUTTON_R1:
                this.justPressedControls.add(Control.SECONDARY_ACTION);
                return true;
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) { return false; }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) { return false; }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) { return false; }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) { return false; }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) { return false; }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) { return false; }
}