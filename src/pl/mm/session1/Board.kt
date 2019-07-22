package pl.mm.session1

import java.util.*

data class Board(val columns: Int, val rows: Int,
                 private val state: Array<Array<Square>> = Array(columns) { Array<Square>(rows) { EmptySquare } }) {

    fun put(piece: Piece, coords: Coords): Board {
        val newState: Array<Array<Square>> = Array(columns) { Array<Square>(rows) { EmptySquare } }
        for(i in (0 until state.size)) {
            for(j in (0 until state[i].size)) {
                newState[i][j] = state[i][j]
            }
        }
        newState[coords.row][coords.col] = SquareWithPiece(piece)
        return this.copy(state = newState)
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
        }.flatten().filter { it.second.piece() == piece }.map { it.first }
    }

    fun isFull() = squaresWith(EmptySquare.piece()).isEmpty()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Board
        if (columns != other.columns || rows != other.rows) return false
        if (state.size != other.state.size) return false

        for(index in (0 until other.state.size)) {
            if (!Arrays.equals(state[index], other.state[index])) return false
        }

        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result  + columns
        result = prime * result  + rows
        result = prime * result  + state.size

        for(index in (0 until state.size)) {
            result += Arrays.deepHashCode(state[index])
        }
        return result
    }

}

data class Coords(val row: Int, val col: Int)

interface Square {
    fun piece(): Piece
}

object EmptySquare : Square {
    override fun piece(): Piece = " "
}

data class SquareWithPiece(val piece: Piece) : Square {
    override fun piece(): Piece = piece
}

typealias Piece = String

