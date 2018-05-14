# Mercury Droid Android IoT Home Weather Monitoring Application

<b>Author :</b> <br>
Arunav Mallik Avi, <br>
Department of Computer Science and Engineering, 
National University, Bangladesh

<b>Descriprtion :</b> <br>
Mercury Droid is one kind of IoT(Internet of things) based "Home Weather Monitoring"
Android mobile applicaton. this application is capable of read home weather activity
like Celsius, Kelvin, Fahrenheit, Heat-Index, Humidity. basically this application can 
comunicate with NodeMCU(ESP-8266MOD), which is and IoT wifi Module. the NodeMCU(ESP-8266MOD) 
is the brain of Mercury Droid IoT Embedded system which collect DHT-11 sensor Temperature
and Humidity data and convert them into JSON format and finally send this JSON data to it's 
webserver and then Mercury Droid android mobile application can read this JSON data from
from NodeMCU(ESP-8266MOD) or Mercury Droid webserver by using a specific static IP address. 
by default this static IP is 192.168.0.107 . this applicationhas also have a special feature to
measure the excessive Temperature by comparing with user given Threshold value. 
if the threshold value is less than Celsius Temperature value than this application 
gives you alert if Threshold value is grater than Celsius value it not gives you alert. 
there are many IoT Tools are out there like "Blynk" , "Cayenne" , "ThingsSpeak etc". 
this tools are very simple to use to capture various sensor data. but this application is unique and open source , 
so that any one can build their own Home weather Monitoring IoT Software and Hardware without using any Ready Maid IoT tools. 

<b>Features :</b>
1. Fast JSON data readabilty
2. Activity is Refreshed every 2 Seconds
3. Excessive temperature calculation basis of threshold
4. NodeMCU(ESP-8266MOD) or Mercury Droid open network Configuration or login service

<b> Important information : </b>
By default the static IP address is 19.168.0.107 but you can change this from NodeMCU(ESP-8266MOD) or MercuryDroid system Arduino IDE Code

<b> Mercury Droid or NodeMCU(ESP-8266MOD) System Arduino IDE Code : <b><br>
https://github.com/avimallik/IoT-Home-weather-monitoring-system-NodeMCU-ESP-8266MOD-Code
  
<b>Screenshoot : </b><br>

![1](https://user-images.githubusercontent.com/21225215/39982715-0bea3f12-5777-11e8-9745-6b90d0f02f3d.png) ![2](https://user-images.githubusercontent.com/21225215/39982716-0c2bc6f8-5777-11e8-8705-cf9fc1ab1aca.png) ![3](https://user-images.githubusercontent.com/21225215/39982717-0c7d6b8e-5777-11e8-9313-b803659d2444.png) ![4](https://user-images.githubusercontent.com/21225215/39982718-0cbcb62c-5777-11e8-94f9-30f930db1973.png) ![5](https://user-images.githubusercontent.com/21225215/39982719-0d41f4a4-5777-11e8-9279-36912e4c69bc.png) ![6](https://user-images.githubusercontent.com/21225215/39982721-0da20b28-5777-11e8-8047-9b339fc6529b.png) ![7](https://user-images.githubusercontent.com/21225215/39982722-0de0d1d2-5777-11e8-96f9-a17d74b7615d.png) ![8](https://user-images.githubusercontent.com/21225215/39982723-0e208a48-5777-11e8-8610-69191fbaac00.png) ![9](https://user-images.githubusercontent.com/21225215/39982725-0e5ebca0-5777-11e8-837e-b2b1b38cbc76.png) ![10](https://user-images.githubusercontent.com/21225215/39982726-0e9bc122-5777-11e8-9600-583d19afccff.png)
