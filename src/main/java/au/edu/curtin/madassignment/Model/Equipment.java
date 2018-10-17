/**
 * Curtin University
 * Mobile Application Development
 * Assignment
 * Aaron Musgrave
 * 25/10/2018
 *
 * Equipment Class
 * Responsible for storing data about an equipment item
 */

package au.edu.curtin.madassignment.Model;

public class Equipment extends Item {
    /* Fields */
    private double mass;

    /* Constructor */
    Equipment() {
        super();
        mass = 0.0;
    }

    /* Accessors */
    public double getMass() {
        return mass;
    }

    /* Mutators */
    void setMass(double inMass) {
        mass = inMass;
    }
}
