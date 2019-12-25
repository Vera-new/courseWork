package ClientPart;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;


// клиентская часть
class Client {
    public static void main(String[] args) throws Exception {
        //определяем номер порта на котором нас ожидает сервер для ответа
        int portNumber = 1777;
        //открыть сокет для обращения к локальному компьютеру
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", portNumber);

            //создаём поток для чтения символов из сокета.
            // для этого открывается поток сокета - socket.getInputStream()
            // потом преобразовываем его в поток символов - new InputStreamReader
            // делаем его читателем строк - BufferedReader

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //создаём поток для записей символов в сокет
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Введите имя: ");
            Scanner nam = new Scanner(System.in);
            String name = nam.nextLine();
            pw.println(name);


            System.out.println("Клиент " + name + " стартовал");//пишем что стартовал клиент
            System.out.print(">>");

            Scanner mes = new Scanner(System.in);
            String messager = mes.nextLine();

            pw.println(messager);


            while (!messager.equals("Пока")) {
                System.out.print(">>");

                messager = mes.nextLine();
                pw.println(messager);
            }


//        br.close();
//        pw.close();
//        socket.close();
        } catch (SocketException e) {
            //          e.printStackTrace();
            socket.close();
        }
 /*      catch (InterruptedException e) {
           e.printStackTrace();*/
        //     }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //     finally {
        //        try {
        //             if (Socket != null){
        //               Socket.close();}
        //        }catch (IOException e) {
        //            e.printStackTrace();

        //        }
    }
       }

