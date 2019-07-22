package pl.mm.session1

import java.util.*

interface GamePolicy<T : Environment> {
    fun checkWinCondition(env: T): Optional<String>
    fun verifyIsMoveValid(env: T, move: Coords): String?
}

class TicTacToePolicy : GamePolicy<TicTacToeEnvironment> {
    override fun checkWinCondition(env: TicTacToeEnvironment): Optional<String> {
        if (canStrikeLine(env.board, env.activePlayerPiece)) {
            return Optional.of("Player $env.activePlayerPiece has won")
        }
        if (canStrikeLine(env.board, env.inactivePlayerPiece())) {
            return Optional.of("Player ${env.inactivePlayerPiece()} has won")
        }
        if (env.board.isFull()) {
            return Optional.of("Game is drawn")
        }
        return Optional.empty()
    }

    private fun canStrikeLine(board: Board, playerPiece: Piece): Boolean {
        val squares = board.squaresWith(playerPiece)
        val lineElements = board.columns
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

    override fun verifyIsMoveValid(env: TicTacToeEnvironment, move: Coords): String? {
            return if (move.col < 0 || move.row < 0) {
                "Incorrect values"
            } else if (env.board.get(move).isPresent) {
                "Filed occupied"
            } else {
                null
            }
    }


}

