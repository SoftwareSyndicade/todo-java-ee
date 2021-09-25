FROM tomcat:10.0.11-jre11
EXPOSE 8080
COPY build/libs/*.war /usr/local/tomcat/webapps