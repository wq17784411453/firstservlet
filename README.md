## servlet简介
> java servlet是和平台无关的服务器端软件，它运行在servlet容器中。servlet容器负责servlet和客户通信以及调用servlet的方法，servlet和客户的通信采用“请求/响应”的模式。
> servlet可完成如下功能
 -创建并返回基于客户请求的动态html页面
 -创建可嵌入到现有的html页面中的部分html也米娜
 -与其他服务器资源（如数据库或基于java的应用程序）进行通信

> 注意：在新建jsp文件的时候，我们发现每次都要去other里面寻找，这太麻烦，我们可以在window-perspective-custumize-shortcuts-里面定制我们可能要新建的文件（jsp，class，html等等）

### 1.servlet的heloworld
#### 1.创建一个servlet接口的实现类。
   public class hello implements Servlet{}
#### 2.在web.xml文件中配置和映射这个servlet
   <!--配置和映射servlet  -->
    <servlet>
  	<!--给你的servlet取名，任意的-->
  	<servlet-name>hello123</servlet-name>
  	<!--指明servlet的名，（包名+类名）注意：这不是任意的-->
  	<servlet-class>com.helloworld.hello</servlet-class>
  </servlet>
  	<!--servlet和servlet-mapping是一种映射关系，一组的-->
  	<servlet-mapping>
  		<!--给你的servlet取名，和上面的servlet-name是一样的-->
  		<servlet-name>hello123</servlet-name>
  		<!--这是再浏览器中输入的访问该servlet的url，任意的-->
  		<url-pattern>/yangxiong</url-pattern>
  	</servlet-mapping>
#### 2. Servlet容器：运行servlet、jsp、filter等的软件的环境
> - 1.可以创建servlet，并调用servlet的相关生命周期方法
> - 2.jsp，filter。listenner，tag。。。。


#### 3.servlet生命周期相关的方法：以下都是由servlet容器负责调用
> - 1.构造器:第一次请求servlet时，创建servlet的实例，调用构造器。这说明servlet是单实例的!
> - 2.init方法：只被调用一次，在创建好实例后立即被调用，用于初始化当前的servlet
> - 3.service：被多次调用，每次请求都会调用service方法，实际用于响应请求的
> - 4.destory：只被调用一次，在当前servlet所在的web应用被卸载前调用，用于释放当前servlet所占的资源


#### 4.<load-on-startup></load-on-startup>参数：
    1.配置在servlet节点中
    <servlet>
  	<servlet-name>hello12</servlet-name>
  	<servlet-class>com.helloworld.secondservlet</servlet-class>
  	<load-on-startup>2</load-on-startup>
  </servlet>
    2.load-on-startup：可以指定servlet被创建的时机。若为负数，则在第一次请求时被创建。若为0或正数，则在当前web应用被servlet容器加载时创建实例，且数组越小越早被创建


#### 5.关于servlet-mapping：
> - 同一个servlet可以被映射到多个url上，即多个<servlet-mapping>元素的<servlet-name>子元素的设置值可以时同一个servlet的注册名。
> - 在servlet映射到的url中也可以使用通配符，但是只能有两种固定的格式：一种格式是：”*.扩展名“，另一种格式是以斜杠（/）开头并以（/）结尾的。

#### 6.ServletConfig：封装了servlet的配置信息，并且可以获取servletcontext对象(注意：init-param必须放在load-on-startup的前面，否则会报错)
> - 1.配置servlet的初始化参数
     <servlet>
  	<!--给你的servlet取名，任意的-->
  	<servlet-name>hello123</servlet-name>
  	<!--指明servlet的名，（包名+类名）注意：这不是任意的-->
  	<servlet-class>com.helloworld.hello</servlet-class>
  	
  	<!-- 配置servlet的初始化参数 -->
  	<init-param>
  		<!--参数名  -->
  		<param-name>user</param-name>
  		<!--参数值  -->
  		<param-value>root</param-value>
  	</init-param>
  	<init-param>
  		<param-name>password</param-name>
  		<param-value>123</param-value>
  	</init-param>
  	
  	<!--<load-on-startup>2</load-on-startup> 可以指定servlet被创建的时机 -->
  </servlet>
  
> - 2.获取初始化参数：
    getInitParameter（String name）：获取指定参数名的初始化参数
    getInitParammeterNames（）：获取参数名组成的Enumeration对象
       String user = servletConfig.getInitParameter("user");
		System.out.println("user," + user);
		Enumeration<String> names = servletConfig.getInitParameterNames();
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			String value = servletConfig.getInitParameter(name);
			System.out.println("^^" + name + " :" + value);
		}


