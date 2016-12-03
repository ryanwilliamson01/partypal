package com.d3.partypal.partypal;

import android.graphics.drawable.Drawable;
import android.media.Image;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 12/2/16.
 */
public class Drink  {


    String name;
    String blurb;
    Drawable thumbnail;
    ArrayList<String> ingredients;
    ArrayList<String> amounts;


    boolean expanded;

    boolean alcholic;

    public Drink(String name, String blurb, Drawable thumbnail, ArrayList<String> ingredients, ArrayList<String> amounts, boolean alcholic) {

        this.name = name;
        this.thumbnail = thumbnail;
        this.ingredients = ingredients;
        this.amounts = amounts;
        this.alcholic = alcholic;
        this.blurb = blurb;
        expanded=false;
    }

    public Drink() {

    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getName() {
        return name;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getAmounts() {
        return amounts;
    }

    public void setAmounts(ArrayList<String> amounts) {
        this.amounts = amounts;
    }

    public boolean isAlcholic() {
        return alcholic;
    }


    public void setAlcholic(boolean alcholic) {
        this.alcholic = alcholic;
    }

}
