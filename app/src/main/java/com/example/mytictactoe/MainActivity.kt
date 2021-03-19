 package com.example.mytictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

 class MainActivity : AppCompatActivity() {
    var activePlayer = 1
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    val winningCombination = setOf(
            setOf(1,2,3),
            setOf(1,5,9),
            setOf(1,4,7),
            setOf(2,5,8),
            setOf(3,6,9),
            setOf(3,5,7),
            setOf(4,5,6),
            setOf(7,8,9),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buClick(view: View) {
        val buSelected = view as Button
        var cellId = when (buSelected.id) {
            R.id.button1 -> 1
            R.id.button2 -> 2
            R.id.button3 -> 3
            R.id.button4 -> 4
            R.id.button5 -> 5
            R.id.button6 -> 6
            R.id.button7 -> 7
            R.id.button8 -> 8
            R.id.button9 -> 9
            else -> 0
        }

        playGame(cellId, buSelected)
    }

    fun playGame(cellId:Int, buSelected: Button) {
        if (activePlayer == 1) {
            buSelected.text = "X"
            buSelected.setBackgroundResource(R.color.blue)
            player1.add(cellId)
            activePlayer = 2
            GlobalScope.launch(Dispatchers.IO){
                    delay(2000)
                Log.d("Player 1", "scope in 2 seconds")

            }
            autoPlay()
        }else {
            buSelected.text = "O"
            buSelected.setBackgroundResource(R.color.dark_green)
            player2.add(cellId)
            activePlayer = 1
        }

        checkWinner()
        buSelected.isEnabled = false
    }

   fun checkWinner(){
        var winner = -1

       Log.d("Player 1", player1.toString() + " " + winningCombination.contains(player1.toSet()).toString())
       Log.d("Player 2", player2.toString() + " " + winningCombination.contains(player2.toSet()).toString())

       if(winningCombination.contains(player1.toSet())) {
           Toast.makeText(this, "Player 1 is the winner", Toast.LENGTH_LONG).show()
           restarGame()
       }
       if (winningCombination.contains(player2.toSet())){
           Toast.makeText(this, "Player 2 is the winner", Toast.LENGTH_LONG).show()
           restarGame()
       }
   }

     fun autoPlay() {
         val combination = (1..9).toList()
         val takenNumbers = player1.plus(player2).toList()
         val untaken = combination.filter {
             !takenNumbers.contains(it)
         }
         if (takenNumbers.size == 9) {
             restarGame()
         }
         val rand = Random(1).nextInt(untaken.size)

         val cellId = untaken[rand]

         var button = when (cellId) {
             1 -> button1
             2 -> button2
             3 -> button3
             4 -> button4
             5 -> button5
             6 -> button6
             7 -> button7
             8 -> button8
             9 -> button9
             else -> {
                 button1
             }
         }

         playGame(cellId, button)

     }


     fun restarGame() {

         player1.clear()
         player2.clear()
         for (i in 1..9){
             var button = when (i) {
                 1 -> button1
                 2 -> button2
                 3 -> button3
                 4 -> button4
                 5 -> button5
                 6 -> button6
                 7 -> button7
                 8 -> button8
                 9 -> button9
                 else -> {
                     button1
                 }
             }
             button!!.isEnabled = true
             button!!.text = ""
             button!!.setBackgroundResource(R.color.white_button)
         }

     }
}