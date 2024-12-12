# Ticket Management System
![image](https://github.com/user-attachments/assets/b45fcb53-ef44-4745-ab5f-41c841e7eeee)

## Introduction
The Ticket Management System is a robust application designed to manage and monitor ticketing operations efficiently. It provides functionalities to configure system settings, start and stop operations, reset the system, and view recent transactions and the ticket pool status. The system comprises a **React-based frontend** and a **Spring Boot backend**, enabling seamless interaction and functionality.

---

## Setup Instructions

### Prerequisites
To build and run this application, ensure the following are installed on your system:

- **Java**: JDK 17 or higher
- **Node.js**: Version 18 or higher
- **npm**: Version 8 or higher
- **Spring Boot**: Version 2.7.x (handled via Maven)
- **Maven**: Version 3.6 or higher

### Frontend Setup
1. Clone the repository:
   ```bash
   git clone <https://github.com/ShenalSen/Real-Time-Event-Ticketing-System-with-Advanced-Producer-Consumer-Implementation.git>
   cd ticket-management-system/frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

   The React frontend will run at,
   
   ➜  Local:  'http://localhost:5173/'
   
   ➜  Network: use --host to expose
   
   ➜  press h + enter to show help

### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd ticket-management-system/backend
   ```

2. Build the Spring Boot application using Maven:
   ```bash
   mvn clean install
   ```

3. Run the backend server:
   ```bash
   java -jar target/tms-backend-0.0.1-SNAPSHOT.jar
   ```

   The backend API will run at `http://localhost:8080/`.

---

## Usage Instructions

### Configuring the System
1. Navigate to the **Configuration** panel in the UI.
2. Fill in the required fields:
   - **Total Number of Tickets**
   - **Ticket Release Rate**
   - **Customer Retrieval Rate**
   - **Maximum Ticket Capacity**
3. Click **Set Configuration**. Ensure all values are greater than 0 and that the total tickets do not exceed the maximum capacity.

### Starting, Stopping, and Resetting the System
1. Use the **Control Panel** in the UI:
   - **Start**: Initiates the system.
   - **Stop**: Halts all operations.
   - **Reset**: Resets the ticket count to its default state.
2. Observe real-time updates on the **Ticket Pool Status** panel.

### Viewing Recent Transactions
1. Access the **Recent Transactions** panel to view the latest 10 transactions.
2. Transactions are updated in real-time when the system is running.

---

## Explanation of UI Controls

### Panels
- **Configuration Panel**:
  - Configure ticketing system settings.
  - Includes input fields with validation to ensure proper values.

- **Control Panel**:
  - Start, stop, or reset the system operations.
  - Buttons trigger respective backend APIs.

- **Ticket Pool Status**:
  - Displays the current total ticket count dynamically.

- **Recent Transactions**:
  - Displays a list of recent transactions, updated every second when polling is enabled.

### Backend API Endpoints
- **POST** `/api/ticketing/configure`:
  Configures the ticketing system.
- **POST** `/api/ticketing/start`:
  Starts the system.
- **POST** `/api/ticketing/stop`:
  Stops the system.
- **POST** `/api/ticketing/reset`:
  Resets the total ticket count.
- **GET** `/api/ticketing/latest-transaction`:
  Retrieves the latest transaction details.

---

## System Architecture
- **Frontend**:
  - React with Tailwind CSS for styling.
  - Uses `fetch` for REST API communication.

- **Backend**:
  - Spring Boot application.
  - RESTful APIs for communication with the frontend.
  - Implements service-layer architecture for business logic.
---

## License & Copyright
This project is licensed under the MIT License. See `LICENSE` for details.
© 2024 [Shenal Raveesha Senarathne](https://shenalsenarathne.me). All Rights Reserved.

---



