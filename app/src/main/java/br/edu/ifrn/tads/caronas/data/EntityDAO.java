package br.edu.ifrn.tads.caronas.data;

import android.util.Log;

import com.google.gson.Gson;

import br.edu.ifrn.tads.caronas.App;

public class EntityDAO<T extends Entity> {
    Database database;

    private Class<T> daoType = null;

    public EntityDAO(Class<T> daoType) {
        database = Database.getInstance();
        this.daoType = daoType;
    }

    public void save(Entity obj) {
        database.save(obj);
    }

    public void update(Entity obj) {
        database.update(obj);
    }

    public void delete(Entity obj) {
        database.remove(obj);
    }

    public void saveOrUpdate(Entity obj) {
        if(obj.getId() == null || obj.getId().isEmpty()) {
            save(obj);
        } else {
            update(obj);
        }
    }
    public T get(String id) {
        return database.find(daoType, id);
    }

}
