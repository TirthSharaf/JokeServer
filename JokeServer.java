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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


class JokeProducer extends Thread {
    Socket sock; //declare socket variable

    //constructor
    JokeProducer(Socket socket) {
        //initialize socket variable
        this.sock = socket;
    }

    //static method that takes in multiple parameters
    //UName = username
    //Uid = user id
    //joke_Order = order of jokes
    //proverb_Order = order of proverbs
    //JokeNum = current joke number
    //ProverNum = current proverb number
    //out = output stream
    static void getJokeProverb(String UName, String Uid, String joke_Order, String proverb_Order, Integer JokeNum, Integer ProverNum, ObjectOutputStream out) throws IOException {


        List<String> UserIdCollection = new ArrayList<>(); //create a list to store user id
        List<String> jokeSequenceList = new ArrayList<>(); //create a list to store joke sequence
        List<String> proverbSequenceList = new ArrayList<>(); //create a list to store proverb sequence

       //store the first, second, third and fourth characters of joke_Order into variables
        String j1 = String.valueOf(joke_Order.charAt(0));
        String j2 = String.valueOf(joke_Order.charAt(1));
        String j3 = String.valueOf(joke_Order.charAt(2));
        String j4 = String.valueOf(joke_Order.charAt(3));

       //add the variables to the jokeSequenceList
        jokeSequenceList.add(j1);
        jokeSequenceList.add(j2);
        jokeSequenceList.add(j3);
        jokeSequenceList.add(j4);

        //store the first, second, third and fourth characters of proverb_Order into variables
        String p1 = String.valueOf(proverb_Order.charAt(0));
        String p2 = String.valueOf(proverb_Order.charAt(1));
        String p3 = String.valueOf(proverb_Order.charAt(2));
        String p4 = String.valueOf(proverb_Order.charAt(3));

        //add the variables to the proverbSequenceList
        proverbSequenceList.add(p1);
        proverbSequenceList.add(p2);
        proverbSequenceList.add(p3);
        proverbSequenceList.add(p4);



        Map<String, String> jokesMap = new HashMap(); //create a map to store jokes
        jokesMap.put("A", "JA " + UName + ": Why was the math book sad? Because it had too many problems.");
        jokesMap.put("B", "JB " + UName + ": Why did the chicken cross the playground? To get to the other slide.");
        jokesMap.put("C", "JC " + UName + ": Why did the cookie go to the doctor? Because it was feeling crumbly.");
        jokesMap.put("D", "JD " + UName + ": Why did the tomato turn red? Because it saw the salad dressing!");



        Map<String, String> proverbsMap = new HashMap(); //create a map to store proverbs
        proverbsMap.put("A", "PA " + UName + ": Honesty is the best policy.");
        proverbsMap.put("B", "PB " + UName + ": A stitch in time saves nine.");
        proverbsMap.put("C", "PC " + UName + ": An apple a day keeps the doctor away.");
        proverbsMap.put("D", "PD " + UName + ": Give a man a fish and you feed him for a day; teach a man to fish and you feed him for a lifetime.");

        JokeServer.PhraseOutput phraseOutput = new JokeServer.PhraseOutput(); //create an instance of JokeServer.PhraseOutput
        if (JokeServer.JokeMode) {
            try {
                if (UserIdCollection.contains(Uid))
                    System.out.println("User Already Registered!!!"); //Check if user id is already registered
                else // If not registered, add the user id to the collection
                    UserIdCollection.add(Uid);

                if (JokeNum == 4) { // If the joke number is 4, shuffle the joke sequence list
                    System.out.println(jokeSequenceList);
                    Collections.shuffle(jokeSequenceList);

                } else { // If the joke number is not 4, get the joke or proverb phrase
                    getJokeOrProverbPhrase(UName,JokeNum, phraseOutput, jokeSequenceList, jokesMap);
                    JokeNum++;
                }
            } catch (IndexOutOfBoundsException ex) {
                out.writeUTF("Search Unsuccessful " + Uid);
            }
        } else {
            try {
                // Check if the user id is already registered
                if (UserIdCollection.contains(Uid))
                    System.out.println("User Already Registered!!!");
                else // If not registered, add the user id to the collection
                    UserIdCollection.add(Uid);

                if (ProverNum == 4) { // If the proverb number is 4, shuffle the proverb sequence list
                    System.out.println(proverbSequenceList);
                    Collections.shuffle(proverbSequenceList);

                } else { // If the proverb number is not 4, get the joke or proverb phrase
                    getJokeOrProverbPhrase(UName, ProverNum, phraseOutput, proverbSequenceList, proverbsMap);

                    ProverNum++;
                }
            } catch (IndexOutOfBoundsException ex) {
                out.writeUTF("Search Unsuccessful!!! " + Uid);
            }
        }

        phraseOutput.setJoke_Index(JokeNum); // Update the joke and proverb indices for the phrase output
        phraseOutput.setProverb_Index(ProverNum);
        out.writeObject(phraseOutput);// Write the phrase output to the output stream and flush
        out.flush();

    }

