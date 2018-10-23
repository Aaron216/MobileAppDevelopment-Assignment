package au.edu.curtin.madassignment.Model.Usable;

import android.content.Context;

import au.edu.curtin.madassignment.Activities.BenKenobiActivity;
import au.edu.curtin.madassignment.Model.*;

public class BenKenobi extends Equipment implements Equipment.Usable {
    /* Constants */
    private static final String NAME = "Ben Kenobi";
    private static final double MASS = 0.0;
    private static final int REQUEST_CONFIRM = 0;

    /* Constructor */
    public BenKenobi() {
        super();
        super.setDescription(NAME);
        super.setMass(MASS);
        super.setValue(1000);
        super.setUsable(true);
        super.setSpecial(false);
    }

    /* Function */
    @Override
    public void use(Context context) {
        context.startActivity(BenKenobiActivity.getIntent(context));
    }
}
