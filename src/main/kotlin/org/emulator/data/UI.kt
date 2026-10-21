package org.emulator.data

interface UI {
    var inputSource: InputSource
    fun input(): String?
    fun printOutput(message: String)
}