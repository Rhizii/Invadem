package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class TankExplosion extends Explosion {

  public TankExplosion(ArrayList<PImage> sprites, int x, int y) {
    super(sprites, x, y);
  }

// only the size of the tank explosion sprite is changed here
  @Override
  public void explode(PApplet app) {
    if (this.state < this.sprites.size()) {
      app.image(sprites.get(this.state), this.x-15, this.y-35, 55, 55);
      this.state++;
    }
  }
}
