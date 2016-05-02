package com.sky.vasco.vascowind;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sky.vasco.vascowind.dummy.DummyContent;
import com.sky.vasco.vascowind.dummy.DummyContent.DummyItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AddTestFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private WindAppDBHelper dbHelper;
    private List<Favourite> currentList;
    private AddCityRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddTestFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AddTestFragment newInstance(int columnCount) {
        AddTestFragment fragment = new AddTestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    public AddCityRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(AddCityRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = WindAppDBHelper.getInstance(getContext().getApplicationContext());
        this.currentList = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Context viewContext = view.getContext();
        final AddTestFragment instanceFragment = this;


        // Set the adapter
        // if (view instanceof RecyclerView) {

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(viewContext));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(viewContext, mColumnCount));
            }
            this.adapter = new AddCityRecyclerViewAdapter(currentList, mListener);
            recyclerView.setAdapter(adapter);
       // }

        //if (view instanceof SearchView){
            //SearchView sView = (SearchView) view;
            SearchView sView = (SearchView) view.findViewById(R.id.searchView);

            SearchManager searchManager = (SearchManager) viewContext.getSystemService(Context.SEARCH_SERVICE);
            sView.setSearchableInfo(searchManager.getSearchableInfo(this.getActivity().getComponentName()));
            sView.setIconifiedByDefault(false);
            sView.setSubmitButtonEnabled(true);

            sView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                new SearchCityTask(instanceFragment).execute(query);
                return true;
            }

        });

        //}

        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Favourite item);
    }

}
