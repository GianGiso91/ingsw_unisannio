package unisannio.ingsoft.bbm.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Antonio on 14/05/2017.
 */
@Entity
public class Beer {
        @Id
        String nameID;
        String fermentazione;

        public String getNameID() {
            return nameID;
        }

        public void setNameID(String id) {
            this.nameID = id;
        }

        public String getFermentazione() {
            return fermentazione;
        }

        public void setFermentazione(String ferm) {
            this.fermentazione = ferm;
        }
}
