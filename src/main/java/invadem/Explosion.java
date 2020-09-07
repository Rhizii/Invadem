package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;


public class Explosion {
  protected ArrayList<PImage> sprites;
  protected int x;
  protected int y;
  // state variable stores which sprite to use in the draw function
  protected int state;

  public Explosion(ArrayList<PImage> sprites, int x, int y) {
    this.sprites = sprites;
    this.x = x;
    this.y = y;
    this.state = 0;
  }


  // draw method of the explosion class
  public void explode(PApplet app) {
    if (this.state < this.sprites.size()) {
      app.image(sprites.get(this.state), this.x, this.y, 24, 24);
      state++;
    }

  }

// checks if the explosion is over - no more sprites left
  public boolean isExplosionOver() {
    return this.state >= this.sprites.size();
  }


  public int getState() {
    return this.state;
  }
}
