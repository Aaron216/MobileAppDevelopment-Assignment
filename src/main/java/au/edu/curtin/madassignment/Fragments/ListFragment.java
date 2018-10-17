package au.edu.curtin.madassignment.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import au.edu.curtin.madassignment.Model.Item;
import au.edu.curtin.madassignment.R;

public class ListFragment extends Fragment {
    /* Fields */

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup ui, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_list, ui, false);

        // Reference UI Elements

        // Set on click listeners

        return  view;
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
        ItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_item_list, parent, false));

            // Get UI element references
        }

        void bind(Item item) {
            // Set text
        }
    }
}
