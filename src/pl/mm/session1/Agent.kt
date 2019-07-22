package pl.mm.session1

abstract class Agent<T : Environment>(val piece: Piece)  {
    abstract fun makeMove(env: T): Coords
    abstract fun gameFinished(env: T)
}
