# Recycleview-HTTP-API

HTTP Request is a common function in mobile programming. We need it to use API, to download images or download files and many other purposes. To do HTTP Request on Android, we can build our own HTTP Client, or we can use libraries built by others. One of HTTP Client libraries that you can use is this Android Asynchronous Http Client (http://loopj.com/android-async-http/). The features of the library are as follows:

* Making asynchronous HTTP requests, handling responses in anonymous callbacks
* HTTP requests happen outside the UI thread
* Requests use a threadpool to cap concurrent resource usage
* GET/POST params builder (RequestParams)
* Multipart file uploads with no additional third party libraries
* Tiny size overhead to your application, only 25kb for everything
* Automatic smart request retries optimized for spotty mobile connections
* Automatic gzip response decoding support for super-fast requests Binary file (images etc) downloading with BinaryHttpResponseHandler
* Built-in response parsing into JSON with JsonHttpResponseHandler
* Persistent cookie store, saves cookies into your app’s SharedPreferences

Why do you need this library, you ask? One of the strong points of this library is that it uses ThreadPool to call HTTP Requests. Wrong implementation of HTTP Client can clog your internet connection. It is flexible. You can modify headers and put parameters with type string or file. You can call HTTP Request get, post, put and delete. And the most significant reason is that it’s easy to use. If your applications need to download a lot of files or image, you should use FileAsyncHttpResponseHandler class. This class saves responses to files on your android device. This is really helpful to save memory. If you need other features that are not included in this library, you can add them yourself. It is an open source project after all. Really, tons of thanks to James Smith who made this library.

# Screenshot
![alt text](https://github.com/yopialfa80/Recycleview-HTTP-API/blob/master/Screenshot.png)
