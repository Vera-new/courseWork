package ServerPart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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

