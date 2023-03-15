/*
1. Name: Tirth Sharaf

2. Date: 2022-01-22

3. Java version: 19.0.1.1

4. Precise command-line compilation examples / instructions:
> javac JokeServer.java
>Javac  JokeClient.java
>Javac  JokeClientAdmin.java

5. Precise examples / instructions to run this program:
In separate shell windows: To run on localhost
CMD window 1> java ColorServer
CMD window 2> java ColorClientAdmin (not compulsory)
CMD window 3> java ColorClient
[...]
CMD window N> java ColorClient

6. Full list of files needed for running the program:
 a. JokeServer.java
 b. JokeClient.java
 c. JokeClientAdmin.java
 Note that we don't specifically need JokeClintAdmin until we need to switch between joke and proverb mode.


7. Notes:
This project is a multithreaded Java program that implements a joke and proverb server and
client system. The JokeServer class creates a server socket and a thread for each client that
connects to it, and uses a map to store jokes and proverbs. The JokeClient class connects to
the JokeServer and allows the user to request jokes and proverbs. The JokeClientAdmin class
allows the administrator to toggle the mode of the server between joke mode and proverb
mode. The project uses the concepts of socket programming and multithreading to create a functional server-client system.


 8. Thanks
 Thanks: https://parade.com/ for providing with Jokes and Proverbs
https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html (Code dated 2019-07-09, by Ramesh)
https://rollbar.com/blog/java-socketexception/#
Also: many YouTube Videos I can't sum it up

And to Dr. Clark Elliot for sharing materials such as Mode Changer Method, Joke psuedo code and Joke State for this practical

*/
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class JokeClientAdmin {

    static String END_PHRASE = "quit";  // A constant string that represents the end phrase, which when entered by the user, will end the program

    public static void main(String[] args) throws IOException {
        String serverDomain = "localhost";// The default server domain is "localhost"

        // If the user passed a command line argument, use that as the server domain
        if (args.length > 0)
            serverDomain = args[0];

        System.out.println("Now Connected with : " + serverDomain + ", Port: 5050");
        Scanner in = new Scanner(System.in); // Create a scanner object to read input from the user

        try { // Create variables to hold the current mode and the user's input
            String mode;
            String anotherMode;
            System.out.println("Press Enter to Toggle Between Modes"); // Prompt the user to press enter to toggle modes

            // Loop until the user enters the end phrase
            do {
                anotherMode = in.nextLine(); // Get the user's input

                // Only toggle the mode if the user did not enter the end phrase
                if (!END_PHRASE.equals(anotherMode.trim())) { // Get the current mode from the server
                    mode = toggleMode(serverDomain);

                    // Print a message to the user depending on the current mode
                    if (mode.equals("JOKE")) {
                        System.out.println("Switched to Proverb Mode");
                    } else if (mode.equals("PROVERB")) {
                        System.out.println("Switched to Joke Mode");
                    }
                }
            } while (!END_PHRASE.equals(anotherMode.trim()));
            System.out.println("Cancelled by user request."); // Print a message if the user has ended the program

        } catch (Exception x) { // Print the error stack trace if an exception occurs
            x.printStackTrace();
        }
        // Close the scanner object
        finally {
            in.close();
        }
    }

    static String toggleMode(String serverName) {
        Socket sock;  // Create a socket to connect to the server

        // Create output and input streams for the socket
        DataOutputStream toAdminWorker;
        DataInputStream fromAdminWorker;

        // Initialize a variable to hold the new mode
        String newMode = "";

        try {  // Connect to the server using the provided server name and port number
            sock = new Socket(serverName, 5050);

            toAdminWorker = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream())); // Create a buffered output stream for sending data to the server
            fromAdminWorker = new DataInputStream(new BufferedInputStream(sock.getInputStream())); // Create a buffered input stream for receiving data from the server

            toAdminWorker.writeUTF(newMode); // Send the current mode to the server
            toAdminWorker.flush(); // Flush the output stream
            newMode = fromAdminWorker.readUTF(); // Read the new mode from the server

            sock.close(); // Close the socket
        } catch (IOException ex) { // Handle any IOExceptions that occur
            System.out.println("Socket error.");
            ex.printStackTrace();
        }

        return newMode;  // Return the new mode
    }

    public static class AdminWorker extends Thread {
        // Store a reference to the socket that was passed to the constructor
        Socket sock;

        public AdminWorker(Socket s) {
            sock = s;
        }

        public void run() {

            // Create output and input streams for the socket
            DataOutputStream out = null;
            DataInputStream in = null;
            try {
                in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
                out = new DataOutputStream(sock.getOutputStream());

                try { // Check the current mode of the server
                    if (JokeServer.JokeMode == true) { // If the current mode is Joke mode
                        System.out.println("Switched to Proverb Mode"); // Set the mode to Proverb mode
                        JokeServer.JokeMode = false;
                        out.writeUTF("JOKE");// Send a message to the server to switch to Proverb mode

                    } else {  // If the current mode is Proverb mode
                        System.out.println("Switched to Joke Mode"); // Set the mode to Joke mode
                        JokeServer.JokeMode = true; // Send a message to the server to switch to Joke mode
                        out.writeUTF("PROVERB");
                    }
                    // Flush the output stream to ensure that the message is sent to the server
                    out.flush();

                } catch (IndexOutOfBoundsException x) { // Handle any index out of bounds exceptions that occur
                    System.out.println("Server Read Failure");
                    x.printStackTrace();
                }
                sock.close(); // Close the socket
            } catch (IOException ioex) { // Handle any IOExceptions that occur
                System.out.println(ioex);
            }
        }
    }
}

