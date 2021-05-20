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

import java.io.*;  // Get the Input Output libraries
import java.net.*; // Get the Java networking libraries

class Worker extends Thread {    // Class definition. Each connection gets its own Worker
    Socket sock;                   // Class member, socket, local to Worker.
    Worker (Socket s) {sock = s;}  // Constructor, assign arg s to local sock

    public void run(){
        // Get I/O streams in/out from the socket:
        PrintStream out = null;
        BufferedReader in = null;
        try {
            in = new BufferedReader
                    (new InputStreamReader(sock.getInputStream()));
            out = new PrintStream(sock.getOutputStream());
            // Note that this branch might not execute when expected:
            try {
                String ClientMsg;
                ClientMsg = in.readLine ();
                System.out.println("Got: " + ClientMsg);

                out.println("Hi. I am your Agent."); // To client...
                out.println("You said: " + ClientMsg + "\n\n");
            } catch (IOException x) {
                System.out.println("Server read error");
                x.printStackTrace ();
            }
            sock.close(); // close this connection, but not the server;
        } catch (IOException ioe) {System.out.println(ioe);}
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

