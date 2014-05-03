package simulator.unittest

import org.junit.Test
import org.junit.Assert._
import simulator.logic._

  /** TESTS:
   *  - Bird set parameters
   *  - Simulator add and delete bird
   *  - Bird move to right directions
   */

class UnitTests {

  val simulator = new simulatorRuntime()
  val bird = new simulatorBird()
  
  /**
   * Test that bird properties are set properly
   */
  @Test def setPosition() {
    bird.setPosition((400, 400))
    assertTrue(bird.getPositionX == 400 && bird.getPositionY == 400)
  }
  
  @Test def setOrientation() {
    bird.setOrientation(3.14)
    assertTrue(bird.getOrientation == 3.14)
  }
  
  /**
   * Test that bird is successfully added to simulatorRuntime
   */
  @Test def addBird() {
	simulator.addBird(bird)
	assertTrue(simulator.birds.contains(bird))
  }
  
  @Test def deleteBird() {
    simulator.deleteBird
    assertFalse(simulator.birds.contains(bird))
  }
  
  /**
   * Test that bird moves correctly to all directions
   */
  bird.setPosition((400, 400))
  
  /** East */
  @Test def testEast() {
    val originalPosition = bird.getPosition
    bird.setOrientation(0.39)
    bird.moveToDirection
    assertTrue((bird.getPositionX - originalPosition._1 == 1) && (bird.getPositionY - originalPosition._2 == 0))
  }
  
  /** North-East */
  @Test def testNorthEast() {
    val originalPosition = bird.getPosition
    bird.setOrientation(1.0)
    bird.moveToDirection
    assertTrue((bird.getPositionX - originalPosition._1 == 1) && (bird.getPositionY - originalPosition._2 == -1))
  }

  /** North */
  @Test def testNorth() {
    val originalPosition = bird.getPosition
    bird.setOrientation(1.7)
    bird.moveToDirection
    assertTrue((bird.getPositionX - originalPosition._1 == 0) && (bird.getPositionY - originalPosition._2 == -1))
  }
  
  /** North-West */
  @Test def testNorthWest() {
    val originalPosition = bird.getPosition
    bird.setOrientation(2.0)
    bird.moveToDirection
    assertTrue((bird.getPositionX - originalPosition._1 == -1) && (bird.getPositionY - originalPosition._2 == -1))
  }
  
  /** West */
  @Test def testWest() {
    val originalPosition = bird.getPosition
    bird.setOrientation(3.0)
    bird.moveToDirection
    assertTrue((bird.getPositionX - originalPosition._1 == -1) && (bird.getPositionY - originalPosition._2 == 0))
  }
  
  /** South-West */
  @Test def testSouthWest() {
    val originalPosition = bird.getPosition
    bird.setOrientation(4.0)
    bird.moveToDirection
    assertTrue((bird.getPositionX - originalPosition._1 == -1) && (bird.getPositionY - originalPosition._2 == 1))
  }
  
  /** South */
  @Test def testSouth() {
    val originalPosition = bird.getPosition
    bird.setOrientation(5.0)
    bird.moveToDirection
    assertTrue((bird.getPositionX - originalPosition._1 == 0) && (bird.getPositionY - originalPosition._2 == 1))
  }
  
  /** South-East */
  @Test def testSouthEast() {
    val originalPosition = bird.getPosition
    bird.setOrientation(5.8)
    bird.moveToDirection
    assertTrue((bird.getPositionX - originalPosition._1 == 1) && (bird.getPositionY - originalPosition._2 == 1))
  }

}