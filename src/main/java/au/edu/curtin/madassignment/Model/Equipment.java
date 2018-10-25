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

import android.content.Context;

import java.util.Random;

public class Equipment extends Item {
    /* Usable Interface */
    public interface Usable {
        String TYPE = "Usable";
        void use(Context context);
    }

    /* Constants */
    private static final String[] EQUIPMENT_NAMES = {
        "Borg Nanites", "Covenant Energy Sword", "Covenant Needler", "Deckard's Hand-Cannon", "Federation Phasor",
        "Goa'uld Ma'Tok", "Han Solo's DL-44", "Imperial Stormtrooper Blaster", "Klingon Batleth",
        "Judge Dredd's Lawgiver Mk II", "Lightsabre", "Malcom Reynolds' Sidearm", "Mobile Infantry Mini-Nuke",
        "Romulan Disruptor", "Stargate Command FN P90", "Tron's Identity Disk", "UNSC Pistol", "UNSC Shotgun"
    };
    static final String[] SPECIAL_NAMES = {"Jade Monkey", "Roadmap", "Ice Scraper"};

    private static final String TYPE = "Equipment";
    private static final String SPECIAL_TYPE = "Special";

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
        super.setType(TYPE);
        super.setDescription(EQUIPMENT_NAMES[index]);
        super.setValue(random.nextInt(VALUE_RANGE) + MIN_VALUE);
        setMass(random.nextDouble()*MASS_RANGE + MIN_MASS);
        setUsable(false);
        setSpecial(false);
    }

    // Special Item Constructor
    public Equipment(String inDescription) {
        super();

        super.setType(SPECIAL_TYPE);
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

    boolean isSpecial() {
        return isSpecial;
    }

    /* Mutators */
    public void setMass(double inMass) {
        mass = inMass;
    }

    protected void setUsable(boolean usable) {
        isUsable = usable;
    }

    protected void setSpecial(boolean special) {
        isSpecial = special;
    }

    /* Functions */
    public void use(Context context) {
        throw new IllegalStateException(this.getDescription() + " cannot be used.");
    }
}
