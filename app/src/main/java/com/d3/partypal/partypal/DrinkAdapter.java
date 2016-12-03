package com.d3.partypal.partypal;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d3.partypal.partypal.Drink;
import com.d3.partypal.partypal.R;

import java.util.ArrayList;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {
    private ArrayList<Drink> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout mRelativeLayout;

        public ViewHolder(RelativeLayout v) {
            super(v);
            mRelativeLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DrinkAdapter(ArrayList<Drink> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DrinkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView name = (TextView) holder.mRelativeLayout.findViewById(R.id.drink_name);
        TextView blurb = (TextView) holder.mRelativeLayout.findViewById(R.id.drink_instructions);


        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataset.get(position).isExpanded()){
                    hideItems(holder,position);
                    mDataset.get(position).setExpanded(false);
                }else {
                    showItems(holder,position);
                    mDataset.get(position).setExpanded(true);
                }
               // MainActivity.showSnackBar("Showing" + Integer.toString(position), v);
            }
        });

        name.setText(mDataset.get(position).getName());
        blurb.setText(mDataset.get(position).getBlurb());


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void hideItems(ViewHolder holder, int position) {

        TextView ingredients[] = {(TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd1),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd2),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd3),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd4),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd5),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd6)};
        if(mDataset.get(position).getIngredients().size() <=6) {
            for (int i = 0; i < mDataset.get(position).getIngredients().size(); i++) {
                ingredients[i].setVisibility(View.GONE);
            }
        }

    }

    public void showItems(ViewHolder holder, int position) {
        TextView[] ingredients = {(TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd1),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd2),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd3),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd4),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd5),
                (TextView) holder.mRelativeLayout.findViewById(R.id.drink_ingrd6)};

        Log.d("Test", ingredients[0].getText().toString());

        if(mDataset.get(position).getIngredients().size() <=6) {
            for (int i = 0; i < mDataset.get(position).getIngredients().size(); i++) {
                ingredients[i].setVisibility(View.VISIBLE);
                String ingredientName = mDataset.get(position).getIngredients().get(i);
                String ingredientAmount = mDataset.get(position).getAmounts().get(i);
                ingredients[i].setText(ingredientName + "  " + ingredientAmount);
            }
        }


        
    }

    public void deleteItem1(){
        mDataset.remove(0);
        notifyDataSetChanged();
    }

    public void swap(ArrayList<Drink> newDataSet) {
        mDataset.clear();
        mDataset.addAll(newDataSet);
        notifyDataSetChanged();
    }
}