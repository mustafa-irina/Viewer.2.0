package View

import java.awt.image.BufferedImage


interface Observer {
    fun onDataChange(image: BufferedImage)
}