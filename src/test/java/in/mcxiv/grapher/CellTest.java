package in.mcxiv.grapher;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

class CellTest {

    public static void main(String[] args) {
        new CellTest().test();
    }

    @Test
    void test() {
        BufferedImage image = new BufferedImage(100, 100, 2);

        new Cell().paintIcon(null, image.createGraphics(), 10, 10);

        ImageDisplayUtility.display("Cell Test", image, 0, 0);
    }
}