package com.sky.vasco.vascowind;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.vasco.vascowind.FavouritesFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Favourite} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FavouriteRecyclerViewAdapter extends RecyclerView.Adapter<FavouriteRecyclerViewAdapter.ViewHolder> {

    private final List<Favourite> mValues;
    private final OnListFragmentInteractionListener mListener;

    public FavouriteRecyclerViewAdapter(List<Favourite> items, OnListFragmentInteractionListener listener) {
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
                .inflate(R.layout.fragment_item_favourite, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Favourite f = mValues.get(position);
        holder.mCityName.setText(f.city_name);
        holder.mSpeed.setText(String.valueOf(f.getLast_wind().getSpeed())+" m/s");
        holder.mDeg.setRotation((float) f.getLast_wind().getDeg());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
        public final TextView mCityName;
        public final TextView mSpeed;
        public final ImageView mDeg;
        public final ImageButton mRemove;
        public Favourite mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCityName = (TextView) view.findViewById(R.id.cityName);
            mSpeed = (TextView) view.findViewById(R.id.speed);
            mDeg = (ImageView) view.findViewById(R.id.imageDegRotation);
            mRemove = (ImageButton) view.findViewById(R.id.removeFav);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCityName.getText() + "'";
        }
    }
}
