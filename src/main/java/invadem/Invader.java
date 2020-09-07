package invadem;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public abstract class Invader extends Collideable implements Fire {
  protected boolean isMovingLeft;
  protected boolean isMovingDown;
  protected boolean isMovingRight;
  protected int movingSideCount;
  protected int movingDownCount;
  protected int health;
  protected ArrayList<PImage> projSprites;
  protected int spriteIndex;
  protected boolean secondFrame;

  public Invader(ArrayList<PImage> sprites, int x, int y, int width, int height, int[] velocity, int health){
    super(sprites, x, y, width, height, velocity, health);
    this.isMovingLeft = true;
    this.isMovingDown = true;
    this.isMovingRight = false;
    this.movingSideCount = 0;
    this.movingDownCount = 0;
    this.projSprites = new ArrayList<>();
    this.spriteIndex = 0;
    this.secondFrame = true;

  }

// returns a new projectile which starts as the at the invader
  public Projectile fire() {
    return new Projectile(this.projSprites,
                          this.x() + 8, this.y() + 8,
                          2, 5, new int[] {0,1}, 1);
  }

// adds all the projectile sprites necessary
  public void addProjSprites(ArrayList<PImage> projSprites) {
    this.projSprites.addAll(projSprites);
  }

// method moves and draws the invader - used every 2nd frame
  public void moveAndDraw(PApplet app) {

    if (this.isMovingDown){
      app.image(this.sprites.get(1), this.x, this.y);
      this.y += 1;
      this.movingDownCount += 1;
      if (this.movingDownCount == 8){
        this.isMovingDown = false;
        this.movingDownCount = 0;
      }
    }
    else if (this.isMovingLeft){
      app.image(this.sprites.get(0), this.x, this.y);
      this.x -= 1;
      this.movingSideCount += 1;
      if (this.movingSideCount == 30){
        this.isMovingDown = true;
        this.isMovingLeft = false;
        this.movingSideCount = 0;
      }
    }

    else if (!(this.isMovingLeft)){
      app.image(this.sprites.get(0), this.x, this.y);
      this.x += 1;
      this.movingSideCount += 1;
      if (this.movingSideCount == 30){
        this.isMovingLeft = true;
        this.isMovingDown = true;
        this.movingSideCount = 0;
      }
    }
  }

// method only moves the invader  - every 2nd frame
  public void draw(PApplet app) {
    if (this.isMovingLeft || this.isMovingRight){
      app.image(this.sprites.get(0), this.x, this.y);
    }
    else {
      app.image(this.sprites.get(1), this.x, this.y);
    }
  }

// methods below only used for testing
  public boolean getIsMovingLeft() {
    return this.isMovingLeft;
  }

  public boolean getIsMovingDown() {
    return this.isMovingDown;
  }

  public int getMovingSideCount() {
    return this.movingSideCount;
  }

  public int getMovingDownCount() {
    return this.movingDownCount;
  }

}
