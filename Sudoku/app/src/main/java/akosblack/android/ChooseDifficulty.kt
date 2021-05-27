package akosblack.android

import akosblack.android.model.SudokuModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button

class ChooseDifficulty : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         requestWindowFeature(Window.FEATURE_NO_TITLE)
         window.setFlags(
             WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN
         )
        setContentView(R.layout.activity_choose_difficulty)

        val diffEasy: Button = findViewById(R.id.diffEasy)
        diffEasy.setOnClickListener {
            SudokuModel.sudoku.setDifficulty(SudokuModel.EASY)
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }
        val diffNormal: Button = findViewById(R.id.diffNormal)
        diffNormal.setOnClickListener {
            SudokuModel.sudoku.setDifficulty(SudokuModel.MEDIUM)
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }
        val diffHard: Button = findViewById(R.id.diffHard)
        diffHard.setOnClickListener {
            SudokuModel.sudoku.setDifficulty(SudokuModel.HARD)
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }
    }

}
