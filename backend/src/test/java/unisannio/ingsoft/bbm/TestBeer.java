package unisannio.ingsoft.bbm; /**
 * Created by gianluca on 19/05/2017.
 */
import junit.framework.TestCase;

import unisannio.ingsoft.bbm.backend.Beer;


public class TestBeer extends TestCase {
   private Beer dummyBeer;

    @Override
    protected void setUp() throws Exception{
        this.dummyBeer= new Beer();
        this.dummyBeer.setIdbeer("Leffe");
        this.dummyBeer.setFermentation("High");
        this.dummyBeer.setWebaddress("example.com");
        this.dummyBeer.setBrewery("Sapori Longobardi");
        this.dummyBeer.setGlass("highball");
        this.dummyBeer.setIbu(5);
        this.dummyBeer.setType("PaleAle");
        this.dummyBeer.setVolume(4.5f);
        this.dummyBeer.setColor("Blonde");

    }

    public void testIdbeer(){
        assertEquals(this.dummyBeer.getIdbeer(),"Leffe");
        this.dummyBeer.setIdbeer("Chimay");
        assertEquals(this.dummyBeer.getIdbeer(), "Chimay");
    }

    public void testFermentation(){
        assertEquals(this.dummyBeer.getFermentation(), "High");
        this.dummyBeer.setFermentation("Low");
        assertEquals(this.dummyBeer.getFermentation(), "Low");
    }

    public void testWebaddress(){
        assertEquals(this.dummyBeer.getWebaddress(),"example.com");
        this.dummyBeer.setWebaddress("example2.com");
        assertEquals(this.dummyBeer.getWebaddress(), "example2.com");
    }

    public void testBrewery(){
        assertEquals(this.dummyBeer.getBrewery(),"Sapori Longobardi");
        this.dummyBeer.setBrewery("Pippo");
        assertEquals(this.dummyBeer.getBrewery(), "Pippo");
    }
    public void testGlass(){
        assertEquals(this.dummyBeer.getGlass(),"highball");
        this.dummyBeer.setGlass("Mug");
        assertEquals(this.dummyBeer.getGlass(), "Mug");
    }
    public void testType(){
        assertEquals(this.dummyBeer.getType(),"PaleAle");
        this.dummyBeer.setType("Witbier");
        assertEquals(this.dummyBeer.getType(), "Witbier");
    }
    public void testVolume(){
        assertEquals(this.dummyBeer.getVolume(),4.5f);
        this.dummyBeer.setVolume(4.3f);
        assertEquals(this.dummyBeer.getVolume(), 4.3f);
    }

    public void testColor(){
        assertEquals(this.dummyBeer.getColor(),"Blonde");
        this.dummyBeer.setColor("Rouge");
        assertEquals(this.dummyBeer.getColor(), "Rouge");
    }




    @Override
    protected void tearDown()throws Exception{
        this.dummyBeer = null;
        assertNull(this.dummyBeer);
    }
}
