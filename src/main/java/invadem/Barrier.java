package invadem;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;


public class Barrier {
  private ArrayList<BarrierPart> parts;


  private int x;
  private int y;

  public Barrier(ArrayList<BarrierPart> parts){
    this.parts = parts;

  }

  public void draw(PApplet app){
    // first removes barrier parts with health 0 - makes the barrier part 'disappear'
    ArrayList<BarrierPart> bpToRemove = new ArrayList<>();
    for (BarrierPart bp : this.parts){
      if (bp.getHealth() <= 0){
        bpToRemove.add(bp);
      }
    }
    this.parts.removeAll(bpToRemove);

    for (BarrierPart bp :  this.parts){
      app.image(bp.getSprites().get(bp.getHealth()-1), bp.x(), bp.y());
    }
  }


  public ArrayList<BarrierPart> getParts(){
    return this.parts;
  }
}
