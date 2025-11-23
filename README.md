# 🔐 Java Password Manager  
A simple, secure, full-stack password manager built with **Spring Boot**, **AES-256 encryption**, and a clean responsive frontend.  
Users can register a master PIN, log in, add/update passwords, and view their encrypted vault — all running on a secure REST API.

Live Demo: **https://java-password-manager.onrender.com**

---

## 🚀 Features

### 🔑 Master PIN System
- Register a **new master PIN** (first-time setup)
- Login using an existing PIN
- Backend prevents accidental vault creation when using Login
- Encrypts all data using AES-256

### 🧠 Password Vault
- Add new password entries  
- Update existing entries  
- Delete entries  
- Search entries by site/app name  
- All data stored encrypted on the server

### 🧮 AES-256 Encryption
- Uses PBKDF2 and AES/CBC/PKCS5Padding  
- Encrypts: site, username, password  
- Data stored as base64 cipher text inside `passwords.txt`

### 🌐 Full Stack Architecture
- **Spring Boot REST API**
- **HTML + CSS + JavaScript** frontend
- Hosted on **Render (Cloud) using Docker**

---

## 📦 Tech Stack

### Backend
- Java 21  
- Spring Boot  
- AES-256 Encryption (PBKDF2 key derivation)  
- Maven  
- Docker (for deployment)

### Frontend
- HTML5  
- CSS3  
- JavaScript (fetch API)

### Deployment
- Render Web Service  
- Docker multi-stage build

---

## 📁 Project Structure

password-manager/
│
├── src/
│ ├── main/
│ │ ├── java/com/example/demo/
│ │ │ ├── controller/ # REST API
│ │ │ ├── service/ # Encryption, file handling
│ │ │ ├── model/ # PasswordEntry class
│ │ │ └── DemoApplication.java
│ │ └── resources/
│ │ ├── static/ # index.html + frontend JS/CSS
│ │ └── application.properties
│
├── Dockerfile
├── pom.xml
└── README.md

yaml
Copy code

---

## 🔧 Installation (Local)

### 1. Clone the repo
```bash
git clone https://github.com/Inayat12473/password-manager.git
cd password-manager
2. Build the app
bash
Copy code
mvn clean package
3. Run the server
bash
Copy code
mvn spring-boot:run
4. Open frontend
bash
Copy code
http://localhost:8080/index.html
🌍 Deployment (Render)
This app uses a Dockerfile for easy deployment.

Build command (handled by Dockerfile)
go
Copy code
mvn clean package -DskipTests
Start command (inside container)
nginx
Copy code
java -Dserver.port=$PORT -jar app.jar
Render automatically:

Builds Docker image

Runs Spring Boot

Exposes public HTTPS URL

🔒 Security Notes
Master PIN is never stored in plain text

All password entries are encrypted before writing to disk

Free Render plan does not provide persistent storage

For production, connect to a real database (PostgreSQL)

🤝 Contributing
Pull requests are welcome.
If you want new features like:

Multi-user accounts

Cloud database (PostgreSQL)

JWT login

Export/Import vault

Feel free to open an issue.

📄 License
This project is open-source under the MIT License.

✨ Author
Inayat Sanadi

Live Demo: https://java-password-manager.onrender.com

yaml
Copy code

---

If you want, I can also generate:

✅ Shields/badges (Build Passing, Java Version, Docker)  
✅ Screenshots section  
✅ Animated GIF demo section  
✅ Dark-mode README styling  

Just tell me.
