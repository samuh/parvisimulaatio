package simulator.gui

import scala.swing.Panel
import java.awt.{ Graphics2D, Color }
import simulator.logic._
import scala.swing.event._

class simulatorView(simulator : simulatorRuntime) extends Panel{
  
  //Graphics
  var collision : Boolean = false
  var aim : Boolean = false
  var flock : Boolean = false
  var viewarea : Boolean = false
  
  listenTo(mouse.moves)
    	reactions +={
    		case e: MouseMoved => simulator.setTargetX(e.point.x); simulator.setTargetY(e.point.y)
   }
  
  override def paintComponent(g: Graphics2D){
    val sea = new Color(0, 102, 204)
    g.setColor(sea)
    g.fillRect(0, 0, this.size.getWidth().toInt, this.size.getHeight().toInt)
    
    def drawTarget = g.setColor(Color.black); g.fillRect(simulator.targetX, simulator.targetY, 10, 10)

    if(simulator.getBirds.length > 0 ){
      if(collision){
        var halo = new Color(0,204,0, 150)
        var c = simulatorMainWindow.simulator.collision.toInt
        g.setColor(halo)
        simulator.getBirds.foreach(b => g.fillOval(3 + b.getPositionX - c/ 2, 2 + b.getPositionY - c / 2, c, c))
      }
      g.setColor(Color.white)
      simulator.getBirds.foreach(b => g.fillOval(b.getPositionX, b.getPositionY, 5, 5))
      g.setColor(Color.black)
      g.fillRect(simulator.targetX, simulator.targetY, 10, 10)
    }
  }

}