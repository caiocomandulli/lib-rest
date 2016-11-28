# REST Android Library

REST was designed to make rest requests more simple and within a standard.

It assumes that all requests use the four basic HTTP methods: 

GET for reading and listing data.

POST for creating new data.

PUT for updating data.

DELETE for deleting data.

All server responses use JSON as its body encoding. Always respecting that at the root
you have a code property, with the status code of the request, a message, describing the status code
and a content with the true content body encoded in json.

An example of a content body of a response.

```json
{"code":200,
"message":"Item created.",
"content":{
    "Identification":1
    }
}
```

## Usage
 
### Making a Request.
 
Initialize the singleton before making requests.
 
```java
 NetworkRequestQueue.getNetworkState(this);
 ```
 
At this point you can set the default server path of your application, this will be appended at the beginning of the url of all requests.
 
```java
 RestRequest.setDefaultServerPath("example.com/examples");
 ```
 
Or your own custom User Agent...

```java
 VolleyRequest.setUserAgent("MyLovelyUserAgent");
 ```
 
Or even any custom header you will need at every request.

```java
 VolleyRequest.addHeader("AnotherCoolHeader", "ThisHeaderIsCool");
 ```

If you request has query parameters you can easily add then using RequestParams.

```java
 RequestParams parameters = new RequestParams();
 parameters.addQueryParam("query", "thisisaquery");
 ```

Then you can instantiate your request, defining a HTTP Method and a path. (See that I pass the query as a parameter as well)

```java
RestRequest request = new RestRequest(RequestMethod.POST, "/create", parameters);
```

If your request has a body, add it as a JSONObject using the addContent method.

```java
JSONObject json = new JSONObject();
json.put("message", "Hello World!");
request.addContent(json);
```

Now all its left is to execute it.

```java
request.execute();
```

### Handling the response.

More important than just making a request is to handle what comes back.

At a lower level we can build a callback that handles the response,
it translates the property "Identification" in the "content" part of our response,
and returns it back to another callback. (Note the returnCallback passed as parameter.)

Also we assume that an unauthorized request can happen, and handle it by checking if the
status code is HTTP_UNAUTHORIZED (The integer 401).

```java
RequestCallback callback = new RequestCallback(returnCallback) {
   @Override
   public void onResponse(RequestResponse response) {
       if (response != null) {
           if (response.getCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
               returnCallback.onUnauthorized();
           } else {
               JSONObject json = response.getContent();
               if (json != null) {
                   try {
                       returnCallback.onReturn(json.getInt("Identification"));
                   } catch (JSONException e) {
                       returnCallback.onInternalError();
                   }
               } else {
                   returnCallback.onInternalError();
               }
           }
       } else {
           returnCallback.onInternalError();
       }
   }
}
```

For this callback to be used, we need to execute the request passing it as parameter.

```java
request.execute(callback);
```

Now at a higher level in your application you can use the ReturnCallback implementation.
It assumes that the response has already been handled and translated to the generic type
inferred in the declaration.

```java
ReturnCallback<Integer> returnCallback = new ReturnCallback<Integer>() {
    @Override
    public void onReturn(Integer value) {
        Log.d("DEBUG", "Got my id:" + value);
    }

    @Override
    public void onUnauthorized() {
        Log.d("DEBUG", "I was not authorized to do this request!");
    }
}
```

By passing this callback into the constructor of our previous callback we create a chain of behaviours,
at a lower level we define only the behaviour that will translate the raw response into our desired object,
or will call the proper callback that indicates the status of this response.

At a higher level we define only what we will do with the final value or at a specific status, and by passing the
higher one as parent of the lower one we establish a chain of behaviours.

### Available status.

Unauthorized (401) usually means when authentication is required and has failed or has not yet been provided.

```java
public void onUnauthorized();
```

Invalid Request (400) usually means that the server cannot or will not process the request due to an apparent client error.

```java
public void onInvalidRequest();
```

Forbidden (403) usually means that the request was a valid request, but the server is refusing to respond to it. The user might be logged in but does not have the necessary permissions for the resource.

```java
public void onForbidden();
```

Conflict (409) usually means  that the request could not be processed because of conflict in the request.

```java
public void onConflict();
```

When the request turns out to have no connection.

```java
public void onNoConnection();
```

Request Timeout (408) usually means the server took too long to produce any response.

```java
public void onTimeout();
```

Internal Error (5XX) usually indicates that the server is aware that it has encountered an error or is otherwise incapable of performing the request.

Also used as a fallback when any other error is not handled.

```java
public void onInternalError();
```

And at any status, always() is always called.

```java
public void always();
```

## Install Library

__Step 1.__ Get this code and compile it

__Step 2.__ Define a dependency within your project. For that, access to Properties > Android > Library and click on add and select the library

##  License

MIT License. See the file LICENSE.md with the full license text.

### Third party libraries

This library uses Volley by Android.

## Compatibility

This Library is valid for Android systems from version Android 4.4 (android:minSdkVersion="19" android:targetSdkVersion="19").
