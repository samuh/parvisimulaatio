package simulator.logic

class simulatorBird(){
  
  private var position : (Int, Int) = (0, 0)
  
  //Acceleration tuple (speed, direction)
  private var acceleration : (Int, Int) = (0, 0)
  private var speed : Double = 1.0
  private var orientation : Int = 0
  
  private var max_force : Int = 0
  private var max_speed : Int = 0
  
  def getPosition = this.position
  
  def getPositionX = this.position._1
  
  def getPositionY = this.position._2
  
  def setPosition(position : (Int, Int)) = this.position = position
  
  def getOrientation = this.orientation
  
  def setOrientatio(orientation : Int) = this.orientation = orientation
  
  def getSpeed = this.speed
  
  def setSpeed(speed : Double) = this.speed = speed
  
  def getAcceleration = this.acceleration
  
  def setAcceleration(acceleration : (Int, Int)) = this.acceleration = acceleration
  
  def avoid(otherBird : simulatorBird) = ???
}