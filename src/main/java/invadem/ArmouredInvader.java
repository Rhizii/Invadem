package invadem;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class ArmouredInvader extends Invader {
  public ArmouredInvader(ArrayList<PImage> sprites, int x, int y, int width, int height, int[] velocity, int health){
    super(sprites, x, y, width, height, velocity, health);
  }
}
