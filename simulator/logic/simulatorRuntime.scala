package simulator.logic

import scala.collection.mutable._
import scala.swing._
import simulator.gui._


class simulatorRuntime() {
  
  var birds = Buffer[simulatorBird](new simulatorBird, new simulatorBird)

  //Simulator parameters saved as variables
  var speed : Int = 50
  var mass : Int = 50
  var turnRate : Int = 50
  
  //Rules 
  var avoidOther : Boolean = false
  var alignment : Boolean = false
  var cohesion : Boolean = false

  //Graphics
  var collisionG : Boolean = false
  var speedG : Boolean = false
  var forceG : Boolean = false
  var viewG : Boolean = false
  
  //Add bird
  def addBird(bird : simulatorBird) = if(birds.size > 0) birds.append(bird)
  
  //Deletes last bird added
  def deleteBird() = if(birds.size > 0) birds.dropRight(1)
  
  val refreshRate = 17 // milliseconds so about 60fps
  
  val timer = new javax.swing.Timer(refreshRate, Swing.ActionListener(e =>
    {
      println("ASD")
      println(alignment)
      var x = birds.head.position._1 + 1
      var y = birds.head.position._2 + 1
      println(x, y)
      birds.head.position = (x, y)
      simulatorMainWindow.viewWindow.repaint
    }
  ))
  
  def getBirds : Buffer[simulatorBird] = return birds
  
}