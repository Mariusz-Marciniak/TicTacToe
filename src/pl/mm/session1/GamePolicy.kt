package pl.mm.session1

import java.util.*
import kotlin.random.Random

interface GamePolicy {
    fun setup()
    fun checkWinCondition(): Optional<String>
    fun nextPlayer()
    fun verifyIsMoveValid(col: String, row: String): String?
}

class TicTacToePolicy : GamePolicy {
    val lineElements = 3
    val board = Board(lineElements, lineElements)
    val playerOnePiece = "X"
    val playerTwoPiece = "O"
    var activePlayerPiece: Piece = playerOnePiece

    override fun setup() {
        activePlayerPiece = if (Random(System.currentTimeMillis()).nextBoolean()) playerOnePiece else playerTwoPiece
    }

    override fun checkWinCondition(): Optional<String> {
        if (canStrikeLine(board.squaresWith(activePlayerPiece))) {
            return Optional.of("Player $activePlayerPiece won")
        }
        if (canStrikeLine(board.squaresWith(inactivePlayerPiece()))) {
            return Optional.of("Player ${inactivePlayerPiece()} won")
        }
        if (board.isFull) {
            return Optional.of("Game draw")
        }
        return Optional.empty()
    }

    private fun canStrikeLine(squares: List<Coords>): Boolean {
        fun diagonalLine() : Boolean {
            var firstDiagonal = true
            var secondDiagonal = true
            for(i in 0 until  lineElements) {
                firstDiagonal = firstDiagonal && squares.contains(Coords(i,i))
                secondDiagonal = secondDiagonal && squares.contains(Coords(i,lineElements - i))
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

    override fun verifyIsMoveValid(row: String, col: String): String? {
        try {
            val colV = Integer.parseInt(col) - 1
            val rowV = Integer.parseInt(row) - 1
            return if (colV < 0 || rowV < 0) {
                "Incorrect values"
            } else if (board.get(Coords(rowV, colV)).isPresent) {
                "Filed occupied"
            } else {
                board.put(activePlayerPiece, Coords(rowV, colV))
                null
            }
        } catch (e: NumberFormatException) {
            return "Given value is not a number"
        }
    }


}

