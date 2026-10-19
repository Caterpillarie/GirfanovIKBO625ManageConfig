package org.emulator.adapters

import org.emulator.data.UI
import java.lang.Exception
import java.net.InetAddress

class ConsoleUI : UI {
    private val prompt: String
        get() {
            val user = System.getProperty("user.name")
            val host = try {
                InetAddress.getLocalHost().hostName
            } catch (e: Exception) { "localhost" }
//            val cwd = System.getProperty("user.dir")
            return "$user@$host:~$"
        }

    override fun input(): String? {
        print("$prompt ")
        return readlnOrNull()
    }

    override fun printOutput(message: String) =
        print(message)
}