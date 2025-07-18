
# ✅ OrangeHRM Automation Task

This is a UI automation project built using **Java**, **Selenium WebDriver**, and **TestNG**, following the **Page Object Model (POM)** design pattern. It automates core functionalities on the [OrangeHRM Demo Site](https://opensource-demo.orangehrmlive.com/), specifically user management (add, verify, delete user).

---

## 🧰 Tools & Technologies Used

| Tool/Library         | 
|----------------------|
| Selenium WebDriver   | 
| TestNG               | 
| Maven                | 
| WebDriverManager     | 
| ExtentReports        | 
| Page Object Model    | 

---

## 💻 How to Set Up the Project Locally

### ✅ Prerequisites

Make sure the following are installed:

- ✅ [Java JDK 21+]
- ✅ [Maven](https://maven.apache.org/install.html)
- ✅ [Git](https://git-scm.com/)
- ✅ IDE IntelliJ IDEA

---

### 📥 1. Clone the Repository

```bash
git clone https://github.com/Mohammed9477/OrangeHRM-AutomationTask.git
cd OrangeHRM-AutomationTask
```

---

### ⚙️ 2. Configure Project Properties

Go to:

```
src/main/resources/config.properties
```

Set the URL and browser:

```properties
browser=chrome
url=https://opensource-demo.orangehrmlive.com/
```

---

### 📦 3. Install Dependencies

Maven handles all dependencies. Run:

```bash
mvn clean install
```

This will download:
- Selenium
- WebDriverManager
- ExtentReports
- TestNG
... and more from the `pom.xml`.

---

## 🚀 How to Run the Tests

You can run tests in **two ways**:

### 🔹 Option 1: From the Terminal

```bash
mvn test
```

### 🔹 Option 2: From the IDE

Right-click on:
```
src/test/java/userManageTest.java
```
and choose `Run`.

---

## 📊 Test Reports

After test execution:

📁 Navigate to:  
```
/test-output/ExtentReports/
```

📝 Open `ExtentReport.html` to view the detailed result.

---

## ✅ Test Scenario Covered

- Login as Admin  
- Navigate to Admin tab  
- Add a new system user  
- Verify the user appears in the table  
- Delete the user  
- Verify the user was removed  

---

## 🙋‍♂️ Author

**Mohammed Samy**  
📌 GitHub: [@Mohammed9477](https://github.com/Mohammed9477)

---

## 🪪 License

MIT License – feel free to use, modify, or share with attribution.
