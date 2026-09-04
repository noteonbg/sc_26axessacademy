package com.bank.account;

import com.bank.account.model.Account;
import com.bank.account.repository.AccountRepository;
import com.bank.account.repository.AccountRepositoryImpl;
import com.bank.account.service.AccountService;
import com.bank.account.service.AccountServiceImpl;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;

public class MainApplication {

    private static int loadPort() {
        int defaultPort = 7000;
        try (InputStream input = MainApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                String portStr = prop.getProperty("server.port");
                if (portStr != null && !portStr.trim().isEmpty()) {
                    return Integer.parseInt(portStr.trim());
                }
            }
        } catch (Exception ignored) {
        }
        String sysPort = System.getProperty("server.port", System.getenv("PORT"));
        if (sysPort != null && !sysPort.trim().isEmpty()) {
            try {
                return Integer.parseInt(sysPort.trim());
            } catch (NumberFormatException ignored) {
            }
        }
        return defaultPort;
    }

    public static HttpServer startServer(int port, AccountRepository repository, AccountService service) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Health check & Info
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if ("/".equals(path) || "/health".equals(path)) {
                String response = "{\"status\":\"UP\",\"port\":" + port + ",\"service\":\"Banking Account Management Service\"}";
                sendJsonResponse(exchange, 200, response);
            } else if (path.startsWith("/api/accounts")) {
                handleAccounts(exchange, repository, service);
            } else {
                sendJsonResponse(exchange, 404, "{\"error\":\"Not Found\"}");
            }
        });

        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        return server;
    }

    private static void handleAccounts(HttpExchange exchange, AccountRepository repository, AccountService service) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(method)) {
            if ("/api/accounts".equals(path) || "/api/accounts/".equals(path)) {
                List<Account> accounts = repository.findAll();
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < accounts.size(); i++) {
                    json.append(accountToJson(accounts.get(i)));
                    if (i < accounts.size() - 1) json.append(",");
                }
                json.append("]");
                sendJsonResponse(exchange, 200, json.toString());
            } else {
                String accountId = path.substring("/api/accounts/".length());
                try {
                    Account acc = service.getAccountDetails(accountId);
                    sendJsonResponse(exchange, 200, accountToJson(acc));
                } catch (Exception e) {
                    sendJsonResponse(exchange, 404, "{\"error\":\"" + e.getMessage() + "\"}");
                }
            }
        } else {
            sendJsonResponse(exchange, 405, "{\"error\":\"Method Not Allowed\"}");
        }
    }

    private static String accountToJson(Account a) {
        return "{\"accountId\":\"" + a.getAccountId() + "\",\"holderName\":\"" + a.getHolderName() +
               "\",\"balance\":" + a.getBalance() + ",\"accountType\":\"" + a.getAccountType() +
               "\",\"active\":" + a.isActive() + "}";
    }

    private static void sendJsonResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   Starting Banking Account Management System (Live Demo)  ");
        System.out.println("==========================================================");

        int port = loadPort();
        AccountRepository repository = new AccountRepositoryImpl(0); // fast in-memory access for HTTP demo
        AccountService service = new AccountServiceImpl(repository);

        try {
            System.out.println("\n[Demo 1] Fetching Account details for ACC1001...");
            Account acc1 = service.getAccountDetails("ACC1001");
            System.out.println("Result: " + acc1);

            System.out.println("\n[Demo 2] Depositing $2500 into ACC1001...");
            Account updatedAcc1 = service.deposit("ACC1001", 2500.0);
            System.out.println("Result: " + updatedAcc1);

            System.out.println("\n[Demo 3] Checking if ACC1002 is High-Value Customer...");
            boolean isHvc = service.isHighValueCustomer("ACC1002");
            System.out.println("Result: ACC1002 High Value Status = " + isHvc);

            HttpServer server = startServer(port, repository, service);
            System.out.println("\n==========================================================");
            System.out.println("🚀 HTTP Server started successfully on port " + port);
            System.out.println("👉 Health Check URL : http://localhost:" + port + "/health");
            System.out.println("👉 All Accounts URL : http://localhost:" + port + "/api/accounts");
            System.out.println("👉 Single Account   : http://localhost:" + port + "/api/accounts/ACC1001");
            System.out.println("==========================================================");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
