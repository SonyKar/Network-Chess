import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket accepted;
        Game game = new Game();

        while (true){
            try {
                accepted = serverSocket.accept();

                if (accepted != null && !game.checkPlayers()){
                    game.addPlayer(accepted);
                    System.out.println("A player was connected!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
