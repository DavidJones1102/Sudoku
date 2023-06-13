import scalafx.Includes.handle
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.{GridPane, Region, StackPane, VBox}

object SolverGui {
    private val boardSize = 9
    private val cellSize = 50
    val board: Board = Board()

    private val gridPane = new GridPane()
    gridPane.padding = Insets(10)
    gridPane.hgap = 1
    gridPane.vgap = 1

    for (row <- 0 until boardSize; col <- 0 until boardSize) {
      val textField = new TextField()
      textField.textProperty().addListener { (_, oldValue, newValue) =>
          if (!newValue.matches("\\d*")) {
            textField.setText(newValue.replaceAll("[^\\d]", ""))
          }
          else{
            try{
              board.array(row)(col) = textField.getText().toInt
            }
            catch{
              case e: Exception => board.array(row)(col) = 0
            }
          }
        }
      textField.prefWidth = cellSize
      textField.prefHeight = cellSize
      gridPane.add(textField, col, row)
    }
    val button = Button("Solve")
    button.onAction = handle{
      board.solveSudoku(0,0)
//      println(board.prettyString())
    }

    gridPane.add(button, 10,4)
    val scene = new Scene(500,600)
    gridPane.alignment = Pos.Center

    scene.root = gridPane

    def solve() = {

    }
  }

