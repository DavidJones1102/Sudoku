import Board.createZerosArray

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.util.Random

case class Board(array: Array[Array[Int]]) {
  def resetBoard(): Unit = {
    (0 to 8).foreach(row => (0 to 8).foreach(col => array(row)(col) = 0))
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

object Board {
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

  def generateRandomWithNEmptyCells(n: Int): Board = {
    val diagonal: Array[Int] = Random.shuffle(1 to 9).toArray
    val arr = Array.ofDim[Int](9, 9)
    (0 until 9).foreach(value => arr(value)(value) = diagonal(value))

    val board = Solver.solve(arr).head
    val cells = Random.shuffle(0 until 81).toArray

    @tailrec
    def removeCells(index: Int = 0, toRemove: Int = n): Unit = {
      if (toRemove > 0 && index < cells.length) {
        val row = cells(index) / 9
        val col = cells(index) % 9
        val value = board(row)(col)
        board(row)(col) = 0
        if Solver.solve(board).length > 1 then {
          board(row)(col) = value
          removeCells(index + 1, toRemove)
        }
        else removeCells(index + 1, toRemove - 1)
      }
    }

    removeCells()
    new Board(board)
  }
}

