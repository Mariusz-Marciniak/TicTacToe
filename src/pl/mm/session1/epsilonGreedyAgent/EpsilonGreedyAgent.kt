package pl.mm.session1.epsilonGreedyAgent

import pl.mm.session1.*
import kotlin.random.Random

object EpsilonGreedyAgent : Agent {

    private const val epsilon: Double = 0.1
    private val qTable = HashMap<Board, Int>()

    override fun makeMove(piece: Piece, board: Board): Coords {
        val emptySquares = board.squaresWith(EmptySquare.piece())
        return if(epsilon > Random.nextDouble(1.0)) {
            makeRandomMove(emptySquares)
        } else {
            makeBestMove(piece, board, emptySquares)
        }
    }

    private fun makeBestMove(piece: Piece, board: Board, emptySquares: List<Coords>): Coords {
        var bestValue = -100
        var bestCoords = emptySquares[0]
        for(coords in emptySquares) {
            val evaluatedBoard = board.put(piece, coords)
            val currentValue = qTable.get(evaluatedBoard) ?: 0
            if(currentValue > bestValue) {
                bestValue = currentValue
                bestCoords = coords
            }
        }
        return bestCoords
    }


    private fun makeRandomMove(emptySquares: List<Coords>): Coords {
        return emptySquares[Random.Default.nextInt(emptySquares.size)]
    }

}
