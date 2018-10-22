package au.edu.curtin.madassignment.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.R;

public class ListFragment extends Fragment {

    /**
     * List Interface
     */
    public interface OnActionListener {
        void onAction();
    }

    /* Constants */
    public static final int MARKET_SELL = 1;
    public static final int MARKET_BUY = 2;
    public static final int WILDERNESS_DROP = 3;
    public static final int WILDERNESS_PICK = 4;

    /* Fields */
    private OnActionListener actionListener;
    private RecyclerView itemRecyclerView;
    private Button actionButton;
    private TextView noItemsText;
    private ItemAdaptor adaptor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup ui, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_list, ui, false);

        // Reference UI Elements
        itemRecyclerView = view.findViewById(R.id.listItems);
        actionButton = view.findViewById(R.id.btnAction);
        noItemsText = view.findViewById(R.id.lblNoItems);

        // Specify Layout
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.actionListener = (OnActionListener)context;
        }
        catch (final ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must implement OnActionListener");
        }
    }

    public void setListType(final int type) {
        if (itemRecyclerView == null) {
            throw new IllegalStateException("Recycler view has not been initialised");
        }

        switch (type) {
            case MARKET_BUY:
                actionButton.setText(getResources().getText(R.string.buy));
                break;

            case MARKET_SELL:
                actionButton.setText(getResources().getText(R.string.sell));
                break;

            case WILDERNESS_DROP:
                actionButton.setText(getResources().getText(R.string.drop));
                break;

            case WILDERNESS_PICK:
                actionButton.setText(getResources().getText(R.string.pick_up));
                break;

            default:
                throw new IllegalArgumentException("Unknown list fragment type");
        }

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adaptor == null) {
                    throw new IllegalStateException("Cannot set list type before setting list data.");
                }

                try {
                    adaptor.actionItems(type);
                }
                catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                actionListener.onAction();
            }
        });
    }

    public void setData(List<Item> inItemList) {
        // Initialise Adaptor
        adaptor = new ItemAdaptor(inItemList);

        // Connect to recycler view
        itemRecyclerView.setAdapter(adaptor);

        update();
    }

    public void update() {
        adaptor.notifyDataSetChanged();

        if (adaptor.getItemCount() > 0) {
            // Show recycler view
            itemRecyclerView.setVisibility(View.VISIBLE);
            noItemsText.setVisibility(View.INVISIBLE);
            actionButton.setEnabled(true);
        }
        else {
            // Hide recycler view
            itemRecyclerView.setVisibility(View.INVISIBLE);
            noItemsText.setVisibility(View.VISIBLE);
            actionButton.setEnabled(false);
        }
    }

    public void clearSelected() {
        adaptor.clearSelected();
    }


    /**
     * Item Adaptor Class
     */
    private class ItemAdaptor extends RecyclerView.Adapter<ItemViewHolder>{
        /* Fields */
        private List<Item> itemList;

        /* Constructor */
        ItemAdaptor(List<Item> inItems) {
            setItems(inItems);
        }

        /* Accessors */
        @Override
        public int getItemCount() {
            return itemList.size();
        }

        /* Mutator */
        void setItems(List<Item> inItems) {
            if (inItems == null) {
                throw new IllegalArgumentException("Items cannot be null");
            }
            this.itemList = inItems;
        }

        /* Functions */
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ItemViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int index) {
            viewHolder.bind(itemList.get(index));
        }

        void actionItems(int type) {
            LinkedList<Item> selectedItems = new LinkedList<>();

            for (Item currItem : itemList) {
                if (currItem.isSelected()) {
                    selectedItems.add(currItem);
                }
            }

            GameData.getInstance().actionItems(type, selectedItems);
        }

        void clearSelected() {
            for (Item currItem : itemList) {
                currItem.setSelected(false);
            }
            notifyDataSetChanged();
        }
    }


    /**
     * Item View Holder Class
     */
    private class ItemViewHolder extends RecyclerView.ViewHolder {
        /* Fields */
        private ConstraintLayout itemLayout;
        private TextView nameText;
        private TextView valueText;
        private TextView propertyText;

        /* Constructor */
        ItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_item_list, parent, false));
            // Get UI element references
            // itemView is field of RecyclerView.ViewHolder
            // not some kind of magically appearing variable
            itemLayout = itemView.findViewById(R.id.layoutItemInfo);
            nameText = itemView.findViewById(R.id.lblItemName);
            valueText = itemView.findViewById(R.id.lblItemValue);
            propertyText = itemView.findViewById(R.id.lblItemProperty);
        }

        /* Functions */
        void bind(final Item item) {
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.toggleSelected();
                    if (item.isSelected()) {
                        itemLayout.setBackground(getResources().getDrawable(R.drawable.rounded_box, null));
                    }
                    else {
                        itemLayout.setBackground(null);
                    }
                }
            });

            // Set text
            nameText.setText(item.getDescription());
            valueText.setText(String.format(Locale.ENGLISH, "$%d", item.getValue()));

            if (item instanceof Equipment) {
                Equipment equipment = (Equipment) item;
                propertyText.setText(String.format(Locale.ENGLISH, "%.2f kg", equipment.getMass()));
            }
            else if (item instanceof Food) {
                Food food = (Food) item;
                propertyText.setText(String.format(Locale.ENGLISH, "%.2f HP", food.getHealth()));
            }
            else {
                throw new IllegalArgumentException("Unknown Item type");
            }

            // Set background
            if (item.isSelected()) {
                itemLayout.setBackground(getResources().getDrawable(R.drawable.rounded_box, null));
            }
            else {
                itemLayout.setBackground(null);
            }
        }
    }

}
