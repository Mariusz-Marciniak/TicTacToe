package pl.mm.session1

interface GamePolicy<T : Environment> {
    fun checkWinCondition(env: T): GameResult
    fun verifyIsMoveValid(env: T, move: Coords): String?
}

enum class GameResult {
    PLAYER_X_WON, PLAYER_O_WON, DRAWN, NOT_DECIDED
}

class TicTacToePolicy : GamePolicy<TicTacToeEnvironment> {
    override fun checkWinCondition(env: TicTacToeEnvironment): GameResult {
        if (canStrikeLine(env.board, env.playerX)) {
            return GameResult.PLAYER_X_WON
        }
        if (canStrikeLine(env.board, env.playerO)) {
            return GameResult.PLAYER_O_WON
        }
        if (env.board.isFull()) {
            return GameResult.DRAWN
        }
        return GameResult.NOT_DECIDED
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

