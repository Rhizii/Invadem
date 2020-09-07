package invadem;

import java.util.ArrayList;
import java.lang.IndexOutOfBoundsException;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

import processing.core.PApplet;
import processing.core.PImage;

import static org.junit.Assert.*;

public class ExplosionTest extends App {
  Explosion e;
  TankExplosion te;
  public static App lastInstance;

  public ExplosionTest() {
    lastInstance = this;
  }

  @Before
  public void explosionTestSetup() {
    e = new Explosion(null, 50, 50);
  }

// tests the Explosion constructor
  @Test
  public void testExplosionConstructor() {
    assertNotNull(e);
  }


// tests the explode() and explosionOver() method
  @Test
  public void testExplosionExplodeExplosionOver() throws IndexOutOfBoundsException {
    PApplet construct = new AppTest();
    String[] args = {"App"};
    PApplet.runSketch(args, construct);
    // AppTest.lastInstance.setup();
    delay(3000);
    App app = AppTest.lastInstance;


// testing for the Explosion Class's explode() method
    e = new Explosion(app.invaderExplosionSprites, 50, 50);
    e.explode(app);
    assertFalse(e.isExplosionOver());
    assertEquals(e.getState(), 1);

    e.explode(app);
    assertEquals(e.getState(), 2);

    for (int i = 0; i < 6; i++) {
      e.explode(app);
    }

    assertTrue(e.isExplosionOver());


    // testing the TankExplosion class's explode() method
    te = new TankExplosion(app.tankExplosionSprites, 50, 50);
    te.explode(app);
    assertEquals(te.getState(), 1);
    te.explode(app);
    assertEquals(te.getState(), 2);
    assertFalse(te.isExplosionOver());

    for (int i = 0; i < 12; i++) {
      te.explode(app);
    }
    assertTrue(te.isExplosionOver());
  }
}
