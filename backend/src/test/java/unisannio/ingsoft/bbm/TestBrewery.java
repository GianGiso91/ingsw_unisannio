package unisannio.ingsoft.bbm;

import com.google.appengine.api.datastore.GeoPt;

import junit.framework.TestCase;

import org.junit.Assert;

import unisannio.ingsoft.bbm.backend.Brewery;

/**
 * Created by gianluca on 19/05/2017.
 */

public class TestBrewery extends TestCase {

    private Brewery dummyBrewery;

    @Override
    protected void setUp() throws Exception{
        this.dummyBrewery = new Brewery();
        String[] beers = {"Leffe Blonde", "Leffe Rouge"};
        this.dummyBrewery.setBeers(beers);
        this.dummyBrewery.setIdbrewery("Leffe");
        this.dummyBrewery.setPlace("Bruxelles");
        float lat = 3.4f;
        float log = 3.1f;
        this.dummyBrewery.setGeopt(new GeoPt(lat, log));
        this.dummyBrewery.setWebaddress("example.com");
    }

    public void testIdbrewery(){
        assertEquals(this.dummyBrewery.getIdbrewery(),"Leffe");
        this.dummyBrewery.setIdbrewery("Chimay");
        assertEquals(this.dummyBrewery.getIdbrewery(), "Chimay");
    }

    public void testPlace(){
        assertEquals(this.dummyBrewery.getPlace(),"Bruxelles");
        this.dummyBrewery.setPlace("Antwerp");
        assertEquals(this.dummyBrewery.getPlace(), "Antwerp");
    }
    public void testBeers(){
        String [] beers={"Leffe Blonde", "Leffe Rouge"};
        Assert.assertArrayEquals(this.dummyBrewery.getBeers(),beers);
        String[] beers2 = {"Peroni", "Heineken"};
        this.dummyBrewery.setBeers(beers2);
        Assert.assertArrayEquals(this.dummyBrewery.getBeers(), beers2);
    }
    public void testGeoPt(){
        assertEquals(this.dummyBrewery.getGeopt().getLatitude(),3.4f);
        assertEquals(this.dummyBrewery.getGeopt().getLongitude(),3.1f);
        this.dummyBrewery.setGeopt(new GeoPt(2.2f,2.5f));
        assertEquals(this.dummyBrewery.getGeopt().getLatitude(),2.2f);
        assertEquals(this.dummyBrewery.getGeopt().getLongitude(),2.5f);
    }
    public void testWebAddress(){
        assertEquals(this.dummyBrewery.getWebaddress(),"example.com");
        this.dummyBrewery.setWebaddress("example2.com");
        assertEquals(this.dummyBrewery.getWebaddress(),"example2.com");
    }

    @Override
    protected void tearDown()throws Exception{
        this.dummyBrewery = null;
        assertNull(this.dummyBrewery);
    }
}
