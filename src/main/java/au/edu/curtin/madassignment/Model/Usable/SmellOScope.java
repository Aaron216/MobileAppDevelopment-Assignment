package au.edu.curtin.madassignment.Model.Usable;

import android.app.Activity;

import au.edu.curtin.madassignment.Activities.SmellActivity;
import au.edu.curtin.madassignment.Model.*;

public class SmellOScope extends Equipment implements Equipment.Usable {
    /* Constants */
    private static final String NAME = "Portable Smell-O-Scope";
    private static final double MASS = 5.0;

    /* Constructor */
    public SmellOScope() {
        super();
        super.setDescription(NAME);
        super.setMass(MASS);
        super.setValue(100);
        super.setUsable(true);
        super.setSpecial(false);
    }

    /* Function */
    public void use() {
        ActivityStarter starter = new ActivityStarter();
        starter.startSmellActivity();
    }

    /* Private Class */
    private class ActivityStarter extends Activity {
        void startSmellActivity() {
            startActivity(SmellActivity.getInent(ActivityStarter.this));
        }
    }
}
