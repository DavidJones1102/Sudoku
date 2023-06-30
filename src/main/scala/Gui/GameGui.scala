package Gui

import scalafx.Includes.*
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.*
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.{GridPane, Region, StackPane, VBox}
import Logic.Board
import scala.App
object GameGui {
  private val board: Board = Board.generateRandomWithNEmptyCells(40)
  val scene: Scene = new Scene(500, 600) {
    stylesheets += getClass.getResource("solver.css").toExternalForm
    root = new GridPane{
      private val backButton = Button("Back")
      backButton.onAction = handle {
        App.setScene(MenuGui.scene)
      }

      add(backButton,0,0)

      private val gridPane = new BoardGui(board).getNode
      add(gridPane, 0, 1)
    }
  }
}



