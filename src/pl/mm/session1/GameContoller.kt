package pl.mm.session1

import pl.mm.session1.epsilonGreedyAgent.EpsilonGreedyAgent
import pl.mm.session1.humanAgent.HumanAgent
import pl.mm.session1.randomAgent.RandomAgent


class TicTacToe {
    private val amountOfSessions = 1000
    private val policy = TicTacToePolicy()
    private val env = TicTacToeEnvironment()
    private val playerOneAgent = EpsilonGreedyAgent(env.playerX, policy, 0.5, 0.1, false)
    private val playerTwoAgent = EpsilonGreedyAgent(env.playerO, policy)
    private val statistics = Statistics(amountOfSessions)

    fun matchSession() {
        for (session in (0 until amountOfSessions)) {
            statistics.announceSession(session)
            println("Session ${session + 1}:")
            env.reset()
            statistics.startingPlayer(env.activePlayerPiece)
            gameLoop()
            println()
            println()
        }
        println(statistics)
    }

    fun gameLoop() {
        mainLoop@ while (true) {
            env.board.printBoard()
            when (val result = policy.checkWinCondition(env)) {
                GameResult.NOT_DECIDED -> {
                    activePlayerMove()
                    changeActivePlayer()
                }
                else -> {
                    println(messageForResult(result))
                    statistics.recordResult(result)
                    playerOneAgent.gameFinished(env)
                    playerTwoAgent.gameFinished(env)
                    break@mainLoop
                }
            }
        }
    }

    private fun messageForResult(result: GameResult): String = when (result) {
        GameResult.PLAYER_X_WON -> "Player X has won"
        GameResult.PLAYER_O_WON -> "Player O has won"
        GameResult.DRAWN -> "Game is drawn"
        GameResult.NOT_DECIDED -> "Game still in progress"
    }

    private fun changeActivePlayer() {
        env.nextPlayer()
    }

    private fun activePlayerMove() {
        activePlayerLoop@ while (true) {
            println("Player ${env.activePlayerPiece} move")
            try {
                val move = chooseMove()
                val moveInvalidError = policy.verifyIsMoveValid(env, move)
                if (moveInvalidError == null) {
                    env.makeMove(move)
                    break@activePlayerLoop
                } else {
                    println("Invalid move: $moveInvalidError")
                }
            } catch (e: IndexOutOfBoundsException) {
                println("Invalid move. Please enter values separated with a comma")
            }
        }
    }

    private fun chooseMove(): Coords {
        return if (env.activePlayerPiece === playerOneAgent.piece) {
            playerOneAgent.makeMove(env)
        } else {
            playerTwoAgent.makeMove(env)
        }
    }
}

class Statistics(amountOfSessions: Int) {
    private val amountOfSections = 10
    private val sessionsPerSection = amountOfSessions / amountOfSections
    private val sectionStatistics = MutableList(amountOfSections) { SectionStatistics(0, 0, 0, 0, 0) }
    private var currentSection = 0

    fun announceSession(sessionNumber: Int) {
        currentSection = sessionNumber / sessionsPerSection
    }

    fun startingPlayer(activePlayerPiece: Piece) {
        when (activePlayerPiece) {
            "X" -> sectionStatistics[currentSection].startingX++
            "O" -> sectionStatistics[currentSection].startingO++
        }
    }

    fun recordResult(result: GameResult) {
        when (result) {
            GameResult.PLAYER_X_WON -> sectionStatistics[currentSection].wonX++
            GameResult.PLAYER_O_WON -> sectionStatistics[currentSection].wonO++
            GameResult.DRAWN -> sectionStatistics[currentSection].draw++
        }
    }

    override fun toString(): String {
        var header = """________________________________________________________
| section | started X | started O | won X | won O | draw |  
|________________________________________________________|
        """
        for (i in (0 until amountOfSections)) {
            header += """
|   ${i + 1}    |    ${sectionStatistics[i].startingX}     |   ${sectionStatistics[i].startingO}      |  ${sectionStatistics[i].wonX}   |  ${sectionStatistics[i].wonO}   | ${sectionStatistics[i].draw}   |"""
        }
        return header
    }

    data class SectionStatistics(var startingX: Int, var startingO: Int, var wonX: Int, var wonO: Int, var draw: Int) {
        override fun toString(): String {
            return super.toString()
        }
    }
}

object Session1 {
    @JvmStatic
    fun main(vararg arr: String) {
        TicTacToe().matchSession()
    }

}
