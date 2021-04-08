package com.example.game.mvvm

import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.game.mvm.model.Player
import com.example.game.mvvm.StringUtility.stringFromNumbers

class MvvmViewModel:ViewModel() {

    var board = Board()
    var cells: ObservableArrayMap<String, String>

    init {
        board.restart()
        cells = ObservableArrayMap()
    }

    fun onClickedCellAt(row:Int, col:Int) {
        val playerThatMoved = board.mark(row, col)

        if (playerThatMoved != null) {

            cells[stringFromNumbers(row, col)] = playerThatMoved.toString()

            if (board.getMvvmWinner() != null)
            {

                MvvmMainActivity().onGameWinnerChanged(board.getMvvmWinner()!!)

            }


        }
    }

    fun getWinner(): MutableLiveData<Player> {
        return board.mvvmWinner
    }
}