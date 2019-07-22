package pl.mm.session1

import java.util.*
import kotlin.random.Random

interface GamePolicy {
    fun setup()
    fun checkWinCondition(): Optional<String>
    fun nextPlayer()
    fun verifyIsMoveValid(coords: Coords): String?
}

class TicTacToePolicy : GamePolicy {
    val lineElements = 3
    val playerOnePiece = "X"
    val playerTwoPiece = "O"

    var board = Board(lineElements, lineElements)
    var activePlayerPiece: Piece = playerOnePiece

    override fun setup() {
        activePlayerPiece = if (Random(System.currentTimeMillis()).nextBoolean()) playerOnePiece else playerTwoPiece
    }

    override fun checkWinCondition(): Optional<String> {
        if (canStrikeLine(board.squaresWith(activePlayerPiece))) {
            return Optional.of("Player $activePlayerPiece has won")
        }
        if (canStrikeLine(board.squaresWith(inactivePlayerPiece()))) {
            return Optional.of("Player ${inactivePlayerPiece()} has won")
        }
        if (board.isFull()) {
            return Optional.of("Game is drawn")
        }
        return Optional.empty()
    }

    private fun canStrikeLine(squares: List<Coords>): Boolean {
        fun diagonalLine() : Boolean {
            var firstDiagonal = true
            var secondDiagonal = true
            for(i in 0 until  lineElements) {
                firstDiagonal = firstDiagonal && squares.contains(Coords(i,i))
                secondDiagonal = secondDiagonal && squares.contains(Coords(i,lineElements -1 - i))
            }
            return firstDiagonal || secondDiagonal
        }
        fun horizontalLine() = squares.groupBy { it.row }.filter { it.value.size == lineElements }.isNotEmpty()
        fun verticalLine() = squares.groupBy { it.col }.filter { it.value.size == lineElements }.isNotEmpty()

        return when {
            squares.size < lineElements -> false
            verticalLine() -> true
            horizontalLine() -> true
            diagonalLine() -> true
            else -> false
        }
    }

    override fun nextPlayer() {
        activePlayerPiece = inactivePlayerPiece()
    }

    private fun inactivePlayerPiece() = if (activePlayerPiece === playerTwoPiece) playerOnePiece else playerTwoPiece

    override fun verifyIsMoveValid(coords: Coords): String? {
            return if (coords.col < 0 || coords.row < 0) {
                "Incorrect values"
            } else if (board.get(coords).isPresent) {
                "Filed occupied"
            } else {
                board = board.put(activePlayerPiece, coords)
                null
            }
    }


}

