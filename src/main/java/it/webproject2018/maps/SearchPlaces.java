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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;
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
    private static String APP_ID = "6hvvbMwr0YPA4xJwBaoX";
    private static String APP_CODE = "o7gS0fA0357N-qxRi401tA";
    public static String BASE_URL = "https://places.cit.api.here.com/places/v1/discover/search?app_id="+APP_ID+"&app_code="+APP_CODE+"&at=%f,%f&q=%s";
    
    public static SearchPlaces GetPlaces(float latitude, float longitude, int distanceRange, String keyword){
        SearchPlaces list = new SearchPlaces();
        
        try{
            String url = String.format(Locale.US, BASE_URL, latitude, longitude, URLEncoder.encode(keyword));
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            
            con.setRequestProperty("Accept", "application/json");
            
            int responseCode = con.getResponseCode();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
               response.append(inputLine);
            }
            in.close();
            
            JSONObject myResponse = new JSONObject(response.toString());
            
            JSONArray elems = myResponse.getJSONObject("results").getJSONArray("items");
            for(int i = 0; i < elems.length(); i++){                
                Place p = Place.ParsePlace(elems.getJSONObject(i));
                if(p != null && p.Distance <= distanceRange)
                    list.add(p);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        return list;
    }
    
}
