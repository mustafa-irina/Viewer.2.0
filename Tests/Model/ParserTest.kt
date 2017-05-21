package Model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.imageio.ImageIO.*
import java.awt.image.BufferedImage
import java.io.File

internal class ParserTest{
    val testPath = "./bmp/"
    val images_8bit = arrayListOf<String>(
           "${testPath}bogts_8bit.bmp",
            "${testPath}freebsd2_8bit.bmp",
            "${testPath}hm_8bit.bmp",
            // "${testPath}lena512.bmp",
            "${testPath}man.bmp"
    )
    val images_24bit = arrayListOf<String>(
            "${testPath}beaut_24bit.bmp",
            "${testPath}dodj_24bit.bmp",
            "${testPath}love_24bit.bmp",
            "${testPath}ogon_24bit.bmp",
            "${testPath}per_24bit.bmp",
            "${testPath}su85_24bit.bmp",
            "${testPath}taet_led_24bit.bmp",
            "${testPath}warrios_24bit.bmp"
    )

    @Test
    fun parse_8bit() {
        images_8bit.forEach { it ->
            val image = File(it)
            val parser = Parser8bit(image)
            parser.parse()
            assertTrue(imagesEquals(parser.image, it))
        }
    }

    @Test
    fun parse_24bit() {
        images_24bit.forEach { it ->
            val image = File(it)
            val parser = Parser24bit(image)
            parser.parse()
            assertTrue(imagesEquals(parser.image, it))
        }
    }

    fun imagesEquals(image1: BufferedImage, filePath: String): Boolean {
        val file = File(filePath)
        val image2 = read(file)
        if (image1.height != image2.height || image1.width != image2.width )
            return false
        for (i in 0 .. image1.height - 1)
            (0 .. image1.width - 1)
                    .filter { image1.getRGB(it, i) != image2.getRGB(it, i) }
                    .forEach { return false }
        return true
    }
}