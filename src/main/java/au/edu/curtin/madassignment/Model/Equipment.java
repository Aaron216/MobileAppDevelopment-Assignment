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
        mass = 0.0;
    }

    /* Accessors */
    public double getMass() {
        return mass;
    }

    /* Mutators */
    public void setMass(double inMass) {
        mass = inMass;
    }
}
