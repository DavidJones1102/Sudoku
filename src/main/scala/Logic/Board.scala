package Logic

import Gui.BoardGui

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.util.Random

case class Board(array: Array[Array[Int]]) {
  private var observer: Option[BoardGui] = None
  def resetBoard(): Unit = {
    (0 to 8).foreach(row => (0 to 8).foreach(col => array(row)(col) = 0))
    informObserver()
  }
  def fillBoard(puzzle: Array[Array[Int]]): Unit = {
    for( x <- 0 until 9; y <- 0 until 9)
    {
      array(x)(y) = puzzle(x)(y)
    }
    informObserver()
  }
  def setObserver(boardGui: BoardGui): Unit = observer = Option(boardGui)
  private def informObserver(): Unit = {
    observer match
      case Some(value) => value.draw()
      case _ =>
  }
}

object Board {
  def apply(): Board = new Board( createZerosArray() )

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

