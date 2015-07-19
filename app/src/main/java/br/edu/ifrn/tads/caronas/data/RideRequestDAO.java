package br.edu.ifrn.tads.caronas.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.lightcouch.View;
import org.lightcouch.ViewResult;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.tads.caronas.App;

public class RideRequestDAO extends EntityDAO<RideRequest> {
    public RideRequestDAO() {
        super(RideRequest.class);
    }

    public RideRequest getByTravelAndUser(Travel t, User u) {
        List<RideRequest> results = database.view("travel/by_user")
                .includeDocs(true)
                .key(t.getId(), u.getId())
                .query(RideRequest.class);
        if(results.size() > 0) {
            return results.get(0);
        } else {
            return null;
        }
    }
}
