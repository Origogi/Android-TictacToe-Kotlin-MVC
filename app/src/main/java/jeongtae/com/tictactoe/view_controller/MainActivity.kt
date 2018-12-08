package jeongtae.com.tictactoe.view_controller

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jeongtae.com.tictactoe.R
import jeongtae.com.tictactoe.model.Board
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var buttons: ViewGroup
    val model = Board()


    val onCellClicked: (v: View) -> Unit = { button: View ->
        if ((button is Button)) {
            val (row, col) = button.tag.toString().split("_").map {
                it.toInt()
            }

            model.mark(row, col)
            button.isEnabled = false
            button.text = model.currentTurn.name
            if (model.state == Board.GameState.FINISHED) {
                tvResult.text = model.currentTurn.name + " is Win!"

                repeat(buttons.childCount) { idx ->
                    val button: Button = buttons.getChildAt(idx) as Button
                    button.isEnabled = false
                }
                printBlueToButtonForWinnerCell(model.winner.name)
            } else {
                model.flipCurrentTurn()
            }
        }
    }

    fun reset() {
        repeat(buttons.childCount) { idx ->
            val button: Button = buttons.getChildAt(idx) as Button
            button.text = ""
            button.isEnabled = true
            button.setBackgroundResource(android.R.drawable.btn_default);

        }
        model.restart()
        tvResult.text = ""
    }

    fun printBlueToButtonForWinnerCell(winner : String) {
        //Check Col
        repeat(3) {row->
            var isWinCells = true
            repeat(3) {col->
                if(!isWinnerCell(row, col, winner)) {
                    isWinCells = false
                }

            }

            if (isWinCells) {
                repeat(3) {col->
                    printButtonColorBlue(row, col)
                }
                return
            }
        }

        //Check Row
        repeat(3) {col->
            var isWinCells = true
            repeat(3) {row->
                if(!isWinnerCell(row, col, winner)) {
                    isWinCells = false
                }            }

            if (isWinCells) {
                repeat(3) {row->
                    printButtonColorBlue(row, col)
                }
                return
            }
        }

        //Check Cross start 0,0
        var isWinCells = true
        repeat(3) {row->
            val col = row
            if(!isWinnerCell(row, col, winner)) {
                isWinCells = false
            }
        }
        if (isWinCells) {
            repeat(3) {row->
                val col = row
                printButtonColorBlue(row, col)
            }
            return
        }

        //Check Cross start 0,2
        isWinCells = true
        repeat(3) {row->
            val col = 2 - row
            if(!isWinnerCell(row, col, winner)) {
                isWinCells = false
            }
        }
        if (isWinCells) {
            repeat(3) {row->
                val col =2 -  row
                printButtonColorBlue(row, col)
            }
            return
        }
    }

    private fun isWinnerCell(row : Int, col : Int, winner: String) :Boolean {
        val idx = 3* row + col
        val playerInCell = (buttons.getChildAt(idx) as Button).text
        return winner == playerInCell
    }

    private fun printButtonColorBlue(row : Int, col : Int) {
        val idx = 3*row + col
        val button : Button = buttons.getChildAt(idx) as Button
        button.setBackgroundColor(Color.MAGENTA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttons = buttonGrid


        repeat(buttons.childCount) { idx ->
            buttons.getChildAt(idx).setOnClickListener(onCellClicked)
        }

        btnReset.setOnClickListener {
            reset()
        }
        reset()
    }
}
