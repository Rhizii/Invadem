package invadem;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class BarrierPart extends Collideable{
  private int health;


  public BarrierPart(ArrayList<PImage> sprites, int x, int y, int width, int height, int[] velocity, int health) {
    super(sprites, x, y, width, height, velocity, health);

  }

  public void hit(){
    if (!(super.health < 0)){
        super.health--;
    }
  }

  public ArrayList<PImage> getSprites(){
    return this.sprites;
  }

}
