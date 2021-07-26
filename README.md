#LIBS Used

Hilt Jetpack -
Since it is android provided jetpack lib, used for dependency injection.
Reference Link: https://developer.android.com/training/dependency-injection/hilt-android

DataBinding -
Android provided lib used for binding pojo classes with UI and eliminate bolier plate code.
Reference Link: https://developer.android.com/topic/libraries/data-binding

retrofit -
As per the requirement we need to consume REST API hence decided to goahead with retrofit as it is most commonly used, have huge developer stack, simple, 
fast and easy to implement.
Reference Link: https://square.github.io/retrofit/

Room -
Another Android provided ORM lite lib used for offline data storing.
Reference Link: https://developer.android.com/jetpack/androidx/releases/room?gclid=CjwKCAjwuvmHBhAxEiwAWAYj-J3wlvidXko6aZg39MqGTD82r4qiiTgFi82UnyYBcnJFrz41hY_-_BoC6EcQAvD_BwE&gclsrc=aw.ds

Paging -
Another Android Jetpack lib used for recycler view.
Reference Link: https://developer.android.com/topic/libraries/architecture/paging/v3-overview

Mockito -
For evaluating app logic using unit test by mocking classes.
Reference Link: https://developer.android.com/training/testing/unit-testing/local-unit-tests

Robolectric -
Haven't used for now but added in-order to support additional unit-testing if onClick and screen navigations are implemented.
Reference Link: http://robolectric.org

Espresso -
For UI automation testing.
Reference Links: https://developer.android.com/training/testing/espresso
https://developer.android.com/training/testing/ui-testing/espresso-testing

MockWebServer - 
For validate and test request & response for HTTP/ HTTPS Calls.
Reference Links: https://github.com/square/okhttp/tree/master/mockwebserver

What is implemented here:
- Consumed https://jsonplaceholder.typicode.com/albums API and displaying data in a recylerview
- Also added offline support using Room DB and loading API data through Jetpack Paging Lib
- Added minor animation while scrolling

What can be done next?
- Can extend this app by consuming other API's like https://jsonplaceholder.typicode.com/photos & https://jsonplaceholder.typicode.com/users
- On click can display album id based photos in a grid view
- Can club user info from users API and display next to Album title
- Tapping on user name can load User information in another screen.

