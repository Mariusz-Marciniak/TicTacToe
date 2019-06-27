package pl.mm.session1

import java.util.*

data class Board(val columns: Int, val rows: Int) {
    private var state = Array(columns) { Array<Square>(rows) { EmptySquare } }

    fun put(piece: Piece, coords: Coords) {
        state[coords.row][coords.col] = SquareWithPiece(piece)
    }

    fun get(coords: Coords): Optional<Piece> = when (val square = state[coords.row][coords.col]) {
        is SquareWithPiece -> Optional.of(square.piece)
        else -> Optional.empty()
    }

    fun printBoard() {
        for (row in 0 until state.size) {
            for (col in 0 until state[row].size) {
                print("| ${state[row][col].piece()} |")
            }
            println(" ")
        }
    }

    fun squaresWith(piece: Piece): List<Coords> {
        return state.withIndex().map { row ->
            row.value.withIndex().map { col ->
                Pair(Coords(row.index, col.index), col.value)
            }
        }.flatten().filter { it.second.piece() == piece }.map {it.first}
    }

    val isFull = squaresWith(EmptySquare.piece()).isEmpty()

}

data class Coords(val row: Int, val col: Int)

interface Square {
    fun piece(): Piece
}

object EmptySquare : Square {
    override fun piece(): Piece = " "
}

class SquareWithPiece(val piece: Piece) : Square {
    override fun piece(): Piece = piece
}

typealias Piece = String

