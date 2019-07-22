package pl.mm.session1.randomAgent

import pl.mm.session1.*
import kotlin.random.Random

object RandomAgent : Agent {

    override fun makeMove(piece: Piece, board: Board): Coords {
        val emptySquares = board.squaresWith(EmptySquare.piece())
        return emptySquares[Random.Default.nextInt(emptySquares.size)]
    }

}
