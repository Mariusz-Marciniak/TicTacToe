package pl.mm.session1.randomAgent

import pl.mm.session1.*
import kotlin.random.Random

class RandomAgent(piece: Piece,
                  val policy: TicTacToePolicy,
                  val repeatWinningSequence: Boolean = false)
    : Agent<TicTacToeEnvironment>(piece) {

    var winningMoves = ArrayList<Coords>()
    var lastMoves = ArrayList<Coords>()

    override fun gameFinished(env: TicTacToeEnvironment) {
        if(repeatWinningSequence && wasWinningSequence(policy.checkWinCondition(env))) {
            winningMoves = lastMoves
        }
        lastMoves = ArrayList()
    }

    private fun wasWinningSequence(checkWinCondition: GameResult): Boolean = when(checkWinCondition) {
        GameResult.PLAYER_X_WON -> piece == "X"
        GameResult.PLAYER_O_WON -> piece == "O"
        else -> false
    }


    override fun makeMove(env: TicTacToeEnvironment): Coords {
        val emptySquares = env.board.squaresWith(EmptySquare.piece())
        var move = emptySquares[Random.Default.nextInt(emptySquares.size)]
        if(repeatWinningSequence && winningMoves.size>lastMoves.size){
            if(emptySquares.contains(winningMoves.elementAt(lastMoves.size))) {
                move = winningMoves.elementAt(lastMoves.size)
            } else {
                winningMoves = ArrayList()
            }
        }
        lastMoves.add(move)
        return move
    }

}
