import scalafx.Includes._
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Label, TextField}
import scalafx.scene.layout.{GridPane, Region, StackPane, VBox}

object SolverGui {
    private val boardSize = 9
    private val cellSize = 50
    private val board: Board = Board()

    private val gridPane = new GridPane()
    gridPane.padding = Insets(10)
    gridPane.hgap = 1
    gridPane.vgap = 1

private val textFieldsArray = Array.ofDim[TextField](9, 9)
for (box <- 0 until 9){
  val miniGrid = new GridPane()
  val vbox = new VBox(miniGrid)
  vbox.getStyleClass().add("vbox")
  for (row <- 0 until 3; col <- 0 until 3) {
    val actualRow = row + box%3*3
    val actualCol = col + box/3*3
//    println(actualRow.toString +" "+actualCol.toString)
    val textField = new TextField()
    textFieldsArray(actualRow)(actualCol) = textField
    if board.array(actualRow)(actualCol) > 0 then textField.setText(board.array(actualRow)(actualCol).toString)

    textField.textProperty().addListener { (_, oldValue, newValue) =>
      if (!newValue.matches("\\d*")) {
        textField.setText(newValue.replaceAll("[^\\d]", ""))
      }
      else {
        try {
          board.array(actualRow)(actualCol) = textField.getText().toInt
        }
        catch {
          case e: Exception => board.array(actualRow)(actualCol) = 0
        }
      }
    }
    textField.prefWidth = cellSize
    textField.prefHeight = cellSize
    miniGrid.add(textField, col, row)
  }
  gridPane.add(vbox,box/3,box%3+1)
}

    private val button = Button("Solve")
    button.onAction = handle{
      if(!board.checkBoardValidity()) {
        new Alert(AlertType.Information, "Invalid board!!!").showAndWait()
      }
      else{
        board.solve(0,0)
        draw()
      }
    }
    private val resetButton = Button("Reset")
    resetButton.onAction = handle {
      board.resetBoard()
//      println(board.prettyString())
      draw()
    }
    private val backButton = Button("Back")
    backButton.onAction = handle {
      MainMenu.start()
    }
    gridPane.add(button, 1,4)
    gridPane.add(resetButton, 1,5)
    gridPane.add(backButton, 0,0)

    val scene = new Scene(500,600){
      stylesheets += getClass.getResource("solver.css").toExternalForm
    }
    gridPane.alignment = Pos.Center

    scene.root = gridPane

    private def draw() = {
      for( box <- 0 until 9){
        for (row <- 0 until 3; col <- 0 until 3) {
          val actualRow = row + box % 3 * 3
          val actualCol = col + box / 3 * 3
          var text: String = ""
          if board.array(actualRow)(actualCol) > 0 then text = board.array(actualRow)(actualCol).toString
          textFieldsArray(actualRow)(actualCol).setText(text)
        }
      }

    }

  }

