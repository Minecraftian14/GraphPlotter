package in.mcxiv.grapher;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph(false, new Graph.OriginPosition(Graph.OriginPositionX.LEFT, Graph.OriginPositionY.BOTTOM));
        for (int i = 0; i < args.length; i += 2)
            graph.addSample(Double.parseDouble(args[i]), Double.parseDouble(args[i + 1]));
        BufferedImage image = new BufferedImage(graph.WIDTH, graph.HEIGHT, 2);
        graph.paintIcon(null, image.createGraphics(), 0, 0);
        ImageIO.write(image, "PNG", new File("output.png") {{
            createNewFile();
        }});
    }
}
