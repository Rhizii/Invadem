package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.List;


public class Tank extends Collideable implements Fire{
  private boolean movingLeft;
  private boolean movingRight;
  private ArrayList<PImage> projSprites;
  private boolean gameEndedTank;


  public Tank(ArrayList<PImage> sprites, int x, int y, int width, int height, int[] velocity, int health){
    super(sprites, x, y, width, height, velocity, health);

    this.movingRight = false;
    this.movingLeft = false;
    this.projSprites = new ArrayList<>();
    this.gameEndedTank = false;

  }

  // ensures that the 'disappears' during the explosion
  public void gameEndedTank() {
    this.gameEndedTank = true;
    sprites = new ArrayList<>();
  }

  @Override
  public boolean isDead() {
    /***
    to avoid the program going down a wrong path after the game has gameEnded
    isDead is set to return false after the game is ended.
    ***/
    if (this.gameEndedTank) {
      return false;
    }
    else {
      return this.health <= 0;
    }
  }

  public void draw(PApplet app) {
    for (PImage img : sprites){
      app.image(img, x, y);
      tick();
    }

  }

  public void addProjSprites(ArrayList<PImage> projSprites) {
    this.projSprites.addAll(projSprites);
  }

// fires a normal projectile
  public Projectile fire() {
    return new Projectile(this.projSprites,
                          this.x() + 11, this.y() - 8,
                          1,3, new int[] {0,-1}, 1);

  }

  public void setHealth(int health) {
    this.health = health;
  }

// sets up boundaries for movement
  public void tick() {
      if (this.movingRight){
        if (this.x < 460){
            this.x += velocity[0];
        }
      }
      else if (this.movingLeft){
        if (this.x > 180) {
            this.x -= velocity[0];
        }
      }
  }



  public void moveRight(){
    this.movingRight = true;
  }

  public void moveLeft(){
    this.movingLeft = true;
  }

  public void notMoveRight(){
    this.movingRight = false;
  }

  public void notMoveLeft(){
    this.movingLeft = false;
  }


// methods below used for testing purposes
  public boolean getMovingLeft() {
    return this.movingLeft;
  }

  public boolean getMovingRight() {
    return this.movingRight;
  }





}
