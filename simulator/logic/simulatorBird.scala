package simulator.logic

class simulatorBird(){
  
  private var position : (Int, Int) = (0, 0)
 
  // In radians
  private var orientation : Double = 0.0
  
  //All of the get and set properties 
  
  def getPosition = this.position
  
  def getPositionX = this.position._1
  
  def getPositionY = this.position._2
  
  def setPosition(position : (Int, Int)) = this.position = position
  
  def move(x : Int, y : Int) = {
    println(getPositionX, getPositionY)
    if(getPositionX > 800){
    	setPosition(2, getPositionY + y)
  	}
    else if(getPositionX <= 1){
      setPosition(800, getPositionY + y)
    }
    else if(getPositionY > 670){
      setPosition(getPositionX + x, 2)
    }
    else if(getPositionY <= 1){
      setPosition(getPositionX + x, 670)
    }
    else{
      setPosition(getPositionX + x, getPositionY + y) 
    }
  }
  
  def getOrientation = this.orientation
  
  def setOrientation(orientation : Double) = this.orientation = orientation
  
  def avoid(otherBird : simulatorBird) = 0.0
  
  def moveTo(rad : Double) = {
    //To right 337.5 - 22.5
    if(rad <= 5.89 && rad >= 0.39){
      move(1, 0)
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
    else if(rad > 2.74 && rad <= 3.53){
      move(-1, 0)
    }
    //To leftdown 202.5 - 247.5
    else if(rad > 3.53 && rad <= 4.31){
      move(-1, -1)
    }
    //To down 247.5 - 292.5
    else if(rad > 4.31 && rad <= 5.1){
      move(0, 1)
    }
    else{
      move(1, 1)
    }
    
  }
}