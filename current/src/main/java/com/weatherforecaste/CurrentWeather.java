package com.weatherforecaste;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpConnectTimeoutException;
import java.util.Scanner;

import org.json.*;

public class CurrentWeather {

    public void showData(JSONObject object) {
        JSONObject locationObj = object.getJSONObject("location");
        String place = locationObj.getString("name");

        JSONObject weatherObj = object.getJSONObject("current");

        String temprature = "Temprature: " + weatherObj.getInt("temp_c");
        
        String condtion = "Condition: " + weatherObj.getJSONObject("condition").getString("text");

        String cloud = "Cloud: " + weatherObj.getInt("cloud") + "%";

        String feels = "Feels like: " + weatherObj.getInt("feelslike_c");

        String windSpeed = "Wind Speed: " + weatherObj.getInt("wind_kph") + "km";

        String precipitation = "Precipitation: " + weatherObj.getInt("precip_mm") + "%";

        String humidity = "Humidity: " + weatherObj.getInt("humidity") + "%";

        String pressure = "Pressure: " + weatherObj.getInt("pressure_mb") + "mb";


        System.out.println("The weather in " + place + " is as follow: \n" + condtion);
        
        System.out.println(temprature + "\n" + cloud  + "\n" + feels + "\n" + windSpeed + "\n" + precipitation + "\n" + humidity + "\n"
                + pressure);

    }

    public void checkWeather(String query) {
        final String API_KEY = "8efd5a63ed6e4c46828110801241903";
        String urlString = "http://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + query;

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                InputStream is = con.getInputStream();

                BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
                StringBuffer responseData = new StringBuffer("");

                String tempLine = "";
                while ((tempLine = bReader.readLine()) != null) {
                    responseData.append(tempLine);
                }

                JSONObject jsonObject = new JSONObject(responseData.toString());
                if (jsonObject != null) {
                    showData(jsonObject);

                }
            }
        } catch (JSONException je) {
            System.err.println(je.toString());
        } catch (HttpConnectTimeoutException httpE) {
            System.err.println(httpE.toString());
        } catch (Exception e) {
            System.err.println(e.toString() + " or Internet is not available.");
        }

    }

    public static void main(String[] args) {
        // System.out.println("Hello world!");
        CurrentWeather cw = new CurrentWeather();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Weather Forecaste Program");

       while (true) {
        System.out.println("\nPlease Enter Location to check weather: ");
        String query = sc.nextLine();
        cw.checkWeather(query);

        System.out.println("\nDo you want to check weather again (Yes/No): ");
        String ch = sc.nextLine();

        if(!(ch.equals("YES")) && !(ch.equals("Yes")) && !(ch.equals("yes"))) break;
       }
       sc.close();

       System.out.println("Thanks for Using this Program");


    }
}