package br.edu.ifrn.tads.caronas.data;

import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.lightcouch.ViewResult;

import java.util.List;

public class TravelDAO extends EntityDAO<Travel> {
    public TravelDAO() {
        super(Travel.class);
    }

    public List<Travel> findAll(){
        List<Travel> travels = database.view("travel/by_id")
                .includeDocs(true)
                .query(Travel.class);
        return travels;
    }

    public List<Travel> findAllByUser(User user) {
        List<Travel> travels = database.view("travel/by_user")
                .includeDocs(true)
                .key(user.getId())
                .query(Travel.class);
        return travels;
    }

    public List<Travel> findByExample(Travel t){
        Gson json = new Gson();
        JsonElement query = json.toJsonTree(t);
        ViewResult<JsonArray, JsonObject, JsonObject> vr = database.view("travel/by_fields")
                .includeDocs(true)
                .key(t.getOrigin(), t.getDestination(), t.getApertureDate(), t.getArrivalDate())
                .queryView(JsonArray.class, JsonObject.class, JsonObject.class);
        return null;
    }

    public List<Travel> findByOriginAndDestination(String origin, String destination) {
        List<Travel> vr = database.view("travel/by_origin_and_destination")
                .includeDocs(true)
                .key(origin, destination)
                .query(Travel.class);
        return vr;
    }
}
