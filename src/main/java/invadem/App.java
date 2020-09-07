package invadem;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.lang.*;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
// import processing.sound.*;





public class App extends PApplet {

    Tank tank;

    // Projectile lists
    ArrayList<Projectile> tankProjs = new ArrayList<>();
    ArrayList<Projectile> invaderProjs = new ArrayList<>();

// invader lists
    ArrayList<Invader> invaders = new ArrayList<>();

// Barrier setup
    ArrayList<Barrier> barriers = new ArrayList<>();
    ArrayList<int[]> barrierCentres = new ArrayList<int[]>();

// explosions list
    ArrayList<Explosion> explosions = new ArrayList<>();


// barrier parts sprites
    ArrayList<PImage> leftSprites = new ArrayList<>();
    ArrayList<PImage> rightSprites = new ArrayList<>();
    ArrayList<PImage> topSprites = new ArrayList<>();
    ArrayList<PImage> solidSprites = new ArrayList<>();
    int[] bpVelocity = new int[] {0,0};
    // barriers dont move


    ArrayList<PImage> tankSprites = new ArrayList<>();
    ArrayList<PImage> tankProjSprites = new ArrayList<>();
    ArrayList<PImage> invaderProjsprites = new ArrayList<>();


// sprites for all the invaders
    ArrayList<PImage> regularInvaderSprites = new ArrayList<>();
    ArrayList<PImage> armouredInvaderSprites = new ArrayList<>();
    ArrayList<PImage> powerInvaderSprites = new ArrayList<>();
    ArrayList<PImage> powerInvaderProjSprites = new ArrayList<>();

// sprites for the explosions
    ArrayList<PImage> invaderExplosionSprites = new ArrayList<>();
    ArrayList<PImage> tankExplosionSprites = new ArrayList<>();

    PFont font;
    PImage gameoverPng;

    int frameCount = 0;
    // nextLevelCount counts the frames to check how long the next level png should be displayed
    int nextLevelCount = 0;
    // dispNextLevel is a boolean which determines whether to displat the next level png or not
    boolean dispNextLevel = false;
    boolean gameover = false;
    int level = 1;
    int currentScore = 0;
    int highscore = 10000;
    // used to ensure that you cannot fire a continious stream of projectiles
    boolean spaceBarPressed;


    public App() {

        //Set up your objects
    }

