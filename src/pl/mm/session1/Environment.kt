package pl.mm.session1

import kotlin.random.Random

interface Environment {
    fun reset()
    fun nextPlayer()
}

class TicTacToeEnvironment: Environment {
    val lineElements = 3
    val playerX = "X"
    val playerO = "O"
    lateinit var board: Board
    lateinit var activePlayerPiece: Piece

    fun inactivePlayerPiece() = if (activePlayerPiece === playerO) playerX else playerO

    override fun reset() {
        activePlayerPiece = if (Random(System.currentTimeMillis()).nextBoolean()) playerX else playerO
        board = Board(lineElements, lineElements)
    }
    override fun nextPlayer() {
        activePlayerPiece = inactivePlayerPiece()
    }

    fun makeMove(move: Coords) {
        board = board.put(activePlayerPiece, move)
    }

}
