package Controller

import Model.*
import View.ViewerBMP
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class ControllerBMP: Controller {
    override fun validateFormat(file: File){
        try {
            val parser = getParser(file)
            parser.addObserver(ViewerBMP())
            parser.parse()
        } catch (exception: IOException) {
            println("Can't read file: ${exception.message}")
        }
    }

    private fun getParser(file: File): Parser {

        val inputFile = FileInputStream(file)
        val header = ByteArray(0x0E)
        inputFile.read(header)

        if (header[0].toInt() != 0x42 || header[1].toInt() != 0x4D)
            throw Exception("Type is not bmp")

        val sizeBI = inputFile.read() + (inputFile.read() shl 8) + (inputFile.read() shl 16) + (inputFile.read() shl 24)
        val info = ByteArray(0x10)
        inputFile.read(info)
        val bitCount: Int

        if (sizeBI == 0x0C)
            bitCount = info[0x06] + (info[0x07].toInt() shl 8)
        else
            bitCount = info[0x0A] + (info[0x0B].toInt() shl 8)

        inputFile.close()

        return parserByBitCount(bitCount, file)
    }

    private fun parserByBitCount(bitCount: Int, file: File): Parser {
        return when (bitCount) {
            8 -> Parser8bit(file)
            24 -> Parser24bit(file)
            else -> TODO("Пока неизвестный формат")
        }
    }
}