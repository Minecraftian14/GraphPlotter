package in.mcxiv.grapher;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

class GraphTest {

    public static void main(String[] args) {
        new GraphTest().test();
    }

    @Test
    void test() {
        Graph graph = new Graph(false, new Graph.OriginPosition(Graph.OriginPositionX.LEFT, Graph.OriginPositionY.BOTTOM))
                .addSample(0,10)
                .addSample(1,20)
                .addSample(2,40)
                .addSample(3,90)
                .addSample(4,160)
                ;

        BufferedImage image = new BufferedImage(graph.WIDTH + 200, graph.HEIGHT + 200, 2);

        graph.paintIcon(null, image.createGraphics(), 20,20);

        ImageDisplayUtility.display("Cell Test", image, 0, 0);
    }

}