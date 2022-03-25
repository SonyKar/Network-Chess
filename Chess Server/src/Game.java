import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game implements Runnable {
    private Socket player1 = null;
    private Socket player2 = null;
    private PieceColor availableColor;
    private enum PieceColor {
        WHITE,
        BLACK
    }

    public void addPlayer(Socket player) {
        if (player1 == null) {
            this.player1 = player;
            try {
                Scanner sin1 = new Scanner(player1.getInputStream());
                if (sin1.hasNext()) {
                    String color = sin1.nextLine();
                    if (color.equals("WHITE")) availableColor = PieceColor.BLACK;
                    else availableColor = PieceColor.WHITE;
                    System.out.println(color);
                }
                new Thread(() -> {
                    while(true) {
                        if (sin1.hasNext()) {
                            String message = sin1.nextLine();
                            System.out.println("Player 1: " + message);
                            if (player2 != null) {
                                try {
                                    messageProcessing(new OutputStreamWriter(player2.getOutputStream()), message);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (message.equals("surrender") || message.equals("disconnected")) {
                                player1 = null;
                                player2 = null;
                                break;
                            }
                        }
                    }
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (player2 == null) {
            this.player2 = player;
            try {
                OutputStreamWriter osr2 = new OutputStreamWriter(player2.getOutputStream());
                osr2.write(availableColor + "\n");
                osr2.flush();

                OutputStreamWriter osr1 = new OutputStreamWriter(player1.getOutputStream());
                osr1.write("connected\n");
                osr1.flush();

                new Thread(this).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkPlayers() {
        return player1 != null && player2 != null;
    }

    @Override
    public void run() {
        OutputStreamWriter osr1 = null;
        try {
            osr1 = new OutputStreamWriter(player1.getOutputStream());
//            osr1.write("Player 1\n");
//            osr1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Scanner sin2 = new Scanner(player2.getInputStream());

            OutputStreamWriter finalOsr1 = osr1;
            new Thread(() -> {
                while (true) {
                    if (sin2.hasNext()) {
                        String message = sin2.nextLine();
                        System.out.println("Player 2: " + message);
                        messageProcessing(finalOsr1, message);
                        if (message.equals("disconnected") || message.equals("surrender")) {
                            break;
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void messageProcessing(OutputStreamWriter finalOsr, String message) {
        try {
            finalOsr.write(message + "\n");
            finalOsr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (message.equals("disconnected") || message.equals("surrender")) {
            player1 = null;
            player2 = null;
        }
    }
}
