package br.edu.ifrn.tads.caronas.data;

import com.google.gson.annotations.SerializedName;

import org.lightcouch.Document;
import java.io.Serializable;

public class Entity implements Serializable {
    protected String docType;
    @SerializedName("_id")
    private String id;
    @SerializedName("_rev")
    private String revision;

    public Entity() {
        docType = this.getClass().getSimpleName();
    }

    public Entity(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entity other = (Entity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
