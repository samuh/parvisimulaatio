package simulator.logic

import scala.collection.mutable._
import scala.swing._
import simulator.gui._
import scala.util.Random


class simulatorRuntime() {
  
  val max_coordinates = (820, 768)
  val vertical_edge_areas = (20, 748)
  val horizontal_edge_areas = (20, 780)
  val rand = new Random(1024)
  
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
  def deleteBird = if(birds.length > 0) birds.remove(birds.length - 1)
  
  def getBirds : Buffer[simulatorBird] = return birds
  
  //Shortcut for repainting the view window
  def repaintView = simulatorMainWindow.viewWindow.repaint
  
  var refreshRate = 0
  
  def updateFPS() : Boolean = {
    timer.setDelay(simulatorMainWindow.optionsWindow.speedModel.getValue.toString().toInt)
    return true
  }
  
  val timer = new javax.swing.Timer(refreshRate, Swing.ActionListener(e =>{
	  updateFPS
      if(birds.length > 0 ){
    	  birds.foreach{b => calculateNewPosition(b)}
      }
      repaintView
    }))
  
  def calculateNewPosition(bird : simulatorBird)={
    var x = bird.getPositionX
    var y = bird.getPositionY
    
    //Get localFlock
    var localFlock = getLocalFlock(bird)
    
    //use atan2(y, x)
    
    if(localFlock.length > 1){
	    //Handle possible collisions
	    val xCollision = (x + 20, x - 20)
	    val yCollision = (y + 20, y - 20) //Make collision area adjustable
	    
	    //Handle alignment and centering
	    var flockOrientation = 0.0
	    var flockCenterX = 0
	    var flockCenterY = 0
	    var avoidAdjustments = 0.0
	    
	    localFlock.foreach{ b =>
	      					var otherX = b.getPositionX
	      					var otherY = b.getPositionY
	      					if((otherX < x + 20 && otherX > x - 20) && (otherY < y + 20 && otherY > y - 20)){
	      					  flockOrientation += b.getOrientation 
	      					  flockCenterX += b.getPositionX
	      					  flockCenterY += b.getPositionY
	      					}
	      					if((x < xCollision._1 || x > xCollision._2) ||  (y < yCollision._1 || y > yCollision._2)){
	      					  avoidAdjustments += bird.avoid(b)
	      					}
	      				  }
	   
	    var centerX = flockCenterX / localFlock.length
	    var centerY = flockCenterY / localFlock.length
	    
	    if((centerX - x) == 0){
	      x += 1
	    }
	    if((centerY - y) == 0){
	      y += 1
	    }
	    
	    var centerAdjustment = math.atan((centerX - x) / (centerY - y))
	    var orientationAdjustment = ( flockOrientation / localFlock.length )
	    
	    var real_direction = (avoidAdjustments + centerAdjustment) / 2
	    
	    bird.moveTo(real_direction)
	   	bird.setOrientation((bird.getOrientation + orientationAdjustment) / 2)
	   	
    }else{
    	var to_center = 0.0
    	to_center = math.atan2((x - 400).toDouble, (y - 400).toDouble)
    	println(math.toDegrees(to_center))
    	bird.moveTo(to_center)
    }

  }
  
  //Localflock is the flock of bird neares to current bird. TODO: Make possible to adjust flock size and visualize flock
  def getLocalFlock(bird : simulatorBird) : Buffer[simulatorBird] ={
    var x = bird.getPositionX
    var y = bird.getPositionY
    val xLocal = (x + 100, x - 100)
    val yLocal = (y + 100, y - 100) //Make collision area adjustable
    
    var localFlock = Buffer[simulatorBird]()
    birds.foreach(b => if((b.getPositionX < xLocal._1 && b.getPositionX > xLocal._2) && (b.getPositionY < yLocal._1 && b.getPositionY > yLocal._2)){ localFlock += b })
    print(localFlock.length)
    return localFlock
  }
  
} //Simulator end

class calculateBirdPosition(bird : simulatorBird) extends Runnable {
  //Calculate birds position
  def run(){
	  
  }

}
