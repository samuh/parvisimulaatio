package simulator.gui

import scala.swing.Panel
import java.awt.{ Graphics2D, Color }
import simulator.logic._

class simulatorView(simulator : simulatorRuntime) extends Panel{
  
  override def paintComponent(g: Graphics2D){
    val sea = new Color(0, 102, 204)
    g.setColor(sea)
    g.fillRect(0, 0, this.size.getWidth().toInt, this.size.getWidth().toInt)
    
    g.setColor(Color.white)
    if(simulator.getBirds.length > 0 ){
      simulator.getBirds.foreach(b => g.fillOval(b.getPositionX, b.getPositionY, 5, 5))
    }
  }

}