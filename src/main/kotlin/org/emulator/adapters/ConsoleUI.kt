package org.emulator.adapters

import org.emulator.data.InputSource
import org.emulator.data.UI
import java.net.InetAddress

class ConsoleUI(
    initialSource: InputSource,
    var currentPath: () -> String = { System.getProperty("user.dir") }
) : UI {
    override var inputSource = initialSource
    private val prompt: String
        get() {
            val user = System.getProperty("user.name")
            val host = try {
                InetAddress.getLocalHost().hostName
            } catch (_: Exception) { "localhost" }
            return "$user@$host:${currentPath()}$"
        }

    override fun input(): String? {
        print("$prompt ")
        return inputSource.nextLine()
    }

    override fun printOutput(message: String) =
        print(message)
}