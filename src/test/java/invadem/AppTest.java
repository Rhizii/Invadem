package invadem;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.BeforeClass;

import processing.core.PImage;
import processing.core.PApplet;

import java.lang.String;
import static org.junit.Assert.*;


public class AppTest extends App{
  public static App lastInstance;
  App app;

  public AppTest() {
    lastInstance = this;
  }

  @Before
  public void testSetup() {
    PApplet construct = new AppTest();
    String[] args = {"App"};
    PApplet.runSketch(args, construct);
    // AppTest.lastInstance.setup();
    delay(3000);
    app = AppTest.lastInstance;
  }

  @Test
  public void testAppSetup() {
    assertNotNull(app.gameoverPng);
    assertNotNull(app.font);
    assertNotNull(app.tankProjSprites);
    assertNotNull(app.regularInvaderSprites);
    assertNotNull(app.armouredInvaderSprites);
    assertNotNull(app.powerInvaderSprites);
    assertNotNull(app.invaderExplosionSprites);
    assertNotNull(app.leftSprites);
    assertNotNull(app.rightSprites);
    assertNotNull(app.topSprites);
    assertNotNull(app.solidSprites);
    assertNotNull(app.tank);
    assertNotNull(app.barrierCentres);
    assertNotNull(app.barriers);
  }

// tests the random firing of invaders - includes diffferent scenarioes for different levels
  @Test
  public void testRandomInvaderFire() {
    // for level = 1
    app.level = 1;
    app.frameCount = 300;
    int prevInvaderProjSize = app.invaderProjs.size();
    app.randomInvaderFire();
    assertEquals(app.invaderProjs.size(), (prevInvaderProjSize+1));

    // when level = 5
    app.level = 5;
    app.frameCount = 480;
    prevInvaderProjSize = app.invaderProjs.size();
    app.randomInvaderFire();
    assertEquals(app.invaderProjs.size(), (prevInvaderProjSize+1));

    // test for levels greater than 5 - ensures that the rate of fire doesnt go beyong 1proj/sec
    app.level = 12;
    app.frameCount = 6000;
    prevInvaderProjSize = app.invaderProjs.size();
    app.randomInvaderFire();
    assertEquals(app.invaderProjs.size(), (prevInvaderProjSize+1));

    // if invaders list is empty check
    app.invaders.removeAll(app.invaders);
    app.level = 2;
    app.frameCount = 240;
    prevInvaderProjSize = app.invaderProjs.size();
    app.randomInvaderFire();
    assertEquals(app.invaderProjs.size(), prevInvaderProjSize);
  }

// checks the if the method clears the relevant lists
  @Test
  public void testAppGoToNextLevel() {
    app.nextLevelCount = 1;
    app.gotoNextLevel();

    assertEquals(app.nextLevelCount, 2);

    app.nextLevelCount = 239;
    app.gotoNextLevel();

    assertEquals(app.nextLevelCount, 0);
    assertFalse(app.dispNextLevel);
  }

  // checks the branch when the invaders reach the barriers - endGame()
  @Test
  public void testAppGameEndCheck1() {
    app.gameEndCheck();
    assertFalse(app.gameover);

    app.invaders = new ArrayList<Invader>(Arrays.asList(
                  new RegularInvader(app.regularInvaderSprites,
                              200, 450, 16, 16, new int[] {0,0}, 1)));
    app.gameEndCheck();
    assertTrue(app.gameover);
  }

// checks the branch when the tank is dead - endGame()
  @Test
  public void testAppGameEndCheck2() {
    app.tank.hit();
    app.tank.hit();
    app.tank.hit();
    app.gameEndCheck();
    assertTrue(app.gameover);
  }

// tests if the endGame() method works properly - includes both cases when the current score is higher and lower than the highscore
  @Test
  public void testAppEndGame() {
    app.currentScore = 500;
    app.endGame();
    assertTrue(app.gameover);
    assertTrue(app.invaders.size() == 0);
    assertTrue(app.invaderProjs.size() == 0);
    assertTrue(app.tankProjs.size() == 0);
    assertTrue(app.highscore == 10000);

    app.nextLevelReset();
    app.currentScore = 15000;
    app.endGame();
    assertTrue(app.gameover);
    assertTrue(app.invaders.size() == 0);
    assertTrue(app.invaderProjs.size() == 0);
    assertTrue(app.tankProjs.size() == 0);
    assertTrue(app.highscore == 15000);
  }

// tests if the newGameReset() method works - clears relevant lists of game objects
  @Test
  public void testAppNewGameReset() {
      app.newGameReset();
      assertTrue(app.level == 1);
      assertTrue(app.gameover == false);
      assertFalse(app.dispNextLevel);
      assertTrue(app.currentScore == 0);
      assertTrue(app.invaders.size() != 0);
      assertTrue(app.barriers.size() != 0);
  }

// checks if user inputs are correctly processed
  @Test
  public void testAppKeyPressed() {
    app.keyCode = LEFT;
    app.keyPressed();
    assertTrue("left key not pressed", app.tank.getMovingLeft());

    app.keyCode = RIGHT;
    app.keyPressed();
    assertTrue("right key not pressed", app.tank.getMovingRight());

    app.keyCode = 32;
    app.keyPressed();
    assertTrue(app.spaceBarPressed);

    app.spaceBarPressed = false;
    app.keyCode = 31;
    app.keyPressed();
    assertFalse(app.spaceBarPressed);

    app.gameover = true;
    app.keyCode = ENTER;
    app.keyPressed();
    // condition which checks if the game as reset or not
    assertFalse(app.gameover);


    app.gameover = false;
    app.keyCode = ENTER;
    app.keyPressed();
    // condition which checks if the game as reset or not
    assertTrue(app.currentScore == 0);
    assertFalse(app.dispNextLevel);
  }