    public void setup() {
        frameRate(60);


        int fontSize = 8;
        font = createFont("PressStart2P-Regular.ttf", fontSize);
        textFont(font);
        gameoverPng = loadImage("gameover.png");


        // Loading Tank Sprites
        tankSprites.add(loadImage("tank1.png"));
        tankProjSprites.add(loadImage("projectile.png"));
        tankSetup();


        // Loading Invader Sprites
        regularInvaderSprites.add(loadImage("invader1.png"));
        regularInvaderSprites.add(loadImage("invader2.png"));

        armouredInvaderSprites.add(loadImage("invader1_armoured.png"));
        armouredInvaderSprites.add(loadImage("invader2_armoured.png"));

        powerInvaderSprites.add(loadImage("invader1_power.png"));
        powerInvaderSprites.add(loadImage("invader2_power.png"));

        invaderProjsprites.add(loadImage("projectile.png"));
        powerInvaderProjSprites.add(loadImage("projectile_lg.png"));

        invaderSetup();


        // Loading Invader Explosion Sprites
        for (int i = 1; i < 8; i++) {
          invaderExplosionSprites.add(loadImage("explosion" + i + ".png"));
        }

        // Loading Tank Explosion Sprites
        for (int i = 1; i < 14; i++) {
          tankExplosionSprites.add(loadImage("tankExplosion" + i + ".png"));
        }



        // Loading barrier sprites
        leftSprites.add(loadImage("barrier_left3.png"));
        leftSprites.add(loadImage("barrier_left2.png"));
        leftSprites.add(loadImage("barrier_left1.png"));
        rightSprites.add(loadImage("barrier_right3.png"));
        rightSprites.add(loadImage("barrier_right2.png"));
        rightSprites.add(loadImage("barrier_right1.png"));
        topSprites.add(loadImage("barrier_top3.png"));
        topSprites.add(loadImage("barrier_top2.png"));
        topSprites.add(loadImage("barrier_top1.png"));
        solidSprites.add(loadImage("barrier_solid3.png"));
        solidSprites.add(loadImage("barrier_solid2.png"));
        solidSprites.add(loadImage("barrier_solid1.png"));

        barrierSetup();
    }

// creates a new tank object at its starting location
    public void tankSetup() {
      tank = new Tank(
                tankSprites, 320, 450, 22, 16, new int[]{1,0}, 3);

      tank.addProjSprites(tankProjSprites);
    }

// creates 3 barriers at their starting locations
    public void barrierSetup() {
      // different centres for each barrier
      barrierCentres.add(new int[] {215, 430});
      barrierCentres.add(new int[] {325, 430});
      barrierCentres.add(new int[] {435, 430});

      int[] c;
      for (int i = 0; i < barrierCentres.size(); i++){
        ArrayList<BarrierPart> barrierParts = new ArrayList<>();
        c = barrierCentres.get(i);


        barrierParts.addAll(Arrays.asList(
                    new BarrierPart(leftSprites, c[0]-8, c[1]-8, 8, 8, bpVelocity, 3),
                    new BarrierPart(rightSprites, c[0]+8, c[1]-8, 8, 8, bpVelocity, 3),
                    new BarrierPart(topSprites, c[0], c[1]-8, 8, 8, bpVelocity, 3),
                    new BarrierPart(solidSprites, c[0]-8, c[1], 8, 8, bpVelocity, 3),
                    new BarrierPart(solidSprites, c[0]+8, c[1], 8, 8, bpVelocity, 3),
                    new BarrierPart(solidSprites, c[0]-8, c[1]+8, 8, 8, bpVelocity, 3),
                    new BarrierPart(solidSprites, c[0]+8, c[1]+8, 8, 8, bpVelocity, 3)
          ));
          barriers.add(new Barrier(barrierParts));
      }
    }


// sets up 40 invaders in their starting locations
    public void invaderSetup() {
      int startingX = 180;
      int startingY = 80;


      for (int i = 0; i < 10; i++){
        for (int j = 0; j < 4; j++){
          if (j == 0){
            ArmouredInvader armInv = new ArmouredInvader(
                                armouredInvaderSprites,
                                startingX + i*35, startingY + j*35,
                                16, 16, new int[]{0,0}, 3);
            armInv.addProjSprites(invaderProjsprites);
            invaders.add(armInv);

          }
          else if (j == 1) {
            PowerInvader powInv = new PowerInvader(
                                powerInvaderSprites,
                                startingX + i*35, startingY + j*35,
                                16, 16, new int[]{0,0}, 1);
            powInv.addProjSprites(powerInvaderProjSprites);
            invaders.add(powInv);
          }
          else {
            Invader inv = new RegularInvader(
                                regularInvaderSprites,
                                startingX + i*35, startingY + j*35,
                                16, 16, new int[]{0,0}, 1);
            inv.addProjSprites(invaderProjsprites);
            invaders.add(inv);

          }

        }
      }

    }

// clears projectile lists and ensures the tank as the same health as the last level
    public void nextLevelReset() {
      tankProjs.removeAll(tankProjs);
      invaderProjs.removeAll(invaderProjs);
      level++;

      // ensures that the new tank as the same health as the old tank
      int prevHealth = tank.getHealth();
      tankSetup();
      tank.setHealth(prevHealth);

      invaderSetup();
    }

// clears projectile lists and sets up all game objects at their starting locations
    public void newGameReset() {
      level = 1;
      gameover = false;
      dispNextLevel = false;
      currentScore = 0;

      tankProjs.removeAll(tankProjs);
      invaderProjs.removeAll(invaderProjs);

      tankSetup();
      invaderSetup();
      barrierSetup();
    }

