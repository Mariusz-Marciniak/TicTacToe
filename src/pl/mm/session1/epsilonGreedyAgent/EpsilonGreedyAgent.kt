package pl.mm.session1.epsilonGreedyAgent

import pl.mm.session1.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

class EpsilonGreedyAgent(piece: Piece,
                         val policy: TicTacToePolicy,
                         private val alpha: Double=0.5,
                         private var epsilon: Double = 1.0,
                         val updateEpsilon: Boolean = true) : Agent<TicTacToeEnvironment>(piece) {
    private var session: Double = 0.0
    private val qTable = HashMap<Board, Double>()
    private val movesInGame = LinkedList<Board>()

    override fun gameFinished(env: TicTacToeEnvironment) {
        updateQTable(env)
        updateEpsilon()
        //printEstimates()
        movesInGame.clear()
    }

    private fun printEstimates() {
        for(board in qTable.keys) {
            println("estimate ${qTable[board]} for board ")
            board.printBoard()
        }
    }

    private fun updateEpsilon() {
        if(updateEpsilon) {
            session = session + 1.0
            epsilon = 0.01 + (1.0 - 0.01) * Math.exp(-0.005 * session)
            println("for session $session epsilon is $epsilon")
        }
    }

    override fun makeMove(env: TicTacToeEnvironment): Coords {
        val emptySquares = env.board.squaresWith(EmptySquare.piece())
        val proposedMove = if(epsilon > Random.nextDouble(1.0)) {
            makeRandomMove(emptySquares)
        } else {
            makeBestMove(env, emptySquares)
        }
        movesInGame.add(env.board.put(env.activePlayerPiece, proposedMove))
        return proposedMove
    }

    private fun updateQTable(env: TicTacToeEnvironment) {
        val reward = rewardFor(policy.checkWinCondition(env))
        var target = reward
        for(prevBoard in movesInGame.asReversed()) {
            val prevBoardEstimate = qTable[prevBoard] ?: 0.0
            val value = prevBoardEstimate + alpha * (target - prevBoardEstimate)
            qTable[prevBoard] = value
            target = value
        }
    }

    private fun rewardFor(checkWinCondition: GameResult): Double = when(checkWinCondition) {
        GameResult.NOT_DECIDED -> 0.0
        GameResult.PLAYER_X_WON -> if(piece == "X") 1.0 else -1.0
        GameResult.PLAYER_O_WON -> if(piece == "O") 1.0 else -1.0
        GameResult.DRAWN -> 0.5
    }

    private fun makeBestMove(env: TicTacToeEnvironment, emptySquares: List<Coords>): Coords {
        var bestValue = -100.0
        var bestCoords = emptySquares[0]
        for(coords in emptySquares) {
            val evaluatedBoard = env.board.put(piece, coords)
            val currentValue = qTable.get(evaluatedBoard) ?: 0.0
            if(currentValue > bestValue) {
                bestValue = currentValue
                bestCoords = coords
            }
        }
        println("Epsilon greedy chooses $bestCoords with value: $bestValue")
        return bestCoords
    }


    private fun makeRandomMove(emptySquares: List<Coords>): Coords {
        return emptySquares[Random.Default.nextInt(emptySquares.size)]
    }

}
