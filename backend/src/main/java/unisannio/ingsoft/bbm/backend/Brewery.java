package unisannio.ingsoft.bbm.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Antonio on 19/05/2017.
 */

@Entity
public class Brewery {
    @Id
    private String idbrewery;
    private String place;
    private String webaddress;
    private GeoPt geopt;

    public String getIdbrewery() {
        return idbrewery;
    }

    public void setIdbrewery(String idbrewery) {
        this.idbrewery = idbrewery;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getWebaddress() {
        return webaddress;
    }

    public void setWebaddress(String webaddress) {
        this.webaddress = webaddress;
    }

    public GeoPt getGeopt() {
        return geopt;
    }

    public void setGeopt(GeoPt geopt) {
        this.geopt = geopt;
    }

    public String[] getBeers() {
        return beers;
    }

    public void setBeers(String[] beers) {
        this.beers = beers;
    }

    private String[] beers;
}
