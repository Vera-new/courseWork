package ServerPart;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// серверная часть
public class ThreadServer {
    public static void main(String[] args) {
        int port = 1777; //определяем номер порта, который будет "слушать" сервер
        try {
            //открыть серверный сокет (ServerSocket)
            ServerSocket serverSocket = new ServerSocket(port);
            //Входим в бесконечный цикл - ожидаем соединения
            while (true) {
                System.out.println("Ожидание связи с портом " + port);
                // получив соединение начинаем работать с сокетом
                Socket fromClientSocket = serverSocket.accept();
                // стартует новый поток для обработки запроса клиента
                new SocketThread(fromClientSocket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
