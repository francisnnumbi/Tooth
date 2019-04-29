# Tooth
java connection to server using http url connection
This utility class for the moment accepts the following:
1. Methods *GET* and *POST*, and *GET* being a default method;
2. Parameter data as **HashMap<String, String>**

It runs on a *separate thread* and has a connection listener that listens for a response or an error.

*It does not depend on a third party library.*

### IMPLEMENTATION
```java
public void test(){
 String url = "http://api.smirl.org/tabwayane/test.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("y", "sikia bintu");
        Tooth tooth = new Tooth(Tooth.Method.POST, url, params, new OnConnectionListener(){
            @Override
            public void onResponse(String response) {
                System.out.println("RESPONSE : " + response);
            }

            @Override
            public void onError(int errorCode, String error) {
             System.out.println("ERROR : Code=" + errorCode + ", Error=" + error);
            }
        });
}
```
The purpose is to create a simple and boiled down utility.

It has four constructors that enables configuration:
1. this is a full set constructor
```java
public Tooth(Method method, String url, HashMap<String, String> data, OnConnectionListener listener)
```
2. here there is no data to be sent
```java
public Tooth(Method method, String url, OnConnectionListener listener)
```
3. here we apply the default **GET** method
```java
public Tooth(String url, HashMap<String, String> data, OnConnectionListener listener)
```
4. here we apply the default **GET** method and there is no data to be sent too.
```java
public Tooth(String url, OnConnectionListener listener)
```
