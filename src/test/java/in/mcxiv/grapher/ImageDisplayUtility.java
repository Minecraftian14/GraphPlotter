package in.mcxiv.grapher;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class ImageDisplayUtility {

    static int size = 150;

    public static Runnable display(String name, Supplier<BufferedImage> image, int x, int y) {
        JFrame frame = new JFrame();
        frame.setTitle(name);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton button = new JButton(createIcon(image.get()));
        button.setToolTipText(name);
        Runnable imageUpdator = () -> button.setIcon(createIcon(image.get()));
        button.addActionListener(e -> imageUpdator.run());
        frame.add(button);

        frame.setAlwaysOnTop(true);
        frame.pack();
        frame.setLocation(frame.getWidth() * x, frame.getHeight() * y);
        frame.setVisible(true);

        return imageUpdator;
    }

    public static void display(String name, BufferedImage __image, int x, int y) {
        final double scale = 1;
        final BufferedImage image = new BufferedImage((int) (scale * __image.getWidth()), (int) (scale * __image.getHeight()), 2);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawImage(__image, 0, 0, image.getWidth(), image.getHeight(), null);
        graphics.dispose();

        SwingUtilities.invokeLater(() -> {
            new JFrame() {
                {
                    setTitle(name);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
//                setLayout(new GridBagLayout());

                    add(new JScrollPane(new JPanel() {
                        {
                            setLayout(null);
                            setSize(image.getWidth(), image.getHeight());
                            setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
                        }
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.drawImage(image, 0, 0, null);
                        }
                    }){{
                        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    }}/*, new GridBagConstraints() {{
                    ipadx = image.getWidth()/2;
                    ipady = image.getHeight()/2;
                }}*/);

                    pack();
                    setAlwaysOnTop(true);
                    setLocation(getWidth() * x, getHeight() * y);
                    setVisible(true);
                }
            };
        });
    }

    private static ImageIcon createIcon(BufferedImage image) {
        BufferedImage show = new BufferedImage(size, size, 2);
        Graphics2D graphics = show.createGraphics();
        graphics.drawImage(image, 0, 0, size, size, null);
        graphics.dispose();
        return new ImageIcon(show);
    }

}
