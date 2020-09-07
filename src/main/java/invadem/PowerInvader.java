package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;
import java.util.ArrayList;

public class PowerInvader extends Invader {
  public PowerInvader(ArrayList<PImage> sprites, int x, int y, int width, int height, int[] velocity, int health) {
    super(sprites, x, y, width, height, velocity, health);

  }

// fires a power projectile instead of a regular projectile
  @Override
  public PowerProjectile fire() {
    return new PowerProjectile(this.projSprites,
                          this.x() + 8, this.y() + 8,
                          2, 5, new int[] {0,1}, 1);
  }
}
