package Gui

import Gui.{BoardGui, MenuGui}
import Logic.{Board, Solver}
import scalafx.Includes.*
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Label, TextField}
import scalafx.scene.layout.{GridPane, Region, StackPane, VBox}

object SolverGui {
  private var solutions: Array[Array[Array[Int]]] = null
  private var solutionNumber = 0;
  val scene: Scene = new Scene(500, 600) {
    stylesheets += getClass.getResource("solver.css").toExternalForm

    root = new GridPane(){
      private val board: Board = Board()
      private val boardGui = new BoardGui(board)
      add(boardGui.getNode, 0, 1)

      private val backButton = Button("Back")
      backButton.onAction = handle {
        App.setScene(MenuGui.scene)
      }


      private val footerButtonPane = new GridPane()
      footerButtonPane.alignment = Pos.Center

      private val solveButton = Button("Solve")
      solveButton.onAction = handle {
        solutions = Solver.solve(board.array)
        board.fillBoard(solutions(solutionNumber))
      }
      private val nextSolution = Button("->")
      nextSolution.onAction = handle {
        solutionNumber = (solutionNumber+1) % solutions.length
        board.fillBoard(solutions(solutionNumber))
      }
      private val prevSolution = Button("<-")
      prevSolution.onAction = handle {
        solutionNumber = (solutionNumber - 1)
        if solutionNumber<0 then solutionNumber = solutions.length-1
        board.fillBoard(solutions(solutionNumber))
      }
      private val resetButton = Button("Reset")
      resetButton.onAction = handle {
        board.resetBoard()
      }
      footerButtonPane.add(prevSolution,0,0)
      footerButtonPane.add(solveButton,1,0)
      footerButtonPane.add(nextSolution,2,0)
      footerButtonPane.add(resetButton,1,1)


      add(backButton, 0, 0)
      add(footerButtonPane, 0, 2)

    }
  }
}

