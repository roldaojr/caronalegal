package br.edu.ifrn.tads.caronas.data;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.lightcouch.CouchDbClientAndroid;
import org.lightcouch.CouchDbProperties;

import java.lang.reflect.Type;

public class Database extends CouchDbClientAndroid {

    public Database(CouchDbProperties properties) {
        super(properties);
    }

    private static Database database;

    public static Database getInstance() {
        if(database == null) {
            //try {
            CouchDbProperties props = new CouchDbProperties()
                    .setDbName("carona_legal")
                    .setCreateDbIfNotExist(false)
                    .setProtocol("http")
                    .setPort(5984)
                    .setHost("192.168.16.218")
                    .setMaxConnections(100)
                    .setConnectionTimeout(5000);
            database = new Database(props);
            //} catch (CouchDbException e) {
            //    Log.e(App.TAG, "Não foi possível se conectar ao servidor");
            //}

            //database.setGsonBuilder(getGsonBuilder());
        }
        return database;
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Register Travel class serializar and deserializer
        gsonBuilder.registerTypeAdapter(Travel.class, new JsonSerializer<Travel>() {
            public JsonElement serialize(Travel src, Type typeOfSrc,
                                         JsonSerializationContext context) {
            return new JsonPrimitive(src.getId());
            }
        });

        gsonBuilder.registerTypeAdapter(Travel.class, new JsonDeserializer<Travel>() {
            public Travel deserialize(JsonElement json, Type typeOfT,
                                        JsonDeserializationContext context) throws JsonParseException {
            return database.find(Travel.class, json.getAsJsonPrimitive().getAsString());
            }
        });

        gsonBuilder.registerTypeAdapter(User.class, new JsonSerializer<User>() {
            public JsonElement serialize(User src, Type typeOfSrc,
                                         JsonSerializationContext context) {
            return new JsonPrimitive(src.getId());
            }
        });

        gsonBuilder.registerTypeAdapter(User.class, new JsonDeserializer<User>() {
            public User deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context) throws JsonParseException {
            return database.find(User.class, json.getAsJsonPrimitive().getAsString());
            }
        });

        return gsonBuilder;
    }
}
