package simulator.gui

import scala.swing._
import Swing._
import scala.swing.BorderPanel.Position._
import swing._
import javax.swing.SpinnerNumberModel
import javax.swing.JSpinner
import javax.swing.JFrame
import javax.swing._
import scala.swing.event._
import simulator.logic.simulatorRuntime

class simulatorOptions extends Panel{
  
	val titlePicture = new Label {
	  icon = new ImageIcon("parvisimulaatio.png")
	}

	val title = new BorderPanel(){
	      maximumSize = new Dimension(200, 50)
	      minimumSize = new Dimension(200, 50)
	      layout(titlePicture) = North
    }
  
	/**
	* All the inputs for changing simulator parameters. Possible values from 1 to 100
  	*/
 	val speedModel = new SpinnerNumberModel(10, 1, 100, 1)
 	val speedSpinner = new JSpinner(speedModel)
    val speedFrame = Component.wrap(speedSpinner)
    val speedLabel = new Label{text = "Delay frequency (ms)"}
    
    /** Inputs will be placed inside GridPanel */
    val optionInputs = new GridPanel(3, 2){
      border = Swing.EmptyBorder(0,0,0, 15)
      maximumSize = new Dimension(200, 125)
      minimumSize = new Dimension(200, 125)
      contents += speedLabel
      contents += speedFrame
    }
    
    val inputsLabel = new JLabel("<html><body><h4 style='text-align:center;margin-left:10px;margin-right:10px;'>Control option parameters. Range from 0 to 100 </h4></body></html>")
    val inputsFrame = Component.wrap(inputsLabel);
    
    val inputWindow = new BorderPanel(){
	      maximumSize = new Dimension(200, 125)
	      minimumSize = new Dimension(200, 125)
	      layout(inputsFrame) = North
	      layout(optionInputs) = South
    }
    
    /** All the slider that change simulator values */
    val ruleLabel = new JLabel("<html><body style='width:100%;'><hr><h3 style='text-align:center;margin-left:30px;padding-right:70px;padding-left:35px;'>Rules</h3></body></html>")
    val ruleFrame = Component.wrap(ruleLabel);
    val collisionSize = new Slider{
      min = 0
      max = 100
      value = 65
    }
    val collisionWeight= new Slider{
      min = 0
      max = 100
      value = 100
    }
    val flockareaSize =  new Slider{
      min = 0
      max = 300
      value = 38
    }
    val alignmentWeight =  new Slider{
      min = 0
      max = 100
      value = 26
    }
    val cohesionWeight =  new Slider{
      min = 0
      max = 100
      value = 40
    }
    val targetWeight =  new Slider{
      min = 0
      max = 100
      value = 70
    }
    
    val ruleInputs = new GridPanel(12,1){
      maximumSize = new Dimension(200, 200)
      minimumSize = new Dimension(200, 200)
      contents += new Label{text = "Collision area"}
      contents += collisionSize
      contents += new Label{text = "Collision weight"}
      contents += collisionWeight
      contents += new Label{text = "Flock size"}
      contents += flockareaSize
      contents += new Label{text = "Alignment"}
      contents += alignmentWeight
      contents += new Label{text = "Cohesion"}
      contents += cohesionWeight
      contents += new Label{text = "Target"}
      contents += targetWeight
    }
    
    val ruleWindow = new BorderPanel(){
	      maximumSize = new Dimension(200, 200)
	      minimumSize = new Dimension(200, 200)
	      layout(ruleFrame) = North
	      layout(ruleInputs) = South
	      
    }
    
    /** All the toggles that enable or disable simulator graphics */
    val graphicLabel = new JLabel("<html><body style='width:100%;'><hr><h3 style='text-align:center;margin-left:22px;padding-right:100px;padding-left:35px;'>Graphics</h3></body></html>")
    val graphicFrame = Component.wrap(graphicLabel);

    val toggleCollision = new ToggleButton{
      text = "Collision"
    }
    
    
    val toggleFlock = new ToggleButton{
      text = "Flock area"
    }
    
    val toggleData = new ToggleButton{
      text = "Info"
    }
    
    val graphicInputs = new GridPanel(3, 2){
      maximumSize = new Dimension(200, 100)
      minimumSize = new Dimension(200, 100)
      contents += toggleCollision
      contents += toggleFlock
      contents += toggleData
    }
    
    val graphicWindow = new BorderPanel(){
	      maximumSize = new Dimension(200, 100)
	      minimumSize = new Dimension(200, 100)
	      layout(graphicFrame) = North
	      layout(graphicInputs) = South
	      
    }
    
    /** Start button that starts and stops the simulator **/
    val startButton = new ToggleButton {
      maximumSize = new Dimension(200, 100)
	  minimumSize = new Dimension(200, 100)
      preferredSize = new Dimension(200, 100)
      text = "Run"
      tooltip = "Toggle simulator on and off."
    }
    
    val addBird = new Button {
      text = "Add new bird"
    }
    
    val deleteBird = new Button {
      text = "Delete last bird"
    }
    
    val buttonWindow = new BorderPanel(){
    	maximumSize = new Dimension(200, 150)
	    minimumSize = new Dimension(200, 150)
    	layout(addBird) = North
	    layout(deleteBird) = South
    }
    
    
    /** Main container for all the invidual parts **/
    val optionWindow = new BoxPanel(Orientation.Vertical) {
      contents += title
	  contents += inputWindow
	  contents += VStrut(10)
	  contents += ruleWindow
	  contents += graphicWindow
	  contents += VStrut(10)
	  contents += buttonWindow
   }
    
    val allOptions = new BorderPanel{
    	maximumSize = new Dimension(200, 768)
    	minimumSize = new Dimension(200, 768)
    	layout(optionWindow) = North
    	layout(startButton) = South
    }
    
    this._contents += allOptions
}