package pl.mm.session1

interface Agent {
    fun makeMove(piece: Piece, board: Board): Coords
}
