package pl.mm.session1.randomAgent

import pl.mm.session1.*
import kotlin.random.Random

class RandomAgent(piece: Piece) : Agent<TicTacToeEnvironment>(piece) {
    override fun gameFinished(env: TicTacToeEnvironment) {
    }

    override fun makeMove(env: TicTacToeEnvironment): Coords {
        val emptySquares = env.board.squaresWith(EmptySquare.piece())
        return emptySquares[Random.Default.nextInt(emptySquares.size)]
    }

}
