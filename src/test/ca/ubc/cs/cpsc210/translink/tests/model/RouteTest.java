package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RouteTest {
    private Route r;
    private Stop s;

    @BeforeEach
    public void setup(){
        r = new Route("25");
        s = new Stop(20, "Test Stop", new LatLon(100,50));
    }

    @Test
    public void testConstructor(){
        r = new Route("25");
        assertEquals(r.getNumber(), "25");
        assertEquals(r.getName(), "");
        assertEquals(r.getPatterns().size(), 0);
        assertEquals(r.getStops().size(), 0);
    }

    @Test
    public void testSetName(){
        assertEquals(r.getName(), "");
        r.setName("TestName");
        assertEquals(r.getName(), "TestName");
    }

    @Test
    public void testAddStop(){
        assertFalse(r.hasStop(s));
        assertFalse(s.getRoutes().contains(r));
        assertEquals(r.getStops().size(), 0);
        r.addStop(s);
        assertTrue(r.hasStop(s));
        assertTrue(s.getRoutes().contains(r));
        assertEquals(r.getStops().size(), 1);

        // Add same stop again
        r.addStop(s);
        assertTrue(r.hasStop(s));
        assertTrue(s.getRoutes().contains(r));
        assertEquals(r.getStops().size(), 1);
    }

    @Test
    public void testHasStop(){
        assertEquals(r.getStops().size(), 0);
        assertFalse(r.hasStop(s));
        r.addStop(s);
        assertEquals(r.getStops().size(), 1);
        assertTrue(r.hasStop(s));
        Stop s2 = new Stop(40, "NewStop", new LatLon(10, 100));
        assertFalse(r.hasStop(s2));

    }

    @Test
    public void testRemoveStop(){
        assertEquals(r.getStops().size(), 0);
        r.addStop(s);
        assertEquals(r.getStops().size(), 1);

        Stop s2 = new Stop(40, "NewStop", new LatLon(10, 100));
        assertFalse(r.hasStop(s2));
        r.removeStop(s2);
    }

    @Test
    public void testEquals(){
        assertTrue(r.equals(r));
        assertFalse(r.equals(null));
        assertFalse(r.equals(s));

        Route r2 = new Route("25");
        r.setName("SameRoute");
        assertTrue(r.equals(r2));

        Route r3 = new Route("24");
        r.setName("DifferentRoute");
        assertFalse(r.equals(r3));
    }

    @Test
    public void testHashcode(){
        Route r2 = new Route("25");
        r.setName("SameRoute");
        assertEquals(r.hashCode(), r2.hashCode());
    }

    @Test
    public void testAddPattern(){
        // Add first item
        assertEquals(r.getPatterns().size(), 0);
        RoutePattern rp = new RoutePattern("TestPattern", "UBC", "EB", r);
        r.addPattern(rp);
        assertEquals(r.getPatterns().size(), 1);
        assertTrue(r.getPatterns().contains(rp));

        // Add duplicate item
        assertEquals(r.getPatterns().size(),1);
        RoutePattern rp2 = new RoutePattern("TestPattern", "Downtown", "WB", r);
        r.addPattern(rp2);
        assertEquals(r.getPatterns().size(), 1);
        assertTrue(r.getPatterns().contains(rp2));


        // Add new item
        assertEquals(r.getPatterns().size(),1);
        RoutePattern rp3 = new RoutePattern("NewTestPattern", "Waterfront", "NB", r);
        r.addPattern(rp3);
        assertEquals(r.getPatterns().size(), 2);
        assertTrue(r.getPatterns().contains(rp3));
    }

    @Test
    public void testGetPatternV1(){
        assertEquals(r.getPatterns().size(), 0);
        RoutePattern rp = r.getPattern("Pattern1", "UBC", "WB");
        assertEquals(r.getPatterns().size(), 1);
        assertEquals(rp.getName(), "Pattern1");
        assertEquals(rp.getDestination(), "UBC");
        assertEquals(rp.getDirection(), "WB");

        RoutePattern rp1 = r.getPattern("Pattern2", "Downtown", "NB");
        assertEquals(r.getPatterns().size(), 2);
        assertEquals(rp1.getName(), "Pattern2");
        assertEquals(rp1.getDestination(), "Downtown");
        assertEquals(rp1.getDirection(), "NB");

        RoutePattern rp3 = r.getPattern("Pattern1", "Jericho", "EB");
        assertEquals(r.getPatterns().size(), 2);
        assertEquals(rp3.getName(), "Pattern1");
        assertEquals(rp3.getDestination(), "Jericho");
        assertEquals(rp3.getDirection(), "EB");
    }

    @Test
    public void testGetPatternV2(){
        assertEquals(r.getPatterns().size(), 0);
        RoutePattern rp = r.getPattern("Pattern1");
        assertEquals(r.getPatterns().size(), 1);
        assertEquals(rp.getName(), "Pattern1");
        assertEquals(rp.getDestination(), "");
        assertEquals(rp.getDirection(), "");

        RoutePattern rp1 = r.getPattern("Pattern2");
        assertEquals(r.getPatterns().size(), 2);
        assertEquals(rp1.getName(), "Pattern2");
        assertEquals(rp1.getDestination(), "");
        assertEquals(rp1.getDirection(), "");

        RoutePattern rp3 = r.getPattern("Pattern1");
        assertEquals(r.getPatterns().size(), 2);
        assertEquals(rp3.getName(), "Pattern1");
        assertEquals(rp3.getDestination(), "");
        assertEquals(rp3.getDirection(), "");
    }
}
