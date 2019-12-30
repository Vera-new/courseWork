package ServerPart;

import com.sun.security.ntlm.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.*;


// добавить мапу
// обработать исключение при отключении клиента
// сдалать адрессованное сообщение




// серверная часть
public class ThreadServer {
    //мои изменения
    //создаём массив для хранения клиентов, которые зашли в чат (полный адрес)
    static HashMap <Socket, String> usersFull=new HashMap<>();
  //  static  ArrayList <ServerSocket> usersFull = new ArrayList <>(); эту строку заменила на мапу
    //создаём массив для хранения клиентов, которые зашли в чат (имена)
    public static  ArrayList <String> users = new ArrayList <>();

    public static void main(String[] args) {
        //определяем номер порта, который будет "слушать" сервер
        int port = 1777;
        ServerSocket serverSocket = null;
        try {
            //открыть серверный сокет (ServerSocket)
            serverSocket = new ServerSocket(port);
            //Входим в бесконечный цикл - ожидаем соединения
            while (true) {
                System.out.println("Ожидание связи с портом " + port);
                // получив соединение начинаем работать с сокетом
                Socket fromClientSocket = serverSocket.accept();
                // стартует новый поток для обработки запроса клиента
                new SocketThread(fromClientSocket).start();
                //добавляем нового пользователя в список тех, кто в чате
            }


        } catch (SocketException e) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (EOFException e) {
            return;
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

            ThreadServer.usersFull.put(fromClientSocket, name);
            Date date=new Date();
            SimpleDateFormat R= new SimpleDateFormat("(HH:mm)");

            System.out.println("Клиент " + name + " стартовал");//пишем что стартовал клиент

            while ((messager = br.readLine()) != null) {
                // печатаем сообщение
                System.out.println(R.format(date) + "Сообщение от " + name + ": " + messager);
                pw.println(R.format(date) + "Сообщение от " + name + ": " + messager);

                if (messager.equals(name)) {
                    //      for ((name=br.readLine()); ThreadServer.users){
                    pw.println(name + ", для вас есть личное сообщение");
                    System.out.println(name + ", для вас есть личное сообщение");

                    //сравниваем с "Пока" и если это так, то выходим из цикла и закрываем соединение
                }else if (messager.equals("Пока")) {
                        // тоже говорим клиенту "Пока"
                        pw.println("Пока");
                        break;
                } else if (messager.equals("Кто в сети?")) {
                        System.out.println(ThreadServer.users);///////////////////////////////////////////////////////////////
                }

          }

        } catch (EOFException e) {
            return;
      }catch (SocketException e){
            try {
                fromClientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        } /*catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}




