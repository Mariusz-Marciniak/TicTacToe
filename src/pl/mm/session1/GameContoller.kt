package pl.mm.session1

import pl.mm.session1.humanAgent.HumanAgent
import pl.mm.session1.randomAgent.RandomAgent


class TicTacToe {
    private val policy = TicTacToePolicy()
    private val env = TicTacToeEnvironment()
    private val playerOneAgent = RandomAgent
    private val playerTwoAgent = HumanAgent

    fun gameLoop() {
        mainLoop@ while (true) {
            env.board.printBoard()
            val winCond = policy.checkWinCondition(env)
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
        env.nextPlayer()
    }

    private fun activePlayerMove() {
        activePlayerLoop@ while(true) {
            println("Player ${env.activePlayerPiece} move")
            try {
                val move = chooseMove()
                val moveInvalidError = policy.verifyIsMoveValid(env, move)
                if (moveInvalidError == null) {
                    env.makeMove(move)
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
        return if(env.activePlayerPiece === env.playerOnePiece) {
            playerOneAgent.makeMove(env.activePlayerPiece, env.board)
        } else {
            playerTwoAgent.makeMove(env.activePlayerPiece, env.board)
        }
    }
}


object Session1 {
    @JvmStatic
    fun main(vararg arr: String) {
        TicTacToe().gameLoop()
    }

}
