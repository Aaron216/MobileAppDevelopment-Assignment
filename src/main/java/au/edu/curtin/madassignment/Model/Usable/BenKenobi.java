package au.edu.curtin.madassignment.Model.Usable;

import au.edu.curtin.madassignment.Model.Equipment;

public class BenKenobi extends Equipment {
    /* Constants */
    private static final String NAME = "Ben Kenobi";
    private static final double MASS = 0.0;

    /* Constructor */
    public BenKenobi() {
        super();
        super.setDescription(NAME);
        super.setMass(MASS);
        super.setValue(1000);
    }

    /* Function */
    @Override
    public void use() {

    }
}
