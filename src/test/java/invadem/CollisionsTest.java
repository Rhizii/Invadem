package invadem;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

import processing.core.PImage;

import static org.junit.Assert.*;

public class CollisionsTest {
  Collideable c1;
  Collideable c2;
  Collideable c3;
  Collideable c4;
  Projectile p1;
  PowerProjectile p2;
  int[] vel = new int[]{0,1};


  @Before
  public void collideableSetup() {
    c1 = new Collideable(null, 20, 20, 16, 16, vel, 3);
    c2 = new Collideable(null, 24, 30, 16, 16, vel, 2);
    c3 = new Collideable(null, 450, 450, 16, 16, vel, 1);
    c4 = new Collideable(null, 10, 10, 16, 16, vel, 3);

    p1 = new Projectile(null, 35, 35, 1, 3, vel, 1);
    p2 = new PowerProjectile(null, 32, 35, 2, 5, vel, 1);


  }

// Tests the Collideable constructor
  @Test
  public void testCollideableConstructor() {
    assertNotNull(c1);
  }

// tests the isDead() method
  @Test
  public void testCollideableIsDead() {
    c3.hit();
    assertTrue(c3.isDead());

    c2.hit();
    assertFalse(c2.isDead());
  }


// tests the collision detection method - collides()
  @Test
  public void testCollides() {
    assertTrue(Collideable.collides(c1, c2));
    assertTrue(Collideable.collides(c1, c4));
    assertFalse(Collideable.collides(c1, c3));
  }


// the following series of test, check if collisiions between different subtypes of Collideable can be detected
  @Test
  public void testCollisionTankProjectiles() {
    assertTrue(Collideable.collides(c1, p1));
    assertFalse(Collideable.collides(c3, p1));
  }

  @Test
  public void testCollisionAttackProj() {
    p1.attack(c1);
    assertEquals(c1.getHealth(), 2);

    p2.attack(c4);
    assertEquals(c4.getHealth(), 0);
  }
}
