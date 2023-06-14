import Board.createZerosArray
import scalafx.application.JFXApp3
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem}
import scalafx.scene.layout.{BorderPane, GridPane, HBox, Pane, StackPane, TilePane, VBox}

import scala.util.Random

case class Board(array: Array[Array[Int]]){
  private def isSafe(row:Int, col:Int, num: Int): Boolean = {
     checkRow(row, num) && checkCol(col,num) && checkBox(row, col, num)
  }
  private def checkRow(row:Int, num: Int): Boolean = {
    !array(row).contains(num)
  }
  private def checkCol(col: Int, num: Int): Boolean = {
    array.forall(element => element(col) != num)
  }
  private def checkBox(row: Int, col:Int, num:Int): Boolean = {
    val boxX = col / 3
    val boxY = row / 3
    val box = for {
      yb <- (boxY * 3) until (boxY * 3 + 3) // indices for rows in THIS box
      xb <- (boxX * 3) until (boxX * 3 + 3) // same for cols
    } yield array(yb)(xb)
    !box.contains(num)
  }

  def checkBoardValidity():Boolean = {
    var flag = true
    (0 until 9).foreach( row => (0 until 9).foreach(col => {
      if array(row)(col) > 0 then {
        val temp = array(row)(col)
        array(row)(col) = 0
        flag &&= isSafe(row,col,temp)
        array(row)(col) = temp
      }
    } ))
    flag
  }
  def solve(row: Int, col:Int): Boolean = {
    if (row >= 9) true
    else if (col >= 9) solve(row+1, 0)
    else if (array(row)(col) > 0) solve(row,col+1)
    else {
        var flag = false
        (1 to 9).foreach { value => if isSafe(row,col, value) then {
            array(row)(col) = value
            flag = solve(row,col+1)
          }
          if !flag then array(row)(col) = 0
        }
        flag
      }

    }


  def resetBoard() = {
    (0 to 8).foreach( row => (0 to 8).foreach( col => array(row)(col) = 0))
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
  def apply(): Board = new Board(Array(
    Array(5, 3, 0, 0, 7, 0, 0, 0, 0),
    Array(6, 0, 0, 1, 9, 5, 0, 0, 0),
    Array(0, 9, 8, 0, 0, 0, 0, 6, 0),
    Array(8, 0, 0, 0, 6, 0, 0, 0, 3),
    Array(4, 0, 0, 8, 0, 3, 0, 0, 1),
    Array(7, 0, 0, 0, 2, 0, 0, 0, 6),
    Array(0, 6, 0, 0, 0, 0, 2, 8, 0),
    Array(0, 0, 0, 4, 1, 9, 0, 0, 5),
    Array(0, 0, 0, 0, 8, 0, 0, 7, 9),
  ))
  def apply(array: Array[Array[Int]]): Board = new Board(array)
  private def createZerosArray() = {
    val array = Array.ofDim[Int](9, 9)
    for (row <- 0 until 9; col <- 0 until 9) {
      array(row)(col) = 0
    }
    array
  }


}

