package Gui

import Gui.BoardElementGui
import Logic.Board

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Control, Label, TextField}
import scalafx.scene.layout.{GridPane, VBox}

class BoardGui(board: Board) {
  board.setObserver(this)

  private val textFieldsArray = Array.ofDim[BoardElementGui](9, 9)

  def getNode: GridPane = {
    val cellSize = 50

    val gridPane = new GridPane()

    gridPane.padding = Insets(10)
    gridPane.hgap = 1
    gridPane.vgap = 1

    for (box <- 0 until 9) {
      val miniGrid = new GridPane()
      val vbox = new VBox(miniGrid)
      vbox.getStyleClass.add("vbox")
      for (row <- 0 until 3; col <- 0 until 3) {
        val actualRow = row + box % 3 * 3
        val actualCol = col + box / 3 * 3
        val field = createField(actualRow, actualCol)
        textFieldsArray(actualRow)(actualCol) = field

        field.prefWidth(cellSize)
        field.prefHeight(cellSize)
        miniGrid.add(field.getNode, col, row)
      }
      gridPane.add(vbox, box / 3, box % 3 + 1)
    }
    gridPane.alignment = Pos.Center
    gridPane
  }

  private def createField(row: Int, col: Int): BoardElementGui = {
    val num = board.array(row)(col)
    num match
      case 0 =>
        val textField = new TextField()
        textField.textProperty().addListener { (_, oldValue, newValue) =>
          if (!newValue.matches("\\d*")) {
            textField.setText(newValue.replaceAll("[^\\d]", ""))
          }
          else {
            try {
              board.array(row)(col) = textField.getText().toInt
            }
            catch {
              case e: Exception => board.array(row)(col) = 0
            }
          }
        }
        new TextFieldElement(textField)
      case _ => new LabelElement( new Label("      " + num.toString) )
  }

  def draw(): Unit = {
    for (row <- 0 until 9; col <- 0 until 9) {
      val num = board.array(row)(col)
      var text: String = ""
      if num > 0 then text = num.toString
      textFieldsArray(row)(col).setText(text)
    }
  }
}
