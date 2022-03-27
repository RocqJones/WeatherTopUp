# WeatherTopUp
TopUp Mama Solution

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
