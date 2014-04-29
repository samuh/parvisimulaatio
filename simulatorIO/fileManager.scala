package simulatorIO
import	java.io.File
import scala.swing._
import scala.swing.event._
import simulator.logic.simulatorBird
import simulator.gui.simulatorMainWindow
import scala.collection.mutable._
import javax.swing._

class fileManager{
  
  val parentFrame = new JFrame()
  val simulator = simulatorMainWindow.simulator
  val main = simulatorMainWindow
  
  def openFile{
    simulator.timer.stop()
    main.optionsWindow.startButton.selected = false
    val chooser = new JFileChooser
    if(chooser.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION){
      val data = scala.xml.XML.loadFile(chooser.getSelectedFile())
      
      simulator.birds = Buffer[simulatorBird]()
      
      try{
	      val target = (data \ "target")
	      simulator.targetX = (target \ "@x").text.toInt
	      simulator.targetY = (target \ "@y").text.toInt
	      main.viewWindow.targetFollowMouse = false
      } catch {
        case e: Exception => null
      }
      
      try{
	      (data \ "rules").foreach{
	        rules => (rules \ "rule").foreach{
	          rule => {
	            (rule \ "@name").text match{
	              case "collisionSize" => simulator.collisionSize = (rule \ "@value").text.toDouble; main.optionsWindow.collisionSize.value = (rule \ "@value").text.toDouble.toInt;
	              case "collision" => simulator.collision = (rule \ "@value").text.toDouble; main.optionsWindow.collisionWeight.value = (rule \ "@value").text.toDouble.toInt;
	              case "alignment" => simulator.alignment = (rule \ "@value").text.toDouble; main.optionsWindow.alignmentWeight.value = (rule \ "@value").text.toDouble.toInt;
	              case "cohesionWeight" => simulator.cohesion = (rule \ "@value").text.toDouble; main.optionsWindow.cohesionWeight.value = (rule \ "@value").text.toDouble.toInt;
	              case "flockareaSize" => simulator.flockSize = (rule \ "@value").text.toDouble; main.optionsWindow.flockareaSize.value = (rule \ "@value").text.toDouble.toInt;
	              case "target" => simulator.target = (rule \ "@value").text.toDouble; main.optionsWindow.targetWeight.value = (rule \ "@value").text.toDouble.toInt;
	            }
	          }
	          simulator.repaintView
	        }
	      }
      } catch {
        case e: Exception => println("No options")
      }
      
      try{
	      (data \ "graphics").foreach{
	        graphics => (graphics \ "graphic").foreach{
	          graphic => {
	            (graphic \ "@name").text match{
	              case "collision" => main.viewWindow.collision = (graphic \ "@value").text.toBoolean; main.optionsWindow.toggleCollision.selected = true
	              case "flock" => main.viewWindow.flock = (graphic \ "@value").text.toBoolean; main.optionsWindow.toggleFlock.selected = true
	              case "data" => main.viewWindow.data = (graphic \ "@value").text.toBoolean; main.optionsWindow.toggleData.selected = true
	            }
	          }
	          simulator.repaintView
	        }
	      }
      } catch {
        case e: Exception => println("No options")
      }
      
      try{
	      (data \ "birds").foreach{
	        birds => (birds \ "bird").foreach{
	          bird => {
	            val newBird = new simulatorBird
	            newBird.setPosition(((bird \ "@x").text.toInt, (bird \ "@y").text.toInt))
	            newBird.setOrientation((bird \ "@orientation").text.toDouble)
	            simulator.addBird(newBird)
	          }
	          simulator.repaintView
	        }
	      }
      } catch {
        case e: Exception => println("Error in the source file")
      }
      
    }
  }
  
  def saveFile{
    simulator.timer.stop()
    main.optionsWindow.startButton.selected = false
    
    val chooser = new JFileChooser
    if(chooser.showSaveDialog(parentFrame)== JFileChooser.APPROVE_OPTION) {
      val filePath = chooser.getSelectedFile()
      val node =
      <scenario>
      	<birds>{ simulator.birds.map(b => b.toXml)}</birds>
      	<rules>{ main.optionsWindow.rulesToXml }</rules>
      	<graphics>{ main.optionsWindow.graphicsToXml }</graphics>
	  </scenario>
      scala.xml.XML.save(filePath.getAbsolutePath(), node)
    }
    simulator.timer.start()
    main.optionsWindow.startButton.selected = true
  }
}