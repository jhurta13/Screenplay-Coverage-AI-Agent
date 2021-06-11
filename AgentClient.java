/** File is: AgentClient.java, Version 1.9

 A client for AgentServer. Elliott, after Hughes, Shoffner, Winslow

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
import java.net.Socket;
import java.util.Scanner;

public class AgentClient{
    public static void main (String args[]) throws IOException {
        String serverName;
        if (args.length < 1) serverName = "localhost";
        else serverName = args[0];

        System.out.println("Screenplay Coverage Agent Client, 1.9.\n");
        System.out.println("Using server: " + serverName + ", Port: 45565");
        String name;
        do {

            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Is there a CATALIST? (Y/N) \n");

            String answer1 = myObj.nextLine();  // Read user input
            System.out.println("CATALIST: " + answer1);  // Output user input

            System.out.println("Is the BALANCE upset? (Y/N) \n");

            String answer2 = myObj.nextLine();  // Read user input
            System.out.println("BALANCE upset: " + answer2);  // Output user input

            System.out.println("Is the Protagonist's DESIRE established? (Y/N) \n");

            String answer3 = myObj.nextLine();  // Read user input
            System.out.println("DESIRE established: " + answer3);  // Output user input

            System.out.println("Is the Protagonist's PROBLEM established? (Y/N) \n");

            String answer4 = myObj.nextLine();  // Read user input
            System.out.println("Protagonist's PROBLEM established: " + answer4);  // Output user input

            System.out.println("Is the Protagonist's NEED established? (Y/N)\n");

            String answer5 = myObj.nextLine();  // Read user input
            System.out.println("Protagonist's NEED established: " + answer5);

            System.out.println("Is the Protagonist's GOAL established? (Y/N)\n");

            String answer6 = myObj.nextLine();  // Read user input
            System.out.println("GOAL established: " + answer6);

            System.out.println("Is the Protagonist's MISSION established? (Y/N)\n");

            String answer7 = myObj.nextLine();  // Read user input
            System.out.println("Protagonist's MISSION established: " + answer7);

            String answercompiled = answer1+answer2+answer3+answer4+answer5+answer6+answer7;


            //send to server for evaluation
            Reader inputString = new StringReader(answercompiled);
            BufferedReader in = new BufferedReader(inputString);
            name = in.readLine();



            if (name.indexOf("quit") < 0)
                getAgentResponse(name, serverName);
        } while (name.indexOf("quit") < 0); // quitSmoking.com?
        System.out.println ("Cancelled by user request.");


    }


    static void getAgentResponse (String name, String serverName){
        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        try{
            /* Open our connection to server port, choose your own port number.. */
            sock = new Socket(serverName, 45565);

            // Create filter I/O streams for the socket:
            fromServer =
                    new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            // Send the Agent Server your string:
            toServer.println(name);
            toServer.flush();

            // Read two or three lines of response from the server,
            // and block while synchronously waiting:
            for (int i = 1; i <=3; i++){
                textFromServer = fromServer.readLine();
                if (textFromServer != null) System.out.println(textFromServer);
            }
            sock.close();
        } catch (IOException x) {
            System.out.println ("Socket error.");
            x.printStackTrace ();
        }
    }
}
