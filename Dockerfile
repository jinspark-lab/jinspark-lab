FROM tomcat:9
WORKDIR /usr/local/tomcat/webapps
ADD target/JinsparkLab-1.0.war .
RUN rm -rf /usr/local/tomcat/webapps/ROOT
CMD ["catalina.sh", "run"]