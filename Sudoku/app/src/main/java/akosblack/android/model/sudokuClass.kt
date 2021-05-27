package akosblack.android.model

import akosblack.android.model.SudokuModel.EMPTY
import akosblack.android.model.SudokuModel.number0

class sudokuClass{

    private var sudokuMatrix: Array<IntArray> = arrayOf(
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0))

    private var BaseMatrix: Array<IntArray> = arrayOf(
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0))

    var isBaseElement: Array<BooleanArray> = arrayOf(
        booleanArrayOf(true, true, true, true, true, true, true, true, true),
        booleanArrayOf(true, true, true, true, true, true, true, true, true),
        booleanArrayOf(true, true, true, true, true, true, true, true, true),
        booleanArrayOf(true, true, true, true, true, true, true, true, true),
        booleanArrayOf(true, true, true, true, true, true, true, true, true),
        booleanArrayOf(true, true, true, true, true, true, true, true, true),
        booleanArrayOf(true, true, true, true, true, true, true, true, true),
        booleanArrayOf(true, true, true, true, true, true, true, true, true),
        booleanArrayOf(true, true, true, true, true, true, true, true, true)
    )

    constructor(matrix: Array<IntArray>){
        for(i in 0 until 9)
            for(j in 0 until 9)
                sudokuMatrix[i][j]=matrix[i][j]
    }

    constructor(){
        for(i in 0 until 9)
            for(j in 0 until 9)
                 sudokuMatrix[i][j]=0
    }

    var currentNumber: Int = EMPTY
    var prevNumber: Int = number0
    var hasEMPTY: Int = 0

    var diffiulty: Int = 0

    fun setDifficulty(diff: Int){
        diffiulty = diff
    }

    fun getDifficulty(): Int{
        return diffiulty
    }

    fun setHasEmpty(X: Int){
        hasEMPTY = X
    }

    fun setBase(matrix: Array<IntArray>){
        for(i in 0 until 9)
            for(j in 0 until 9)
                sudokuMatrix[i][j]=matrix[i][j]
    }

    fun setIsBaseElement(){
        for(i in 0 until 9)
            for(j in 0 until 9)
                isBaseElement[i][j]=true
    }

    private fun swapCOL(X: Int, Y: Int){

        var temp = IntArray(9)
        for(i in 0 until 9)
            temp[i]=0

        for (j in 0 until 9) {
            temp[j] = getFieldContent(X, j)
            setFieldContent(X, j, getFieldContent(Y, j))
            setFieldContent(Y, j, temp[j])
        }
    }

    fun swapROW(X: Int, Y: Int) {

        var temp = IntArray(9)
        for (i in 0 until 9)
            temp[i] = 0

        for (j in 0 until 9) {
            temp[j] = getFieldContent(j, X)
            this.setFieldContent(j, X, this.getFieldContent(j, Y))
            this.setFieldContent(j, Y, temp[j])
        }

    }

    fun getFieldContent(x: Int, y: Int): Int {
        return sudokuMatrix[x][y]
    }

    fun setFieldContent(x: Int, y: Int, content: Int): Int {
        sudokuMatrix[x][y] = content
        return content
    }

    private fun genRandomNumber(min: Int, max: Int): Int{
        return (min..max).shuffled().first()
    }

    fun setPlayer(x: Int){
        currentNumber = x
    }

    private fun removeFieldContent(){

        var genX: Int = genRandomNumber(0,8)
        var genY: Int = genRandomNumber(0,8)

        if (sudokuMatrix[genX][genX] != number0) {
            sudokuMatrix[genX][genY] = number0
            isBaseElement[genX][genY]=false
            hasEMPTY++
        }

    }

    fun genBoard() {

        currentNumber = EMPTY
        hasEMPTY = 0
        var realEmpty: Int = 0

        for(i in 0 until genRandomNumber(75, 100)) {

            swapCOL(genRandomNumber(0, 2), genRandomNumber(0, 2))
            swapCOL(genRandomNumber(3, 5), genRandomNumber(3, 5))
            swapCOL(genRandomNumber(6, 8), genRandomNumber(6, 8))

            swapROW(genRandomNumber(0, 2), genRandomNumber(0, 2))
            swapROW(genRandomNumber(3, 5), genRandomNumber(3, 5))
            swapROW(genRandomNumber(6, 8), genRandomNumber(6, 8))

        }

        for(i in 0 until 9)
            for(j in 0 until 9)
                BaseMatrix[i][j]=sudokuMatrix[i][j]

        while(hasEMPTY != diffiulty){
            removeFieldContent()
        }

        for(i in 0 until 9) {
            for (j in 0 until 9) {
                if (sudokuMatrix[i][j] == 0)
                    realEmpty++
            }
        }

        hasEMPTY = realEmpty
    }

    fun Validate(): Boolean{
        for(i in 0 until 9)
            for(j in 0 until 9)
                if(sudokuMatrix[i][j]!=BaseMatrix[i][j])
                    return false
        return true
    }

}