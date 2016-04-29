package com.sky.vasco.vascowind;

/**
 * Created by Vasco on 26/04/2016.
 */
public class Favourite {

    public int city_id;
    public String city_name;
    public String country_code;
    public Wind last_wind;

    public Favourite() {
    }

    public Favourite(int city_id, String city_name) {
        this.city_id = city_id;
        this.city_name = city_name;
    }

    public Favourite(int city_id, String city_name, Wind last_wind) {
        this.city_id = city_id;
        this.city_name = city_name;
        this.last_wind = last_wind;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Wind getLast_wind() {
        return last_wind;
    }

    public void setLast_wind(Wind last_wind) {
        this.last_wind = last_wind;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}
