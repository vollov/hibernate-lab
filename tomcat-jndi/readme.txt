This app demo set hibernate with jndi on tomcat
remote debug 
catalina.bat jpda start
http://localhost:8080/tomcat-jndi/GetEmployeeByID?empId=1

## eclipse project settings
mvn -Dwtpversion=2.0 eclipse:eclipse


Tomcat JNDI DataSource Configuration

For configuring tomcat container to initialize DataSource, we need to make some changes in tomcat server.xml and context.xml files.

server.xml
<Resource name="jdbc/MyLocalDB"
      global="jdbc/MyLocalDB"
      auth="Container"
      type="javax.sql.DataSource"
      driverClassName="com.mysql.jdbc.Driver"
      url="jdbc:mysql://localhost:3306/sandbox"
      username="pankaj"
      password="pankaj123"
       
      maxActive="100"
      maxIdle="20"
      minIdle="5"
      maxWait="10000"/>
      
Add above resource in the server.xml GlobalNamingResources element.     
      
context.xml
<ResourceLink name="jdbc/MyLocalDB"
              global="jdbc/MyLocalDB"
              auth="Container"
              type="javax.sql.DataSource" />
              
Add above ResourceLink in the context.xml file, it’s required so that applications can access the JNDI resource with name jdbc/MyLocalDB.

Just restart the server, you should not see any errors in the tomcat server logs. If there are any wrong configurations, such as password is wrong, you will get the corresponding exception in the server log.

You also need to make sure that MySQL driver jar file is inside the tomcat lib directory, otherwise tomcat will not be able to create database connection and you will get ClassNotFoundException in logs.


see:
https://tomcat.apache.org/tomcat-5.5-doc/jndi-datasource-examples-howto.html

	
<Context>

    <!-- maxActive: Maximum number of dB connections in pool. Make sure you
         configure your mysqld max_connections large enough to handle
         all of your db connections. Set to -1 for no limit.
         -->

    <!-- maxIdle: Maximum number of idle dB connections to retain in pool.
         Set to -1 for no limit.  See also the DBCP documentation on this
         and the minEvictableIdleTimeMillis configuration parameter.
         -->

    <!-- maxWait: Maximum time to wait for a dB connection to become available
         in ms, in this example 10 seconds. An Exception is thrown if
         this timeout is exceeded.  Set to -1 to wait indefinitely.
         -->

    <!-- username and password: MySQL dB username and password for dB connections  -->

    <!-- driverClassName: Class name for the old mm.mysql JDBC driver is
         org.gjt.mm.mysql.Driver - we recommend using Connector/J though.
         Class name for the official MySQL Connector/J driver is com.mysql.jdbc.Driver.
         -->
    
    <!-- url: The JDBC connection url for connecting to your MySQL dB.
         -->

  <Resource name="jdbc/sandboxDB" auth="Container" type="javax.sql.DataSource"
               maxActive="100" maxIdle="30" maxWait="10000"
               username="root" password="javadude" driverClassName="com.mysql.jdbc.Driver"
               url="jdbc:mysql://localhost:3306/sandbox"/>

</Context>

		
3. web.xml configuration

Now create a WEB-INF/web.xml for this test application.

		
	
<web-app xmlns="http://java.sun.com/xml/ns/j2ee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
    version="2.4">
  <description>MySQL Test App</description>
  <resource-ref>
      <description>DB Connection</description>
      <res-ref-name>jdbc/TestDB</res-ref-name>
      <res-type>javax.sql.DataSource</res-type>
      <res-auth>Container</res-auth>
  </resource-ref>
</web-app>              