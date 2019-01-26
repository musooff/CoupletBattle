package com.ballboycorp.battle.battle.newcouplet.model

/**
 * Created by musooff on 26/01/2019.
 */

enum class Input (var isCorrect: Boolean, var solution: String) {
    WRONG_STARTING(false, "Лутфан байт бо харфи %s пеш оваред"),
    WRONG_AUTHOR(false, "Лутфан аз шоирони тастикшуда байт пеш оваред"),
    BLACK_AUTHOR(false, "Лутфан аз шоирро интихоб кунед."),
    TOO_SHORT(false, "Мисраи шумо хеле кутох аст."),
    NOT_TAJIK(false, "Лутфан харфхои точики истифода баред."),
    CORRECT_INPUT(true, ""),
    BLACK_INPUT(false, "Лутфан мисраро пур кунед.")

}