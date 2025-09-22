package com.example.Restaurant_Suggestion.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "restaurants")
public class Restaurant {

    @Id
    private String id;
    private String name, address, city, cuisine, vibe;
    private double rating;
    private int priceLevel;
    private int averageCostPerPerson;
    private List<String> cuisines, specialtyDishes, dietaryOptions, ambienceTags, occasionTags, deliveryPartners;
    private boolean hasParking, isWheelchairAccessible, acceptsReservations, hasDelivery, servesAlcohol;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    public Restaurant() {}

    public Restaurant(String name, String address, String city, String cuisine, double rating, int priceLevel, String vibe, int averageCostPerPerson, List<String> cuisines, List<String> specialtyDishes, List<String> dietaryOptions, boolean hasParking, boolean isWheelchairAccessible, boolean acceptsReservations, List<String> ambienceTags, List<String> occasionTags, boolean hasDelivery, List<String> deliveryPartners, boolean servesAlcohol, GeoJsonPoint location) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.cuisine = cuisine;
        this.rating = rating;
        this.priceLevel = priceLevel;
        this.vibe = vibe;
        this.averageCostPerPerson = averageCostPerPerson;
        this.cuisines = cuisines;
        this.specialtyDishes = specialtyDishes;
        this.dietaryOptions = dietaryOptions;
        this.hasParking = hasParking;
        this.isWheelchairAccessible = isWheelchairAccessible;
        this.acceptsReservations = acceptsReservations;
        this.ambienceTags = ambienceTags;
        this.occasionTags = occasionTags;
        this.hasDelivery = hasDelivery;
        this.deliveryPartners = deliveryPartners;
        this.servesAlcohol = servesAlcohol;
        this.location = location;
    }
    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getVibe() {
        return vibe;
    }

    public void setVibe(String vibe) {
        this.vibe = vibe;
    }

    public int getAverageCostPerPerson() {
        return averageCostPerPerson;
    }

    public void setAverageCostPerPerson(int averageCostPerPerson) {
        this.averageCostPerPerson = averageCostPerPerson;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public List<String> getSpecialtyDishes() {
        return specialtyDishes;
    }

    public void setSpecialtyDishes(List<String> specialtyDishes) {
        this.specialtyDishes = specialtyDishes;
    }

    public List<String> getDietaryOptions() {
        return dietaryOptions;
    }

    public void setDietaryOptions(List<String> dietaryOptions) {
        this.dietaryOptions = dietaryOptions;
    }

    public boolean isHasParking() {
        return hasParking;
    }

    public void setHasParking(boolean hasParking) {
        this.hasParking = hasParking;
    }

    public boolean isWheelchairAccessible() {
        return isWheelchairAccessible;
    }

    public void setWheelchairAccessible(boolean wheelchairAccessible) {
        isWheelchairAccessible = wheelchairAccessible;
    }

    public boolean isAcceptsReservations() {
        return acceptsReservations;
    }

    public void setAcceptsReservations(boolean acceptsReservations) {
        this.acceptsReservations = acceptsReservations;
    }

    public List<String> getAmbienceTags() {
        return ambienceTags;
    }

    public void setAmbienceTags(List<String> ambienceTags) {
        this.ambienceTags = ambienceTags;
    }

    public List<String> getOccasionTags() {
        return occasionTags;
    }

    public void setOccasionTags(List<String> occasionTags) {
        this.occasionTags = occasionTags;
    }

    public boolean isHasDelivery() {
        return hasDelivery;
    }

    public void setHasDelivery(boolean hasDelivery) {
        this.hasDelivery = hasDelivery;
    }

    public List<String> getDeliveryPartners() {
        return deliveryPartners;
    }

    public void setDeliveryPartners(List<String> deliveryPartners) {
        this.deliveryPartners = deliveryPartners;
    }

    public boolean isServesAlcohol() {
        return servesAlcohol;
    }

    public void setServesAlcohol(boolean servesAlcohol) {
        this.servesAlcohol = servesAlcohol;
    }
}