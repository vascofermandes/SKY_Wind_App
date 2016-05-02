package com.sky.vasco.vascowind;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sky.vasco.vascowind.AddTestFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Favourite} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AddCityRecyclerViewAdapter extends RecyclerView.Adapter<AddCityRecyclerViewAdapter.ViewHolder> {

    private final List<Favourite> mValues;
    private final OnListFragmentInteractionListener mListener;
    private WindAppDBHelper dbHelper;

    public AddCityRecyclerViewAdapter(List<Favourite> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

    }

    public void changeList(List<Favourite> list){
        if ( mValues != null)
            mValues.clear();

        mValues.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        if ( dbHelper == null)
            dbHelper = WindAppDBHelper.getInstance(parent.getContext().getApplicationContext());


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        Favourite f = mValues.get(position);
        holder.mCity.setText(f.getCity_name()+", "
        + f.getCountry_code());
        if (dbHelper.checkIfExists(f.city_id))
            holder.mButton.setVisibility(View.INVISIBLE);
        else
            holder.mButton.setVisibility(View.VISIBLE);


        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    dbHelper.addFavourite(holder.mItem);
                    notifyItemChanged(position);

                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageButton mButton;
        public final TextView mCity;
        public Favourite mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mButton = (ImageButton) view.findViewById(R.id.imageButton);
            mCity = (TextView) view.findViewById(R.id.city);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mCity.getText() + "'";
        }
    }
}
