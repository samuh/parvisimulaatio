package simulator.logic

import scala.collection.mutable._
import scala.swing._
import simulator.gui._
import scala.util.Random


class simulatorRuntime() {
  
  val max_coordinates = (820, 768)
  val vertical_edge_areas = (20, 748)
  val horizontal_edge_areas = (20, 780)
  val rand = new Random()
  
  var flockCenter = 0
  var flockSpeed = 0
  
  var birds = Buffer[simulatorBird]()

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
  
  //Create bird
  def createBird() = {
    var bird = new simulatorBird()
    bird.setPosition(rand.nextInt(819), rand.nextInt(768))
    addBird(bird)
  }
  
  //Add bird
  def addBird(bird : simulatorBird) = birds.append(bird);
  
  //Deletes last bird added
  def deleteBird = if(birds.size > 0) birds.dropRight(1)
  
  def getBirds : Buffer[simulatorBird] = return birds
  
  //Shortcut for repainting the view window
  def repaintView = simulatorMainWindow.viewWindow.repaint
  
  def calculateAverages = {
    
  }
  
  val refreshRate = 17 // milliseconds so about 60fps
  
  val timer = new javax.swing.Timer(refreshRate, Swing.ActionListener(e =>{
      if(birds.length > 0 ){
    	  birds.foreach(b => b.setPosition(b.getPosition._1 + 1, b.getPosition._2 + 1))
      }
      repaintView
    }))
  
  def calculateBird(bird : simulatorBird)={
    var x = bird.getPositionX
    var y = bird.getPositionY
    
    //Handle possible collisions
    val xCollision = (x - 20, y - 20)
    val yCollision = (x - 20, y - 20)
    birds.foreach(b => if((x < xCollision._1 || x > xCollision._2) ||  (y < yCollision._1 || y > yCollision._2)) bird.avoid(b))

  }
  
} //Class end

class calculateBirdPosition(bird : simulatorBird) extends Runnable {
  //Calculate birds position
  def run(){
	  
  }

}
