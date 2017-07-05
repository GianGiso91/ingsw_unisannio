package unisannio.ingsoft.bbm.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Arrays;

@Entity
public class Brewery {
  @Id
  private String idbrewery;
  private String place;
  private String webaddress;
  private GeoPt geopt;
  private String[] beers;

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
    return Arrays.copyOf(beers, beers.length);
  }

  public void setBeers(String... beers) {
    this.beers = Arrays.copyOf(beers, beers.length);
  }

}
