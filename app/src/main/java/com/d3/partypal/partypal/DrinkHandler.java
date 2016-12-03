package com.d3.partypal.partypal;


import android.os.StrictMode;
import android.util.Log;

import com.d3.partypal.partypal.Drink;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;


import org.json.*;




/**
 * Created by Ryan on 12/2/16.
 */
public class DrinkHandler {


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String[] ListIngredients() {

        String ingredients[];
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            JSONObject json = readJsonFromUrl("http://www.thecocktaildb.com/api/json/v1/1/list.php?i=list");

            JSONArray drinks = json.getJSONArray("drinks");

            ingredients = new String[drinks.length()];
            for (int i = 0; i < drinks.length(); i++) {
                ingredients[i]=drinks.getJSONObject(i).get("strIngredient1").toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            ingredients = new String[1];
        }
        return ingredients;
    }

    public static ArrayList<Drink> findDrinks(String ingredient) {
        ArrayList<Drink> foundDrinks = new ArrayList<Drink>();
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            if(ingredient.contains(" ")){
                ingredient = ingredient.replaceAll(" ","%20");
            }
            String url = "http://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + ingredient;
            JSONObject json = readJsonFromUrl(url);

            JSONArray drinks = json.getJSONArray("drinks");


            for (int i = 0; i < drinks.length(); i++) {
                foundDrinks.add(getData(drinks.getJSONObject(i).get("idDrink").toString()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundDrinks;
    }


    public static Drink getData(String drinkID) {

        Drink drink = new Drink();

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            String url = "http://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + drinkID;
            JSONObject json = readJsonFromUrl(url);

            JSONArray drinks = json.getJSONArray("drinks");


            for (int i = 0; i < drinks.length(); i++) {


                drink.setName(drinks.getJSONObject(i).get("strDrink").toString());

                ArrayList<String> ingredients = new ArrayList<String>();
                ArrayList<String> amounts = new ArrayList<String>();

                //Get all the ignredients/amounts
                for (int x = 1; x <= 15; x++) {
                    if (!(drinks.getJSONObject(i).get("strIngredient" + x).toString().equals(""))) {

                        ingredients.add(drinks.getJSONObject(i).get("strIngredient" + x).toString());
                        amounts.add(drinks.getJSONObject(i).get("strMeasure" + x).toString());

                    }
                }
                drink.setIngredients(ingredients);
                drink.setAmounts(amounts);


                drink.setBlurb(drinks.getJSONObject(i).get("strInstructions").toString());

                //Check if its alcholic
                if (drinks.getJSONObject(i).get("strAlcoholic").toString().equals("Alcoholic")) {
                    drink.setAlcholic(true);
                } else {
                    drink.setAlcholic(false);
                }

//                //Grab the image
//                Image image = null;
//                if (drinks.getJSONObject(i).get("strDrinkThumb").toString().equals( null)) {
//                    image = ImageIO.read(new URL(drinks.getJSONObject(i).get("strDrinkThumb").toString()));
//                }
//                drink.setThumbnail(image);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return drink;
    }

    public static void main(String[] args) {

        //ListIngredients();

        Scanner reader = new Scanner(System.in);

        System.out.println("Enter a drink ingredient");
        String input = reader.nextLine();

        ArrayList<Drink> drinks = findDrinks(input);


        if(drinks!=null) {
            for (Drink d : drinks) {
                System.out.println("Name: " + d.getName());
                for (int i = 0; i < d.getIngredients().size(); i++) {
                    System.out.println("Ingredient: " + d.getIngredients().get(i) + " Amount: " + d.getAmounts().get(i));
                }
                System.out.println();
            }
        }else{
            System.out.println("No Drinks Found");
        }


    }
}
