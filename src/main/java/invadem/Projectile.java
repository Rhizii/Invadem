package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;
import java.util.ArrayList;
public class Projectile extends Collideable {

  protected int dmg;

  public Projectile(ArrayList<PImage> sprites, int x, int y, int width, int height, int[] velocity, int health){
    super(sprites, x, y, width, height, velocity, health);
    this.dmg = 1;

  }
  public void draw(PApplet app) {
    for (PImage img : this.sprites){
      app.image(img, this.x, this.y);
      tick();
    }
  }

// moving logic
  public void tick() {
      this.x += velocity[0];
      this.y += velocity[1];
  }

// reduces the health if the collideable passed through depending on how much damage the projectile does
  public void attack(Collideable c) {
    for (int i = 0; i < this.dmg; i++) {
      c.hit();
    }
  }
}
