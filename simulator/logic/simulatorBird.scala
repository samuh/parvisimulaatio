package simulator.logic

class simulatorBird(){
  
  private var position : (Int, Int) = (0, 0) //(X, Y)
  private var orientation : Double = 0.0 //In Radians
  private var turnRate : Double = 0.2
  
  //All of the get and set properties 
  
  def getPosition = this.position
  
  def getPositionX = this.position._1
  
  def getPositionY = this.position._2
  
  def setPosition(position : (Int, Int)) = this.position = position
  
  def getOrientation = this.orientation
  
  def setOrientation(orientation : Double) = this.orientation = orientation
  
  def getTurnRate = this.turnRate
  
  def avoid(otherBird : simulatorBird) ={
    var distanceToOther = distanceTo(-otherBird.getPositionX, -otherBird.getPositionY)
    var weight = 0.3//simulator.gui.simulatorMainWindow.simulator.distance(distanceToOther._1, distanceToOther._2) / 100
    var avoid = math.atan2(distanceToOther._2, distanceToOther._1)
    turn(avoid, weight)
  }
  
  def distanceTo(xTo : Int, yTo : Int) : (Int,Int)={
	  var x = getPositionX
	  var y = getPositionY
	  if(x == 0){
	    x = 1
	  }
	  if(y == 0){
	    y = 1
	  }
	  if(x < xTo && y > yTo){		//Target right up
	    return((xTo - x), (y - yTo)) 
	  }
	  else if(x > xTo && y > yTo){ 	//Target left up
	    return(-(x - xTo), (y - yTo))
	  }
	  else if(x > xTo && y < yTo){	//Target left down
	    return(-(x - xTo), -(yTo - y))
	  }
	  else{							//Target right down
	    return((xTo - x), -(yTo - y))
	  }
  }
  
  def turn(newDirection : Double, weight : Double) ={
    var currentOrientation = getOrientation
    var turnTo = newDirection
    var newOrientation = 0.0
    
    //Adjust radians so that [0, 2Pi] is covered
    if(turnTo <= 0.0){
      turnTo = (math.Pi * 2) + turnTo
    }
    
    //println(turnTo)
    //Rajatapaus 0 ja 2Pi jolloin syntyy "aalto" koska lintu ei osaa päättää suunta
    //Tämä kuntoon niin valmis
    
    if(turnTo < currentOrientation && (currentOrientation > turnTo + 0.1 || currentOrientation < turnTo - 0.1)){
    	println("turnTo smalle than current")
		if(currentOrientation - turnTo < ((math.Pi * 2) - currentOrientation + turnTo)){
		  newOrientation = (currentOrientation - (weight * 0.01))
		}else{
		  newOrientation = (currentOrientation + (weight * 0.01))
		}
		if(newOrientation <= 0){
		  setOrientation((math.Pi * 2) - newOrientation)
		}else{
		  setOrientation(newOrientation)
		}
    }
    
    if(turnTo > currentOrientation && (currentOrientation > turnTo + 0.1 || currentOrientation < turnTo - 0.1)){
		if(turnTo - currentOrientation < ((math.Pi * 2) - turnTo + currentOrientation)){
		  newOrientation = currentOrientation + (weight * 0.01)
		}else{
		  newOrientation = (currentOrientation - (weight * 0.01))
		}
		if(newOrientation >= (math.Pi * 2)){
		  setOrientation(0 + newOrientation)
		}else{
		  setOrientation(newOrientation)
		}
    }
  }
  
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
  
  def moveToDirection = {
    var rad = getOrientation
    //To right 337.5 - 22.5
    if(rad > 5.08 && rad <= (math.Pi * 2)){
      move(1, 1)
    }
    //To upright 22.5 - 67.5
    else if(rad > 0.39 && rad <= 1.178){
      move(1, -1)
    }
    //To up 67.5 - 112.5
    else if(rad > 1.178 && rad <= 1.96){
      move(0, -1)
    }
    //To upleft 112.5 - 157.5
    else if(rad > 1.96 && rad <= 2.74){
      move(-1, -1)
    }
    //To left 157.5 - 202.5
    else if(rad > 2.74 && rad <= 3.51){
      move(-1, 0)
    }
    //To leftdown 202.5 - 247.5
    else if(rad > 3.52 && rad <= 4.3){
      move(-1, 1)
    }
    //To down 247.5 - 292.5
    else if(rad > 4.3 && rad <= 5.08){
      move(0, 1)
    }
    else{
      move(1, 0)
    }
  }
}