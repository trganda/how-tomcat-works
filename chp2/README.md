## Application1

`Application1`实现了一个非常非常简单的`servlet`容器，并同时支持访问静态资源。

这个`servlet`容器会针对编写的`servlet`完成以下步骤：

* 当`servlet`第一次被访问时，载入并调用它的`init`方法
* 对于每一次不同的请求，会创建对应的`javax.servlet.ServletRequest`和`javax.servlet.ServletResponse`对象
* 调用`servlet`的`service`方法
* 当`servlet`不再需要时，调用`destroy`方法并卸载它

### 使用方式

运行`HttpServer`

对于静态资源，放置在当前项目的`webroot`目录下，访问

```http
http://127.0.0.1:8080/index.html
```

对于`servlet`，同样放置在`webroot`目录下，访问

```http
http://127.0.0.1:8080/servlet/com.trganda.servlet.PrimitiveServlet
```

注意需要输入全限定名称。

## Application2

作者在书中提到，`Application1`的`ServletProcessor#process`方法存在一定的安全隐患。

```java
    // ...

    Servlet servlet = (Servlet) tServlet.newInstance();
    servlet.service(request, response);
    
    // ...
```

当调用`service`方法时，传入的`request`和`response`方法的类型分别为自己实现的`Request`和`Response`，但需要的参数类型是`ServletRequest`和`ServletResponse`。
此时，`servlet`的开发者可以在编写`servlet`时，将参数强行向下转换为`Request`，并可以执行预期之外的`public`方法。

为此，需要使用`Facade`的类，它会代理`Request|Response`从`ServletRequest|ServletResponse`实现的方法，但不提供其它方法。同时将该类型的参数传递给`service`方法。

```java
    // ...

    Servlet servlet = (Servlet) tServlet.newInstance();

    RequestFacade requestFacade = new RequestFacade(request);
    ResponseFacade responseFacade = new ResponseFacade(response);
    servlet.service(requestFacade, responseFacade);
    
    // ...
```