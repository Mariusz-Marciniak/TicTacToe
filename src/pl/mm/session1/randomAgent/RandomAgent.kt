package pl.mm.session1.randomAgent

import pl.mm.session1.Agent
import pl.mm.session1.Board
import pl.mm.session1.Coords
import pl.mm.session1.EmptySquare
import kotlin.random.Random

object RandomAgent : Agent {

    override fun makeMove(board: Board): Coords {
        val emptySquares = board.squaresWith(EmptySquare.piece())
        return emptySquares[Random.Default.nextInt(emptySquares.size)]
    }

}
