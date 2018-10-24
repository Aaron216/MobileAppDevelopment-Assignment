package au.edu.curtin.madassignment.Model.Usable;

import android.content.Context;

import au.edu.curtin.madassignment.Model.*;

public class ImprobabilityDrive extends Equipment implements Equipment.Usable {
    /* Constants */
    private static final String NAME = "Improbability Drive";
    private static final double MASS = -Math.PI;

    /* Constructor */
    public ImprobabilityDrive() {
        super();
        super.setDescription(NAME);
        super.setMass(MASS);
        super.setValue(100);
        super.setUsable(true);
        super.setSpecial(false);
    }

    /* Function */
    @Override
    public void use(Context context) {
        // context.startActivity(ImprobabilityDriveActivity.getIntent(context));

        GameData gameInstance = GameData.getInstance();

        gameInstance.getPlayer().removeItem(this);
        gameInstance.generateMap();
    }
}
