package au.edu.curtin.madassignment.Model.Usable;

import android.content.Context;

import java.util.UUID;

import au.edu.curtin.madassignment.Activities.ImprobabilityDriveActivity;
import au.edu.curtin.madassignment.Model.*;

public class ImprobabilityDrive extends Equipment implements Equipment.Usable {
    /* Constants */
    public static final String NAME = "Improbability Drive";
    private static final double MASS = -Math.PI;

    /* Constructor */
    public ImprobabilityDrive() {
        super();
        super.setType(Usable.TYPE);
        super.setDescription(NAME);
        super.setMass(MASS);
        super.setValue(100);
        super.setUsable(true);
        super.setSpecial(false);
    }

    public ImprobabilityDrive(UUID inID) {
        this();
        super.setItemID(inID);
    }

    /* Function */
    @Override
    public void use(Context context) {
        context.startActivity(ImprobabilityDriveActivity.getIntent(context, super.getIDString()));
    }

    public void confirm() {
        GameData gameInstance = GameData.getInstance();
        gameInstance.getPlayer().removeItem(this);
        gameInstance.generateMap();
    }
}
