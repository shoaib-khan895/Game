package com.example.game.mvm.presenter

import com.example.game.mvm.model.Board
import com.example.game.mvm.view.TicTacToeView

class TicTacToePresenter():Presenter {


    lateinit var view: TicTacToeView
    constructor(view:TicTacToeView):this(){
        this.view = view
    }


    var model: Board = Board()
    override fun onCreate() {
        model.restart()
    }


    //to mark the box
    fun onButtonSelected(row: Int, col: Int) {
        var playerThatMoved = model.mark(row, col)

        if (playerThatMoved !=null){
            view.setButtonText(row, col, playerThatMoved.toString())

            if (model.getGameWinner()!=null)
            {
                view.showWinner(playerThatMoved.toString())
            }

        }
        else
            view.showWinner("Its a Draw")
    }

}