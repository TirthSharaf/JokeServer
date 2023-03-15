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
import java.net.Socket;
import java.util.*;

public class JokeClient {
    static String END_PHRASE = "quit"; // constant variable to end the program if the user inputs "quit"

    public static void main(String[] args) {
        String serverDomain = "localhost"; // server domain is set to "localhost" by default

        int J_Ind = 0;// variables to keep track of the current joke index
        int P_Ind = 0;// variables to keep track of the current proverb index
        ArrayList<Integer> index_List = new ArrayList<>(); // list to store the current joke and proverb index

        if (args.length > 0) // if a command line argument is provided, set server domain to that argument
            serverDomain = args[0];

        // print out the server domain and port number that the program is communicating with
        System.out.println("Now Communicating with : " + serverDomain + ", Port: 4545");
        Scanner scanner = null;

        List<String> order = new ArrayList<>();     // create lists to store the order of jokes and proverbs
        List<String> RandomJokeOrder = new ArrayList<>();

        // add the options for the joke order
        RandomJokeOrder.add("A");
        RandomJokeOrder.add("B");
        RandomJokeOrder.add("C");
        RandomJokeOrder.add("D");

        // shuffle the options for the joke order
        Collections.shuffle(RandomJokeOrder);

        // create a string of the shuffled joke order options
        StringBuilder joke_Order_String = new StringBuilder(RandomJokeOrder.size());
        for (String s : RandomJokeOrder) {
            joke_Order_String.append(s);
        }

        // add the joke order string to the order list
        order.add(joke_Order_String.toString());

        // repeat the above process for proverbs
        List<String> RandomProverbOrder = new ArrayList<>();

        //Adding options for jokes
        RandomProverbOrder.add("A");
        RandomProverbOrder.add("B");
        RandomProverbOrder.add("C");
        RandomProverbOrder.add("D");

        Collections.shuffle(RandomProverbOrder); // shuffle the options for the proverb order

        // create a string of the shuffled proverb order options
        StringBuilder proverb_Order_String = new StringBuilder(RandomProverbOrder.size());
        for (String s : RandomProverbOrder) {
            proverb_Order_String.append(s);
        }

        order.add(proverb_Order_String.toString()); // add the proverb order string to the order list


        try {
            scanner = new Scanner(System.in); // create a scanner object to read user input

            String User_Name;
            do {  // prompt the user to enter their name and keep prompting if the input is empty
                System.out.print("Please Enter your name or type (quit) to end: ");
                User_Name = scanner.nextLine();
            } while (User_Name.isEmpty());

            if (!END_PHRASE.equals(User_Name.trim())) { // if the user enters "quit", exit the program
                System.out.print("\nPress enter to hear a joke or proverb: \n ");

                String user_Id = UUID.randomUUID().toString();   // generate a random user ID

                while (true) { // clear the indexArray list
                    index_List.clear();

                    String input = scanner.nextLine(); // get input from user
                    if (END_PHRASE.equals(input.trim())) // if the user enters "quit", exit the loop
                        break;

                    // get the joke or proverb from the server using the user's name, user ID, and current joke/proverb order and index
                    index_List = getJokesOrProverb(User_Name, user_Id, order.get(0), order.get(1), J_Ind, P_Ind, serverDomain);

                    J_Ind = index_List.get(0);  // update the current joke and proverb index
                    P_Ind = index_List.get(1);

                    if (J_Ind == 4) {    // if all jokes have been received, shuffle the joke options and reset the joke index
                        J_Ind = 0;

                        RandomJokeOrder.clear();

                        for (int i = 0; i < joke_Order_String.length(); i++) {  // Convert the jokeOrderString to a List
                            RandomJokeOrder.add(String.valueOf(joke_Order_String.charAt(i)));
                        }

                        // shuffle the joke options
                        Collections.shuffle(RandomJokeOrder);

                        // create a new string from the shuffled joke options
                        StringBuilder newJokeOrderString = new StringBuilder(RandomJokeOrder.size());
                        for (String s : RandomJokeOrder) {
                            newJokeOrderString.append(s);
                        }

                        //update the order list with the new joke order
                        order.set(0, newJokeOrderString.toString());


                    }

                    if (P_Ind == 4) {   // if all proverbs have been received, shuffle the proverb options and reset the proverb index
                        P_Ind= 0;

                        RandomProverbOrder.clear();

                        for (int i = 0; i < proverb_Order_String.length(); i++) {    // convert the proverbOrderString to a List
                            RandomProverbOrder.add(String.valueOf(proverb_Order_String.charAt(i)));
                        }

                        // shuffle the proverb options
                        Collections.shuffle(RandomProverbOrder);

                        // create a new string from the shuffled proverb options
                        StringBuilder newProverbOrderString = new StringBuilder(RandomProverbOrder.size());
                        for (String s : RandomProverbOrder) {
                            newProverbOrderString.append(s);
                        }

                        // update the order list with the new proverb order
                        order.set(1, newProverbOrderString.toString());
                    }


                }
            }

        } catch (Exception e) {
            System.out.println("Error encountered " + e.getMessage());  // print the exception message if an exception occurs
        } finally {
            scanner.close();  // close the scanner object
            System.out.println("Request Cancelled By User.");  // print message indicating that the program has ended
        }

    }

