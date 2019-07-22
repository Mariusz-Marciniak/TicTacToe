package pl.mm.session1

import kotlin.random.Random

interface Environment {
    fun reset()
    fun nextPlayer()
}

class TicTacToeEnvironment: Environment {
    val lineElements = 3
    val playerOnePiece = "X"
    val playerTwoPiece = "O"
    var board = Board(lineElements, lineElements)
    var activePlayerPiece: Piece = playerOnePiece

    fun inactivePlayerPiece() = if (activePlayerPiece === playerTwoPiece) playerOnePiece else playerTwoPiece

    override fun reset() {
        activePlayerPiece = if (Random(System.currentTimeMillis()).nextBoolean()) playerOnePiece else playerTwoPiece
    }
    override fun nextPlayer() {
        activePlayerPiece = inactivePlayerPiece()
    }

    fun makeMove(move: Coords) {
        board = board.put(activePlayerPiece, move)
    }

}
