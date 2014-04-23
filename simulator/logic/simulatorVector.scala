package simulator.logic

import scala.math

class simulatorVector {
  
  var x : Double = 0.0
  var y : Double = 0.0
  
  def slope = (y / x)
  
  def directionRadians = math.atan(slope)
  
  def setXY(x : Int,y : Int) = this.x = x; this.y = y 

}