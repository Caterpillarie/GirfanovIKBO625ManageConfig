package org.emulator.adapters

import org.emulator.data.InputSource
import org.emulator.data.UI
import java.net.InetAddress

class ConsoleUI(initialSource: InputSource) : UI {
    override var inputSource = initialSource
    private val prompt: String
        get() {
            val user = System.getProperty("user.name")
            val host = try {
                InetAddress.getLocalHost().hostName
            } catch (_: Exception) { "localhost" }
//            val cwd = System.getProperty("user.dir")
            return "$user@$host:~$"
        }

    override fun input(): String? {
        print("$prompt ")
        return inputSource.nextLine()
    }

    override fun printOutput(message: String) =
        print(message)
}