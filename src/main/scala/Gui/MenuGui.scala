package Gui

import Gui.GameGui
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{StackPane, VBox}

object MenuGui {
  val scene: Scene = new Scene(500, 500) {
    stylesheets += getClass.getResource("styles.css").toExternalForm

    val solverButton = new Button("Solver")
    solverButton.onAction = _ => {
      App.setScene(SolverGui.scene)
    }

    val newGameButton = new Button("New game")
    newGameButton.onAction = _ => {
      App.setScene(GameGui.scene)
    }
    val menuBox = new VBox(solverButton, newGameButton)
    val rootPane = new StackPane()
    menuBox.alignment = Pos.Center
    rootPane.children.add(menuBox)

    root = rootPane
  }
}
