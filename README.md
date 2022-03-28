# WeatherTopUp
TopUp Mama Solution.
- The app will desplay weather details of current city (Nairobi) and a list of other 20 cities. The data is fetched in real-time and the application uses internet to ensure the data displayed is correct and upto date.

### API - Open Weather
I'm using [Open Weather API](https://openweathermap.org/api) for this application.
#### Example Endpoints
- [api.openweathermap.org/data/2.5/weather?q=Nairobi&appid=[API Key]](https://api.openweathermap.org/data/2.5/weather?q=Nairobi&appid=[apikey])
- [api.openweathermap.org/data/2.5/group?id=524901,703448,2643743&appid=[API Key]](http://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743&appid=[apikey])

### UI/UX
Got the visual look and feel from this [Adobe XD Weather App Demo Prototype](https://xd.adobe.com/view/cb610950-3fbb-4ef5-ac1c-fe964389dedd-9e32/specs/).

### Summary of Technologies and Features used.
- Kotlin.
- **IDE:** Android Studio.
- [View Binding](https://developer.android.com/topic/libraries/view-binding) : Allows you to more easily write code that interacts with views
- [Adapter](https://developer.android.com/reference/android/widget/Adapter) : Bridge between an **AdapterView** and the underlying data for that view
- [Android Singleton Pattern](https://google.github.io/volley/requestqueue) : Helps in setting up RequestQueue

### Architecture 
**MVVM**(ModelView View Model architecture) Software architectural pattern that facilitates the separation of the development of the graphical user interface (the view) be it via a markup language or GUI code.
![MVVVM Img](screenshots/mvvm.png)

### Third-party Libraries.
- Volley.

### Unit Tests
- Here we're testing code which doesn't call the Android API to verify our app's correctness, functional behavior, and usability before you release it publicly.
- **Example Unit Test** method from my code
```
@Test
fun convert_Kelvin_To_Celsius() {
    val res = 27.75
    assertEquals(String.format("%.1f", res), userDefined.convertKelvinToCelsius(300.9).toString())
}
```
