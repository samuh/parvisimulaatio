package simulator.logic

import scala.collection.mutable._
import scala.swing._


class simulatorRuntime() {
  
  var birds = Buffer[simulatorBird]()
  
  //Simulator parameters saved as variables
  var speed : Int = 50
  var mass : Int = 50
  var turnRate : Int = 50
  
  //Rules 
  var avoidOther : Boolean = true
  var alignment : Boolean = true
  var cohesion : Boolean = true

  //Graphics
  var collision : Boolean = true
  
  //Add bird
  def addBird(bird : simulatorBird) = if(birds.size > 0) birds.append(bird)
  
  //Deletes last bird added
  def deleteBird() = if(birds.size > 0) birds.dropRight(1)
  
  val refreshRate = 17 // milliseconds so about 60fps
  
  val timer = new javax.swing.Timer(refreshRate, Swing.ActionListener(e =>
    {
      println("ASD")
    }
  ))
  
}