package server;

import spark.*;

public class Server {

    public static void main(String[] args) {
        var port = 8080;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        Server server = new Server();
        server.run(port);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/test",(req, res) -> "Hello BYU!");

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