    private static void getJokeOrProverbPhrase(String userName,Integer proverbIndex, JokeServer.PhraseOutput phraseOutput, List<String> proverbOrderList, Map<String, String> proverbsMap) throws IOException {

        String displayMsg = "New client request from user " + userName; //message to display when a new client request is made

        switch (proverbOrderList.get(proverbIndex)) {  // Switch statement to determine which proverb to return based on the proverbIndex
            case "A":
                phraseOutput.setPhrase(proverbsMap.get("A")); // Set the phraseOutput to the proverb associated with key "A"
                System.out.println(displayMsg); // Print out the display message with the client's username
                break;

            case "B":
                phraseOutput.setPhrase(proverbsMap.get("B"));// Set the phraseOutput to the proverb associated with key "B"
                System.out.println(displayMsg);// Print out the display message with the client's username
                break;

            case "C":
                phraseOutput.setPhrase(proverbsMap.get("C")); // Set the phraseOutput to the proverb associated with key "C"
                System.out.println(displayMsg);// Print out the display message with the client's username
                break;

            case "D":
                phraseOutput.setPhrase(proverbsMap.get("D")); // Set the phraseOutput to the proverb associated with key "D"
                System.out.println(displayMsg);// Print out the display message with the client's username
                break;

        }
    }

    public void run() {
// Initialize variables to store client name, joke and proverb order
        String clientNameAndOrderString;
        String jokeOrderString;
        String proverbOrderString;

        // Initialize variables to store the current index for jokes and proverbs
        int jokeIndex = 0;
        int proverbIndex = 0;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream;

        try {
            objectOutputStream = new ObjectOutputStream(sock.getOutputStream()); // Create output stream to send data to client
            objectInputStream = new ObjectInputStream(sock.getInputStream()); // Create input stream to receive data from client
            try {
                String userName;
                String userId;

                // Read client input from the input stream
                JokeClient.ClientInput clientInput = (JokeClient.ClientInput) objectInputStream.readObject();
                userId = clientInput.getUserId();
                clientNameAndOrderString = clientInput.getClientNameAndOrderString();

                // Split the clientNameAndOrderString to separate the client name, joke order, and proverb order
                String[] clientNameAndOrderArray = clientNameAndOrderString.split(":");

                userName = clientNameAndOrderArray[0]; // Get the client name from the first element of the array
                jokeOrderString = clientNameAndOrderArray[1]; // Get the joke order from the second element of the array
                proverbOrderString = clientNameAndOrderArray[2]; // Get the proverb order from the third element of the array

                jokeIndex = clientInput.getJokeIndex(); // Get the current joke index from the client input

                proverbIndex = clientInput.getProverbIndex(); // Get the current proverb index from the client input

                // Call the method to get the joke or proverb
                getJokeProverb(userName, userId, jokeOrderString, proverbOrderString, jokeIndex, proverbIndex, objectOutputStream);

            } catch (IndexOutOfBoundsException x) { // Handle the exception if there is an error reading from the server
                System.out.println("Error Reading From Server!!!");
                x.printStackTrace();
            }
            sock.close(); // Close the socket
        } catch (IOException | ClassNotFoundException ioex) {
            System.out.println(ioex);
        }
    }
}


public class JokeServer {

    static boolean JokeMode = true; //static variable to store the current mode of the server (joke or proverb)

    public static void main(String[] a) throws IOException {
        int queue_length = 6; //queue length for server socket
        int port = 4545; //port number for server socket
        Socket socket; //variable to store socket connection

        ModeServer modeServer = new ModeServer(); //create an instance of ModeServer
        Thread thread = new Thread(modeServer); //create a new thread and run the modeServer
        thread.start();

        ServerSocket serverSocket = new ServerSocket(port, queue_length); //create a new server socket with the specified port and queue length

        System.out.println("Starting Tirth's Joke Server 999.0, Listening At Port 4545. \n");

        while (true) {   //infinite loop to accept incoming connections
            socket = serverSocket.accept();
            new JokeProducer(socket).start(); //create a new JokeProducer thread and start it
        }
    }

    static class PhraseOutput implements Serializable {  //static inner class PhraseOutput
        Integer jokeIdx;
        Integer proverbIdx;
        String Statement;

        public PhraseOutput() { //constructor
            super();
        }

        public PhraseOutput(Integer jokeIndex, Integer proverbIndex, String phrase) {  //parameterized constructor
            this.jokeIdx = jokeIndex;
            this.proverbIdx = proverbIndex;
            this.Statement = phrase;
        }

        public Integer getJoke_Index() { //getter method for joke index

            return jokeIdx;
        }

        public void setJoke_Index(Integer jokeIndex) {
            this.jokeIdx = jokeIndex;
        } //setter method for joke index

        public Integer getProverb_Index() {
            return proverbIdx;
        } //getter method for proverb index

        public void setProverb_Index(Integer proverbIndex) { //setter method for proverb index
            this.proverbIdx = proverbIndex;
        }

        public String getPhrase() {
            return Statement;
        } //getter method for phrase

        public void setPhrase(String phrase) {
            this.Statement = phrase;
        }  //setter method for phrase
    }

}

class ModeServer implements Runnable {
    public static boolean adminSwitch = true; //static variable to store whether the admin switch is on or off

    public void run() {
        int port = 5050; //port number for the server socket
        Socket socket; //variable to store socket connection

        try {  //create a new server socket with the specified port
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Starting Up Tirth's Mode Server 999.0, Listening At Port 5050. \n");

            //infinite loop to accept incoming connections while admin switch is on
            while (adminSwitch) {
                socket = serverSocket.accept();

                new JokeClientAdmin.AdminWorker(socket).start(); //create a new AdminWorker thread and start it
            }
        } catch (IOException ioex) {
            System.out.println(ioex);
        }
    }
}


