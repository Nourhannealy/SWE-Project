# Investment Manager

A **JavaFX desktop application** that helps users manage their personal investments efficiently. With features like user authentication, asset management, and automated financial and zakat reporting, this tool is ideal for individuals seeking better financial oversight.

## Features

- **User Authentication**
  - Secure Signup and Login functionality
- **Asset Management**
  - Add new assets (e.g., stocks, real estate, savings, etc.)
  - Edit or remove existing assets
- **Financial Reporting**
  - Generate detailed financial reports
  - Calculate **net worth** based on current asset values
- **Zakat Reporting**
  - Automatically calculate zakat owed based on eligible assets

---

## Tech Stack

- **Java 17+**
- **JavaFX** for UI
- **Scene Builder** for FXML layout design
- **SQLite** or for lightweight embedded database (optional)
- **JDBC** for database access

---

## Project Structure
InvestmentManager/
│
├── src/
│ ├── reporting/
│ │ ├── views/
│ │ │__ users/
| | |__ model/
| | |__ db/
| | |__ controller/
│ │ └── resources/
│ │ ├── views/
│ │ └── styles/
│ └── App.java
├── README.md

## 🛠️ Installation & Running

### Prerequisites

- Java 17 or higher
- JavaFX SDK installed and configured
- sqlite JDBC
