/*
  Curtin University
  Mobile Application Development
  Assignment
  Aaron Musgrave
  25/10/2018

  Equipment Class
  Responsible for storing data about an equipment item
 */

package au.edu.curtin.madassignment.Model;

import java.util.Random;

public class Equipment extends Item {
    /* Usable Interface */
    public interface Usable {
        void use();
    }

    /* Constants */
    private static final String[] EQUIPMENT_NAMES = {
        "Borg Nanites", "Covenant Energy Sword", "Covenant Needler", "Deckard's Hand-Cannon", "Federation Phasor",
        "Goa'uld Ma'Tok", "Han Solo's DL-44", "Imperial Stormtrooper Blaster", "Klingon Batleth",
        "Judge Dredd's Lawgiver Mk II", "Lightsabre", "Malcom Reynolds' Sidearm", "Mobile Infantry Mini-Nuke",
        "Romulan Disruptor", "Stargate Command FN P90", "Tron's Identity Disk", "UNSC Pistol", "UNSC Shotgun"
    };
    private static final int VALUE_RANGE = 20;
    private static final int MIN_VALUE = 1;
    private static final int MIN_SPECIAL_VALUE = 10;
    private static final double MASS_RANGE = 10.0;
    private static final double MIN_MASS = 0.1;


    /* Fields */
    private boolean isUsable;
    private boolean isSpecial;
    private double mass;

    /* Constructor */
    public Equipment() {
        super();

        // Choose random item
        Random random = new Random();
        int index = random.nextInt(EQUIPMENT_NAMES.length);
        super.setDescription(EQUIPMENT_NAMES[index]);
        super.setValue(random.nextInt(VALUE_RANGE) + MIN_VALUE);
        setMass(random.nextDouble()*MASS_RANGE + MIN_MASS);
        setUsable(false);
        setSpecial(false);
    }

    // Special Item Constructor
    public Equipment(String inDescription) {
        super();

        super.setDescription(inDescription);

        // Set Value and Mass
        Random random = new Random();
        super.setValue(random.nextInt(VALUE_RANGE) + MIN_SPECIAL_VALUE);
        setMass(random.nextDouble()*MASS_RANGE + MIN_MASS);
        setUsable(false);
        setSpecial(true);
    }

    /* Accessors */
    public double getMass() {
        return mass;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    /* Mutators */
    public void setMass(double inMass) {
        mass = inMass;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }
}
