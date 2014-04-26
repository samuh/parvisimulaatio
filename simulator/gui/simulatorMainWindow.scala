package simulator.gui

import scala.swing._
import scala.swing.BorderPanel.Position._
import event._
import java.awt.{Color, Graphics2D}
import simulator.logic._
import scala.swing.event._

object simulatorMainWindow extends SimpleSwingApplication{
  
  /** Create new simulatorRuntime **/
  val simulator = new simulatorRuntime()
  val width = 824
  val height = 768
  
  /** Create new optionsWindows **/
  val optionsWindow = new simulatorOptions{
      preferredSize = new Dimension(200, 768)
  }
  
  /** Create viewWindow containing graphics */
  val viewWindow = new simulatorView(simulator){
      preferredSize = new Dimension(810, 768)
  }
	
  def top = new MainFrame{
    title = "Parvisimulaattori"
    
    /** Add inner windows to main window */
    contents = new BorderPanel {
      layout(optionsWindow) = East
      layout(viewWindow) = West
    }
    
    /** Listen to buttons and react accordingly */
    listenTo(optionsWindow.toggleCollision, optionsWindow.toggleAim, optionsWindow.toggleFlock, optionsWindow.toggleData, optionsWindow.addBird, optionsWindow.deleteBird, optionsWindow.startButton)
    reactions += {
      case ButtonClicked(optionsWindow.toggleCollision) => viewWindow.collision = optionsWindow.toggleCollision.selected; simulator.repaintView
      case ButtonClicked(optionsWindow.toggleAim) => viewWindow.aim = optionsWindow.toggleAim.selected; simulator.repaintView
      case ButtonClicked(optionsWindow.toggleFlock) => viewWindow.flock = optionsWindow.toggleFlock.selected; simulator.repaintView
      case ButtonClicked(optionsWindow.toggleData) => viewWindow.data = optionsWindow.toggleData.selected; simulator.repaintView
	  case ButtonClicked(optionsWindow.addBird) => simulator.createBird
	  case ButtonClicked(optionsWindow.deleteBird) => simulator.deleteBird
	  case ButtonClicked(optionsWindow.startButton) => if(optionsWindow.startButton.selected) simulator.timer.start() else simulator.timer.stop()
    }
    
    /** Listen to sliders and change values */
    listenTo(optionsWindow.collisionSize, optionsWindow.alignmentWeight, optionsWindow.cohesionWeight, optionsWindow.flockareaSize, optionsWindow.targetWeight)
    reactions +={
      case ValueChanged(optionsWindow.collisionSize) => simulator.collision = optionsWindow.collisionSize.value.toDouble; simulator.repaintView
      case ValueChanged(optionsWindow.alignmentWeight) => simulator.alignment = optionsWindow.alignmentWeight.value; simulator.repaintView
      case ValueChanged(optionsWindow.cohesionWeight) => simulator.cohesion = optionsWindow.cohesionWeight.value; simulator.repaintView
      case ValueChanged(optionsWindow.flockareaSize) => simulator.flock = optionsWindow.flockareaSize.value; simulator.repaintView
      case ValueChanged(optionsWindow.targetWeight) => simulator.target = optionsWindow.targetWeight.value; simulator.repaintView
    }

    /** Define menu bar and windows size **/
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