package com.movilbox.movilboxprueba.Retrofit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.movilbox.movilboxprueba.models.User;

import java.lang.reflect.Type;

// debido a que algunos datos del user estan en objetos dentro de objetos, utilizo esta clase para accederlos
// sin tener que crear varios nuevos modelos de datos, segun el funcionamiento retrofit+gson
public class gsonDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String id = json.getAsJsonObject().get("id").getAsString();
        String name = json.getAsJsonObject().get("name").getAsString();
        String username = json.getAsJsonObject().get("username").getAsString();
        String email = json.getAsJsonObject().get("email").getAsString();
        String phone = json.getAsJsonObject().get("phone").getAsString();
        String website = json.getAsJsonObject().get("website").getAsString();


        String address_street = json.getAsJsonObject().get("address").getAsJsonObject().get("street").getAsString();
        String address_suite = json.getAsJsonObject().get("address").getAsJsonObject().get("suite").getAsString();
        String address_city = json.getAsJsonObject().get("address").getAsJsonObject().get("city").getAsString();
        String address_zipcode = json.getAsJsonObject().get("address").getAsJsonObject().get("zipcode").getAsString();
        String address_geo_lat = json.getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lat").getAsString();
        String address_geo_lng = json.getAsJsonObject().get("address").getAsJsonObject().get("geo").getAsJsonObject().get("lng").getAsString();


        String company_name = json.getAsJsonObject().get("company").getAsJsonObject().get("name").getAsString();
        String company_catchPhrase = json.getAsJsonObject().get("company").getAsJsonObject().get("catchPhrase").getAsString();
        String company_bs = json.getAsJsonObject().get("company").getAsJsonObject().get("bs").getAsString();


        return new User (id,name,username,email,phone,website,
                        address_street,address_suite,address_city,address_zipcode,address_geo_lat,address_geo_lng,
                        company_name,company_catchPhrase,company_bs);
    }
}
