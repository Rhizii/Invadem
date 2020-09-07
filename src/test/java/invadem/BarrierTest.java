package invadem;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

import processing.core.PImage;

import static org.junit.Assert.*;

public class BarrierTest extends App {

  ArrayList<BarrierPart> barrierParts = new ArrayList<>();
  Barrier barrier;
  int[] vel = new int[] {0,0};
  @Before
  public void barrierPartSetup() {

    barrierParts.add (new BarrierPart(null, 10, 10, 8, 8, vel, 3));
    barrierParts.add (new BarrierPart(null, 18, 10, 8, 8, vel, 3));
    barrierParts.add (new BarrierPart(null, 26, 10, 8, 8, vel, 3));

  }

// tests the BarrierPart Constructor
  @Test
  public void testBarrierPartConstructor() {

    BarrierPart bp = new BarrierPart(null, 26, 50, 8, 8, vel, 3);
    assertNotNull(bp);
    assertNotNull(barrierParts);
  }

// test the Barrier constructor
  @Test
  public void testBarrierConstructor() {
    barrier = new Barrier(barrierParts);
    assertNotNull(barrier);
  }

// tests teh barrier part's hit method and checcks if it is dead or not certain scenarioes
  @Test
  public void testBarrierPartHit() {
    BarrierPart bp = new BarrierPart(null, 26, 50, 8, 8, vel, 3);
    bp.hit();
    assertEquals(bp.getHealth(), 2);
    assertFalse(bp.isDead());

    bp.hit();
    bp.hit();
    bp.hit();
    bp.hit();
    assertEquals(bp.getHealth(), 0);
    assertTrue(bp.isDead());
  }


// tests the getter method for getting the sprites
  @Test
  public void testBarrierPartGetSprites() {
    BarrierPart bp = new BarrierPart(null, 26, 50, 8, 8, vel, 3);
    assertEquals(bp.getSprites(), null);
  }

}
