package com.ballboycorp.battle.common.utils

/**
 * Created by musooff on 16/01/2019.
 */

object CoupletUtils {

    private const val ALLOWED_KEYS = "йқуӯкеёнгғшҳзхъфҷвапролджэячсмитӣбюЙҚУӮКЕЁНГҒШҲЗХЪФҶВАПРОЛДЖЭЯЧСМИТӢБЮ -\"':;!?,."
    private const val ALLOWED_SIGNS = "-\"':;!?,. "
    private const val ALLOWED_LETTERS = "йқуӯкеёнгғшҳзхъфҷвапролджэячсмитӣбюЙҚУӮКЕЁНГҒШҲЗХЪФҶВАПРОЛДЖЭЯЧСМИТӢБЮ"
    private const val SPECIAL_I = "Ӣ"
    private const val NORMAL_I = "И"
    private const val MIN_LENGTH = 10

    fun canSubmit(startingLetter: String?, line1: String, line2: String) : Boolean{
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
        if (startingLetter == SPECIAL_I){
            return isValidFirstLetter(NORMAL_I, line)
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
}