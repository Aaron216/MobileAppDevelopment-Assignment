package au.edu.curtin.madassignment.Model.Usable;

import java.util.List;

import au.edu.curtin.madassignment.Model.*;

public class BenKenobi extends Equipment implements Equipment.Usable {
    /* Constants */
    private static final String NAME = "Ben Kenobi";
    private static final double MASS = 0.0;

    /* Constructor */
    public BenKenobi() {
        super();
        super.setDescription(NAME);
        super.setMass(MASS);
        super.setValue(1000);
        super.setUsable(true);
        super.setSpecial(false);
    }

    /* Function */
    @Override
    public void use() {
        GameData gameInstance = GameData.getInstance();
        List<Item> items = gameInstance.getCurrentArea().getItemList();

        gameInstance.getPlayer().getItemList().remove(this);
        gameInstance.getPlayer().addItems(items);
        gameInstance.getCurrentArea().removeItems(items);
    }
}
