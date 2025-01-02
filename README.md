# Meeting Scheduler

## Overview

In the modern educational landscape, effective communication between students and teachers plays a vital role in fostering academic success. To enhance this interaction, our Appointment Management Application has been developed to streamline the process of scheduling meetings. The application ensures that students can conveniently arrange appointments with their teachers at mutually agreeable times, promoting better collaboration and understanding.
The application is built using the TCP/IP protocol and follows a TCP Client-Server model. In this model, the client side is responsible for requesting services from the server, while the server remains “always online,” waiting to handle requests from multiple clients. Importantly, clients do not directly contact each other; all interactions are facilitated through the server.
The application provides a range of features tailored for both students and teachers. Students can log in, register accounts, view available time slots from teachers, and schedule individual or group meetings as needed. They can also manage their appointments by canceling scheduled meetings or viewing meetings planned for a specific week. On the other hand, teachers can declare and edit their availability for meetings, specifying time slots for group or individual sessions as necessary. Additionally, teachers can view meetings scheduled by students on specific dates, document the content of ongoing or past meetings, and review the history of past meetings with each student along with associated meeting minutes. These functionalities work together to ensure seamless scheduling, improve time management, and foster productive academic interactions.

## Getting Started

Clone this project and run locally, the User Interface design is very human and easy to use without any instruction

### Prerequisites
Here is the translation of your request into English:

1. **Install Java Development Kit (JDK)**:  
   Since the application is developed in Java, you need to install the Java Development Kit (JDK).  

2. **Install PostgreSQL** (as the application uses PostgreSQL database):  
   You need to install PostgreSQL to connect to and manage the database.  
   After installation, you will need to configure the database and create the necessary tables for the application. This information can be found in the Java files or detailed instructions in the project documentation.


Here is a step-by-step guide for running your application based on the steps you provided:

### Step 1: Create and Connect to the Database (in `SqlConnection.java`)
1. Open the `SqlConnection.java` file.
2. In this file, ensure you have the correct database URL, username, and password for connecting to your database.

### Step 2: Add Necessary Libraries to Build Path
1. Go to **Build Path** -> **Configure Build Path**.

### Step 3: Determine the IP Address of the Server (for Demo on Multiple Machines)
1. Open the command prompt on Windows.
2. Type the following command to get the IP address:

   Windows
   ```
   ipconfig
   ```
   Linux
   ```
   ifconfig
   ```
4. Find the **IPv4 Address** for your network adapter. This is the IP address of the machine.

### Step 4: Edit the `ConstantValue.java` File
1. Open the `ConstantValue.java` file.
2. Find the `HOST_ADDRESS` constant, and set the value according to your setup:
   - For local testing (on a single machine), set it to `localhost` or `127.0.0.1`.
    
   - For testing on multiple machines, use the IP address obtained from `ipconfig`:
    

### Step 5: Run the Application
1. Run the `Server.java` file to start the server. This will initialize the server and make it ready to accept connections.
2. After the server is up, run the `CalendlyApplication.java` file to start the main application.
3. The application should now be connected to the database and running on the specified server.

