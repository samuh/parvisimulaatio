package simulatorIO
import	java.io.File
import scala.swing._
import scala.swing.event._

class fileManager{
  
  val list = new ListView("DATA".split(""))
  
  def openFile{
    val chooser = new FileChooser
    if(chooser.showOpenDialog(list) == FileChooser.Result.Approve){
      val data = scala.xml.XML.loadFile(chooser.selectedFile)
      
      (data \ "birds").foreach{
        birds => (birds \ "bird").foreach{
          bird => println((bird \ "@x").text)
        }
      }
      
    }
  }
}