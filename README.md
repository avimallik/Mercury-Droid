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
