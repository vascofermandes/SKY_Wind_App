package com.sky.vasco.vascowind;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class SearchCityTask extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog pDialog;
        private WindAppDBHelper dbHelper;

    FavouritesFragment container;

    public SearchCityTask(FavouritesFragment f) {
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
        protected JSONObject doInBackground(String... search) {

            JSONObject jsonObject = new JSONObject();
            JSONWind jc = new JSONWind();
            //TODO: Safe store the API Key.

                String url = String.format("http://api.openweathermap.org/data/2.5/find?q=%s&type=like&appid=71f92de99a61afd3d37577e2895357d4",
                        search[0]);
                JSONObject jsonFavourite = jc.getJSONFromUrl(url);

            // Return the data from specified url
            return jsonObject;
        }


        protected void onPostExecute(JSONObject arg) {
            JSONWind jc = new JSONWind();
            List<Favourite> cities = new ArrayList<>();
            cities = jc.getSearchCity(arg);

            //return the cities List


            pDialog.dismiss();

            container.notifyAll();
        }
    }
