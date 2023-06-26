import scalafx.Includes.*
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Control, Label, TextField}
import scalafx.scene.layout.{GridPane, Region, StackPane, VBox}

object Game {
  private val cellSize = 50
  private val board: Board = Board()

  private val gridPane = new GridPane()
  gridPane.padding = Insets(10)
  gridPane.hgap = 1
  gridPane.vgap = 1

  private val textFieldsArray = Array.ofDim[Control](9, 9)
  for (box <- 0 until 9) {
    val miniGrid = new GridPane()
    val vbox = new VBox(miniGrid)
    vbox.getStyleClass().add("vbox")
    for (row <- 0 until 3; col <- 0 until 3) {
      val actualRow = row + box % 3 * 3
      val actualCol = col + box / 3 * 3
      val field = createField(actualRow, actualCol)
      textFieldsArray(actualRow)(actualCol) = field

      field.prefWidth = cellSize
      field.prefHeight = cellSize
      miniGrid.add(field, col, row)
    }
    gridPane.add(vbox, box / 3, box % 3 + 1)
  }

  private val button = Button("Check validity")
  button.onAction = handle {
    if (!board.checkBoardValidity()) {
      new Alert(AlertType.Information, "Invalid board!!!").showAndWait()
    }
    else {
      new Alert(AlertType.Information, "Valid board").showAndWait()
      //      draw()
    }
  }
  //  private val uniqueButton = Button("isUnique")
  //  uniqueButton.onAction = handle {
  //    Board.generate()
  //    if (board.uniqueSolution(0, 0,0) >= 2) {
  //      new Alert(AlertType.Information, "Invalid board!!!").showAndWait()
  //    }
  //    else {
  //      new Alert(AlertType.Information, "Valid board").showAndWait()
  //      //      draw()
  //    }
  //  }
  //  gridPane.add(uniqueButton, 1, 5)
  private val backButton = Button("Back")
  backButton.onAction = handle {
    MainMenu.start()
  }
  gridPane.add(button, 1, 4)
  gridPane.add(backButton, 0, 0)

  val scene = new Scene(500, 600) {
    stylesheets += getClass.getResource("solver.css").toExternalForm
  }
  gridPane.alignment = Pos.Center

  scene.root = gridPane

  def createField(row: Int, col: Int): Control =
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
        textField
      case _ => new Label("      " + num.toString)
}



