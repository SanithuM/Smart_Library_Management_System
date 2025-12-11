# 📚 Smart Library Management System (SLMS)


## 📖 Project Overview

The **Smart Library Management System (SLMS)** is a comprehensive Java-based application designed to manage university library operations. It handles book inventory, user memberships, borrowing cycles, fine calculations, and notifications.

The primary goal of this project is to demonstrate the practical application of **Object-Oriented Programming (OOP)** principles and six specific **Design Patterns** to solve complex software architecture problems.

---

## 🚀 Key Features

* **User Management:** Supports Students, Faculty, and Guests with specific borrowing limits and loan periods.
* **Book Management:** Add new books with optional metadata (Author, Edition, Tags).
* **Borrowing Lifecycle:** Full cycle of Borrowing, Returning, and Reserving books.
* **Dynamic Fines:** Automatically calculates overdue fines based on user type (Strategy Pattern).
* **Notifications:** Alerts users when reserved books become available (Observer Pattern).
* **Command History:** Logs all user actions with support for undo operations.
* **CLI Interface:** Interactive Command Line Interface for Librarians and Members.

---

## 🏗️ Design Patterns Implemented

This project strictly adheres to the assignment requirements by implementing the following patterns:

| Pattern | Usage in Project |
| :--- | :--- |
| **Strategy** | **Fine Calculation:** Switches algorithms (50 vs 20 vs 100 LKR) based on `User` type. |
| **State** | **Book Status:** Manages behavior transitions between `Available`, `Borrowed`, and `Reserved`. |
| **Observer** | **Notifications:** Users subscribe to `Book` updates and get notified on return. |
| **Builder** | **Book Creation:** Constructs complex `Book` objects with optional fields like edition/tags. |
| **Command** | **User Actions:** Encapsulates `Borrow`, `Return`, and `Reserve` requests as objects for history logging. |
| **Decorator** | **Dynamic Features:** Adds labels like `[FEATURED]` or `[SPECIAL EDITION]` to books at runtime. |

---

## 📂 Project Structure

The source code is organized into modular packages under `src/slms`:

```text
src/
└── slms/
    ├── builder/      # K2559097_BookBuilder (Object Construction)
    ├── command/      # Borrow, Return, Reserve Commands & Invoker
    ├── decorator/    # Featured & SpecialEdition Decorators
    ├── model/        # Core Entities: Book, User, BorrowRecord
    ├── state/        # Book States: Available, Borrowed, Reserved
    ├── strategy/     # Fine Strategies: Student, Faculty, Guest
    └── ui/           # Main Entry Point & CLI Interface
