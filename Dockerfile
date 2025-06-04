FROM tomcat:10.0-jdk17

COPY target/Shoe-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

# Bước 5: Chạy Tomcat
CMD ["catalina.sh", "run"]
