package com.ballboycorp.battle.common.utils

import com.ballboycorp.battle.battle.newcouplet.model.Input

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

    fun canSubmit(startingLetter: String?, line1: String, line2: String, author: String, authorLists: List<String>): Input {
        if (line1.isBlank() || line2.isBlank()) {
            return Input.BLACK_INPUT
        }
        if (!isValidFirstLetter(startingLetter, line1)){
            return Input.WRONG_STARTING
        }
        if (isTooShort(line1) || isTooShort(line2)){
            return Input.TOO_SHORT
        }
        if (!isTajik(line1) || !isTajik(line2)){
            return Input.NOT_TAJIK
        }
        if (author.isBlank()){
            return Input.BLACK_AUTHOR
        }
        if (!authorLists.contains(author)){
            return Input.WRONG_AUTHOR
        }
        return Input.CORRECT_INPUT
    }

    fun getEndingLetter(line: String): String {
        var reversed = line.reversed()
        while (ALLOWED_SIGNS.contains(reversed[0])) {
            reversed = reversed.substring(1)
        }
        return reversed[0].toString().toUpperCase()

    }

    private fun isValidFirstLetter(startingLetter: String?, line: String): Boolean {
        if (startingLetter == null) {
            return true
        }
        when (startingLetter) {
            LETTER_I_ZADA -> return isValidFirstLetter(LETTER_I, line)
            LETTER_YE -> return isValidFirstLetter(LETTER_E, line)
        }
        return line[0].toString().toUpperCase() == startingLetter
    }

    private fun isTooShort(line: String) : Boolean {
        return line.length < MIN_LENGTH
    }

    private fun isTajik(line: String): Boolean {

        line.forEach {
            if (!ALLOWED_KEYS.contains(it)) {
                return false
            }
        }

        return true
    }

    fun firstLineHint(startingLetter: String?): String {
        return if (startingLetter == null) {
            FIRST_LINE_DEFAULT
        } else {
            FIRST_LINE_WITH_LETTERS + when (startingLetter) {
                LETTER_I_ZADA -> LETTER_I
                LETTER_YE -> LETTER_E
                else -> startingLetter
            }
        }
    }

    fun allowedLetters(startingLetter: String?): String? {
        return when (startingLetter) {
            LETTER_I_ZADA -> LETTER_I
            LETTER_YE -> LETTER_E
            else -> startingLetter
        }
    }
}