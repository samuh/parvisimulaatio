package simulator.gui

import scala.swing._
import scala.swing.BorderPanel.Position._
import event._
import java.awt.{Color, Graphics2D}
import simulator.logic._
import scala.swing.event._

object simulatorMainWindow extends SimpleSwingApplication{
  
  val simulator = new simulatorRuntime()
  val width = 824
  val height = 768
  
  val optionsWindow = new simulatorOptions{
      preferredSize = new Dimension(200, 768)
  }
  
  val viewWindow = new simulatorView(simulator){
      preferredSize = new Dimension(810, 768)
  }
	
  def top = new MainFrame{
    title = "Parvisimulaattori"
    
    //Add inner windows to main window
    contents = new BorderPanel {
      layout(optionsWindow) = East
      layout(viewWindow) = West
    }
    
    //Listen to buttons and react accordingly
    listenTo(optionsWindow.toggleCollision, optionsWindow.toggleAim, optionsWindow.toggleFlock, optionsWindow.toggleViewarea, optionsWindow.addBird, optionsWindow.deleteBird, optionsWindow.startButton)
    reactions += {
      case ButtonClicked(optionsWindow.toggleCollision) => viewWindow.collision = optionsWindow.toggleCollision.selected
      case ButtonClicked(optionsWindow.toggleAim) => viewWindow.aim = optionsWindow.toggleAim.selected
      case ButtonClicked(optionsWindow.toggleFlock) => viewWindow.flock = optionsWindow.toggleFlock.selected
      case ButtonClicked(optionsWindow.toggleViewarea) => viewWindow.viewarea = optionsWindow.toggleViewarea.selected
	  case ButtonClicked(optionsWindow.addBird) => simulator.createBird
	  case ButtonClicked(optionsWindow.deleteBird) => simulator.deleteBird
	  case ButtonClicked(optionsWindow.startButton) => if(optionsWindow.startButton.selected) simulator.timer.start() else simulator.timer.stop()
    }
    
    listenTo(optionsWindow.collision, optionsWindow.alignment, optionsWindow.cohesion, optionsWindow.flockarea, optionsWindow.target)
    reactions +={
      case ValueChanged(optionsWindow.collision) => simulator.collision = optionsWindow.collision.value
      case ValueChanged(optionsWindow.alignment) => simulator.alignment = optionsWindow.alignment.value
      case ValueChanged(optionsWindow.cohesion) => simulator.cohesion = optionsWindow.cohesion.value
      case ValueChanged(optionsWindow.flockarea) => simulator.flock = optionsWindow.flockarea.value
      case ValueChanged(optionsWindow.target) => simulator.target = optionsWindow.target.value
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