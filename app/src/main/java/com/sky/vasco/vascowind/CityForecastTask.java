package com.sky.vasco.vascowind;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class CityForecastTask extends AsyncTask<Favourite, Void, JSONObject> {
        private ProgressDialog pDialog;
        private WindAppDBHelper dbHelper;
        private int cityID;

    FavouritesFragment container;

    public CityForecastTask(FavouritesFragment f) {
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
        protected JSONObject doInBackground(Favourite... city) {
            this.cityID = city[0].getCity_id();
            JSONObject jsonObject = new JSONObject();
            JSONWind jc = new JSONWind();
            //TODO: Safe store the API Key.

                String url = String.format("http://api.openweathermap.org/data/2.5/forecast?id=%d&appid=71f92de99a61afd3d37577e2895357d4",
                        cityID);
                JSONObject jsonFavourite = jc.getJSONFromUrl(url);

            // Return the data from specified url
            return jsonObject;
        }


        protected void onPostExecute(JSONObject arg) {
            JSONWind jc = new JSONWind();
            List<Wind> winds = new ArrayList<>();
            winds = jc.getCityForecast(arg);
            
            //return the forecast List


            pDialog.dismiss();

            container.notifyAll();
        }
    }
