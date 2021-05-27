package akosblack.android

import akosblack.android.list.ResultCreateFragment
import akosblack.android.model.Result
import akosblack.android.model.SudokuModel
import akosblack.android.model.SudokuModel.EMPTY
import akosblack.android.model.SudokuModel.number1
import akosblack.android.model.SudokuModel.number2
import akosblack.android.model.SudokuModel.number3
import akosblack.android.model.SudokuModel.number4
import akosblack.android.model.SudokuModel.number5
import akosblack.android.model.SudokuModel.number6
import akosblack.android.model.SudokuModel.number7
import akosblack.android.model.SudokuModel.number8
import akosblack.android.model.SudokuModel.number9
import akosblack.android.viewmodel.ResultsViewModel
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class GameActivity() : AppCompatActivity(),
    ResultCreateFragment.ResultCreatedListener{

    private lateinit var resultsViewModel: ResultsViewModel

    var btnarray = arrayOfNulls<Button>(10)

    private fun setSelected(x: Int){
        for(i in 0 until 10){
            btnarray[i]?.isSelected = i == (x-1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        SudokuModel.sudoku.setHasEmpty(0)
        SudokuModel.sudoku.setIsBaseElement()
        SudokuModel.sudoku.setBase(SudokuModel.baseMatrix)
        SudokuModel.sudoku.genBoard()
        setContentView(R.layout.activity_game)
        setSupportActionBar(findViewById(R.id.restart_toolbar))

        resultsViewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)

        val clock: Chronometer = findViewById(R.id.chornometer)
        clock.stop()
        clock.start()

        val customBtnNum1: Button = findViewById(R.id.custom_button1)
        val customBtnNum2: Button = findViewById(R.id.custom_button2)
        val customBtnNum3: Button = findViewById(R.id.custom_button3)
        val customBtnNum4: Button = findViewById(R.id.custom_button4)
        val customBtnNum5: Button = findViewById(R.id.custom_button5)
        val customBtnNum6: Button = findViewById(R.id.custom_button6)
        val customBtnNum7: Button = findViewById(R.id.custom_button7)
        val customBtnNum8: Button = findViewById(R.id.custom_button8)
        val customBtnNum9: Button = findViewById(R.id.custom_button9)
        val customBtnRemove: Button = findViewById(R.id.custom_buttonRemove)

        btnarray[0] = customBtnNum1
        btnarray[1] = customBtnNum2
        btnarray[2] = customBtnNum3
        btnarray[3] = customBtnNum4
        btnarray[4] = customBtnNum5
        btnarray[5] = customBtnNum6
        btnarray[6] = customBtnNum7
        btnarray[7] = customBtnNum8
        btnarray[8] = customBtnNum9
        btnarray[9] = customBtnRemove

        customBtnNum1.setOnClickListener {
            setSelected(1)

            if(SudokuModel.sudoku.currentNumber != number1)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(number1)
        }

        customBtnNum2.setOnClickListener{
            setSelected(2)

            if(SudokuModel.sudoku.currentNumber != number2)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(number2)
        }

        customBtnNum3.setOnClickListener{
            setSelected(3)

            if(SudokuModel.sudoku.currentNumber != number3)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(number3)
        }

        customBtnNum4.setOnClickListener{
            setSelected(4)

            if(SudokuModel.sudoku.currentNumber != number4)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(number4)
        }

        customBtnNum5.setOnClickListener{
            setSelected(5)

            if(SudokuModel.sudoku.currentNumber != number5)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(number5)
        }

        customBtnNum6.setOnClickListener{
            setSelected(6)

            if(SudokuModel.sudoku.currentNumber != number6)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(number6)
        }

        customBtnNum7.setOnClickListener{
            setSelected(7)

            if(SudokuModel.sudoku.currentNumber != number7)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(number7)
        }

        customBtnNum8.setOnClickListener{
            setSelected(8)

            if(SudokuModel.sudoku.currentNumber != number8)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(number8)
        }

        customBtnNum9.setOnClickListener{
            setSelected(9)

            if(SudokuModel.sudoku.currentNumber != number9)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(number9)
        }

        customBtnRemove.setOnClickListener{
            setSelected(10)

            if(SudokuModel.sudoku.currentNumber != EMPTY)
                SudokuModel.sudoku.prevNumber=SudokuModel.sudoku.currentNumber
            SudokuModel.sudoku.setPlayer(EMPTY)
        }
    }

    fun endGame() {
        if(SudokuModel.sudoku.Validate()){
            Toast.makeText(this, "Gratulálok! Sikerült!", Toast.LENGTH_LONG).show()
            SudokuModel.sudoku.setHasEmpty(0)
            SudokuModel.sudoku.setIsBaseElement()

            val clock: Chronometer = findViewById(R.id.chornometer)
            clock.stop()

            SudokuModel.winnedTimeWhenFinished =  clock.text.toString()

            val resultCreateFragment = ResultCreateFragment()
            resultCreateFragment.show(supportFragmentManager, "TAG")

        }
        else {
            Toast.makeText(this, "Valami nem jó...", Toast.LENGTH_LONG).show()
            return
        }

    }

    override fun onResultCreated(result: Result) {
        resultsViewModel.insert(result)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.restart) {
            val clock: Chronometer = findViewById(R.id.chornometer)
            clock.base = SystemClock.elapsedRealtime()
            SudokuModel.sudoku.setHasEmpty(0)
            SudokuModel.sudoku.setIsBaseElement()
            SudokuModel.sudoku.setBase(SudokuModel.baseMatrix)
            SudokuModel.sudoku.genBoard()

        }
        return super.onOptionsItemSelected(item)
    }

}

