package unisannio.ingsoft.bbm.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Beer {
  @Id
  String idbeer;
  String type;
  String brewery;
  String webaddress;
  String fermentation;
  float volume;
  String color;
  int ibu;
  String glass;

  public String getIdbeer() {
    return idbeer;
  }

  public void setIdbeer(String idbeer) {
    this.idbeer = idbeer;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBrewery() {
    return brewery;
  }

  public void setBrewery(String brewery) {
    this.brewery = brewery;
  }

  public String getWebaddress() {
    return webaddress;
  }

  public void setWebaddress(String webaddress) {
    this.webaddress = webaddress;
  }

  public String getFermentation() {
    return fermentation;
  }

  public void setFermentation(String fermentation) {
    this.fermentation = fermentation;
  }

  public float getVolume() {
    return volume;
  }

  public void setVolume(float volume) {
    this.volume = volume;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public int getIbu() {
    return ibu;
  }

  public void setIbu(int ibu) {
    this.ibu = ibu;
  }

  public String getGlass() {
    return glass;
  }

  public void setGlass(String glass) {
    this.glass = glass;
  }

}
