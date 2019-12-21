package ServerPart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;



// серверная часть
public class ThreadServer {
    //мои изменения
    //создаём массив для хранения клиентов, которые зашли в чат (полный адрес)
    static  ArrayList <ServerSocket> usersFull = new ArrayList <>();
    //создаём массив для хранения клиентов, которые зашли в чат (имена)
    static  ArrayList <String> users = new ArrayList <>();
    //закончились мои изменения
    public static void main(String[] args) {
        //определяем номер порта, который будет "слушать" сервер
        int port = 1777;
        try {
            //открыть серверный сокет (ServerSocket)
            ServerSocket serverSocket = new ServerSocket(port);
            //Входим в бесконечный цикл - ожидаем соединения
            while (true) {
                System.out.println("Ожидание связи с портом " + port);
                // получив соединение начинаем работать с сокетом
                Socket fromClientSocket=serverSocket.accept();
                // стартует новый поток для обработки запроса клиента
                new SocketThread(fromClientSocket).start();
                //добавляем нового пользователя в список тех, кто в чате
                usersFull.add(serverSocket);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

}

class SocketThread extends Thread {

    public Socket fromClientSocket;
    public SocketThread(Socket fromClientSocket) {

        this.fromClientSocket=fromClientSocket;
    }


    @Override
    public void run(){
        //автоматически будут закрыты все ресурсы
        try (
                Socket localSocket = fromClientSocket;
                PrintWriter pw = new PrintWriter(localSocket.getOutputStream(), true);
                BufferedReader br=new BufferedReader(new InputStreamReader(localSocket.getInputStream())))
        {
            //читаем сообщения от клиента до тех пор пока он не скажет "Пока"

            String messager;
            //ура! тут научился видеть имя!!!!
            String name=br.readLine();
            ThreadServer.users.add(name);
            Date date=new Date();
            SimpleDateFormat R= new SimpleDateFormat("(HH:mm)");

            while ((messager = br.readLine()) != null) {
                // печатаем сообщение
                for (ServerSocket serverSocket: ThreadServer.usersFull){///////////////////////////////////////////////////////////////
                    pw.println(R.format(date) + "Сообщение от " + name + ": " + messager);
                    System.out.println(R.format(date) + "Сообщение от " + name + ": " + messager);
                    pw.flush();
                }

                //сравниваем с "Пока" и если это так, то выходим из цикла и закрываем соединение
                if (messager.equals("Пока")) {
                    // тоже говорим клиенту "Пока"
                    pw.println("Пока");
                    break;}
                else  if (messager.equals("Кто в сети?")) {
                    System.out.println(ThreadServer.users);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

