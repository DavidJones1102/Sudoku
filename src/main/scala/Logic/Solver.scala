package Logic

import scala.annotation.tailrec

object Solver {
  def solve(board: Array[Array[Int]]): Array[Array[Array[Int]]] = {
    var solutions = Array.ofDim[Int](0,9,9)

    def innerSolve(board: Array[Array[Int]], row: Int = 0, col: Int = 0): Unit = {
      if row >= 9 then solutions+:=board.map(_.clone())
      else if (col >= 9) innerSolve(board, row+1, 0)
      else if (board(row)(col) > 0) innerSolve(board,row, col + 1)
      else {
          (1 to 9).filter( value => isSafe(board, row, col, value)).foreach { value =>
            if board(row)(col) == 0  && solutions.length < 10 then {
              board(row)(col) = value
              innerSolve(board, row, col+1)
              board(row)(col) = 0
            }
        }
      }
    }
    innerSolve(board)
    solutions
  }

  private def isSafe(board: Array[Array[Int]], row: Int, col: Int, num: Int): Boolean = {
    checkRow(board,row, num) && checkCol(board,col, num) && checkBox(board,row, col, num)
  }

  private def checkRow(board: Array[Array[Int]], row: Int, num: Int): Boolean = {
    !board(row).contains(num)
  }

  private def checkCol(board: Array[Array[Int]], col: Int, num: Int): Boolean = {
    board.forall(element => element(col) != num)
  }

  private def checkBox(board: Array[Array[Int]], row: Int, col: Int, num: Int): Boolean = {
    val boxX = col / 3
    val boxY = row / 3
    val box = for {
      yb <- (boxY * 3) until (boxY * 3 + 3) // indices for rows in THIS box
      xb <- (boxX * 3) until (boxX * 3 + 3) // same for cols
    } yield board(yb)(xb)
    !box.contains(num)
  }
  def validBoard(board: Array[Array[Int]]): Boolean = {
    var num = 0
    var flag = true
    for (row <- 0 until 9; col <- 0 until 9) {
      num = board(row)(col)
      board(row)(col) = 0
      flag &&= num == 0 || isSafe(board, row, col, num)
      board(row)(col) = num
    }
    flag
  }

  def filledBoard(board: Array[Array[Int]]): Boolean = {
    board.count(row => row.contains(0)) == 0
  }
}
