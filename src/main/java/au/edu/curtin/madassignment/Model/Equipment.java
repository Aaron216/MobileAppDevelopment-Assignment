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
    /* Constants */
    private static final String[] EQUIPMENT_NAMES = {
        "Borg Nanites", "Covenant Energy Sword", "Covenant Needler", "Deckard's Hand-Cannon", "Federation Phasor",
        "Goa'uld Ma'Tok", "Han Solo's DL-44", "Imperial Stormtrooper Blaster", "Klingon Batleth",
        "Judge Dredd's Lawgiver Mk II", "Lightsabre\n", "Malcom Reynolds' Sidearm", "Mobile Infantry Mini-Nuke",
        "Romulan Disruptor", "Stargate Command FN P90", "Tron's Identity Disk", "UNSC Pistol", "UNSC Shotgun"
    };

    /* Fields */
    private double mass;

    /* Constructor */
    public Equipment() {
        super();

        // Choose random item
        Random random = new Random();
        int index = random.nextInt(EQUIPMENT_NAMES.length);
        super.setDescription(EQUIPMENT_NAMES[index]);
        super.setValue(random.nextInt(20));
        setMass(random.nextDouble()*10.0);
    }

    /* Accessors */
    public double getMass() {
        return mass;
    }

    /* Mutators */
    public void setMass(double inMass) {
        mass = inMass;
    }

    /* Function */
    @Override
    public void use() {
        // This item cannot be used
    }
}
