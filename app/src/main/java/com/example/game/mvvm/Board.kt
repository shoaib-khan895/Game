package com.example.game.mvvm

import androidx.lifecycle.MutableLiveData
import com.example.game.mvm.model.GameState
import com.example.game.mvm.model.Player

class Board {

    lateinit var currentTurn: Player
    lateinit var gameState: GameState
    private val cells = Array(3){Array(3){" "} }
    var winner: Player?= null
    var mvvmWinner: MutableLiveData<Player> = MutableLiveData()


    private fun reset() {
        for (i in 0..2){
            for(j in 0..2){
                cells[i][j]= ""
            }
        }
    }
    fun restart(){
        reset()
        winner= null

        currentTurn = Player.X
        gameState = GameState.IN_PROGRESS
    }

    fun mark(row: Int, col: Int): Player? {

        var playerThatMoved: Player? = null
        if (isValid(row,col)){
            cells[row][col]= currentTurn.toString()

            playerThatMoved=currentTurn

            if (isWinningMoveByPlayer(currentTurn, row, col))
            {

                mvvmWinner.value= currentTurn

                gameState = GameState.FINISHED

            }
            else{

                flipTurn()
            }
        }
        return if (gameState == GameState.FINISHED){
            mvvmWinner.value
        }
        else
            playerThatMoved

    }


    fun getMvvmWinner(): Player?{
        return mvvmWinner.value
    }

    //function to flip the turn
    private fun flipTurn() {
        if (currentTurn == Player.X)
        { currentTurn = Player.O
        }
        else
        {currentTurn = Player.X
        }
    }
    private fun isWinningMoveByPlayer(player: Player, currentRow: Int, currentCol: Int): Boolean {

        return ((
                // 3 -in-a row
                cells[currentRow][0] == player.toString()
                        && cells[currentRow][1]== player.toString()
                        && cells[currentRow][2]== player.toString())

                // or 3-in-a column
                ||(cells[0][currentCol]== player.toString()
                && cells[1][currentCol]== player.toString()
                && cells[2][currentCol]== player.toString())

                // diagnol
                ||(currentCol==currentRow
                && cells[0][0]== player.toString()
                && cells[1][1]== player.toString()
                && cells[2][2]== player.toString())

                //opposite diagnal
                ||(currentCol+currentRow ==2
                && cells[0][2]== player.toString()
                && cells[1][1]== player.toString()
                && cells[2][0]== player.toString())

                )
    }


    //check if the cell selected is valid or not
    fun isValid(row: Int, col: Int):Boolean{
        return if (gameState == GameState.FINISHED){
         false
        }

        else
            if( isOutOfBound(row) || isOutOfBound(col)) {
                false
            }
            else !(isCellValueAlreadySet(row,col))

    }
    //check that whether the  cell is already set
    private fun isCellValueAlreadySet(row: Int, col: Int): Boolean {
        return !(cells[row][col].isEmpty())
    }

    //check that given row or col is in range
    private fun isOutOfBound(row: Int): Boolean {
        return (row<0 || row>2)
    }

}