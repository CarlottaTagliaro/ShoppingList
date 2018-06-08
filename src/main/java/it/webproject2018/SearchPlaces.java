/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author davide
 */

/*

EXAMPLE usage:
                                        //lat       //long      //mt range //keyword
SearchPlaces p = SearchPlaces.GetPlaces(46.074779f, 11.121749f, 500, "farmacia");
        
for(int i = 0; i < p.size(); i++){
    System.out.println(p.get(i).Address);
    System.out.println(p.get(i).GMapsUrl);
    System.out.println("-------");
}


*/
public class SearchPlaces extends ArrayList<Place> {
    public static String BASE_URL = "https://maps.googleapis.com/maps/api/place/radarsearch/json?location=%f,%f&radius=%d&keyword=%s&key=" + Place.API_KEY;
    
    public static SearchPlaces GetPlaces(float latitude, float longitude, int distanceRange, String keyword){
        SearchPlaces list = new SearchPlaces();
        
        try{
            String url = String.format(BASE_URL, latitude, longitude, distanceRange, keyword);
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
            
            JSONArray elems = myResponse.getJSONArray("results");
            for(int i = 0; i < elems.length(); i++){
                String place_id = elems.getJSONObject(i).getString("place_id");
                
                Place p = Place.GetPlaceById(place_id);
                if(p != null)
                    list.add(p);
            }
        }
        catch (Exception e){
        }
        
        return list;
    }
    
}
