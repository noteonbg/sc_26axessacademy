import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Simple Core Java TCP client - no Maven, no Spring, no external jars.
 * Connects to another container by hostname (Docker network DNS).
 */
public class InterestClient {

    public static void main(String[] args) throws Exception {
        String host = System.getenv().getOrDefault("SERVER_HOST", "localhost");
        int port = 5000;

        String principal = System.getenv().getOrDefault("PRINCIPAL", "100000");
        String rate = System.getenv().getOrDefault("RATE", "3.5");
        String days = System.getenv().getOrDefault("DAYS", "30");
        String request = principal + "," + rate + "," + days;

        System.out.println("Connecting to " + host + ":" + port);
        System.out.println("Sending: " + request);

        Socket socket = new Socket(host, port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println(request);
        String response = in.readLine();

        System.out.println("Response from server: " + response);

        socket.close();
    }
}
