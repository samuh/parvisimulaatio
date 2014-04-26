package simulator.gui

import scala.swing.Panel
import java.awt.{ Graphics2D, Color }
import simulator.logic._
import scala.swing.event._                                          
import java.awt.RenderingHints

class simulatorView(simulator : simulatorRuntime) extends Panel{
  
  /** Graphic on/off variables */
  var collision : Boolean = false
  var flockColor : Boolean = false
  var flock : Boolean = false
  var data : Boolean = false
  
  var targetFollowMouse : Boolean = true
  
  /** Listen to mouse to get the target */
  listenTo(mouse.moves, mouse.clicks)
    	reactions +={
    		case e: MouseMoved => {
    		  if(targetFollowMouse){
    		    simulator.setTargetX(e.point.x); simulator.setTargetY(e.point.y)
    		    this.repaint()
    		  }
    		}
    		case e: MouseClicked =>{
    		  simulator.setTargetX(e.point.x); simulator.setTargetY(e.point.y)
    		  targetFollowMouse = !targetFollowMouse
    		  this.repaint()
    		}
   }
  
  override def paintComponent(g: Graphics2D){
    
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON )
    
    val sea = new Color(0, 102, 204)
    g.setColor(sea)
    g.fillRect(0, 0, this.size.getWidth().toInt, this.size.getHeight().toInt)
    
    /** Draw target under the mouse */
    def drawTarget() ={
      if(simulatorMainWindow.optionsWindow.startButton.selected){
        g.setColor(Color.black)
        g.drawLine(simulator.targetX, simulator.targetY - 5, simulator.targetX, simulator.targetY + 5)
        g.drawLine(simulator.targetX - 5, simulator.targetY, simulator.targetX + 5, simulator.targetY)
      }
    }
    
    def drawData()={
      g.setColor(Color.white)
      g.drawString("Bird count : " + simulatorMainWindow.simulator.birds.length.toString, 5, 12)
    }
    
    /** Visualize flock area */
    def visualizeFlockArea(x : Int, y : Int) ={
      g.setColor(new Color(0, 51, 102))
      g.drawOval(x - (simulator.flockSize.toInt / 2),y - (simulator.flockSize.toInt / 2),simulator.flockSize.toInt, simulator.flockSize.toInt)
    }
    
    /** Visualize collision area */
    def visualizeCollision(x : Int, y : Int) ={
      g.setColor(new Color(0, 204, 102, 100))
      g.fillOval(x - (simulator.collisionSize.toInt / 2), y - (simulator.collisionSize.toInt / 2), simulator.collisionSize.toInt, simulator.collisionSize.toInt)
    }
    
    def drawBird(x : Int, y : Int, rad : Double, color : Color)={
      g.setColor(Color.white)
      var direction = rad
      
      /**Draw line to head */
      var headPointX = 10 * math.cos(rad)
      var headPointY = 10 * math.sin(rad)
      //g.drawLine(x, y, x + headPointX.toInt, y - headPointY.toInt)
      
      /**Draw line to left point */
      var leftPointX = (7 * math.cos(rad + 2.1))
      var leftPointY = (7 * math.sin(rad + 2.1))
      //g.drawLine(x, y, x + leftPointX.toInt, y - leftPointY.toInt)
      
      /**Draw line to right point */
      var rightPointX = (7 * math.cos(rad - 2.1))
      var rightPointY = (7 * math.sin(rad - 2.1))
      //g.drawLine(x, y, x + rightPointX.toInt, y - rightPointY.toInt)
      g.fillPolygon(Array(x + headPointX.toInt, x + leftPointX.toInt, x, x + rightPointX.toInt), Array(y - headPointY.toInt, y - leftPointY.toInt, y, y - rightPointY.toInt), 4)
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
	      if(data){
	        drawData()
	      }
	      drawBird(x, y, b.getOrientation, b.getColor)
      }
    }
    drawTarget()
  }

}