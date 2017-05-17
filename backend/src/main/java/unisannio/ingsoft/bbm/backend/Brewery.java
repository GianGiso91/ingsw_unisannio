package unisannio.ingsoft.bbm.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


/**
 * Created by gianluca on 17/05/2017.
 */
@Entity
public class Brewery {
    @Id
    String nameBrewery;
    String posizione;
    GeoPt geoPt;
    String indirizzoweb;
    String[] birre;

    public String getNameBrewery() {
        return nameBrewery;
    }

    public String getPosizione() {
        return posizione;
    }

    public GeoPt getGeoPt() {
        return geoPt;
    }

    public String getIndirizzoweb() {
        return indirizzoweb;
    }

    public String[] getBirre() {
        return birre;
    }

    public void setNameBrewery(String nameBrewery) {
        this.nameBrewery = nameBrewery;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public void setGeoPt(GeoPt geoPt) {
        this.geoPt = geoPt;
    }

    public void setIndirizzoweb(String indirizzoweb) {
        this.indirizzoweb = indirizzoweb;
    }

    public void setBirre(String[] birre) {
        this.birre = birre;
    }

}