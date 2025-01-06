import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

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
        } else if (!isValidEmail(email)) {
            showError("Invalid email format.");
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
        connection.setRequestProperty("Content-Type", "application/json");

        String jsonInputString = String.format("{\"username\": \"%s\", \"email\": \"%s\"}", name, email);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            resultLabel.setText("User registered successfully.");
        } else {
            resultLabel.setText("Registration failed.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserRegistrationForm().setVisible(true));
    }
}
