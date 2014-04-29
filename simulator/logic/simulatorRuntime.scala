package simulator.logic

import scala.collection.mutable._
import scala.swing._
import simulator.gui._
import scala.util.Random
import java.awt.Color


class simulatorRuntime() {
  
  val rand = new Random(1024)
  var targetX = 420
  var targetY = 350
  
  var refreshRate = 0
  
  var birds = Buffer[simulatorBird]()
  
  var collisionSize : Double = 65.0
  var collision : Double = 100.0
  var flockSize : Double = 38.0
  var alignment : Double = 26.0
  var cohesion : Double = 40.0
  var target : Double = 70.0
  
  def setTargetX(x : Int) = targetX = x
  
  def setTargetY(y : Int) = targetY = y

  /**
   * Create new bird at random location
   */
  def createBird() = {
    var bird = new simulatorBird()
    bird.setPosition(rand.nextInt(819), rand.nextInt(768))
    addBird(bird)
  }
  
  /**
   * Add bird to simulatorRuntime birds
   */
  def addBird(bird : simulatorBird) ={
    birds.append(bird)
    repaintView
  }
  
  /**
   * Delete last bird from simulatorRuntime birds
   */
  def deleteBird ={
    if(birds.length > 0){
      birds.remove(birds.length - 1)
      repaintView
    } 
  }
  
  /**
   * Return all simulatorRuntime birds
   */
  def getBirds : Buffer[simulatorBird] = return birds
  
  /**
   * Repaint the view window
   */
  def repaintView = simulatorMainWindow.viewWindow.repaint
  
  /**
   * Update the delay of timer function from optionWindow slider
   */
  def updateFPS() : Boolean = {
    timer.setDelay(simulatorMainWindow.optionsWindow.speedModel.getValue.toString().toInt)
    return true
  }
  
  /**
   * Move the birds and repaint the view
   */
  val timer = new javax.swing.Timer(refreshRate, Swing.ActionListener(e =>{
	  updateFPS
      if(birds.length > 0 ){
    	  birds.foreach{b => calculateNewPosition(b)}
      }
      repaintView
    }))
  
  /**
   * Count distance between to point (via Pythagora)
   */
  def distance(x : Int, y : Int) : Double = math.sqrt(math.pow(x, 2) + math.pow(y, 2))
  
  /** Calculate new bird position taking account all of the rules
   *  
   *  Birds are moved simply by turning and then moving them to direction of their orientation
   *  All of the data is gathered from local flock
   *  
   *  Other birds from the flock that bird should try to avoid
   *  Flock center that bird should move towards to
   *  Flock orientation that bird should try to match
   * 
   */
  def calculateNewPosition(bird : simulatorBird)={
    var x = bird.getPositionX
    var y = bird.getPositionY
    
    /** Get local flock */
    var localFlock = getLocalFlock(bird)
    
    if(localFlock.length > 1){
	    /** Set collision limits */
	    
	    /** Initialize rule parameters */
	    var flockOrientation = 0.0
	    var flockCenterX = 0
	    var flockCenterY = 0
	    var avoidAdjustments = 0.0
	    
	    localFlock.foreach{ b =>
	      					if(b != bird){
		      					var otherX = b.getPositionX
		      					var otherY = b.getPositionY
		      					flockOrientation += b.getOrientation 
		      					flockCenterX += b.getPositionX
		      					flockCenterY += b.getPositionY
		      					if( distance(math.abs(otherX - x), math.abs(otherY - y)) < collision){
		      						bird.avoid(b)
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
	    
	    var coordDifference = bird.distanceTo(targetX, targetY)
    	var to_target = math.atan2(coordDifference._2, coordDifference._1)
    	bird.turn(to_target, ((distance(coordDifference._1, coordDifference._2) / 100.0)) * (simulatorMainWindow.simulator.target / 100))
	    
	    var centerDifference = bird.distanceTo(centerX, centerY)
	    var centerAdjustment = math.atan2(centerDifference._2, centerDifference._1)
	    bird.turn(centerAdjustment, (simulatorMainWindow.simulator.cohesion / 70))
	    
	    var orientationAdjustment = ( flockOrientation / localFlock.length )
	   	bird.turn(orientationAdjustment, (simulatorMainWindow.simulator.alignment / 70))
	   	bird.moveToDirection
	   	
    }else{
    	/** Lonely bird should move towards the target */
    	var coordDifference = bird.distanceTo(targetX, targetY)
    	var to_target = math.atan2(coordDifference._2, coordDifference._1)
    	bird.turn(to_target, (1.0 / (distance(coordDifference._1, coordDifference._2) / 100)) + (simulatorMainWindow.simulator.target / 100))
    	bird.moveToDirection
    }

  }
  /** Get local flock of the bird
   *  
   *  @param bird local flock of this bird
   * 
   * Local flock is the nearest birds of the current bird.
   * The current bird should take to account the orientation
   * and flock center of this flock if those parameters are true
   * 
   * The current bird IS part of the flock, so local flock length 
   * will always be at least one
   */

  def getLocalFlock(bird : simulatorBird) : Buffer[simulatorBird] ={
    var x = bird.getPositionX
    var y = bird.getPositionY
    val xLocal = (x + flockSize, x - flockSize)
    val yLocal = (y + flockSize, y - flockSize)
    
    var localFlock = Buffer[simulatorBird]()
    birds.foreach{b => 
      				if((b.getPositionX < xLocal._1 && b.getPositionX > xLocal._2) && (b.getPositionY < yLocal._1 && b.getPositionY > yLocal._2)){ 
      				  localFlock += b
      				}}
    return localFlock
  }
}
