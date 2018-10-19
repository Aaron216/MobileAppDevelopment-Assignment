package au.edu.curtin.madassignment.Model.Usable;

import au.edu.curtin.madassignment.Model.Equipment;

public class ImprobabilityDrive extends Equipment {
    /* Constants */
    private static final String NAME = "Improbability Drive";
    private static final double MASS = -Math.PI;

    /* Constructor */
    public ImprobabilityDrive() {
        super();
        super.setDescription(NAME);
        super.setMass(MASS);
        super.setValue(100);
    }

    /* Function */
    public void use() {

    }
}
