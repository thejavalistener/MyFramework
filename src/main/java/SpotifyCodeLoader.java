import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class SpotifyCodeLoader extends JFrame {
    private JTextField uriField;
    private JLabel imageLabel;


    public SpotifyCodeLoader() {
        setTitle("Visualizador de Spotify Code");
        setSize(500, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        uriField = new JTextField("spotify:album:3j9nlCtanXCPvoaFzrpjPs"); // podés cambiar el URI
        JButton loadButton = new JButton("Mostrar Código");
        imageLabel = new JLabel("", SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Spotify URI: "), BorderLayout.WEST);
        topPanel.add(uriField, BorderLayout.CENTER);
        topPanel.add(loadButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        loadButton.addActionListener(e -> {
            String uri = uriField.getText().trim();
            try {
                String encodedUri = java.net.URLEncoder.encode(uri, "UTF-8");
                String url = "https://scannables.scdn.co/uri/plain/png/000000/white/280/" + encodedUri;
                BufferedImage image = ImageIO.read(new URL(url));
                imageLabel.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen.\n" + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SpotifyCodeLoader().setVisible(true));
    }
}