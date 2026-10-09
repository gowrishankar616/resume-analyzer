# Resume Analyzer

A full-stack web application designed to simplify resume uploading and analysis. Resume Analyzer provides a user-friendly interface where users can upload their resumes and process them through a backend service.

## 🚀 Project Overview

Resume Analyzer is developed using React.js for the frontend and Java Spring Boot for the backend. The application demonstrates full-stack development concepts, REST API integration, file uploading, and backend processing.

## ✨ Features

- **Resume Upload:** Upload resumes through a simple user interface.
- **REST API Integration:** Connect the React frontend with the Spring Boot backend.
- **Backend Processing:** Handle uploaded resume files on the server.
- **User-Friendly Interface:** Simple and easy-to-use design.
- **Full-Stack Architecture:** Separate frontend and backend modules.
- **Maintainable Code Structure:** Organized project folders for easier development.

## 🛠️ Technologies Used

**Frontend**
- React.js
- JavaScript
- HTML5
- CSS3
- Vite

**Backend**
- Java
- Spring Boot
- Spring Web
- REST APIs
- Maven

**Tools**
- Git and GitHub
- Visual Studio Code
- Eclipse IDE
- Postman

## 📂 Project Structure

```text
resume-analyzer/
├── resume-analyzer-frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── vite.config.js
│
├── resume-analyzer-backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       └── resources/
│   └── pom.xml
│
└── README.md
```

*Note: The structure above is a typical layout. Adjust it to match your actual project files.*

## ⚙️ Installation and Setup

### Prerequisites

Make sure you have installed:

- Java JDK
- Node.js and npm
- Maven (if not using the Maven Wrapper)
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/gowrishankar616/resume-analyzer.git
cd resume-analyzer
```

### 2. Run the Backend

Open a terminal and navigate to the backend folder:

```bash
cd resume-analyzer-backend
```

Run the application using Maven:

```bash
mvn spring-boot:run
```

Alternatively, open the backend project in Eclipse and run the Spring Boot application.

### 3. Run the Frontend

Open another terminal from the repository root:

```bash
cd resume-analyzer-frontend
npm install
npm run dev
```

Open the local URL displayed in your terminal, usually `http://localhost:5173`.

## 🔄 How It Works

1. The user opens the Resume Analyzer web application.
2. The user selects and uploads a resume.
3. The React frontend sends the resume file to the Spring Boot backend.
4. The backend receives and processes the uploaded file.
5. The application handles the response and displays the relevant result supported by the implemented features.

## 🎯 Project Objectives

- Understand full-stack application development.
- Implement file upload functionality.
- Develop and integrate REST APIs.
- Connect a React frontend with a Java Spring Boot backend.
- Improve practical skills in Java, React.js, and API testing.

## 🔮 Future Enhancements

- Extract text from uploaded PDF and DOCX resumes.
- Identify important skills and qualifications.
- Compare resume content against a job description.
- Provide resume improvement suggestions.
- Add user authentication and resume history.
- Develop a responsive dashboard for analysis results.

## 👨‍💻 Developer

**Gowri Shankar**

- GitHub: [@gowrishankar616](https://github.com/gowrishankar616)

## 📄 License

This project is available for learning and portfolio purposes. Add a specific open-source license if you intend to distribute it under particular reuse terms.

---

If you find this project useful, consider giving the repository a ⭐ on GitHub!
