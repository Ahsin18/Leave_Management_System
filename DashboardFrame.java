package UI;
import javax.swing.*;
import javax.swing.border.EmptyBorder; 
import java.awt.*;
import java.sql.*;
import DB.DBConnection;
public class DashboardFrame extends JFrame
{
    private JPanel contentPanel;
    public DashboardFrame()
    {
        setTitle("Dashboard");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //slid bar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(10, 0, 10, 0)); 

        // TITLE (TOP)
        JLabel title = new JLabel("Menu");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(10, 15, 15, 15));


        // buttons
        JButton dashboardBtn =  createSidebarButton("Dashboard");
        JButton ApplyLeaveBtn =  createSidebarButton("Apply Leave");
        ApplyLeaveBtn.addActionListener(e -> showApplyLeaveForm());
        JButton myLeavesBtn =  createSidebarButton(" My Leave History");
        myLeavesBtn.addActionListener(e -> {
         new ViewLeavesFrame();
        });
        JButton logoutBtn =  createSidebarButton("Logout");
        
        
         dashboardBtn.setBackground(new Color(52, 152, 219));

        sidebar.add(title); 
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); 
        sidebar.add(dashboardBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5))); 
        sidebar.add(ApplyLeaveBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(myLeavesBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // content panel
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);
  
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
         
    }

    private JButton createSidebarButton(String text)
    {
       JButton btn = new JButton(text);

        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setFocusPainted(false);

        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setBorderPainted(false);

        btn.setBackground(new Color(52, 73, 94));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        return btn;
    }


   private void showApplyLeaveForm() {

    contentPanel.removeAll();
    contentPanel.setLayout(new GridBagLayout());

    // ===== MAIN FORM PANEL =====
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setPreferredSize(new Dimension(420, 420));
    formPanel.setBackground(Color.WHITE);
    formPanel.setBorder(new EmptyBorder(30, 40, 30, 40)); // better padding

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(12, 12, 12, 12);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // ===== TITLE =====
    JLabel title = new JLabel("Apply for Leave");
    title.setFont(new Font("Segoe UI", Font.BOLD, 22));
    title.setHorizontalAlignment(SwingConstants.CENTER);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    formPanel.add(title, gbc);

    gbc.gridwidth = 1;

    // ===== FROM DATE =====
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.anchor = GridBagConstraints.EAST;
    formPanel.add(new JLabel("From Date:"), gbc);

    gbc.gridx = 1;
    JTextField fromDate = new JTextField();
    fromDate.setPreferredSize(new Dimension(200, 30));
    formPanel.add(fromDate, gbc);

    // ===== TO DATE =====
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.anchor = GridBagConstraints.EAST;
    formPanel.add(new JLabel("To Date:"), gbc);

    gbc.gridx = 1;
    JTextField toDate = new JTextField();
    toDate.setPreferredSize(new Dimension(200, 30));
    formPanel.add(toDate, gbc);

    // ===== LEAVE TYPE =====
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.anchor = GridBagConstraints.EAST;
    formPanel.add(new JLabel("Leave Type:"), gbc);

    gbc.gridx = 1;
    JComboBox<String> leaveType = new JComboBox<>(
            new String[]{"Sick Leave", "Casual Leave", "Paid Leave"}
    );
    leaveType.setPreferredSize(new Dimension(200, 30));
    formPanel.add(leaveType, gbc);

    // ===== REASON =====
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.anchor = GridBagConstraints.NORTHEAST;
    formPanel.add(new JLabel("Reason:"), gbc);

    gbc.gridx = 1;
    JTextArea reason = new JTextArea(4, 15);
    reason.setLineWrap(true);
    reason.setWrapStyleWord(true);

    JScrollPane scroll = new JScrollPane(reason);
    scroll.setPreferredSize(new Dimension(200, 80));
    formPanel.add(scroll, gbc);

    // ===== BUTTON =====
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;

    JButton submitBtn = new JButton("Apply");
    submitBtn.setBackground(new Color(52, 152, 219));
    submitBtn.setForeground(Color.WHITE);
    submitBtn.setFocusPainted(false);
    submitBtn.setPreferredSize(new Dimension(120, 35));

    formPanel.add(submitBtn, gbc);
    submitBtn.addActionListener(e -> {
    try {
        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO leaves(username, from_date, to_date, leave_type, reason) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, "ahsin"); // later dynamic
        pst.setString(2, fromDate.getText());
        pst.setString(3, toDate.getText());
        pst.setString(4, leaveType.getSelectedItem().toString());
        pst.setString(5, reason.getText());

        pst.executeUpdate();

        JOptionPane.showMessageDialog(null, "Leave Applied Successfully!");

    } catch (Exception ex) {
        ex.printStackTrace();
    }
});

    // ===== CENTER FORM =====
    contentPanel.add(formPanel, new GridBagConstraints());

    contentPanel.revalidate();
    contentPanel.repaint();
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardFrame().setVisible(true);
        });
    }
}

