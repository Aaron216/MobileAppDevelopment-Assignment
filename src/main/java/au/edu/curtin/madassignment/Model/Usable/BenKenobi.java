package au.edu.curtin.madassignment.Model.Usable;

import android.content.Context;

import java.util.List;
import java.util.UUID;

import au.edu.curtin.madassignment.Activities.BenKenobiActivity;
import au.edu.curtin.madassignment.Model.*;

public class BenKenobi extends Equipment implements Equipment.Usable {
    /* Constants */
    public static final String NAME = "Ben Kenobi";
    private static final double MASS = 0.0;

    /* Constructor */
    public BenKenobi() {
        super();
        super.setType(Usable.TYPE);
        super.setDescription(NAME);
        super.setMass(MASS);
        super.setValue(1000);
        super.setUsable(true);
        super.setSpecial(false);
    }

    public BenKenobi(UUID inID) {
        this();
        super.setItemID(inID);
    }

    /* Function */
    @Override
    public void use(Context context) {
        context.startActivity(BenKenobiActivity.getIntent(context, super.getIDString()));
    }

    public void confirm() {
        GameData gameInstance = GameData.getInstance();
        List<Item> items = gameInstance.getCurrentArea().getItemList();

        gameInstance.getPlayer().removeItem(this);
        gameInstance.getPlayer().addItems(items);
        gameInstance.getCurrentArea().removeItems(items);
    }
}
