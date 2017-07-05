package unisannio.ingsoft.bbm.backend; /**
 * Created by gianluca on 19/05/2017.
 */
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestBeer extends TestCase {
   private Beer dummyBeer;

    @Before
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

    @Test
    public void testIdbeer(){
        assertEquals(this.dummyBeer.getIdbeer(),"Leffe");
        this.dummyBeer.setIdbeer("Chimay");
        assertEquals(this.dummyBeer.getIdbeer(), "Chimay");
    }

    @Test
    public void testFermentation(){
        assertEquals(this.dummyBeer.getFermentation(), "High");
        this.dummyBeer.setFermentation("Low");
        assertEquals(this.dummyBeer.getFermentation(), "Low");
    }

    @Test
    public void testWebaddress(){
        assertEquals(this.dummyBeer.getWebaddress(),"example.com");
        this.dummyBeer.setWebaddress("example2.com");
        assertEquals(this.dummyBeer.getWebaddress(), "example2.com");
    }

    @Test
    public void testBrewery(){
        assertEquals(this.dummyBeer.getBrewery(),"Sapori Longobardi");
        this.dummyBeer.setBrewery("Pippo");
        assertEquals(this.dummyBeer.getBrewery(), "Pippo");
    }

    @Test
    public void testGlass(){
        assertEquals(this.dummyBeer.getGlass(),"highball");
        this.dummyBeer.setGlass("Mug");
        assertEquals(this.dummyBeer.getGlass(), "Mug");
    }

    @Test
    public void testType(){
        assertEquals(this.dummyBeer.getType(),"PaleAle");
        this.dummyBeer.setType("Witbier");
        assertEquals(this.dummyBeer.getType(), "Witbier");
    }

    @Test
    public void testVolume(){
        assertEquals(this.dummyBeer.getVolume(),4.5f);
        this.dummyBeer.setVolume(4.3f);
        assertEquals(this.dummyBeer.getVolume(), 4.3f);
    }

    @Test
    public void testColor(){
        assertEquals(this.dummyBeer.getColor(),"Blonde");
        this.dummyBeer.setColor("Rouge");
        assertEquals(this.dummyBeer.getColor(), "Rouge");
    }

    @After
    protected void tearDown()throws Exception{
        assertNotNull(this.dummyBeer);
    }
}
