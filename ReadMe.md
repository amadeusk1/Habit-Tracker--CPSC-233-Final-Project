# Habit Tracker—JavaFX Application

## Project Overview
Habit Tracker is a simple JavaFX-based application designed to help users track their daily activities and goals.
It allows users to log the number of hours they spend on various tasks (e.g., Sleep, Exercise, Study, Work, Leisure) and
compare them against their goals. It also provides detailed summaries and insights into user habits and goal achievements.

## Features
- Load previously saved schedule data from a `.csv` file
- Enter daily hours for key activities
- Set daily goals for each activity
- Save new or updated schedules to a file
- View and analyze progress using eight summary tools:
    - TotalHoursLogged
    - TotalHoursLogged/Day
    - Max. & Min. Activity
    - WeeklyGoalsAchieved
    - DailyGoalsAchieved
    - NumberGoalsAchieved
    - Goals Exceeded (Day)
    - Remaining Time (Day)
- GUI built with JavaFX and styled using minimal custom CSS

## Technologies Used
- Java 23
- JavaFX 23.0.1 (FXML-based UI)
- Scene Builder (for FXML layout)
- Maven for dependency management and building

## Authors
- Dominik Trzesicki
- Ahnaf Farhan Khan
- Amadeus Kaczmarek

## Email Contacts
- dominik.trzesicki@ucalgary.ca
- ahnaf.farhankhan@ucalgary.ca
- amadeus.kaczmarek@ucalgary.ca

## Getting Started
### Requirements
- Java 17+ (Java 23 recommended)
- JavaFX SDK 21.0.1 or later (configured in Maven)
- Scene Builder (optional, for FXML editing)

### How to Run
1. Clone the repository or download the source code
2. Open the project in IntelliJ IDEA or another IDE
3. Ensure Maven resolves dependencies
4. Run using the JavaFX Maven Plugin:
```
mvn clean javafx:run
```

## File Structure
- `MainGUI.java` - Main class launching the JavaFX application
- `MainController.java` - Handles all UI logic and user actions
- `Main.fxml` - UI layout file
- `FileLoader.java` - Handles loading data from file
- `FileSaver.java` - Handles saving data to file
- `Goals.java` - Singleton class managing goal data
- `Activity.java` - Represents a day's activity data
- `style.css` - Optional CSS file for button styling and layout polish

## How It Works
- Users can load a previous `.csv` file or begin fresh
- Enter hours for each activity and set corresponding goals
- Confirm entries to save them
- Use right-side tools to view summaries and analytics

## About Section in App
Accessible via Help > About in the menu bar. It includes authorship, version info, and a short description of the project.

## Version
v1.1

## License
This project is for educational purposes and may be freely modified or extended for personal use.

