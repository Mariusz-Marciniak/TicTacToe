package pl.mm.session1


class TicTacToe {
    private val policy = TicTacToePolicy()

    fun gameLoop() {
        mainLoop@ while (true) {
            policy.board.printBoard()
            val winCond = policy.checkWinCondition()
            if (winCond.isPresent) {
                println(winCond.get())
                break@mainLoop
            } else {
                activePlayerMove()
                changeActivePlayer()
            }
        }
    }

    private fun changeActivePlayer() {
        policy.nextPlayer()
    }

    private fun activePlayerMove() {
        activePlayerLoop@ while(true) {
            print("choose square for ${policy.activePlayerPiece} (row, column) e.g. 2,3: ")
            try {
                val (row, col) = readLine()!!.split(',')
                val moveInvalidError = policy.verifyIsMoveValid(row, col)
                if (moveInvalidError == null) {
                    break@activePlayerLoop
                } else {
                    println("Invalid move: $moveInvalidError")
                }
            } catch(e: IndexOutOfBoundsException) {
                println("Invalid move. Please enter values separated with a comma")
            }
        }
    }
}


object Session1 {
    @JvmStatic
    fun main(vararg arr: String) {
        TicTacToe().gameLoop()
    }

}