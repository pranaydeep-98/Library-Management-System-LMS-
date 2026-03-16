# Library-Management-System-LMS-
# 📚 Library Management System

A **Full Stack Library Management System** built using **Spring Boot** for the backend and **React** for the frontend.
This system allows **administrators to manage books and users**, while **users can search, borrow, and return books easily**.

The application uses **JWT Authentication** to provide secure login and role-based access for **Admin** and **User**.

---

# 🚀 Features

## 👨‍💼 Admin Features

* Admin login with role-based authentication
* Add new books to the library
* Edit book details
* Increase the number of copies of existing books
* Delete books from the system
* View borrowing history
* Manage users

---

## 👤 User Features

* User registration and login
* Search books across the entire library
* Borrow available books
* Return borrowed books
* View borrowed books list

---

# 🛠️ Tech Stack

## Backend

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* MySQL
* Maven

## Frontend

* React
* React Router
* Axios
* CSS / Bootstrap

---

# 📂 Project Structure

```
library-management-system
│
├── backend
│   ├── controllers
│   ├── services
│   ├── repositories
│   ├── models
│   └── security
│
├── frontend
│   ├── components
│   ├── pages
│   ├── services
│   └── styles
│
└── README.md
```

---

# 🔐 Authentication

This project uses **JWT (JSON Web Token)** authentication.

Workflow:

1. User/Admin logs in using username and password
2. Backend generates a JWT token
3. Token is stored in browser LocalStorage
4. Token is sent with every API request
5. Backend verifies token before allowing access

---

# 📚 Borrow Book Workflow

1. User logs into the system
2. User searches for books in the library
3. If the book is available, a **Borrow** option is displayed
4. When the user borrows a book:

   * Available book count decreases
   * Borrow record is stored in the database
5. Admin can view borrowing history from the dashboard

---

# ⚙️ Installation & Setup

## 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/library-management-system.git
```

---

## 2️⃣ Backend Setup

Navigate to backend folder

```bash
cd backend
```

Run the Spring Boot application

```bash
mvn spring-boot:run
```

Backend runs at:

```
http://localhost:8080
```

---

## 3️⃣ Frontend Setup

Navigate to frontend folder

```bash
cd frontend
```

Install dependencies

```bash
npm install
```

Start the React application

```bash
npm start
```

Frontend runs at:

```
http://localhost:3000
```

---

# 🗄️ Database

Database used: **MySQL**

Main tables:

* Users
* Books
* Borrow Records
