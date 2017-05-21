package Model

import View.Observer
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.util.*

abstract class Parser(val file: File): Observable {

    val observers = ArrayList<Observer>()
    protected val fileBytes = ByteArray(file.length().toInt())
    protected val fileStructure = HashMap<String, Int>()
    protected val colorTable = ArrayList<Int>()
    val image: BufferedImage


    init {
        preParse()
        val imageWidth = fileBytesToInt(0x12, 4)
        val imageHeight = fileBytesToInt(0x16, 4)

        if (imageHeight < 0)
            image = BufferedImage(imageWidth, -imageHeight, BufferedImage.TYPE_INT_RGB)
        else
            image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)

    }

    protected fun preParse() {
        val inputStream = FileInputStream(file)
        inputStream.read(fileBytes)
        inputStream.close()
        println(fileBytes.size)

        fileStructure.put("fileSize", fileBytesToInt(0x02, 4))
        fileStructure.put("offsetBits", fileBytesToInt(0x0A, 4))
        val biSize = fileBytesToInt(0x0E, 4)
        fileStructure.put("biSize", biSize)
        val imageWidth = fileBytesToInt(0x12, 4)
        val imageHeight = fileBytesToInt(0x16, 4)
        fileStructure.put("width", imageWidth)
        fileStructure.put("height", imageHeight)
        val bitCount = fileBytesToInt(0x1C, 2)
        fileStructure.put("bitCount", bitCount)
        fileStructure.put("compression", fileBytesToInt(0x1E, 4))
        fileStructure.put("sizeImage", fileBytesToInt(0x22, 4))
        fileStructure.put("clrUsed", fileBytesToInt(0x2E, 4))

        if (bitCount <= 8) {

            var currentOffset = 0x36
            val clrUsed = fileStructure["clrUsed"] ?: 0
            val colorCount = if (biSize == 0x0C || clrUsed == 0) Math.pow(2.0, bitCount.toDouble()).toInt() else clrUsed
            val colorSize = if (biSize == 0x0C) 3 else 4

            for (i in 0..colorCount - 1) {
                colorTable.add(fileBytesToInt(currentOffset, colorSize))
                currentOffset += colorSize
            }
        }
    }

    abstract fun parse()

    override fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun delObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun alertObservers() {
        observers.forEach { it.onDataChange(this.image) }
    }

    protected fun fileBytesToInt(offset: Int, length: Int) = (0..length - 1).sumBy { ((fileBytes[offset + it].toInt() + 256) % 256) shl (8 * it) }

}