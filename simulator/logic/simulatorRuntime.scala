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

  var targetX = 420
  var targetY = 350
  
  def setTargetX(x : Int) = targetX = x
  
  def setTargetY(y : Int) = targetY = y
  
  var birds = Buffer[simulatorBird]()

  //Simulator parameters saved as variables
  var speed : Int = 50
  var mass : Int = 50
  var turnRate : Int = 50
  
  //Rules 
  var collision : Double = 0.0
  var alignment : Double = 0.0
  var cohesion : Double = 0.0 
  var flock : Double = 0.0
  var target : Double = 0.0

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
  
  def distance(x : Int, y : Int) : Double = math.sqrt(math.pow(x, 2) + math.pow(y, 2))
  
  def calculateNewPosition(bird : simulatorBird)={
    var x = bird.getPositionX
    var y = bird.getPositionY
    
    //Get localFlock
    var localFlock = getLocalFlock(bird)
    
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
	      					if(b != bird){
		      					var otherX = b.getPositionX
		      					var otherY = b.getPositionY
		      					if((otherX < x + 50 && otherX > x - 50) && (otherY < y + 50 && otherY > y - 50)){
		      					  flockOrientation += b.getOrientation 
		      					  flockCenterX += b.getPositionX
		      					  flockCenterY += b.getPositionY
		      					}
		      					if((x < xCollision._1 || x > xCollision._2) &&  (y < yCollision._1 || y > yCollision._2)){
		      						//bird.avoid(b)
		      					}
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
	    
	    var centerDifference = bird.distanceTo(centerX, centerY)
	    var centerAdjustment = math.atan2(centerDifference._2, centerDifference._1)
	    //bird.turn(centerAdjustment, 0.3)
	    
	    var coordDifference = bird.distanceTo(targetX, targetY)
    	var to_target = math.atan2(coordDifference._2, coordDifference._1)
    	bird.turn(to_target, (distance(coordDifference._1, coordDifference._2) / 100))
	    
	    var orientationAdjustment = ( flockOrientation / localFlock.length )
	   	//bird.turn(orientationAdjustment, 0.1)
	   	bird.moveToDirection
    }else{
    	//Lonely bird should move towards the target
    	var coordDifference = bird.distanceTo(targetX, targetY)
    	var to_target = math.atan2(coordDifference._2, coordDifference._1)
    	bird.turn(to_target, (distance(coordDifference._1, coordDifference._2) / 100))
    	bird.moveToDirection
    }

  }
  
  //Localflock is the flock of bird neares to current bird. TODO: Make possible to adjust flock size and visualize flock
  def getLocalFlock(bird : simulatorBird) : Buffer[simulatorBird] ={
    var x = bird.getPositionX
    var y = bird.getPositionY
    val xLocal = (x + 50, x - 50)
    val yLocal = (y + 50, y - 50) //Make collision area adjustable
    
    var localFlock = Buffer[simulatorBird]()
    birds.foreach(b => if((b.getPositionX < xLocal._1 && b.getPositionX > xLocal._2) && (b.getPositionY < yLocal._1 && b.getPositionY > yLocal._2)){ localFlock += b })
    return localFlock
  }
  
  
} //Simulator end

class calculateBirdPosition(bird : simulatorBird) extends Runnable {
  //Calculate birds position
  def run(){
	  
  }

}
