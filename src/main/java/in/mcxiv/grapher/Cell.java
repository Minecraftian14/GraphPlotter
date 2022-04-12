package in.mcxiv.grapher;

import javax.swing.*;
import java.awt.*;

public class Cell implements Icon {

    public static final Cell INSTANCE = new Cell();

    public static final Color PAPER_COLOR = new Color(248, 245, 240);
    public static final Color BOLD_LINE = new Color(0, 220, 37);
    public static final Color THIN_LINE = new Color(154, 255, 165);

    public static final int LINE_THICKNESS = 1;
    public static final int LINE_SPACING = 5;

    public static final int THAT_SPACE = LINE_THICKNESS * 10 + LINE_SPACING * 10;

    public static final int WIDTH = LINE_THICKNESS * 10 + LINE_SPACING * 10;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.setColor(PAPER_COLOR);
        g.fillRect(x, y, WIDTH, WIDTH);

        g.setColor(THIN_LINE);
        for (int
             i = 0,
             lx = LINE_THICKNESS + LINE_SPACING;
             i < 9;
             i++, lx += LINE_THICKNESS + LINE_SPACING
        ) {
            g.drawLine(x + lx, y, x + lx, y + THAT_SPACE);
            g.drawLine(x, y + lx, x + THAT_SPACE, y + lx);
        }

        g.setColor(BOLD_LINE);
        g.drawRect(x, y, WIDTH, WIDTH);
    }

    @Override
    public int getIconWidth() {
        return WIDTH;
    }

    @Override
    public int getIconHeight() {
        return WIDTH;
    }
}