    // empties relevant lists after the game is over
    public void endGame() {
      this.image(gameoverPng, 270, 240);
      invaders.removeAll(invaders);
      invaderProjs.removeAll(invaderProjs);
      tankProjs.removeAll(tankProjs);
      tank.gameEndedTank();

      if (currentScore > highscore) {
        highscore = currentScore;
      }
      gameover = true;
    }


// checks if any of the game end conditions are met
    public void gameEndCheck() {
      // when tank is dead
      if (tank.isDead()) {

        explosions.add(new TankExplosion(tankExplosionSprites, tank.x(), tank.y()));
        endGame();
      }

      // when invaders reech the barriers
      else if (invaders.size() != 0) {
          boolean gameEnded = false;
          // loops through invader list to see if any of them have reach the barriers
          for (Invader i : invaders) {
            if (400 - i.y() <= 10) {
              gameEnded = true;
            }
          }
          if (gameEnded) {
            endGame();
          }
      }
    }

// checks for collisions between invaders and tank projectiles |
// and removes collided game objects from their lists
    public void checkInvaderProjCollisions() {
      // invader and tank projectile collisions
      ArrayList<Invader> invToRemove = new ArrayList<>();
      ArrayList<Projectile> tankProjsToRemove = new ArrayList<>();
      for (Invader i : invaders){
        for (Projectile p : tankProjs){
          if (Collideable.collides(i,p)){
            p.attack(i);
            if (i.isDead()){
                invToRemove.add(i);

                explosions.add(new Explosion(invaderExplosionSprites,
                                                      i.x(), i.y()));

                if (i.getClass() == RegularInvader.class) {
                  currentScore += 100;
                }
                else if (i.getClass() == PowerInvader.class){
                  currentScore += 250;
                }

            }
            tankProjsToRemove.add(p);
          }
        }
      }

      invaders.removeAll(invToRemove);
      tankProjs.removeAll(tankProjsToRemove);
      tankProjsToRemove.removeAll(tankProjsToRemove);
    }

// checks for collisions between barriers and all projectiles
// and removes collided game objects from their lists
    public void checkBarrierProjCollisions() {
      ArrayList<Projectile> tankProjsToRemove = new ArrayList<>();
      ArrayList<Projectile> invaderProjsToRemove = new ArrayList<>();
      ArrayList<Projectile> powerInvaderProjsToRemove = new ArrayList<>();
      for (Barrier barrier : barriers){

        for (BarrierPart bp : barrier.getParts()){
          for (Projectile p : tankProjs){
            if (Collideable.collides(bp, p)){
              p.attack(bp);
              tankProjsToRemove.add(p);
            }
          }

          for (Projectile p : invaderProjs) {
            if (Collideable.collides(bp, p)) {
              if (p.getClass() == PowerProjectile.class) {
                p.attack(bp);
              } else {
                  p.attack(bp);
              }

              invaderProjsToRemove.add(p);
            }
          }
        }
      }

      tankProjs.removeAll(tankProjsToRemove);
      invaderProjs.removeAll(invaderProjsToRemove);
      invaderProjsToRemove.removeAll(invaderProjsToRemove);
    }


// checks if explosions are occuring/finished and either continue exploding/end the explosion
    public void checkExplosions() {
      ArrayList<Explosion> explosionToRemove = new ArrayList<>();
      for (Explosion e : explosions) {
        if (e.isExplosionOver()) {
          explosionToRemove.add(e);
        } else {
          e.explode(this);
        }
      }
      explosions.removeAll(explosionToRemove);
    }

// checks collisions between tank and invader projectiles
    public void checkTankProjCollisions() {
      ArrayList<Projectile> invaderProjsToRemove = new ArrayList<>();
      for (Projectile p : invaderProjs) {
        if (Collideable.collides(p, tank)) {
          if (p.getClass() == PowerProjectile.class) {
            p.attack(tank);
          }
          else {
            p.attack(tank);
            invaderProjsToRemove.add(p);
          }
        }
      }
      invaderProjs.removeAll(invaderProjsToRemove);
    }

// randomly chooses an invader to fire every x seconds (depends on level)
    public void randomInvaderFire() {
      if (invaders.size() != 0) {
        int rate;
        if (level >= 5) {
          rate = 60;
        }
        else {
            rate = 60*(6-level);
        }

        if (frameCount%rate == 0) {
          Random rand = new Random();
          int randInt = rand.nextInt(invaders.size());
          Invader inv = invaders.get(randInt);

          invaderProjs.add(inv.fire());
        }
      }
    }

// displays next leve png and resets game for the next level
    public void gotoNextLevel() {
      this.image(
              loadImage("nextlevel.png"), 270, 240);


      nextLevelCount++;
      if (nextLevelCount == 240) {
        dispNextLevel = false;
        nextLevelCount = 0;

        nextLevelReset();
      }
    }



    public void settings() {
        size(640, 480);
    }

// draws the all the game objects in the App
    public void draw() {
      background(0);
      text("Highscore: " + highscore, 490, 30);
      text("Current Score:  " + currentScore, 20, 30);
      text("Health: " + tank.getHealth(), 20, 50);
      text("Level: " + level, 490, 50);



      gameEndCheck();

      if (gameover) {
        this.image(gameoverPng, 270, 240);
      }

      else if (dispNextLevel && !gameover) {
        gotoNextLevel();
      }

      else if (invaders.size() == 0) {
        dispNextLevel = true;
      }


      tank.draw(this);

      for (Projectile p : tankProjs) {
        p.draw(this);
      }


      for (Projectile p : invaderProjs) {
        p.draw(this);
      }


      for (Invader i : invaders) {
        if (frameCount % 2 == 0) {
          i.moveAndDraw(this);
        }
        else {
          i.draw(this);
        }
      }


      for (Barrier b : barriers) {
        b.draw(this);
      }


      checkInvaderProjCollisions();
      checkBarrierProjCollisions();
      checkExplosions();
      checkTankProjCollisions();
      randomInvaderFire();


      frameCount++;

    }

// checks if any keys are presssed and it takes action accordingly
    public void keyPressed(){
      if (keyCode == LEFT){
        tank.moveLeft();
        tank.draw(this);
      }
      else if (keyCode == RIGHT){
        tank.moveRight();
        tank.draw(this);
      }
      else if (!spaceBarPressed && keyCode == 32){
        tankProjs.add(tank.fire());
        // tankFireSound.play();
        spaceBarPressed = true;
      }
      else if (gameover) {
        if (keyCode == ENTER) {
          newGameReset();
        }
      }

    }

    // checks if any keys are released and it takes action accordingly
    public void keyReleased(){
      if (keyCode == LEFT){
        tank.notMoveLeft();
        tank.draw(this);
      }
      else if (keyCode == RIGHT){
        tank.notMoveRight();
        tank.draw(this);
      }
      else if (keyCode == 32) {
        spaceBarPressed = false;
      }
    }


    public static void main(String[] args) {
        PApplet.main("invadem.App");
    }


}
