import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserRegistrationForm extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JLabel resultLabel;

    public UserRegistrationForm() {
        setTitle("User Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("Enter Name: ");
        nameField = new JTextField(20);
        add(nameLabel);
        add(nameField);

        JLabel emailLabel = new JLabel("Enter Email: ");
        emailField = new JTextField(20);
        add(emailLabel);
        add(emailField);

        JButton submitButton = new JButton("Submit");
        add(submitButton);

        resultLabel = new JLabel("");
        add(resultLabel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
    }

    private void handleSubmit() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            showError("Please fill in all fields.");
        } else {
            try {
                sendToBackend(name, email);
            } catch (Exception ex) {
                showError("Error sending data to backend: " + ex.getMessage());
            }
        }
    }

    private void sendToBackend(String name, String email) throws Exception {
        URL url = new URL("http://localhost:8080/user/register");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.getOutputStream().write(("username=" + name + "&email=" + email).getBytes());

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            resultLabel.setText("User registered successfully.");
        } else {
            resultLabel.setText("Registration failed.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserRegistrationForm().setVisible(true));
    }
}
