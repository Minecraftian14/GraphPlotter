package in.mcxiv.grapher;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.function.ToIntFunction;

public class Graph implements Icon {

    private static final Color PENCIL_COLOR = new Color(77, 77, 77);

    public interface AxialPosition {
        int calculate(Graph graph);
    }

    public enum OriginPositionX {
        LEFT(graph -> graph.MARGIN + Cell.WIDTH * 2),
        CENTER(graph -> graph.MARGIN + graph.CELLS_IN_X * Cell.WIDTH / 2),
        RIGHT(graph -> graph.MARGIN + (graph.CELLS_IN_X - 2) * Cell.WIDTH);

        private final ToIntFunction<Graph> function;

        OriginPositionX(ToIntFunction<Graph> function) {
            this.function = function;
        }

        int calculate(Graph graph) {
            return function.applyAsInt(graph);
        }
    }

    public enum OriginPositionY {
        TOP(graph -> graph.MARGIN + Cell.WIDTH * 2),
        CENTER(graph -> graph.MARGIN + graph.CELLS_IN_Y * Cell.WIDTH / 2),
        BOTTOM(graph -> graph.HEIGHT - graph.MARGIN - Cell.WIDTH * 2);

        private final ToIntFunction<Graph> function;

        OriginPositionY(ToIntFunction<Graph> function) {
            this.function = function;
        }

        int calculate(Graph graph) {
            return function.applyAsInt(graph);
        }
    }

    public record OriginPosition(OriginPositionX x, OriginPositionY y) {
        static OriginPosition BOTTOM_LEFT = new OriginPosition(OriginPositionX.LEFT, OriginPositionY.BOTTOM);
    }

    public final int RADIUS = 3;
    public final int DIAMETER = RADIUS * 2 + 1;

    public final int CELLS_IN_X;
    public final int CELLS_IN_Y;

    public final int MARGIN;

    public final int WIDTH;
    public final int HEIGHT;

    public final Point originPosition;

    public final HashMap<Double, Double> map = new HashMap<>();

    public Graph() {
        this(true, OriginPosition.BOTTOM_LEFT);
    }

    public Graph(boolean isPortrait, OriginPosition originPosition) {
        CELLS_IN_X = isPortrait ? 20 : 26;
        CELLS_IN_Y = isPortrait ? 26 : 20;

        MARGIN = Cell.WIDTH;

        HEIGHT = MARGIN * 2 + Cell.WIDTH * CELLS_IN_Y + Cell.LINE_THICKNESS;
        WIDTH = MARGIN * 2 + Cell.WIDTH * CELLS_IN_X + Cell.LINE_THICKNESS;

        this.originPosition = new Point(originPosition.x.calculate(this), originPosition.y.calculate(this));
    }

    public Graph addSample(double x, double y) {
        map.put(x, y);
        return this;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        paintGraph(g, x, y);
        paintAxis(g, x, y);

        g.translate(x + originPosition.x, y + originPosition.y);
        g.setColor(PENCIL_COLOR);

        class AtomicDouble {
            double value;

            public AtomicDouble(double value) {
                this.value = value;
            }
        }

        var max_x = new AtomicDouble(map.keySet().stream().findAny().get());
        var min_x = new AtomicDouble(max_x.value);
        var x_vals = map.keySet().stream().mapToDouble(value -> value)
                .peek(value -> {
                    max_x.value = Math.max(max_x.value, value);
                    min_x.value = Math.min(min_x.value, value);
                }).sorted().boxed()
                .toList();

        var max_y = new AtomicDouble(map.values().stream().findAny().get());
        var min_y = new AtomicDouble(max_y.value);
        var y_vals = x_vals.stream().map(map::get).mapToDouble(value -> value)
                .peek(value -> {
                    max_y.value = Math.max(max_y.value, value);
                    min_y.value = Math.min(min_y.value, value);
                }).boxed().toList();

        assert x_vals.size() == y_vals.size();

        int _px = 0, _py = 0;

        var scale_x = (CELLS_IN_X - 3f) * Cell.WIDTH * 1f / (max_x.value /*- min_x.value*/);
        var scale_y = (CELLS_IN_Y - 3f) * Cell.WIDTH * 1f / (max_y.value /*- min_y.value*/);

        for (int i = 0, s = x_vals.size(); i < s; i++) {
            int px = (int) (x_vals.get(i) * scale_x);
            int py = (int) (y_vals.get(i) * scale_y);
            g.fillOval(px - RADIUS, -py - RADIUS, DIAMETER, DIAMETER);
            if (i > 0) g.drawLine(_px, -_py, px, -py);
            _px = px;
            _py = py;
        }
    }

    private void paintGraph(Graphics g, int x, int y) {
        for (int i = 0; i < CELLS_IN_X; i++) {
            for (int j = 0; j < CELLS_IN_Y; j++) {
                Cell.INSTANCE.paintIcon(null, g,
                        x + MARGIN + i * Cell.THAT_SPACE,
                        y + MARGIN + j * Cell.THAT_SPACE);
            }
        }
    }

    private void paintAxis(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.translate(x, y);
        g.drawLine(originPosition.x, MARGIN + Cell.WIDTH, originPosition.x, HEIGHT - MARGIN - Cell.WIDTH);
        g.drawLine(MARGIN + Cell.WIDTH, originPosition.y, WIDTH - MARGIN - Cell.WIDTH, originPosition.y);
        g.translate(-x, -y);
    }

    @Override
    public int getIconWidth() {
        return WIDTH;
    }

    @Override
    public int getIconHeight() {
        return HEIGHT;
    }
}
