package br.edu.ifrn.tads.caronas.data;

import android.util.Log;

import com.google.gson.Gson;

import br.edu.ifrn.tads.caronas.App;

public class EntityDAO<T> {
    Database database;

    private Class<T> daoType = null;

    public EntityDAO(Class<T> daoType) {
        database = Database.getInstance();
        this.daoType = daoType;
    }

    public void save(T obj) {
        database.save(obj);
    }

    public void update(T obj) {
        database.update(obj);
    }

    public void delete(T obj) {
        database.remove(obj);
    }

    public T get(String id) {
        return database.find(daoType, id);
    }

}
