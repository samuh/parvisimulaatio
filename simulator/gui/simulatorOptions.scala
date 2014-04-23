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
  
	val titleLabel = new JLabel("<html><body><h2 style='margin-left:25px;padding-right:25px;'>Parvisimulaattori</h2><p style='margin-left:45px;padding-right:25px;'>by Samu Hautala</p><hr></body></html>")
    val titleFrame = Component.wrap(titleLabel);
	
	val title = new BorderPanel(){
	      maximumSize = new Dimension(200, 50)
	      minimumSize = new Dimension(200, 50)
	      layout(titleFrame) = North
    }
  
	//All the inputs for changing simulator parameters. Possible values from 1 to 99
	//Particle speed input and label
  
 	val speedModel = new SpinnerNumberModel(34, 1, 100, 1)
	
 	val speedSpinner = new JSpinner(speedModel)
    val speedFrame = Component.wrap(speedSpinner)
    val speedLabel = new Label{text = "Speed (ms)"}
    
    //val collisionSpinner = new JSpinner(collisionModel);
    //val collisionFrame = Component.wrap(collisionSpinner);
    //val collisionLabel = new Label{text = "Collision area"}
    
    //val turnrateSpinner = new JSpinner(turnrateModel);
    //val turnrateFrame = Component.wrap(turnrateSpinner);
    //val turnrateLabel = new Label{text = "Turnrate"}
    
    //Inputs will be placed inside GridPanel
    val optionInputs = new GridPanel(3, 2){
      border = Swing.EmptyBorder(0,0,0, 15)
      maximumSize = new Dimension(200, 125)
      minimumSize = new Dimension(200, 125)
      contents += speedLabel
      contents += speedFrame
      //contents += speedFrame
      //contents += collisionLabel
      //contents += collisionFrame
      //contents += turnrateLabel
      //contents += turnrateFrame
    }
    
    val inputsLabel = new JLabel("<html><body><h4 style='text-align:center;margin-left:10px;margin-right:10px;'>Control option parameters. Range from 0 to 10 </h4></body></html>")
    val inputsFrame = Component.wrap(inputsLabel);
    
    val inputWindow = new BorderPanel(){
	      maximumSize = new Dimension(200, 125)
	      minimumSize = new Dimension(200, 125)
	      layout(inputsFrame) = North
	      layout(optionInputs) = South
    }
    
    //All the toggles that enable or disable simulator rules
    val ruleLabel = new JLabel("<html><body style='width:100%;'><hr><h3 style='text-align:center;margin-left:30px;padding-right:70px;padding-left:35px;'>Rules</h3></body></html>")
    val ruleFrame = Component.wrap(ruleLabel);
    
    val collision = new JScrollBar(0, 50, 10, 0, 100)
    val alignment = new JScrollBar(0, 50, 10, 0, 100)
    val cohesion = new JScrollBar(0, 50, 10, 0, 100)
    
    val collisionFrame = Component.wrap(collision);
    val alignmentFrame = Component.wrap(alignment);
    val cohesionFrame = Component.wrap(cohesion);
    
    val ruleInputs = new GridPanel(6,1){
      maximumSize = new Dimension(200, 125)
      minimumSize = new Dimension(200, 125)
      contents += new Label{text = "Collision area"}
      contents += collisionFrame
      contents += new Label{text = "Alignment"}
      contents += alignmentFrame
      contents += new Label{text = "Cohesion"}
      contents += cohesionFrame
    }
    
    val ruleWindow = new BorderPanel(){
	      maximumSize = new Dimension(200, 125)
	      minimumSize = new Dimension(200, 125)
	      layout(ruleFrame) = North
	      layout(ruleInputs) = South
	      
    }
    
    //All the toggles that enable or disable simulator rules
    val graphicLabel = new JLabel("<html><body style='width:100%;'><hr><h3 style='text-align:center;margin-left:30px;padding-right:50px;padding-left:35px;'>Graphics</h3></body></html>")
    val graphicFrame = Component.wrap(graphicLabel);

    val toggleCollisionGraphic = new ToggleButton{
      text = "Collision"
    }
    
    val toggleSpeedGraphic = new ToggleButton{
      text = "Speed vector"
    }
    
    val toggleForceGraphic = new ToggleButton{
      text = "Force vector"
    }
    
    val toggleViewGraphic = new ToggleButton{
      text = "Viewarea"
    }
    
    val graphicInputs = new GridPanel(3, 2){
      maximumSize = new Dimension(200, 150)
      minimumSize = new Dimension(200, 150)
      contents += toggleCollisionGraphic
      contents += toggleSpeedGraphic
      contents += toggleForceGraphic
      contents += toggleViewGraphic
    }
    
    val graphicWindow = new BorderPanel(){
	      maximumSize = new Dimension(200, 150)
	      minimumSize = new Dimension(200, 150)
	      layout(graphicFrame) = North
	      layout(graphicInputs) = South
	      
    }
   
    
    //Start button that starts and stops the simulator
    val startButton = new ToggleButton {
      maximumSize = new Dimension(200, 160)
	  minimumSize = new Dimension(200, 160)
      preferredSize = new Dimension(200, 160)
      text = "Start"
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
    
    
    //Main container for all the invidual parts
    val optionWindow = new BoxPanel(Orientation.Vertical) {
      contents += title
	  contents += inputWindow
	  contents += VStrut(10)
	  contents += ruleWindow
	  contents += graphicWindow
	  contents += VStrut(50)
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