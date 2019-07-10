package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {

    abstract fun formatMessage() : String
    companion object AbstractFactory {
        var lastId = -1

        fun makeMessage(from: User?,
                        chat: Chat,
                        date: Date = Date(),
                        type: String,
                        payload: Any?,
                        isIncoming: Boolean = false) : BaseMessage {
            lastId++

            return when(type) {
                "text" -> TextMessage("$lastId", from, chat, isIncoming, date, payload as String)
                else -> ImageMessage("$lastId", from, chat, isIncoming, date, payload as String)
            }
        }
    }
}

enum class MessageType {
    TEXT,
    IMAGE
}