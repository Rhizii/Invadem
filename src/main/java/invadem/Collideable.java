package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;
import java.util.ArrayList;


public class Collideable {
  protected ArrayList<PImage> sprites;
  protected int x;
  protected int y;
  protected int width;
  protected int height;
  protected int[] velocity;
  protected int health;

  public Collideable(ArrayList<PImage> sprites, int x, int y, int width, int height, int[] velocity, int health) {
      this.sprites = sprites;
      this.x = x;
      this.y = y;
      this.velocity = velocity;
      this.width = width;
      this.height = height;
      this.health = health;
  }

  public int x(){
    return this.x;
  }

  public int y(){
    return this.y;
  }

  public int height(){
    return this.height;
  }

  public int width(){
    return this.width;
  }


  public int getHealth(){
    if (this.health < 0){
      return 0;
    } else {
        return this.health;
    }
  }

  public void hit() {
    this.health--;
  }

  public boolean isDead() {
    return this.health <= 0;
  }

// collision detection method 
  public static boolean collides(Collideable c1, Collideable c2){
      if (c1.x() < (c2.x() + c2.width()) &&
          (c1.x() + c1.width()) > c2.x() &&
          c1.y() < (c2.y() + c2.height()) &&
          (c1.height() + c1.y()) > c2.y()
          ){
            return true;
          }
      else{
        return false;
      }
  }




}
