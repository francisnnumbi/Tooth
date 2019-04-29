# Tooth
It is the most simplified java connection to server using http url connection
This utility class for the moment accepts the following:
1. Methods *GET* and *POST*, and *GET* being a default method;
2. Parameter data as **HashMap<String, String>**

It runs on a *separate thread* and has a connection listener that listens for a response or an error.

*It does not depend on a third party library.*

#### OnConnectionListener
It is located in *org.smirl.tooth.listener* package, it is an interface that enables listening to connection when result is returned or exception is raised from the server.
```java
public interface OnConnectionListener {

    void onResponse(String response);

    void onError(int errorCode, String error);
}
```
#### Method
It is an enum inside Tooth class. It supports so far **GET** and **POST**
```java
 public enum Method {
    GET("GET"),
    POST("POST");
    /**
     * the String value of the Method
     */
    public String value;

    Method(String method) {
        this.value = method;
    }
}
```

### IMPLEMENTATION
This is a simple example of how Tooth class can be implemented:
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