    static ArrayList<Integer> getJokesOrProverb(String usr_Name, String usr_ID, String j_Ord_Str, String p_Ord_Str, Integer j_Index, Integer p_Index, String serverDomain) throws ClassNotFoundException {             //custom method that returns the appropriate joke or proverb from JokeServer
        //custom method that returns the appropriate joke or proverb from JokeServer


        //initialize variables
        Socket socket = null;
        ObjectInputStream fromServer;
        String textFromServer = null;
        ObjectOutputStream objectOutputStream = null;
        ArrayList<Integer> index = new ArrayList<>();
        index.clear();


        try {  //create a socket to connect to the server
            socket = new Socket(serverDomain, 4545);

            //create input and output streams to send and receive data
            fromServer = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            //create a clientInput object to send to the server
            ClientInput clientInput = new ClientInput(usr_ID, usr_Name + ":" + j_Ord_Str + ":" + p_Ord_Str, j_Index, p_Index);

            objectOutputStream.writeObject(clientInput); //send the clientInput object to the server
            objectOutputStream.flush();  //receive a PhraseOutput object from the server

            JokeServer.PhraseOutput phraseOutput = (JokeServer.PhraseOutput) fromServer.readObject();
            textFromServer = phraseOutput.getPhrase();
            j_Index = phraseOutput.getJoke_Index();
            p_Index = phraseOutput.getProverb_Index();

            for (int i = 0; i < 3; i++) {

                //print the joke or proverb received from the server
                if (textFromServer != null && i == 0) System.out.println(textFromServer);

                    //update the joke index
                else if (textFromServer != null && i == 1) {
                    if (j_Index == 4) {  // Print message when all jokes have been received
                        System.out.println("Joke Cycle Completed");
                        index.add(j_Index);
                    }
                    else {  // Add current joke index to the index list
                        index.add(j_Index);
                    }

                    //update the proverb index
                } else if (textFromServer != null && i == 2) {
                    if (p_Index == 4) {  // Print message when all proverbs have been received
                        System.out.println("Proverb Cycle Completed");
                        index.add(p_Index);
                    } else { // Add current proverb index to the index list
                        index.add(p_Index);
                    }
                }
            }

            socket.close();      //close the socket
        } catch (IOException ex) {
            System.out.println("Socket error.");
            ex.printStackTrace();
        }

        return index;   //return the updated joke and proverb index

    }

    static class ClientInput implements Serializable {
        String userId; // variable to store the user's unique ID
        String clientNameAndOrderString; // variable to store the user's name and order string
        Integer jokeIndex; // variable to store the current joke index
        Integer proverbIndex;  // variable to store the current proverb index

        public ClientInput() {
            super();
        } //default constructor

        //parameterized constructor

        public ClientInput(String userId, String clientNameAndOrderString, Integer jokeIndex, Integer proverbIndex) {
            this.userId = userId;
            this.clientNameAndOrderString = clientNameAndOrderString;
            this.jokeIndex = jokeIndex;
            this.proverbIndex = proverbIndex;
        }

        public String getUserId() {
            return userId;
        } //getter for userId

        public void setUserId(String userId) {
            this.userId = userId;
        } //setter for userId

        public String getClientNameAndOrderString() {  //getter for clientNameAndOrderString
            return clientNameAndOrderString;
        }

        public void setClientNameAndOrderString(String clientNameAndOrderString) {  //setter for clientNameAndOrderString
            this.clientNameAndOrderString = clientNameAndOrderString;
        }

        public Integer getJokeIndex() {
            return jokeIndex;
        }  //getter for jokeIndex

        public void setJokeIndex(Integer jokeIndex) {
            this.jokeIndex = jokeIndex;
        }  //setter for jokeIndex

        public Integer getProverbIndex() {
            return proverbIndex;
        }  //getter for proverbIndex

        public void setProverbIndex(Integer proverbIndex) {  //setter for proverbIndex

            this.proverbIndex = proverbIndex;
        }


    }

}