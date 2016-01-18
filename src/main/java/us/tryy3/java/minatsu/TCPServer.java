package us.tryy3.java.minatsu;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import us.tryy3.java.minatsu.logger.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by tryy3 on 2016-01-09.
 */
public class TCPServer extends Thread {
    private int port;
    private ServerSocket server;
    private Bot bot;
    private Map<UUID, Connection> connections = new HashMap<>();
    private final Logger logger = new Logger(null);

    public TCPServer(Options options, final Bot bot) {
        this.bot = bot;
        port = options.getPort();

    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);

            logger.info("Waiting on listener connections.");

            while (true) {
                Socket socket = server.accept();

                logger.info("Listener connection made from %s:%s", getIpAsString(socket.getInetAddress()), socket.getPort());

                UUID uuid = UUID.randomUUID();

                Connection connection = new Connection(socket, uuid, this);
                connections.put(uuid, connection);
                connection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getIpAsString(InetAddress address) {
        byte[] ipAddress = address.getAddress();
        StringBuffer str = new StringBuffer();
        for(int i=0; i<ipAddress.length; i++) {
            if(i > 0) str.append('.');
            str.append(ipAddress[i] & 0xFF);
        }
        return str.toString();
    }

    public Connection getConnection(UUID uuid) {
        return this.connections.get(uuid);
    }

    public Map<UUID, Connection> getConnections() {
        return connections;
    }

    public void sendAll(String message) {
        for (Connection connection : connections.values()) {
            connection.sendMessage(message);
        }
    }

    public void stopConnection() {
        for (Connection connection : connections.values()) {
            connection.close();
        }
        try {
            this.server.close();
            this.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(Connection connection) {
        this.connections.remove(connection.getUuid());
    }

    public class Connection extends Thread {
        private Socket socket;
        private UUID uuid;
        private BufferedReader in;
        private PrintWriter out;
        private TCPServer parent;

        public Connection(Socket socket, UUID uuid, TCPServer parent) {
            this.socket = socket;
            this.uuid = uuid;
            this.parent = parent;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String msg = in.readLine();

                    JsonArray json = new JsonParser().parse(msg).getAsJsonArray();

                    if (json == null || json.size() <= 0) {
                        logger.severe("Got a TCP request, but the json was invalid.");
                        continue;
                    }

                    bot.read(this, json);
                } catch (SocketException e) {
                    try {
                        in.close();
                        out.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    this.parent.remove(this);
                    this.interrupt();
                    break;
                } catch (IOException e) {
                    System.out.println("Error");
                    e.printStackTrace();
                }
            }
        }

        public UUID getUuid() {
            return uuid;
        }

        public void sendRaw(String message) {
            out.println(message);
        }

        public void sendMessage(String message, Object... var) {
            sendMessage(String.format(message, var));
        }

        public void sendMessage(JsonArray messages) {
            JsonArray obj = new JsonArray();

            obj.add("sendMessage");
            obj.add(messages);

            sendRaw(obj.toString());
        }
        public void sendMessage(String id, String message) {
            JsonArray writeArgs = new JsonArray();
            JsonArray msgArgs = new JsonArray();

            msgArgs.add(id);
            msgArgs.add(message);

            writeArgs.add("sendMessage");
            writeArgs.add(msgArgs);

            sendRaw(writeArgs.toString());
        }

        public void close() {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}