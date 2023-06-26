import scala.annotation.tailrec

object Solver extends  App {
  private val board = Array(
    Array(0, 0, 0, 0, 7, 0, 0, 0, 0),
    Array(0, 0, 0, 1, 9, 5, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 6, 0),
    Array(8, 0, 0, 0, 6, 0, 0, 0, 3),
    Array(4, 0, 0, 8, 0, 3, 0, 0, 1),
    Array(7, 0, 0, 0, 2, 0, 0, 0, 6),
    Array(0, 6, 0, 0, 0, 0, 2, 8, 0),
    Array(0, 0, 0, 4, 1, 9, 0, 0, 5),
    Array(0, 0, 0, 0, 8, 0, 0, 7, 9),
  )
  solve(board)

  def solve(board: Array[Array[Int]], row: Int = 0, col: Int = 0): Array[Array[Array[Int]]] = {
    var solutions = Array.ofDim[Int](0,9,9)

    @tailrec
    def innerSolve(board: Array[Array[Int]], row: Int = 0, col: Int = 0): Unit = {
      if row >= 9 then {
        solutions +:= board
        println(prettyString(solutions(0)))
      }
      else if (col >= 9) innerSolve(board, row+1, 0)
      else if (board(row)(col) > 0) innerSolve(board,row, col + 1)
      else {
          (1 to 9).filter( value => isSafe(board, row, col, value)).foreach { value =>
            board(row)(col) = value
            solve(board, row, col+1)
            if solutions.length <= 10 then {board(row)(col) = 0}
        }
      }
    }
    innerSolve(board, row, col)
//    println(solutions.length)
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

  def prettyString(array: Array[Array[Int]]): String = {
    array.grouped(3).map { bigGroup =>
      bigGroup.map { row =>
        row.grouped(3).map { smallGroup =>
          smallGroup.mkString(" ", " ", " ")
        }.mkString("|", "|", "|")
      }.mkString("\n")
    }.mkString("+-------+-------+-------+\n", "\n+-------+-------+-------+\n", "\n+-------+-------+-------+")
  }
}

//def solve(row: Int, col: Int): Boolean = {
//    if (row >= 9) true
//    else if (col >= 9) solve(row + 1, 0)
//    else if (array(row)(col) > 0) solve(row, col + 1)
//    else {
//      var flag = false
//      (1 to 9).foreach { value =>
//        if isSafe(row, col, value) then {
//          array(row)(col) = value
//          flag = solve(row, col + 1)
//        }
//        if !flag then array(row)(col) = 0
//      }
//      flag
//    }
//  }