package ClientPart;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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

