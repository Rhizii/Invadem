package invadem;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

import processing.core.PImage;

import static org.junit.Assert.*;

public class TankTest {


  Tank tank;


  @Before
  public void setup() {
    tank = new Tank(
              null, 320, 450, 22, 16, new int[]{1,0}, 3);
  }

  // tests construction of the tank
   @Test
   public void testTankConstruction() {
     assertNotNull(tank);
   }

// tests the individual movement methods in the Tank class
   @Test
   public void testTankMoving() {
     tank.moveRight();
     assertEquals(tank.getMovingRight(), true);

     tank.moveLeft();
     assertEquals(tank.getMovingLeft(), true);

     tank.notMoveLeft();
     assertEquals(tank.getMovingLeft(), false);

     tank.notMoveRight();
     assertEquals(tank.getMovingRight(), false);
   }

// tests teh isDead() method
   @Test
   public void testTankIsDead() {
     tank.hit();
     tank.hit();
     tank.hit();
     assertTrue(tank.isDead());

     // health of the tank is different - 1 | edge case
     Tank tank2 = new Tank(
                      null, 320, 450, 22, 16, new int[]{1,0}, 1);
    tank2.hit();
    tank2.hit();
    tank2.hit();
    tank2.hit();

    assertTrue(tank2.isDead());
   }

   @Test
   public void testTankIsNotDead() {
     tank.hit();
     tank.hit();
     assertFalse(tank.isDead());
   }

// test the logic of the tick() method to ensure the tank moves appropiately
   @Test
   public void testTankMoveLeftRight() {
     tank.moveRight();
     tank.notMoveLeft();
     tank.tick();
     assertEquals(tank.x(), 321);
     tank.tick();
     tank.tick();
     tank.tick();
     assertEquals(tank.x(), 324);

     tank.moveLeft();
     tank.notMoveRight();
     tank.tick();
     tank.tick();
     assertEquals(tank.x(), 322);
   }

// tests if teh tank moves beyong the boundaries or not
  @Test
  public void testTankMovingOutsideBoundaries() {
    tank.moveRight();
    tank.notMoveLeft();

    for (int i = 0; i < 480; i++) {
      tank.tick();
    }
    assertEquals(tank.x(), 460);

    tank.moveLeft();
    tank.notMoveRight();

    for (int i = 0; i < 480; i++) {
      tank.tick();
    }

    assertEquals(tank.x(), 180);
  }

// tests if the add method works appropiately
  @Test
  public void testTankAddProjSprites() {
    ArrayList<PImage> tankProjSprites = new ArrayList<>();
    tankProjSprites.add(null);
    tank.addProjSprites(tankProjSprites);
    assertNotNull(tankProjSprites);
  }

// tests the fire method - it returns a projectile object
  @Test
  public void testTankFire() {
    assertNotNull(tank.fire());
  }


// testing the setHealth() method
  @Test
  public void testTankSetHealth() {
    tank.setHealth(5);
    assertTrue(tank.getHealth() == 5);
  }

}
