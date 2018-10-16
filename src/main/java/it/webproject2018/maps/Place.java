/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.maps;

import org.json.JSONObject;

/**
 *
 * @author davide
 */
public class Place {    
    public String Name;
    public Integer Distance;
    
    public Place(String Name, Integer Distance){
        this.Name = Name;
        this.Distance = Distance;
    }
    
    
    public static Place ParsePlace(JSONObject place){
        return new Place(place.getString("title"), place.getInt("distance"));
    }   
}
