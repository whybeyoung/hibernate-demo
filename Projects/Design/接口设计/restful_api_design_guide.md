# Restful api design
---

[TOC]

 - api url前缀api/v1。

 - api中不能有动词，只能有名词，且为复数。

 - HTTP动词：
>GET（SELECT）：从服务器取出资源（一项或多项）。
POST（CREATE）：在服务器新建一个资源。
PUT（UPDATE）：在服务器更新资源（客户端提供改变后的完整资源）。
PATCH（UPDATE）：在服务器更新资源（客户端提供改变的属性）。
DELETE（DELETE）：从服务器删除资源。
HEAD：获取资源的元数据。
OPTIONS：获取信息，关于资源的哪些属性是客户端可以改变的。

 - 状态码：
>200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
>201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
202 Accepted - [\*]：表示一个请求已经进入后台排队（异步任务）
204 NO CONTENT - [DELETE]：用户删除数据成功。
400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
401 Unauthorized - [\*]：表示用户没有权限（令牌、用户名、密码错误）。
403 Forbidden - [\*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
404 NOT FOUND - [\*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
500 INTERNAL SERVER ERROR - [\*]：服务器发生错误，用户将无法判断发出的请求是否成功。
[状态码的完全列表见这里](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)

 - 返回结果：
>GET /collection：返回资源对象的列表（数组）
GET /collection/resource：返回单个资源对象
POST /collection：返回新生成的资源对象
PUT /collection/resource：返回完整的资源对象
PATCH /collection/resource：返回完整的资源对象
DELETE /collection/resource：返回一个空文档

 - 错误处理
error:
```
{
    "message": "Validation Failed"
}
```
success:
例如
/user/:id直接返回用户的json值
/users返回用户的数组

 - HATEOAS(Hypermedia as the engine of application state)
[Richardson 提出的 REST 成熟度模型](http://restcookbook.com/Miscellaneous/richardsonmaturitymodel/)
> - 第一个层次（Level 0）: Web 服务只是使用 HTTP 作为传输方式，实际上只是远程方法调用（RPC）的一种具体形式。SOAP 和 XML-RPC 都属于此类。
> - 第二个层次（Level 1）: Web 服务引入了资源的概念。每个资源有对应的标识符和表达。
> - 第三个层次（Level 2）: Web 服务使用不同的 HTTP 方法来进行不同的操作，并且使用 HTTP 状态码来表示不同的结果。如 HTTP GET 方法来获取资源，HTTP DELETE 方法来删除资源。
> - 第四个层次（Level 3）: Web 服务使用 HATEOAS。在资源的表达中包含了链接信息。客户端可以根据链接来发现可以执行的动作。
先实现第三个层次，如有需要暴露给除web端以外的客户端或服务时升级为第四个层次（[Spring HATEOAS ](https://projects.spring.io/spring-hateoas/)

  参考链接：
[github REST API v3](https://developer.github.com/v3/)
[RESTful API 设计指南](http://www.ruanyifeng.com/blog/2014/05/restful_api.html)
[Spring HATEOAS](https://projects.spring.io/spring-hateoas/)
[RESTful API 设计参考文献列表](https://github.com/aisuhua/restful-api-design-references)
[json api](http://jsonapi.org/)