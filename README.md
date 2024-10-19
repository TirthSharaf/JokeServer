# JokeServer - Multi-Threaded Client-Server Application in Java

This project is a multi-threaded client-server system in Java, which supports communication between multiple clients and the server, alongside an administration channel for server management. The project demonstrates fundamental concepts in network programming, threading, and state management for multiple simultaneous conversations.

## Project Overview:

• JokeServer.java: This is the main server file, handling client connections and processing requests. It manages multiple worker threads to allow simultaneous interactions with clients.

• JokeClient.java: This file allows clients to connect to the JokeServer, send requests, and receive humorous responses or messages.

• JokeClientAdmin.java: This is an administrative client that connects to the JokeServer, providing functionalities to change the server’s mode or manage client interactions.



# Key Features:

• Multi-threading: The server is designed to handle multiple client requests simultaneously, demonstrating a multi-processing environment.

• State Management: The server maintains the state of each client conversation despite the stateless nature of the communication protocol.

• Administration Channel: A secondary administration client allows the server’s mode to be changed dynamically, adding more control to the server.



# Technical Details:

• The server can handle multiple worker threads, each representing a client connection, allowing for scalability.

• The project demonstrates the principles of TCP/IP networking and socket programming in Java.

• The server provides a stateless communication protocol while internally maintaining client state across distributed conversations.

• Basic logging of client-server interactions can be captured manually via console output to a log file.


# How to Run the Project:

• Compile all files using javac *.java (executed twice).

• Start the server with java JokeServer.

• In separate terminals, run clients using java JokeClient and the administration client using java JokeClientAdmin.

• The clients can send requests to the server, and the administration client can change server modes.


# Custom Modifications:
• Added comments throughout the code to explain how client-server communication is managed.

•Implemented a simplified logging mechanism to capture client-server interactions.


This project provided valuable experience in building a multi-threaded client-server system while managing multiple concurrent client connections. It also helped me understand the complexities of maintaining client state in a stateless protocol environment.

