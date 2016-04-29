package com.sky.vasco.vascowind;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class UpdateWindTask extends AsyncTask<Favourite, Void, List<JSONObject>> {
        private ProgressDialog pDialog;
        private WindAppDBHelper dbHelper;

    FavouritesFragment container;

    public UpdateWindTask(FavouritesFragment f) {
        this.dbHelper = WindAppDBHelper.getInstance(f.getContext());
        this.container = f;
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
        protected List<JSONObject> doInBackground(Favourite... args) {

            List<JSONObject> jsonObjectsList = new ArrayList<>();
            JSONWind jc = new JSONWind();
            //TODO: Safe store the API Key.
            for (int i = 0; i < args.length; i++) {
                String url = String.format("http://api.openweathermap.org/data/2.5/weather?id=%d&appid=71f92de99a61afd3d37577e2895357d4",
                        args[i].getCity_id());
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

            container.notifyAll();
        }
    }
