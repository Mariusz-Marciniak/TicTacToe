package pl.mm.session1.humanAgent

import pl.mm.session1.Agent
import pl.mm.session1.Board
import pl.mm.session1.Coords
import pl.mm.session1.Piece
import java.lang.IndexOutOfBoundsException

object HumanAgent : Agent {

    override fun makeMove(piece: Piece, board: Board): Coords {
        while (true) {
            try {
                print("Choose row and column to set $piece e.g. 2,3: ")
                val (row, col) = readLine()!!.split(',')
                val colV = Integer.parseInt(col) - 1
                val rowV = Integer.parseInt(row) - 1
                return Coords(rowV, colV)
            } catch (e: NumberFormatException) {
                println("Given value is not a number")
            } catch (e: IndexOutOfBoundsException) {
                println("You need to provide row and column values")
            }
        }
    }

}
