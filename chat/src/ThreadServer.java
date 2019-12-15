import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


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

class SocketThread extends Thread {
    private Socket fromClientSocket;
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

// мои изменения 2
            //  String name_i;

//закончились мои изменения 2
            int counter = 0; //Количество подключенных клиентов
            int i = counter++;//клиент подключился
            // окончание моих изменений 1
            System.out.print("Ведите имя: ");
            Scanner scan = new Scanner(System.in);
            String name_i = scan.nextLine();
            System.out.println("Клиент №"+counter+" "+ name_i +" подключился");
            System.out.println("Сервер: Привет, "+name_i);

            while ((messager = br.readLine()) != null) {
                // печатаем сообщение
                System.out.println("Сообщение от "+name_i +": "+ messager);
                //сравниваем с "Пока" и если это так, то выходим из цикла и закрываем соединение
                if (messager.equals("Пока")) {
                    // тоже говорим клиенту "Пока"
                    pw.println("Пока");
                    break;
                } else {
                    // посылаем клиенту ответ
                    // messager = "сервер отвечает: Вы написали " +messager; //не будем посылать клиенту ответ
                    pw.println(messager);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


// клиентская часть
class Client {
    public static void main(String[] args) throws Exception {
        int portNumber = 1777; //определяем номер порта на котором нас ожидает сервер для ответа
//мои изиенения 3
        String name_i;
        String messager;
// закончились мои изменения 3
        //      String messager = "тестовая строка"; // подготавливаем строку для запроса
        System.out.println("Клиент стартовал");//пишем что стартовал клиент
        Socket socket = new Socket("127.0.0.1", portNumber);//открыть сокет для обращения к локальному компьютеру
        //создаём поток для чтения символов из сокета.
        // для этого открывается поток сокета - socket.getInputStream()
        // потом преобразовываем его в поток символов - new InputStreamReader
        // делаем его читателем строк - BufferedReader
        BufferedReader br= new BufferedReader (new InputStreamReader(socket.getInputStream()));
        //создаём поток для записей символов в сокет
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        //определяем тестовую строку в сокет
// далее мои изменения, до них всё работало
        //     Scanner scan = new Scanner(System.in);
        //здесь пошли изменения 2
        //    name_i=scan.nextLine();
        //    pw.println(name_i);
//////////////////////////////////////////////////////////////////////////////////////////////// далее всё ушло в do while
      /* Scanner scan2=new Scanner(System.in);
  //закончились изменения 2
        messager = scan2.nextLine(); // подготавливаем строку для запроса
// закончились изменения

       pw.println(messager);*/
        //выводим в цикл чтения, что нам ответил сервер
/////////////////////////////////////////////////////////////////////////////// мой блок while

        do {
            Scanner scan2 = new Scanner(System.in);
            //закончились изменения 2
            messager = scan2.nextLine(); // подготавливаем строку для запроса
// закончились изменения

            pw.println(messager);
            if (messager.equals("Пока")) {
                break;
            }
// печатаем ответ от сервера на консоль для проверки
            //  System.out.println(messager);  //не печатаем сообщение
            //   pw.println("bye");
        }while ((messager = br.readLine()) !=null);

        /*while ((messager = br.readLine()) !=null) {
            //если пришёл ответ "bye", то заканчиваем цикл
            ///////////////////////////////////////////////////////////////////////


            if (messager.equals("bye")){
                break;
            }
            // печатаем ответ от сервера на консоль для проверки
            System.out.println(messager);
         //   pw.println("bye");
        }*/
        br.close();
        pw.close();
        socket.close();
    }
}
