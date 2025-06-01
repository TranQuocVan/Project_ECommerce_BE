FROM tomcat:10.0-jdk17

COPY target/Shoe-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/Shoe_war_exploded.war

EXPOSE 8080

# Bước 5: Chạy Tomcat
CMD ["catalina.sh", "run"]
