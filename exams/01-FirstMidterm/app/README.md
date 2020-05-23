# SpaceX Launches

## Description

This is an Android application which fetches the information about the all of the recorded SpaceX launches and displays their information to user.

## Usage

The application is pretty straightforward to use, however this is a short summary of the main potential use cases:

1. **Authentication** - To use the application to its fullest potential, the user must sign up first, so that signing in becomes possible
2. **Launches** - Once the user signs in, the main part of the application will be displayed in form of a list of SpaceX launch cards that convey information about specific recorded spacecraft launches
3. **Specific Launch** - User can click on the card to navigate to the launch's specific page that holds even further information regarding the launch
4. **Query Parameters** - This menu, in short, - a feature, enables the user to tweak the query parameters which work under the hood to fetch the launch information. User can specify the way the fetching process will be done by altering the values present in the menu
5. **Signing Out** - User can decide to sign out of the application
6. **Help** - User can refer to the "About" menu item which creates a dialogue that holds information about the application itself and where the user can look into for further enrichment of knowledge
7. **Exit** - User can decide to exit the application without signing out, which means that the next time the user reopens the application, authentication will happen automatically

## Technical Details

Under the hood, the application uses Firebase Authentication for user authentication system.

Some parts of the application, especially networking parts, are made asynchronous by utilizing Kotlin's coroutines.

The application sends GET requests via Retrofit to SpaceX API service and sets the gathered data to the main page's recycler view, which in turn displays the launch information of all gathered items and enables the user to access specific launch information by clicking on the launch card, which in turn also sends a GET request to the desired launch endpoint.

SpaceX API is rich in providing developers to customize their workflow based on their needs for using the service. One way the application utilizes this opportunity is by implementing a query system to filter out the desired fields/attributes from the network calls. As a result, less data than necessary is exchanged (useful for speed), and only the fields/attributes that matter to developer are preserved. The application lets both the developer and the user to tweak the query filters in such way that fetch different, interesting and relevant launch data.

The rest of the application is built akin to lego blocks, by using different flavours of views that are available in the Android SDK and then logically coupling them together.

## Dependencies

The full list of dependencies are written in the **build.gradle** files.
