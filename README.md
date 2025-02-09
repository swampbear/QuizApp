# QuizApp153
Welcome to the Quiz & Gallery Android Application! This project is developed as part of a mandatory task in DAT153 at HVL. The application is designed to showcase essential Android development concepts by integrating interactive quiz functionality with a dynamic gallery management system.

The app use XML to render the user interface and utilizes Java for handling the application logic.
## Key Features

- **MainActivity:**  
  Serves as the central hub where users can navigate between the Quiz and Gallery features.

- **QuizActivity:**  
  - Starts the quiz only if there are at least three entries available.  
  - Users can answer quiz questions, where the score increases for correct answers, and only the question count increases for incorrect responses.  
  - The quiz continues until the user decides to end it, at which point they are returned to the MainActivity.

- **GalleryActivity:**  
  - Allows users to manage a collection of entries.  
  - Users can add new entries via a bottom sheet, which prompts them to enter a name and upload an image.  
  - Provides options to remove entries and sort them alphabetically or in reverse order.  
  - Includes a back button to return to the MainActivity.
 ## Run Configurations for Android Studio

To run the **Quiz & Gallery Android Application** in Android Studio, follow these steps:

1. **Open the Project:**
   - Launch Android Studio and open the project.

2. **Edit Run Configurations:**
   - Go to **Run** > **Edit Configurations...**
   - Click the **+** button and select **Android App**.

3. **Configure the New Run Configuration:**
   - **Name:** `QuizApp`
   - **Module:** `app`
   - **Deploy:** Use the default APK deployment settings.
   - **Launch Options:** Select **Default Activity** (or specify `MainActivity` if needed).
   - Click **Apply** and then **OK**.

4. **Run the Application:**
   - Use the **Run** button in the toolbar or press **Shift + F10** to start the app.

## Application flowchart
We have providerd a flowchart showing the flows inside the application. This includes expected and unexpected flows with illustration of how errors from the user is handled
```mermaid
flowchart TD
    %% Main Screen
    A[MainActivity] --> B{Select Option}
    B -- "Gallery" --> C[GalleryActivity]
    B -- "Quiz" --> D[QuizActivity]

    %% GalleryActivity Branch
    C --> E[Display Gallery]
    E --> F[Add Entry]
    E --> G[Remove Entry]
    E --> H[Sort Alphabetically]
    E --> I[Sort Reverse Alphabetically]

    %% Adding a new entry via a bottom sheet
    F --> J[Show Bottom Sheet]
    J --> K[Enter Name & Upload Image]
    K --> L{Both fields provided?}
    L -- Yes --> M[Add Entry to Gallery]
    L -- No --> N[Show Notice: Fill in both fields]
    N --> K
    M --> E

    %% QuizActivity Branch
    D --> O{At least 3 entries?}
    O -- No --> P[Display Message: Add more entries]
    P --> Q[Return to MainActivity]
    O -- Yes --> R[Display Quiz Question 3 options]
    
    %% Handling quiz responses
    R --> S{Correct Option?}
    S -- Yes --> T[Increase Score & Question Count]
    S -- No --> U[Increase Question Count Only]
    T --> V[Next Question]
    U --> V
    V --> R
    
    %% Option to end quiz
    R --> W[End Quiz]
    W --> A
```


```
