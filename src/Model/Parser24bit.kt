package Model

import java.io.File

class Parser24bit(file: File): Parser(file) {

    override fun parse() {
        preParse()
        var currentOffset = fileStructure["offsetBits"]!!
        val imageWidth = fileStructure["width"]!!
        val imageHeight = fileStructure["height"]!!
        val vector = if (imageHeight > 0) -1 else 1
        val edge = if (imageHeight > 0) -1 else -imageHeight
        var currentLine = if (imageHeight > 0) imageHeight - 1 else 0
        val addition = if ((imageWidth * 3) % 4 == 0) 0 else 4 - ((imageWidth * 3) % 4)

        while (currentLine != edge) {
            for (currentColumn in 0 .. imageWidth - 1) {
                image.setRGB(currentColumn, currentLine, fileBytesToInt(currentOffset, 3))
                currentOffset += 3
            }
            currentOffset += addition
            currentLine += vector
        }

        alertObservers()
    }
}