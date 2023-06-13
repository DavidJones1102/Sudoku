import scalafx.application.JFXApp3
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem}
import scalafx.scene.layout.{BorderPane, GridPane, HBox, Pane, StackPane, TilePane, VBox}

case class Board(array: Array[Array[Int]]){
  def isSafe(row:Int, col:Int, num: Int): Boolean = {
     checkRow(row, num) && checkCol(col,num) && checkBox(row, col, num)
  }
  private def checkRow(row:Int, num: Int): Boolean = {
    !array(row).contains(num)
  }
  private def checkCol(col: Int, num: Int): Boolean = {
    array.forall(element => element(col) != num)
  }
  private def checkBox(row: Int, col:Int, num:Int): Boolean = {
    var flag = true
    var curr_row = -1
    var curr_col = -1
    for(i <- 0 until 3; j<- 0 until 3){
      curr_row = row/3 + i
      curr_col = col/3*3 +j
      if(row != curr_row || col != curr_col) {
        flag = flag && array(curr_row)(curr_col) != num
      }
    }
    flag
  }

  def solveSudoku(row: Int, col:Int): Unit = {
    if row >= 9 then println(this.prettyString())
    else if col >= 9 then solveSudoku(row+1,0)
    else if array(row)(col) > 0 then solveSudoku(row, col+1)
    else (1 to 9).filter(value => isSafe(row,col,value)).foreach{ value =>
      array(row)(col) = value
      solveSudoku(row,col+1)
      array(row)(col) = 0
    }
  }

  def prettyString(): String = {
    array.grouped(3).map { bigGroup =>
      bigGroup.map { row =>
        row.grouped(3).map { smallGroup =>
          smallGroup.mkString(" ", " ", " ")
        }.mkString("|", "|", "|")
      }.mkString("\n")
    }.mkString("+-------+-------+-------+\n", "\n+-------+-------+-------+\n", "\n+-------+-------+-------+")
  }
}

object Board{
  def apply(): Board = new Board(createZerosArray())
  def apply(array: Array[Array[Int]]): Board = new Board(array)
  private def createZerosArray() = {
    val array = Array.ofDim[Int](9, 9)
    for (row <- 0 until 9; col <- 0 until 9) {
      array(row)(col) = 0
    }
    array
  }
}

