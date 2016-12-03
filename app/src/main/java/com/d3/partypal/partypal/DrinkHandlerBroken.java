//package com.d3.partypal.partypal;
//
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//import android.media.Image;
//import android.os.AsyncTask;
//import android.util.Log;
//
//
//import com.android.volley.Cache;
//import com.android.volley.Network;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.BasicNetwork;
//import com.android.volley.toolbox.DiskBasedCache;
//import com.android.volley.toolbox.HurlStack;
//import com.android.volley.toolbox.JsonObjectRequest;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import org.json.*;
//
//
//
//
//
///**
// * Created by Ryan on 12/2/16.
// */
//public class DrinkHandler {
//
//    public Context mContext;
//
//    // Instantiate the cache
//    private Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024); // 1MB cap
//
//
//    // Set up the network to use HttpURLConnection as the HTTP client.
//    private Network network = new BasicNetwork(new HurlStack());
//
//    private RequestQueue mRequestQueue;
//
//
//
//    public DrinkHandler(Context context){
//
//        this.mContext=context;
//        mRequestQueue = new RequestQueue(cache, network);
//        // Start the queue
//        mRequestQueue.start();
//
//    }
//
//
//    public static Drawable LoadImageFromWebOperations(String url) {
//        try {
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");
//            return d;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private static String readAll(Reader rd) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        int cp;
//        while ((cp = rd.read()) != -1) {
//            sb.append((char) cp);
//        }
//        return sb.toString();
//    }
//
//    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
//
//
//        InputStream is = new URL(url).openStream();
//        try {
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//            String jsonText = readAll(rd);
//            JSONObject json = new JSONObject(jsonText);
//            return json;
//        } finally {
//            is.close();
//        }
//    }
//
//
//
//    public void ListIngredients() {
//
//        try {
//            JSONObject json = readJsonFromUrl("http://www.thecocktaildb.com/api/json/v1/1/list.php?i=list");
//
//            JSONArray drinks = json.getJSONArray("drinks");
//
//            for (int i = 0; i < drinks.length(); i++) {
//                System.out.println("Ingredient " + drinks.getJSONObject(i).get("strIngredient1").toString());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public  void findDrinks(JSONObject data) {
//        ArrayList<Drink> foundDrinks = new ArrayList<Drink>();
//        try {
//
//            JSONArray drinks = data.getJSONArray("drinks");
//
//            for (int i = 0; i < drinks.length(); i++) {
//                foundDrinks.add(getData(drinks.getJSONObject(i).get("idDrink").toString()));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        ((MainActivity) MainActivity.activity).updateRecylerView(foundDrinks);
//    }
//
//    public void makeDrinkRequest(String ingredient){
//
//        if(ingredient.contains(" ")){
//            ingredient = ingredient.replaceAll(" ","%20");
//        }
//        String url = "http://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + ingredient;
//
//
//
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            findDrinks(response);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//
//                    }
//                });
//
//        mRequestQueue.add(jsObjRequest);
//
//    }
//
//    public void getDrinkData(String drinkID){
//
//        String url = "http://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + drinkID;
//
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            findDrinks(response);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//
//                    }
//                });
//
//        mRequestQueue.add(jsObjRequest);
//    }
//
//    public Drink getData(String drinkID) {
//
//        Drink drink = new Drink();
//
//        try {
//
//
//            JSONArray drinks = new JSONArray();
//
//            for (int i = 0; i < drinks.length(); i++) {
//
//
//                drink.setName(drinks.getJSONObject(i).get("strDrink").toString());
//
//                ArrayList<String> ingredients = new ArrayList<String>();
//                ArrayList<String> amounts = new ArrayList<String>();
//
//                //Get all the ignredients/amounts
//                for (int x = 1; x <= 15; x++) {
//                    if (!(drinks.getJSONObject(i).get("strIngredient" + x).toString().equals(""))) {
//
//                        ingredients.add(drinks.getJSONObject(i).get("strIngredient" + x).toString());
//                        amounts.add(drinks.getJSONObject(i).get("strMeasure" + x).toString());
//
//                    }
//                }
//                drink.setIngredients(ingredients);
//                drink.setAmounts(amounts);
//
//                //Check if its alcholic
//                if (drinks.getJSONObject(i).get("strAlcoholic").toString().equals("Alcoholic")) {
//                    drink.setAlcholic(true);
//                } else {
//                    drink.setAlcholic(false);
//                }
//
//                //Grab the image
//                Drawable image = null;
//                if (drinks.getJSONObject(i).get("strDrinkThumb").toString().equals( null)) {
//                    image = LoadImageFromWebOperations(drinks.getJSONObject(i).get("strDrinkThumb").toString());
//                }
//                drink.setThumbnail(image);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return drink;
//    }
//
//
//}
