package au.edu.curtin.madassignment.Model.Usable;

import au.edu.curtin.madassignment.Model.Equipment;

public class SmellOScope extends Equipment {
    /* Constants */
    private static final String NAME = "Portable Smell-O-Scope";
    private static final double MASS = 5.0;

    /* Constructor */
    public SmellOScope() {
        super();
        super.setDescription(NAME);
        super.setMass(MASS);
        setValue(100);
    }

    /* Function */
    @Override
    public void use() {

    }
}
