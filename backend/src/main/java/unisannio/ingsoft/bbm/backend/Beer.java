package unisannio.ingsoft.bbm.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Antonio on 14/05/2017.
 */
@Entity
public class Beer {
        @Id
        String nameID;
        String tipo;
        String brewery;
        String indirizzoweb;
        String fermentazione;
        String grado;
        String colore;
        String IBU;
        String bicchiere;

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getIndirizzoweb() {
        return indirizzoweb;
    }

    public void setIndirizzoweb(String indirizzoweb) {
        this.indirizzoweb = indirizzoweb;
    }

    public String getFermentazione() {
        return fermentazione;
    }

    public void setFermentazione(String fermentazione) {
        this.fermentazione = fermentazione;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public String getIBU() {
        return IBU;
    }

    public void setIBU(String IBU) {
        this.IBU = IBU;
    }

    public String getBicchiere() {
        return bicchiere;
    }

    public void setBicchiere(String bicchiere) {
        this.bicchiere = bicchiere;
    }
}
