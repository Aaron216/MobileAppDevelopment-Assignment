package au.edu.curtin.madassignment.Model.Usable;

import android.content.Context;

import java.util.UUID;

import au.edu.curtin.madassignment.Activities.*;
import au.edu.curtin.madassignment.Model.*;

public class SmellOScope extends Equipment implements Equipment.Usable {
    /* Constants */
    public static final String NAME = "Portable Smell-O-Scope";
    private static final double MASS = 5.0;

    /* Constructor */
    public SmellOScope() {
        super();
        super.setType(Usable.TYPE);
        super.setDescription(NAME);
        super.setMass(MASS);
        super.setValue(100);
        super.setUsable(true);
        super.setSpecial(false);
    }

    public SmellOScope(UUID inID) {
        this();
        super.setItemID(inID);
    }

    /* Function */
    @Override
    public void use(Context context) {
        // Start smell activity
        context.startActivity(SmellActivity.getIntent(context));

        // Remove smell-o-scope from player
        GameData.getInstance().getPlayer().removeItem(this);
    }
}
