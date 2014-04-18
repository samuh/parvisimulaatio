package simulator.gui

import scala.swing.Panel
import java.awt.{ Graphics2D, Color }
import simulator.logic

class simulatorView extends Panel{
  
  override def paintComponent(g: Graphics2D){
    val sea = new Color(0, 102, 204)
    g.setColor(sea)
    g.fillRect(0, 0, 824, 768)
    
    g.setColor(Color.white)
    var location = simulatorMainWindow.simulator.getBirds.head.position
    println("Location" + location)
    g.fillOval(location._1, location._2, 10, 10)
  }

}