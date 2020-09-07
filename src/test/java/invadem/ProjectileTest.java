package invadem;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

import processing.core.PImage;

import static org.junit.Assert.*;

public class ProjectileTest {

  Projectile proj;
  ArrayList<PImage> projSprites = new ArrayList<>();




  @Before
  public void projectileTestSetup() {
    proj = new Projectile(projSprites, 10, 10, 1, 3, new int[] {0,1}, 1);
  }

// tests the Projectile's constructor
   @Test
   public void testProjectileConstruction() {
       assertNotNull(proj);
   }

   // tests the horizontal and vertical movement of the projectile
   @Test
   public void testProjectileTick() {
     proj.tick();
     assertEquals(proj.y(), 11);

     for (int i = 0; i < 500; i++) {
       proj.tick();
     }

     assertEquals(proj.y(), 511);

     Projectile proj2 = new Projectile(projSprites, 10, 10, 1, 3, new int[] {1,2}, 1);
     proj2.tick();
     proj2.tick();

     assertEquals(proj2.y(), 14);
     assertEquals(proj2.x(), 12);
   }

// tests if the Projectile can attack Collideables
   @Test
   public void testProjectileAttack() {
     Collideable c = new Collideable(null, 20, 20, 14, 14, new int[] {0,0}, 4);
     proj.attack(c);
     assertTrue(c.getHealth() == 3);
   }

}