  // checks if user inputs are correctly processed
  @Test
  public void testAppKeyReleased() {
    app.keyCode = LEFT;
    app.keyReleased();
    assertFalse("left key not released", app.tank.getMovingLeft());

    app.keyCode = RIGHT;
    app.keyReleased();
    assertFalse("right key not released", app.tank.getMovingRight());

    app.keyCode = 32;
    app.keyReleased();
    assertFalse(app.spaceBarPressed);

    app.keyCode = 31;
    app.keyReleased();
    assertFalse(app.spaceBarPressed);
  }

// checks the collision method for tank and projectiles
  @Test
  public void appTestCheckTankProjCollisions() {
    // first part - collision between tank and normal projectile
    app.invaderProjs = new ArrayList<Projectile>(Arrays.asList(
                              new Projectile(app.invaderProjsprites, 330, 460, 1, 3,
                              new int[]{0,1}, 1)));
    app.checkTankProjCollisions();
    assertTrue(app.tank.getHealth() == 2);

    // second part - collision between tank and power projectile
    app.invaderProjs = new ArrayList<Projectile>(Arrays.asList(
                              new PowerProjectile(app.invaderProjsprites, 330, 460, 1, 3,
                              new int[]{0,1}, 3)));
    app.checkTankProjCollisions();
    assertTrue(app.tank.isDead());
  }

  @Test
  public void appTestCheckBarrierProjCollisions() {
    app.tankProjs = new ArrayList<Projectile>(Arrays.asList(
                        new Projectile(null, 28, 38, 1, 3, new int[] {0,1}, 1)
    ));

    int[] c = new int[] {20, 30};
    int[] bpVelocity = new int[] {0,0};
    ArrayList<BarrierPart> barrierParts = new ArrayList<>();
    app.barriers.removeAll(app.barriers);

    barrierParts.addAll(Arrays.asList(
                new BarrierPart(null, c[0]-8, c[1]-8, 8, 8, bpVelocity, 3),
                new BarrierPart(null, c[0]+8, c[1]-8, 8, 8, bpVelocity, 3),
                new BarrierPart(null, c[0], c[1]-8, 8, 8, bpVelocity, 3),
                new BarrierPart(null, c[0]-8, c[1], 8, 8, bpVelocity, 3),
                new BarrierPart(null, c[0]+8, c[1], 8, 8, bpVelocity, 3),
                new BarrierPart(null, c[0]-8, c[1]+8, 8, 8, bpVelocity, 3),
                new BarrierPart(null, c[0]+8, c[1]+8, 8, 8, bpVelocity, 3)));

    app.barriers.add(new Barrier(barrierParts));

    app.checkBarrierProjCollisions();
    // checks the health if the 'last barrierpart' initialised above
    assertTrue(app.barriers.get(0).getParts().get(6).getHealth() == 2);
  }

  @Test
  public void appTestCheckInvaderProjCollisions() {
    // first part  - collision between the invader and projectile
    app.tankProjs = new ArrayList<Projectile>(Arrays.asList(
                        new Projectile(null, 200, 450, 1, 3, new int[] {0,1}, 1)
    ));

    app.invaders = new ArrayList<Invader>(Arrays.asList(
                  new RegularInvader(null, 200, 450, 16, 16, new int[] {0,0}, 1)));

    app.currentScore = 0;
    app.checkInvaderProjCollisions();
    assertTrue(app.currentScore == 100);

// second part - collision between teh invadera and power projectile
    app.tankProjs = new ArrayList<Projectile>(Arrays.asList(
                        new Projectile(null, 210, 210, 1, 3, new int[] {0,1}, 1)
    ));

    app.invaders = new ArrayList<Invader>(Arrays.asList(
                  new PowerInvader(null, 210, 210, 16, 16, new int[] {0,0}, 1)));

    app.currentScore = 0;
    app.checkInvaderProjCollisions();
    assertTrue(app.currentScore == 250);

  // third part - collision doesnt occur
  app.tankProjs = new ArrayList<Projectile>(Arrays.asList(
                      new Projectile(null, 230, 210, 1, 3, new int[] {0,1}, 1)
  ));

  app.invaders = new ArrayList<Invader>(Arrays.asList(
                new PowerInvader(null, 210, 210, 16, 16, new int[] {0,0}, 1)));

  app.currentScore = 0;
  app.checkInvaderProjCollisions();
  assertTrue(app.currentScore == 0);


  }
}
