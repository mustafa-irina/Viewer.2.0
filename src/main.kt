import Controller.*
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    for (file in args) {
        val controller = when {
            file.endsWith(".bmp", true) -> ControllerBMP()
            else -> exitProcess(1)
        }

        try {
            controller.validateFormat(File(file))
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}