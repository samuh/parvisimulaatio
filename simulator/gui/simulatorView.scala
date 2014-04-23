package simulator.gui

import scala.swing.Panel
import java.awt.{ Graphics2D, Color }
import simulator.logic._
import scala.swing.event._

class simulatorView(simulator : simulatorRuntime) extends Panel{
  
  var targetX = 0
  var targetY = 0
  
  listenTo(mouse.clicks)
    	reactions +={
    		case e: MouseClicked => targetX = e.point.x; targetY = e.point.y;
    }
  
  override def paintComponent(g: Graphics2D){
    val sea = new Color(0, 102, 204)
    g.setColor(sea)
    g.fillRect(0, 0, this.size.getWidth().toInt, this.size.getWidth().toInt)
    
    def drawTarget = g.setColor(Color.black); g.fillRect(targetX, targetY, 10, 10)

    if(simulator.getBirds.length > 0 ){
      var halo = new Color(0,204,0, 150)
      g.setColor(halo)
      simulator.getBirds.foreach(b => g.fillOval(b.getPositionX - 10, b.getPositionY - 10, 25, 25))
      g.setColor(Color.white)
      simulator.getBirds.foreach(b => g.fillOval(b.getPositionX, b.getPositionY, 5, 5))
      g.setColor(Color.black)
      g.fillRect(targetX, targetY, 10, 10)
    }
  }

}