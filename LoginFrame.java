package UI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import DB.DBConnection;
public class LoginFrame extends JFrame
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;


    public LoginFrame()
    {
        setTitle("SMART LEAVE MANAGEMENT SYSTEM ");
        setSize(420,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(236, 240, 241));

        // Card panel
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setPreferredSize(new Dimension(320, 380));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Smart Leave System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        cardPanel.add(titleLabel, gbc);

        // Username Label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridy = 1;
        cardPanel.add(usernameLabel, gbc);

        // Username Field
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(220, 35));

        gbc.gridy = 2;
        cardPanel.add(usernameField, gbc);

        // Password Label
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridy = 3;
        cardPanel.add(passLabel, gbc);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(220, 35));

        gbc.gridy = 4;
        cardPanel.add(passwordField, gbc);

        // Button
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 35));

        loginButton.addActionListener(e -> {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());

    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, username);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Login Successful");

            new DashboardFrame(); // open next screen
            dispose(); // close login

        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }
});
        gbc.gridy = 5;
        cardPanel.add(loginButton, gbc);

        // Add card to center
        mainPanel.add(cardPanel);

        add(mainPanel);


    }


    public static void main(String [] ahsin)
    {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
