/**
 * Curtin University
 * Mobile Application Development
 * Assignment
 * Aaron Musgrave
 * 25/10/2018
 *
 * Food Class
 * Responsible for storing data about a food item
 */

package au.edu.curtin.madassignment.Model;

public class Food extends Item {
    /* Fields */
    private double health;

    /* Constructor */
    Food() {
        super();
        health = 0.0;
    }

    /* Accessors */
    double getHealth() {
        return health;
    }

    /* Mutators */
    void setHealth(double inHealth) {
        health = inHealth;
    }
}