#### 7.servletcontext接口
> - 1.可以由servletconfig获取；该对象代表当前web应用：可以认为servletcontext是当前web应用的一个大管家。可以从中获取到当前web应用的各个方面的信息。
>   - 获取当前web应用初始化参数
      设置初始化参数：
      <context-param>
  		<param-name>driver</param-name>
  		<param-value>com.mysql.jdbc.Driver</param-value>
  </context-param>
      方法：
    getInitParameter（String name）：获取指定参数名的初始化参数
    getInitParammeterNames（）：获取参数名组成的Enumeration对象

      
> - 2.servlet引擎为每个web应用程序都创建了一个对应的servlet对象，servletcontext对象被包含在servletconfig对象中，调用servletconfig.getservletcontext方法可以返回servletcontext对象的引用。
> - 3.由于一个web应用程序中所有的servlet都共享同一个servletcontext对象，所以，servletcontext对象被称之为application对象（web应用程序对象）。
    功能：获取web应用程序的初始化参数；记录日志；application域范围的属性；访问资源文件；获取虚拟路径所映射的本地路径；web应用程序之间的访问；servletcontext的其他方法。


#### 8.使用GET方式传递参数：
 > - 在浏览器地址栏中输入某个url地址或单击网页上的一个超链接时，浏览器发出的HTTP请求消息方式为GET；如果网页中的<form>表单的method属性被设置为了“GET”，浏览器提交这个form表单生成的HTTP请求消息的请求方式也为GET；使用GET请求方式给WEB服务器传递参数的格式：
   http://www.servlet.com/count.jsp?name=yx&password=123
 
> - 使用GET方式传递参数：
>   - post请求方式主要用于向web服务器程序提交form表单中的数据：form表单的method置为post；post方式将各个表单字段元素及其数据文件作为HTTP消息的实体内容发送给WEB服务器，传送的数据量要比GET方式大得多。


#### 9.如何在servlet中获取请求信息
> - 1. servlet中的service（）方法用于应答请求：因为每次请求都会调用service（）方法
     public void service(ServletRequest request, ServletResponse respose) throws ServletException, IOException{}
     ServletRequest：封装了请求信息，可以从获取到的任何请求消息
     ServletResponse ：封装了响应信息，如果想给用户什么响应，具体可以使用该接口的方法实现
     这两个接口的实现类都是tomcat服务器给予实现的，并在服务器调用service方法时传入
> - 2. ServletRequest
>   - 获取请求参数：
       String getParameterValue（String name）：根据请求参数的名字，返回参数值；若请求参数有多个值（例如checkbox），该方法只能获取到第一个提交的值
	String[] getParameterValue（String name）:根据请求参数的名字，返回请求参数对应的字符串数组
	Enumeration getParameterNmaes（）：返回参数对应的Enumeration 对象，类似于servletConfig（或servletContext）的getInitParamerterNames（）方法
	Map getParameterMap（）：返回请求参数的键值对：key：参数名，value：参数值，String数组类型
> - 3.HttpServletRequest
	是ServletRequest的子接口，针对http请求所定义。里面包含了大量获取http请求相关的方法
> - 4.ServletReponse
	封装响应信息，如果想给用户什么响应，具体可以使用该接口的方法实现。
	getWriter（）：返回getWriter对象，调用该对象的print（）方法，将把print（）中的参数直接打印到客户的浏览器上
	设置响应的内容类型：response.setContextType("applition/msword");这个的响应的内容类型就是word
	void sendRedirect（String location）：请求的重定向（此方法为httpservletreponse中定义）



      

### 1.GenericServlet
	是一个servlet，是servlet接口和servletconfig接口的实现类，但是一个抽象类，其中的service方法为抽象方法
	如果新建的servlet程序直接继承GenericServlet会使开发更简洁

### 2.htpservlet
	是一个servlet，继承自GenericServlet，针对http协议所定制
	在servlet（）方法中直接把SevletRequest和ServletResponse转为HttpServletRequest和HttpServletResponse并调用了重载的service（HttpServletRequest，HttpServletResponse）；
在service（HttpServletRequest，HttpServletResponse）获取请求方式：request.getMethod().根据请求方式创建了doXxx（）方法方法（xxx为具体的请求方式，比如doGet，doPost）
	实际开发中，直接继承HttpServlet，并根据请求方式重写doXxx（）方法接口
	好处：直接针对性的覆盖doXxx（）方法；直接使用HttpServletRequest和HttpServletResponse，不再需要强转
