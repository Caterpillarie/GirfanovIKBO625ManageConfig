package org.emulator.data

interface UI {
    fun input(): String?
    fun printOutput(message: String)
}