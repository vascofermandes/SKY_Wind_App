package com.sky.vasco.vascowind;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vasco on 27/04/2016.
 */

public final class JSONWind {

    //JSON Node Post Tags
    private static final String TAG_CITY = "city";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";

    private static final String TAG_WIND = "wind";
    private static final String TAG_SPEED = "speed";
    private static final String TAG_DEG = "deg";
    private static final String TAG_DATE = "dt";

    private static final String TAG_SYS = "sys";
    private static final String TAG_COUNTRY = "country";

    private static final String TAG_LIST = "list";


    // constructor
    public JSONWind() {

    }

    public JSONObject getJSONFromUrl(String uri) {

        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while((json = bufferedReader.readLine())!= null){
                sb.append(json+"\n");
            }

            //TODO: check if should use  trim() or not....
            String stream = sb.toString();

            JSONObject res = new JSONObject(stream);

            return res;

        }catch(Exception e){
            return null;
        }
        }


    public List<Wind> getCityForecast(JSONObject forecast) {
        List<Wind> windList = new ArrayList<>();
        try {

            JSONArray l = forecast.getJSONArray(TAG_LIST);

            for (int i = 0; i < l.length(); i++) {
                Wind wind = new Wind();

                wind.setSpeed(l.getJSONObject(i).getInt(TAG_DATE));

                JSONObject jsonWind = l.getJSONObject(i).getJSONObject(TAG_WIND);
                wind.setSpeed(jsonWind.getDouble(TAG_SPEED));
                wind.setDeg(jsonWind.getDouble(TAG_DEG));

                windList.add((wind));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return windList;
    }

    public Wind getCityWind(JSONObject wind) {
        Wind nWind = new Wind();

        try {
            nWind.setSpeed(wind.getDouble(TAG_DATE));
            JSONObject jsonWind = wind.getJSONObject(TAG_WIND);
            nWind.setSpeed(jsonWind.getDouble(TAG_SPEED));
            nWind.setSpeed(jsonWind.getDouble(TAG_DEG));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return nWind;
    }

    public List<Favourite> getSearchCity(JSONObject searchResult) {
        List<Favourite> cityList = new ArrayList<>();
        try {

            JSONArray l = searchResult.getJSONArray(TAG_LIST);

            for (int i = 0; i < l.length(); i++) {
                Favourite favourite = new Favourite();
                Wind wind = new Wind();

                favourite.setCity_id(l.getJSONObject(i).getInt(TAG_ID));
                favourite.setCity_name(l.getJSONObject(i).getString(TAG_NAME));
                wind.setSpeed(l.getJSONObject(i).getInt(TAG_DATE));

                JSONObject jsonWind = l.getJSONObject(i).getJSONObject(TAG_WIND);
                wind.setSpeed(jsonWind.getDouble(TAG_SPEED));
                wind.setDeg(jsonWind.getDouble(TAG_DEG));

                favourite.setCountry_code(l.getJSONObject(i).getJSONObject(TAG_SYS).getString(TAG_COUNTRY));
                favourite.setLast_wind(wind);

                cityList.add((favourite));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cityList;
    }
}