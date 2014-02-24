package simulator.gui

import scala.swing.Panel
import java.awt.{ Graphics2D, Color }

class simulatorView extends Panel{
  
  override def paintComponent(g: Graphics2D){
    val sea = new Color(0, 102, 204)
    g.setColor(sea)
    g.fillRect(0, 0, 824, 768)
  }

}