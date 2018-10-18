/*
  Curtin University
  Mobile Application Development
  Assignment
  Aaron Musgrave
  25/10/2018

  Food Class
  Responsible for storing data about a food item
 */

package au.edu.curtin.madassignment.Model;

import java.util.Random;

public class Food extends Item {
    /* Constants */
    private static final String FOOD_NAMES[] = {
        "Angazi Burger", "Angazi Fries", "Basement HSP", "Basement Kebab", "Bookmark Breakfast",
        "Bookmark Dumplings", "Coffee", "Doughnuts", "Main Cafe Nachos", "Main Cafe Sushi",
        "Tav Burger", "Tav Chicken Parma", "Tav Chips", "Tav Schnitzel", "Vege Patch Carrot"
    };

    /* Fields */
    private double health;

    /* Constructor */
    Food() {
        super();

        // Choose Random Food
        Random random = new Random();
        int index = random.nextInt(FOOD_NAMES.length);
        super.setDescription(FOOD_NAMES[index]);
        super.setValue(random.nextInt(10));
        setHealth(random.nextDouble()*20.0);
    }

    /* Accessors */
    public double getHealth() {
        return health;
    }

    /* Mutators */
    void setHealth(double inHealth) {
        health = inHealth;
    }
}
