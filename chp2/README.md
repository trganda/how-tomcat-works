## Application1

`Application1`实现了一个非常非常简单的`servlet`容器，并同时支持访问静态资源。

这个`servlet`容器会针对编写的`servlet`完成以下步骤：

* 当`servlet`第一次被访问时，载入并调用它的`init`方法
* 对于每一次不同的请求，会创建对应的`javax.servlet.ServletRequest`和`javax.servlet.ServletResponse`对象
* 调用`servlet`的`service`方法
* 当`servlet`不再需要时，调用`destroy`方法并卸载它

### 使用方式

对于静态资源，放置在当前项目的`webroot`目录下，访问

```http
http://127.0.0.1:8080/index.html
```

对于`servlet`，同样放置在`webroot`目录下，访问

```http
http://127.0.0.1:8080/servlet/com.trganda.servlet.PrimitiveServlet
```

注意需要输入全限定名称。