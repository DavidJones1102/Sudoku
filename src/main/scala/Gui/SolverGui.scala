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
  private var solutionNumber = 0
  val scene: Scene = new Scene(500, 600) {
    stylesheets += getClass.getResource("solver.css").toExternalForm

    root = new GridPane(){
      private val board: Board = Board()
      private val boardGui = new BoardGui(board)
      add(boardGui.getNode, 0, 1)

      private val backButton = Button("Back")
      backButton.onAction = _ => {
        App.setScene(MenuGui.scene)
      }


      private val footerButtonPane = new GridPane()
      footerButtonPane.alignment = Pos.Center


      private val nextSolution = Button("->")
      nextSolution.onAction = _ => {
        solutionNumber = (solutionNumber+1) % solutions.length
        board.fillBoard(solutions(solutionNumber))
      }
      nextSolution.setVisible(false);

      private val prevSolution = Button("<-")
      prevSolution.onAction = _ => {
        solutionNumber -= 1
        if solutionNumber<0 then solutionNumber = solutions.length-1
        board.fillBoard(solutions(solutionNumber))
      }
      prevSolution.setVisible(false)

      private val solveButton = Button("Solve")
      solveButton.onAction = _ => {
        if Solver.validBoard(board.array) then {
          solutions = Solver.solve(board.array)
          solutionNumber = 0
          prevSolution.setVisible(solutions.length > 1)
          nextSolution.setVisible(solutions.length > 1)
          board.fillBoard(solutions(solutionNumber))
        }
        else{
          new Alert(AlertType.Information, "Invalid board").showAndWait()
        }
      }

      private val resetButton = Button("Reset")
      resetButton.onAction = _ => {
        board.resetBoard()
        solutions = null
        prevSolution.setVisible(false)
        nextSolution.setVisible(false)
        solutionNumber = 0
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

