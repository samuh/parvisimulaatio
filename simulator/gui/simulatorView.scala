package simulator.gui

import scala.swing.Panel
import java.awt.{ Graphics2D, Color }
import simulator.logic._
import scala.swing.event._

class simulatorView(simulator : simulatorRuntime) extends Panel{
  
  /** Graphic on/off variables */
  var collision : Boolean = false
  var aim : Boolean = false
  var flock : Boolean = false
  var data : Boolean = false
  
  var targetFollowMouse : Boolean = true
  
  /** Listen to mouse to get the target */
  listenTo(mouse.moves, mouse.clicks)
    	reactions +={
    		case e: MouseMoved => {
    		  if(targetFollowMouse){
    		    simulator.setTargetX(e.point.x); simulator.setTargetY(e.point.y)
    		  }
    		}
    		case e: MouseClicked =>{
    		  simulator.setTargetX(e.point.x); simulator.setTargetY(e.point.y)
    		  targetFollowMouse = !targetFollowMouse
    		}
   }
  
  override def paintComponent(g: Graphics2D){
    val sea = new Color(0, 102, 204)
    g.setColor(sea)
    g.fillRect(0, 0, this.size.getWidth().toInt, this.size.getHeight().toInt)
    
    /** Draw target under the mouse */
    def drawTarget() ={
      if(simulatorMainWindow.optionsWindow.startButton.selected){
        g.setColor(Color.red)
        g.fillOval(simulator.targetX - 5, simulator.targetY - 5, 10, 10)
      }
    }
    
    /** Visualize flock area */
    def visualizeFlockArea(x : Int, y : Int) ={
      g.setColor(new Color(0, 51, 102))
      g.drawOval(x - (simulator.flock.toInt / 2) + 2,y - (simulator.flock.toInt / 2) + 2,simulator.flock.toInt, simulator.flock.toInt)
    }
    
    /** Visualize collision area */
    def visualizeCollision(x : Int, y : Int) ={
      g.setColor(new Color(0, 204, 102, 100))
      g.fillOval(x - (simulator.collision.toInt / 2) + 2, y - (simulator.collision.toInt / 2) + 2, simulator.collision.toInt, simulator.collision.toInt)
    }
    
    /** Draw birds */
    if(simulator.getBirds.length > 0 ){
      simulator.getBirds.foreach{
        b => 
          var x = b.getPositionX
          var y = b.getPositionY
	      if(flock){
	        visualizeFlockArea(x, y)
	      }
	      if(collision){
	        visualizeCollision(x, y)
	      }
	      g.setColor(Color.white)
          g.fillOval(x, y, 5, 5)
      }
      drawTarget()
    }
  }

}