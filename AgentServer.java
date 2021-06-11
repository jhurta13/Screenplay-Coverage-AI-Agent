/** File is: InetServer.java, Version 1.9

 A multithreaded server for AgentClient. Elliott, after Hughes, Shoffner, Winslow

 You will need at least TWO separate command windows to run this N > 1 process program.

 ShellA> java AgentServer
 ShellB> java AgentClient

 Optionally, to run across the Internet:

 ShellB> java AgentClient YourDomainNameOfOtherMachine

 You can run 5,000 AgentClients if you wish. Each will have a separate conversation with its Worker.

 Windows Bat File:

 > start java AgentServer
 > start java AgentClient
 ----------------------------------------------------------------------*/

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

class Worker extends Thread {    // Class definition. Each connection gets its own Worker
    Socket sock;                   // Class member, socket, local to Worker.
    private static final String ENCODING = "ISO-8859-1";


    Worker(Socket s) {
        sock = s;
    }  // Constructor, assign arg s to local sock

    public void run() {
        // Get I/O streams in/out from the socket:
        PrintStream out = null;
        BufferedReader in = null;

        Scanner s = null;
        try {
            s = new Scanner(new File("C:/users/jhsandoval/Desktop/rules.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<String>();
        while (s.hasNext()){
            list.add(s.nextLine());
        }
        s.close();

        try {
            in = new BufferedReader
                    (new InputStreamReader(sock.getInputStream()));
            out = new PrintStream(sock.getOutputStream());
            // Note that this branch might not execute when expected:
            try {
                String ClientMsg;
                ClientMsg = in.readLine();
                out.println("Server Received Responses... Evaluating Screenplay...");

                if(ClientMsg.charAt(0)=='Y'){
                    ClientMsg = "Great, your screenplay has a Catalyst \n and test";

                    if(ClientMsg.charAt(1)=='Y'){
                        ClientMsg.concat("GDGDSGDSGD");
                    }
                }
                else{
                    ClientMsg = "Consider adding a CATALYST to you Screenplay \n";
                }

                if (ClientMsg.charAt(1)=='Y'){
                    ClientMsg += "Great, your screenplay has an UPSET BALANCE \n";
                }
                else if(ClientMsg.charAt(1)=='N')
                {
                    ClientMsg += "Consider UPSETTING THE BALANCE in your Screenplay \n";
                }

                else if(ClientMsg.charAt(2)=='Y'){
                    ClientMsg += "Great, your Protagonist has a DESIRE  \n";
                }
                else if(ClientMsg.charAt(2) =='N')
                    {
                    ClientMsg += "Consider giving your PROTAGONIST a DESIRE \n";
                }

                else if(ClientMsg.charAt(3)=='Y'){
                    ClientMsg += "Great, your Protagonist has a PROBLEM  \n";
                }
                else if(ClientMsg.charAt(3)=='N'){
                    ClientMsg += "Consider giving your PROTAGONIST a PROBLEM \n";
                }

                else if(ClientMsg.charAt(4)=='Y'){
                    ClientMsg += "Great, your Protagonist has a NEED  \n";
                }
                else if(ClientMsg.charAt(4)=='N') {
                    ClientMsg += "Consider giving your PROTAGONIST a NEED \n";
                }

                else if(ClientMsg.charAt(5)=='Y'){
                    ClientMsg += "Great, your Protagonist has a GOAL  \n";
                }
                else if (ClientMsg.charAt(5)=='N'){
                    ClientMsg += "Consider giving your PROTAGONIST a GOAL \n";
                }

                else if(ClientMsg.charAt(6)=='Y'){
                    ClientMsg += "Great, your Protagonist has a MISSION  \n";
                }
                else if(ClientMsg.charAt(6)=='N'){
                    ClientMsg += "Consider giving your PROTAGONIST a MISSION \n";
                }


                out.print("Your Screenplay Coverage: \n" + ClientMsg);  // Output user input
                String message = in.readLine();

            } catch (IOException x) {
                System.out.println("Server read error");
                x.printStackTrace();
            }
            sock.close(); // close this connection, but not the server;
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

}
public class AgentServer {

    public static void main(String a[]) throws IOException {

        int q_len = 6; /* Not interesting. # of simultaneous requests for OpSys to queue */
        int port = 45565;
        Socket sock; // This will hold the new connection.

        ServerSocket servsock = new ServerSocket(port, q_len); // Create a doorbell socket

        System.out.println
                ("Film Coverage Agent server 1.9 starting up, listening at port 45565.\n");
        while (true) {
            sock = servsock.accept(); // Wait for the next client connection. Ding-Dong! Hello?
            new Worker(sock).start(); // Spawn worker to handle it, and start the thread.
        }
    }
}

