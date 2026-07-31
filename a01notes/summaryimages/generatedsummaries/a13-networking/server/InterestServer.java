import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple Core Java TCP server - no Maven, no Spring, no external jars.
 * Receives: principal,rate,days
 * Returns: calculated simple interest
 */
public class InterestServer {

    public static double calculateSimpleInterest(double principal, double rate, int days) {
        return (principal * rate * days) / (100.0 * 365.0);
    }

    public static void main(String[] args) throws Exception {
        int port = 5000;
        System.out.println("InterestServer started on port " + port);
        System.out.println("Waiting for client requests...");

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("Client connected: " + client.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            String request = in.readLine();
            System.out.println("Received: " + request);

            try {
                String[] parts = request.split(",");
                double principal = Double.parseDouble(parts[0].trim());
                double rate = Double.parseDouble(parts[1].trim());
                int days = Integer.parseInt(parts[2].trim());

                double interest = calculateSimpleInterest(principal, rate, days);
                String response = "Principal=" + principal
                        + ", Rate=" + rate
                        + ", Days=" + days
                        + ", Interest=" + interest;

                out.println(response);
                System.out.println("Sent: " + response);
            } catch (Exception e) {
                out.println("ERROR: send data as principal,rate,days example 100000,3.5,30");
            }

            client.close();
        }
    }
}
