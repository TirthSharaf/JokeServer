# JokeServer
DePaul University CSC435

Overview:
Joke-checklist <-- Download and complete the JokeServer checklist. Submit to D2L along with your Java source code.
In this assignment you will build a pair of multi-threaded servers that accept input from multiple clients, and return appropriate output. In addition to the basic client-server model, you will also implement a secondary administration channel to your servers, and manually maintain the state of all conversations within your distributed application.

Each aspect of the specifications requires you to solve a particular client-server problem while maintaining a conversation within a stateless protocol. The assignment is designed to give you general-purpose exposure to writing code for a client-server multi-processing environment, and working with threads, neither of which is trivial.

Your finished server is just for fun, but with minor changes it can serve as the basis for a real, viable, client-server application handling thousands of client conversations simultaneously. Although we will only be testing with from 1 to 5 clients at a time, your program should be designed to run thousands of worker threads at once.

Note that we will not make these servers thread-safe, which is beyond the scope of this assignment. However, you are free to do so if you have the expertise and wish to do so.

Administration:
Supplemental links:
Mode Changer method
Asynchronous threads
Joke pseudo code
Fake UUID
Joke state
Complete the ColorServer first. You are free to use that code as a basis to start this assignment. Note: you may not copy any other code, including InetServer code, or you will be guilty of plagiarism. WRITE YOUR OWN CODE.
Update your Joke-checklist as you make progress on the JokeServer assignment.
There is a high priority on our being able to download, and run, your three java applications (JokeServer, JokeClient, JokeClientAdmin) without any complications. So, even though this may not be the most elegant coding style, put all your source files in one directory, along with your checklist, the JokeLog.txt, and so on (no .class files!) then zip together before submitting to D2L. Make sure that your programs will compile when we issue "javac *.java" (twice) at the command line prompt from within this single directory. NEVER use java packages. That is, the package statement should not appear in your source code.
Submit to D2L well before the deadline.
Submission files for JokeServer:
Joke-checklist.html
JokeServer.java
JokeClient.java
JokeClientAdmin.java
JokeLog.txt
Joke-postings.txt
The filenames MUST be precise because we use automated scripts as part of the grading process, including with the various plagiarism checkers. Zip all these files together and submit the one standard .zip file to the JokeServer D2L dropbox. Make sure that you are familiar with the assignment submission rules. Programs that do not precisely conform to the rules will not be graded. Please do not ask for an exception to this policy.
Additionally, concatenate your three JokeServer Java source code files into one file (.docx, or .html with .txt as a last option) for submission to the JokeServerTII D2L Dropbox link for plagiarism checking. CHECK YOUR SUBMISSION to see if it will be flagged for plagirism. [In D2L, click on the number link showing the number of submissions you've made and follow this path to retrieve your plagiarism report.] There may be some minor overlap with the work of others, but the overall percentage should be low, and your comments should always be unique. If you include some of the ColorServer code I provide (which is allowed) it will be flagged, but we are not concerned about that.
Your concatenated files will not be used for compilation and you need not be concerned about formatting, but the TEXT MUST BE THE SAME as the JokeServer Java files you have submitted for grading, and the single TII file must be readable by the plagiarism checker.

Be sure to include the required java header file as comments at the top of each of your java files.
Allow yourself enough time to get a basic version of the JokeServer running. Debugging TCP/IP on your machine, having difficulties wtih your firewall, spawning runaway processes, etc. comes with the territory, and you should not underestimate the amount of time this will take you during the initial phases of this project.
You will have to figure out a way to capture the console output from your running programs so that the output, showing your working programs in each of the terminal windows, can be placed in your JokeLog.txt file. Manual Copy and Paste from the console is probably the easiest scheme, and exactly what I suggest. I do not recommend writing to a file, because it takes a lot more work and you'll have to address having three output streams from at least three running processes in three files that later have to get concatenated together. Don't waste time making the output fancy.
Note that you have to maintain a theoretically unlimited number of arrays, or other data structures, to keep track of client state; you have to connect from two different kinds of clients, and so on. Each of these steps may take some thought, so leave plenty of time to get this assignment finished.
Allow yourself enough time to format the presentation of your work exactly as specified. This may take some thought and some experimentation the first time you do this. For example, your programs must compile and run from the command line. You should NOT assume that you can get this done at the last minute.
I strongly recommend that you complete this assignment incrementally, and each time you get a partial version running, you submit it to D2L (with the appropriate checklist!). This way, if you run out of time you will still get substantial partial credit. If you submit a partial version (with the matching checklist) you should put a console message for the grader when the program starts up, and a note in the comments section of your checklist that this is a partial version of the assignment and you plan to resubmit later.
Read this whole assignment page before you get started so you know where all the hints, links and explanations are.
