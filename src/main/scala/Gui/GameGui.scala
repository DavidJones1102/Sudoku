package Gui
import Logic.Solver
import scalafx.Includes.*
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.*
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.{GridPane, Region, StackPane, VBox}
import Logic.Board
import scalafx.stage.StageStyle

import scala.App
object GameGui {
  var board: Board = null
  val scene: Scene = new Scene(500, 600) {
    stylesheets += getClass.getResource("solver.css").toExternalForm
    root = new GridPane {
      private val backButton = Button("Back")
      backButton.onAction = handle {
        App.setScene(MenuGui.scene)
      }

      add(backButton, 0, 0)

      private var gridPane = newGame()
      add(gridPane, 0, 1)

      private val footerButtonPane = new GridPane()
      footerButtonPane.alignment = Pos.Center

      private val newGameButton = Button("New game")
      newGameButton.onAction = handle {
        children.remove(gridPane)
        gridPane = newGame()
        add(gridPane, 0, 1)
      }

      private val checkButton = Button("Check")
      checkButton.onAction = handle {
        if !Solver.filledBoard(board.array) then {
          new Alert(AlertType.Information, "Fill all cells").showAndWait()
        }
        else if Solver.validBoard(board.array) then {
          new Alert(AlertType.Information, "Perfect!").showAndWait()
        }
        else{
          new Alert(AlertType.Information, "Incorrect :'(").showAndWait()
        }
      }
      footerButtonPane.add(checkButton, 0, 0)
      footerButtonPane.add(newGameButton, 0, 1)
      add(footerButtonPane, 0, 2)
    }
  }

  private def newGame(n: Int = 40): GridPane = {
    board = Board.generateRandomWithNEmptyCells(createPopUpInputField())
    val gridPane = new BoardGui(board).getNode
    gridPane
  }

  private def createPopUpInputField(): Int = {
    val dialog = new TextInputDialog()
    dialog.setResult("40")
    dialog.title = "Difficulty"
    dialog.headerText = "How many fields should be empty?"
    dialog.initStyle(StageStyle.Undecorated)

    val result = dialog.showAndWait()
    var number: Int = 40
    result match {
      case Some(value) =>
        if (value.matches("\\d+")) number = value.toInt
      case None =>
    }
    number
  }
}



