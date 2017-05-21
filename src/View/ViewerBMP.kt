package View

import View.Observer
import java.awt.image.BufferedImage
import javax.swing.JFrame

class ViewerBMP: Observer {

    override fun onDataChange(image: BufferedImage) {
        draw(image)
    }

    fun draw(image: BufferedImage) {
        val frame = JFrame("Viewer")
        val panel = DrawImage(image)

        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.contentPane.add(panel)
        frame.setSize(image.width, image.height)
        frame.isVisible = true
    }
}