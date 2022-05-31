FROM public.ecr.aws/docker/library/tomcat:9.0.59-jdk11-openjdk
WORKDIR /usr/local/tomcat/webapps
ADD target/JinsparkLab-1.0.war .
RUN rm -rf /usr/local/tomcat/webapps/ROOT
CMD ["catalina.sh", "run"]