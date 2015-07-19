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
        View dbview = database.view("ride_request/by_travel_and_user")
                .includeDocs(true)
                .key(t.getId(), u.getId());
        ViewResult<JsonArray, JsonObject, RideRequest> vr = dbview.queryView(JsonArray.class, JsonObject.class, RideRequest.class);
        for(ViewResult<JsonArray, JsonObject, RideRequest>.Rows v: vr.getRows()) {
            return v.getDoc();
        }
        return null;
    }
}
