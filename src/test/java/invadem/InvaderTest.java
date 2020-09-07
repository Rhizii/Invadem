package invadem;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.lang.String;


import processing.core.PImage;
import processing.core.PApplet;


public class InvaderTest extends App {

    public static App lastInstance;

    RegularInvader regInvader;
    PowerInvader powInvader;
    ArmouredInvader armInvader;

    public InvaderTest() {
      lastInstance = this;
    }

    @Before
    public void setup() {
      regInvader = new RegularInvader(
                null,
                0, 100,
                16, 16, new int[]{0,0}, 1);

      powInvader = new PowerInvader(
            null,
            20, 100,
            16, 16, new int[]{0,0}, 1);

      armInvader = new ArmouredInvader(
            null,
            40, 100,
            16, 16, new int[]{0,0}, 3);

    }

// tests the Invader constructors for each subtype
   @Test
   public void testInvaderConstruction() {
       assertNotNull("Regular Invader Constructor broken", regInvader);
       assertNotNull("PowerInvader Constructor broken", powInvader);
       assertNotNull("ArmouredInvader Constructor broken", armInvader);
   }


// tests the fire() method
   @Test
   public void testInvaderFire() {
     assertNotNull(regInvader.fire());
     assertNotNull(powInvader.fire());
     assertNotNull(armInvader.fire());
   }

// tests if the invader moves according to the specification - ensures that all cases are covered
   @Test
   public void testInvaderMoveAndDraw() {
     PApplet construct = new AppTest();
     String[] args = {"App"};
     PApplet.runSketch(args, construct);
     // AppTest.lastInstance.setup();
     delay(3000);
     App app = AppTest.lastInstance;

     Invader i = new RegularInvader(app.regularInvaderSprites,
                          40, 10, 16, 16,
                          new int[]{0,0}, 1);

     i.moveAndDraw(app);
     assertEquals(i.getMovingDownCount(), 1);
     for (int j = 0; j < 7; j++) {
       i.draw(app);
       i.moveAndDraw(app);
     }

     assertEquals(i.getMovingDownCount(), 0);
     assertFalse(i.getIsMovingDown());
     i.draw(app);

     i.moveAndDraw(app);
     assertTrue(i.getIsMovingLeft());
     assertEquals(i.getMovingSideCount(), 1);

     for (int j = 0; j < 30; j++) {
       i.draw(app);
       i.moveAndDraw(app);
     }

     assertEquals(i.getMovingSideCount(), 0);
     assertTrue(i.getIsMovingDown());
     assertFalse(i.getIsMovingLeft());



   }

}
