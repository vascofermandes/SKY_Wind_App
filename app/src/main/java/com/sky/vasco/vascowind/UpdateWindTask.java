package com.sky.vasco.vascowind;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class UpdateWindTask extends AsyncTask<String, Void, List<JSONObject>> {
        private ProgressDialog pDialog;
        private WindAppDBHelper dbHelper;
        private List<Favourite> favourites;

    FavouritesFragment container;

    public UpdateWindTask(FavouritesFragment f) {
        this.dbHelper = WindAppDBHelper.getInstance(f.getActivity().getApplicationContext());
        this.container = f;
        this.favourites = dbHelper.getAllFavourites();
    }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(this.container.getActivity());
            pDialog.setMessage("Updating Wind ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected List<JSONObject> doInBackground(String... arg) {

            List<JSONObject> jsonObjectsList = new ArrayList<>();
            JSONWind jc = new JSONWind();
            //TODO: Safe store the API Key.
            for (int i = 0; i < favourites.size(); i++) {
                String url = String.format("http://api.openweathermap.org/data/2.5/weather?id=%d&appid=71f92de99a61afd3d37577e2895357d4",
                        favourites.get(i).getCity_id());
                JSONObject jsonFavourite = jc.getJSONFromUrl(url);
                jsonObjectsList.add(jsonFavourite);
            }

            // Return the data from specified url
            return jsonObjectsList;
        }


        protected void onPostExecute(List<JSONObject> args) {
            JSONWind jc = new JSONWind();
            for (int i = 0; i < args.size(); i++) {
                Wind w = jc.getCityWind(args.get(i));
                try {
                    dbHelper.updateFavouriteLastWind(args.get(i).getInt("id"), w);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            pDialog.dismiss();
            if(args.size()>0)
                container.getAdapter().changeList(dbHelper.getAllFavourites());
        }
    }
