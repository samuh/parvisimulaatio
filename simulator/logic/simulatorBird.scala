package simulator.logic

import simulator.gui.simulatorMainWindow
import java.awt.Color

class simulatorBird(){
  
  /** Position in (x, y) */
  private var position : (Int, Int) = (0, 0)
  
  /** Orientation in radians */
  private var orientation : Double = 0.0
  
  /** Turnrate in radians */
  private var turnRate : Double = 0.2
  
  private var color : Color = Color.white
  
  def toXml = <bird x={this.getPositionX.toString} y={this.getPositionY.toString} orientation={this.getOrientation.toString} />
  
  def getPosition = this.position
  
  def getPositionX = this.position._1
  
  def getPositionY = this.position._2
  
  def setPosition(position : (Int, Int)) = this.position = position
  
  def getOrientation = this.orientation
  
  def setOrientation(orientation : Double) = this.orientation = orientation
  
  def getColor = this.color
  
  def setColor(newColor : Color) = this.color = newColor 
  
  /** Avoid other bird
   * 
   * @param otherBird bird to avoid
   * 
   * Gets distance to other bird, assigns weight how strongly to avoid
   * and then takes action by turning to opposite direction
   */
  def avoid(otherBird : simulatorBird) ={
    var distanceToOther = distanceTo(otherBird.getPositionX, otherBird.getPositionY)
    var weight = (1.0 / (distance(distanceToOther._1, distanceToOther._2) / 100)) * (simulatorMainWindow.simulator.collision / 100)
    var avoid = math.atan2(-distanceToOther._2, -distanceToOther._1)
    turn(avoid, weight)
  }
  
  /**
   * Count distance (hypotenusa) with two sides (via Pythagora)
   * 
   * @param x horizontal length
   * @param y vertical length
   */
  def distance(x : Int, y : Int) : Double = math.sqrt(math.pow(x, 2) + math.pow(y, 2))
  
  /**Count distance to other point in coordinates
   * 
   * @param xTo coordinate x of point of interest
   * @param yTo coordinate y of point of interest
   * 
   * @return tuple of true coordinate difference
   * 
   */
  def distanceTo(xTo : Int, yTo : Int) : (Int,Int)={
	  var x = getPositionX
	  var y = getPositionY
	  if(x == 0){
	    x = 1
	  }
	  if(y == 0){
	    y = 1
	  }
	  /** Target right up */
	  if(x < xTo && y > yTo){		
	    return((xTo - x), (y - yTo)) 
	  }
	  /** Target left up */
	  else if(x > xTo && y > yTo){ 	
	    return(-(x - xTo), (y - yTo))
	  }
	  /** Target left down */
	  else if(x > xTo && y < yTo){	
	    return(-(x - xTo), -(yTo - y))
	  }
	  /** Target right down */
	  else{							
	    return((xTo - x), -(yTo - y))
	  }
  }
  
  /** Turn bird to new direction
   *  
   *  @param newDirection new direction in radians
   *  @param weight how much of turn is applied
   *  
   *  Takes new direction as radians
   *  If new direction is negative adjust so that
   *  it is full circle
   *  
   *  ex. -1.34 -> 4.65
   *  
   *  Turn to direction, left or right, the shortest way
   * 
   */
  def turn(newDirection : Double, weight : Double) ={
    var currentOrientation = getOrientation
    var turnTo = newDirection
    var newOrientation = 0.0
    
    if(turnTo <= 0.0){
      turnTo = (math.Pi * 2) + turnTo
    }
    if(turnTo < currentOrientation && (currentOrientation < turnTo + 20 || currentOrientation > turnTo - 20)){
    	if(math.abs(currentOrientation - turnTo) > math.Pi){
    	  newOrientation = currentOrientation + (weight * 0.01)
    	  if(newOrientation > math.Pi * 2){
    	    setOrientation(weight * 0.01)
    	  }else{
    	    setOrientation(newOrientation)
    	  }
    	}else{
    	  newOrientation = (currentOrientation - (weight * 0.01))
    	  if(newOrientation < 0){
    	    setOrientation((math.Pi * 2) - (weight * 0.01))
    	  }else{
    	    setOrientation(newOrientation)
    	  }
    	}
    }
    if(turnTo > currentOrientation && (currentOrientation < turnTo + 20 || currentOrientation > turnTo - 20)){
		if(math.abs(turnTo - currentOrientation) > math.Pi){
    	  newOrientation = (currentOrientation - (weight * 0.01))
    	  if(newOrientation < 0){
    	    setOrientation((math.Pi * 2) - (weight * 0.01))
    	  }else{
    	    setOrientation(newOrientation)
    	  }
    	}else{
    	   newOrientation = currentOrientation + (weight * 0.01)
    	  if(newOrientation > math.Pi * 2){
    	    setOrientation(weight * 0.01)
    	  }else{
    	    setOrientation(newOrientation)
    	  }
    	}
    }
  }
  
  /** Moves bird to x, y location
   *  
   * @param x new x coordinate
   * @param y new y coordinate
   * 
   * Changes x and y coordinates of the bird
   * Takes in account edge cases when bird flies
   * over the edge -> appears from the other side
   */
  def move(x : Int, y : Int) = {
    if(getPositionX  > 808){
    	setPosition(2, getPositionY + y)
  	}
    else if(getPositionX < 1){
      setPosition(808, getPositionY + y)
    }
    else if(getPositionY  > 718){
      setPosition(getPositionX + x, 2)
    }
    else if(getPositionY < 1){
      setPosition(getPositionX + x, 718)
    }
    else{
      setPosition(getPositionX + x, getPositionY + y) 
    }
  }
  
  /** Moves bird to specified direction via current orientation
   *  
   *  Covers full circle in radians [0, 2Pi]
   *  Move only one step
   */
  def moveToDirection = {
    var rad = getOrientation
    /** To right 337.5 - 22.5 */
    if(rad > 5.08 && rad <= (math.Pi * 2)){
      move(1, 1)
    }
    /** To upright 22.5 - 67.5 */
    else if(rad > 0.39 && rad <= 1.178){
      move(1, -1)
    }
    /** To up 67.5 - 112.5 */
    else if(rad > 1.178 && rad <= 1.96){
      move(0, -1)
    }
    /** To up left 112.5 - 157.5 */
    else if(rad > 1.96 && rad <= 2.74){
      move(-1, -1)
    }
    /** To left 157.5 - 202.5 */
    else if(rad > 2.74 && rad <= 3.51){
      move(-1, 0)
    }
    /** To left down 202.5 - 247.5 */
    else if(rad > 3.51 && rad <= 4.3){
      move(-1, 1)
    }
    /** To down 247.5 - 292.5 */
    else if(rad > 4.3 && rad <= 5.08){
      move(0, 1)
    }
    /** To right */
    else{
      move(1, 0)
    }
  }
}