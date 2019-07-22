package pl.mm.session1.humanAgent

import pl.mm.session1.*
import java.lang.IndexOutOfBoundsException

class HumanAgent(piece: Piece) : Agent<TicTacToeEnvironment>(piece) {

    override fun gameFinished(env: TicTacToeEnvironment) {
    }

    override fun makeMove(env: TicTacToeEnvironment): Coords {
        while (true) {
            try {
                print("Choose row and column to set ${env.activePlayerPiece} e.g. 2,3: ")
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
