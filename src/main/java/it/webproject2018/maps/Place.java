/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.maps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 
import org.json.JSONObject;

/**
 *
 * @author davide
 */
public class Place {
    public static String API_KEY = "AIzaSyBgZR3Tv4DRUOj0C-f88JudvtS8HnpPsXE";
    public static String BASE_URL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&key=" + API_KEY;
    
    
    public String GMapsUrl;
    public String Address;
    
    public Place(String gMapsUrl, String address){
        GMapsUrl = gMapsUrl;
        Address = address;
    }
    
    
    public static Place GetPlaceById(String place_id){
        try{
            String url = String.format(BASE_URL, place_id);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
               response.append(inputLine);
            }
            in.close();
            
            JSONObject myResponse = new JSONObject(response.toString());
            
            String mapsUrl = myResponse.getJSONObject("result").getString("url");
            String address = myResponse.getJSONObject("result").getString("vicinity");
            
            return new Place(mapsUrl, address);
        }
        catch (Exception e){
            return null;
        }
    }   
}
