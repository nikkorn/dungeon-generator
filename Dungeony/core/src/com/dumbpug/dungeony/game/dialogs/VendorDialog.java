package com.dumbpug.dungeony.game.dialogs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.dialog.Dialog;
import com.dumbpug.dungeony.engine.dialog.DialogPosition;
import com.dumbpug.dungeony.game.object.objects.Vendor;

/**
 * A vendor dialog, displaying items for sale.
 */
public class VendorDialog extends Dialog<SpriteBatch> {
    /**
     * Creates a new instance of the VendorDialog class.
     * @param vendor The vendor entity.
     */
    public VendorDialog(Vendor vendor) {
        super(vendor, DialogPosition.ABOVE);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // TODO
    }
}
