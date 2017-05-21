package View

import java.awt.Graphics
import java.awt.Panel
import java.awt.image.BufferedImage

class DrawImage(val image: BufferedImage): Panel() {

    override fun paint(graphics: Graphics) {
        graphics.drawImage(image, 0, 0, this)
    }

}