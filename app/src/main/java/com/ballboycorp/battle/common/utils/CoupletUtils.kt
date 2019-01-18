package com.ballboycorp.battle.common.utils

/**
 * Created by musooff on 16/01/2019.
 */

object CoupletUtils {

    private const val ALLOWED_KEYS = "йқуӯкеёнгғшҳзхъфҷвапролджэячсмитӣбюЙҚУӮКЕЁНГҒШҲЗХЪФҶВАПРОЛДЖЭЯЧСМИТӢБЮ -\"':;!?,."
    private const val ALLOWED_SIGNS = "-\"':;!?,. "
    private const val ALLOWED_LETTERS = "йқуӯкеёнгғшҳзхъфҷвапролджэячсмитӣбюЙҚУӮКЕЁНГҒШҲЗХЪФҶВАПРОЛДЖЭЯЧСМИТӢБЮ"
    private const val LETTER_I_ZADA = "Ӣ"
    private const val LETTER_I = "И"
    private const val LETTER_YE = "Е"
    private const val LETTER_E = "Э"
    private const val MIN_LENGTH = 10

    private const val FIRST_LINE_WITH_LETTERS = "Мисраи аввал бо харфи "
    private const val FIRST_LINE_DEFAULT = "Мисраи аввал"

    fun canSubmit(startingLetter: String?, line1: String, line2: String) : Boolean{
        if (line1.isBlank() || line2.isBlank()){
            return false
        }
        return isValidFirstLetter(startingLetter, line1) && isValidLine(line1) && isValidLine(line2)
    }

    fun getEndingLetter(line: String): String {
        var reversed = line.reversed()
        while (ALLOWED_SIGNS.contains(reversed[0])){
            reversed = reversed.substring(1)
        }
        return reversed[0].toString().toUpperCase()

    }

    private fun isValidFirstLetter(startingLetter: String?, line: String) : Boolean {
        if (startingLetter == null){
            return true
        }
        when (startingLetter){
            LETTER_I_ZADA -> return isValidFirstLetter(LETTER_I, line)
            LETTER_YE -> return isValidFirstLetter(LETTER_E, line)
        }
        return line[0].toString().toUpperCase() == startingLetter
    }

    private fun isValidLine(line: String) : Boolean {

        if (line.length < MIN_LENGTH){
            return false
        }

        line.forEach {
            if (!ALLOWED_KEYS.contains(it)){
                return false
            }
        }

        return true
    }

    fun allowedLetters(startingLetter: String?) : String {
        return if (startingLetter == null){
            FIRST_LINE_DEFAULT
        }
        else {
            FIRST_LINE_WITH_LETTERS + when (startingLetter){
                LETTER_I_ZADA -> LETTER_I
                LETTER_YE -> LETTER_E
                else -> startingLetter
            }
        }
    }
}