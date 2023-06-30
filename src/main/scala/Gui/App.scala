package Gui

import Gui.*
import Gui.App.stage
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.*
import scalafx.scene.layout.*

object App extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Menu"
      scene = MenuGui.scene
    }
  }
  def setScene(scene: Scene): Unit = stage.scene = scene
}