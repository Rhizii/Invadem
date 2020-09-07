package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;
import java.util.ArrayList;

public class PowerProjectile extends Projectile {
  // damage is different - 3 instead of the normal projectile's 1
  public PowerProjectile (ArrayList<PImage> sprites, int x, int y, int width, int height, int[] velocity, int health) {
    super(sprites, x, y, width, height, velocity, health);
    this.dmg = 3;
  }
}
