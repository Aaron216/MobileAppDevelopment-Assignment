package au.edu.curtin.madassignment.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import au.edu.curtin.madassignment.Model.*;
import au.edu.curtin.madassignment.R;

public class ListFragment extends Fragment {
    /* Fields */
    private RecyclerView itemRecyclerView;
    private Button actionButton;

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

        // Specify Layout
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public void setButtonText(String buttonText) {
        actionButton.setText(buttonText);
    }

    public void setData(List<Item> inItemList) {
        // Set Item List
        List<Item> itemList = inItemList;

        // Create adaptor
        ItemAdaptor adaptor = new ItemAdaptor(itemList);

        // Connect to recycler view
        itemRecyclerView.setAdapter(adaptor);
    }

    private class ItemAdaptor extends RecyclerView.Adapter<ItemViewHolder>{
        /* Fields */
        private List<Item> itemList;

        /* Constructor */
        public ItemAdaptor (List<Item> inItems) {
            this.itemList = inItems;
        }

        /* Accessors */
        @Override
        public int getItemCount() {
            return itemList.size();
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
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        /* Fields */
        private TextView nameText;
        private TextView valueText;
        private TextView propertyText;

        ItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_item_list, parent, false));

            // Get UI element references
            // itemView is field of RecyclerView.ViewHolder
            // not some kind of magically appearing variable
            nameText = itemView.findViewById(R.id.lblItemName);
            valueText = itemView.findViewById(R.id.lblItemValue);
            propertyText = itemView.findViewById(R.id.lblItemProperty);
        }

        void bind(Item item) {
            // Set text
            nameText.setText(item.getDescription());
            valueText.setText(String.format(Locale.ENGLISH, "$%d", item.getValue()));

            if (item instanceof Equipment) {
                Equipment equipment = (Equipment) item;
                propertyText.setText(String.format(Locale.ENGLISH, "%2f kg", equipment.getMass()));
            }
            else if (item instanceof Food) {
                Food food = (Food) item;
                propertyText.setText(String.format(Locale.ENGLISH, "%2f HP", food.getHealth()));
            }
            else {
                throw new IllegalArgumentException("Unknown Item type");
            }
        }
    }
}
