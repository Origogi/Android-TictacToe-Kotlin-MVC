package jeongtae.com.tictactoe.model

class Board {

    val cells: Array<Array<Cell>> = Array(3, {
        Array(3, {
            Cell(Player.NONE)
        })
    })

    enum class GameState {
        IN_PROGRESS,
        FINISHED
    }

    lateinit var winner: Player
    lateinit var state: GameState
    lateinit var currentTurn: Player

    init {
        restart()
    }

    fun restart() {
        clearCells()
        currentTurn = Player.X
        state = GameState.IN_PROGRESS
    }

    fun mark(row: Int, col: Int) {
        if (isValid(row, col)) {
            cells[row][col].value = currentTurn
        }

        if (isWinningMoveByPlayer(currentTurn, row, col)) {
            state = GameState.FINISHED
            winner = currentTurn
        }
//        else {
//            flipCurrentTurn()
//        }

    }

    private fun clearCells() {
        cells.forEach { cellRow ->
            cellRow.forEach { cell ->
                cell.value = Player.NONE
            }
        }
    }

    private fun isValid(row: Int, col: Int): Boolean {
        if (state == GameState.FINISHED) {
            return false
        }

        if (isOutOfBounds(row) || isOutOfBounds(col)) {
            return false
        }

        if (isCellValueAlreadySet(row, col)) {
            return false
        }
        return true

    }

    private fun isOutOfBounds(idx: Int) = idx < 0 || idx > 2

    private fun isCellValueAlreadySet(row: Int, col: Int) = cells[row][col].value != Player.NONE

    private fun isWinningMoveByPlayer(player: Player, currentRow: Int, currentCol: Int): Boolean {
        return (cells[currentRow][0].value == player         // 3-in-the-row
                && cells[currentRow][1].value == player
                && cells[currentRow][2].value == player
                || cells[0][currentCol].value == player      // 3-in-the-column
                && cells[1][currentCol].value == player
                && cells[2][currentCol].value == player
                || currentRow == currentCol            // 3-in-the-diagonal
                && cells[0][0].value == player
                && cells[1][1].value == player
                && cells[2][2].value == player
                || currentRow + currentCol == 2    // 3-in-the-opposite-diagonal
                && cells[0][2].value == player
                && cells[1][1].value == player
                && cells[2][0].value == player)
    }

    fun flipCurrentTurn() {
        if (currentTurn == Player.X) {
            currentTurn = Player.O
        } else {
            currentTurn = Player.X
        }
    }
}