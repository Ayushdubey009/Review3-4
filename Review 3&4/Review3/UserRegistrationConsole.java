import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserRegistrationConsole {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to User Registration Console");

        // Get user input for name and email
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        // Validate the input (simple checks)
        if (name.isEmpty() || email.isEmpty()) {
            System.out.println("Error: Please fill in all fields.");
        } else if (!isValidEmail(email)) {
            System.out.println("Error: Invalid email format.");
        } else {
            try {
                sendToBackend(name, email);
            } catch (Exception e) {
                System.out.println("Error sending data to backend: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // Method to validate email format
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Method to send data to the backend
    private static void sendToBackend(String name, String email) throws Exception {
        URL url = new URL("http://localhost:8080/user/register");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.getOutputStream().write(("username=" + name + "&email=" + email).getBytes());

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Error: Registration failed.");
        }
    }
}
