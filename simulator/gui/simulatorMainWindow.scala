package simulator.gui

import scala.swing._
import scala.swing.BorderPanel.Position._
import java.awt.{Color, Graphics2D}
import simulator.logic._
import scala.swing.event._

object simulatorMainWindow extends SimpleSwingApplication{
  
  val simulator = new simulatorRuntime()
	
  def top = new MainFrame{
    title = "Parvisimulaattori"
      
    //Init view window
    val viewWindow = new simulatorView{
      preferredSize = new Dimension(824, 768)
    }
    
    //Init options windows
    val optionsWindow = new simulatorOptions{
      preferredSize = new Dimension(200, 768)
    }
    
    //Add inner windows to main window
    contents = new BorderPanel {
      layout(optionsWindow) = West
      layout(viewWindow) = East
    }
    
    listenTo(optionsWindow.startButton)
    reactions += {
      case ButtonClicked(b) =>
        simulator.timer.start()
    }
    
    //Define menubar and windows size
    size = new Dimension(1024, 768)
    centerOnScreen()
    resizable = false
    menuBar = new MenuBar {
      contents += new Menu("Menu") {
        contents += new MenuItem("Load Scenario...")
        contents += new MenuItem("Save scenario..")
        contents += new MenuItem(Action("Exit") {
          sys.exit(0)
        })
      }
    }
  } 
}