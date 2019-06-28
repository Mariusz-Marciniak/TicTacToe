package pl.mm.session1

import pl.mm.session1.humanAgent.HumanAgent
import pl.mm.session1.randomAgent.RandomAgent


class TicTacToe {
    private val policy = TicTacToePolicy()
    private val playerOneAgent = HumanAgent
    private val playerTwoAgent = RandomAgent

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
            println("Player ${policy.activePlayerPiece} move")
            try {
                val moveInvalidError = policy.verifyIsMoveValid(chooseMove())
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

    private fun chooseMove(): Coords {
        return if(policy.activePlayerPiece === policy.playerOnePiece) {
            playerOneAgent.makeMove(policy.board)
        } else {
            playerTwoAgent.makeMove(policy.board)
        }
    }
}


object Session1 {
    @JvmStatic
    fun main(vararg arr: String) {
        TicTacToe().gameLoop()
    }

}
