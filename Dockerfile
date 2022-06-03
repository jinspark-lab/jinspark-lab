FROM public.ecr.aws/docker/library/tomcat:9.0.59-jdk11-openjdk
WORKDIR /usr/local/tomcat/webapps
RUN rm -rf /usr/local/tomcat/webapps/ROOT
ADD target/ROOT.war .
#RUN rm -rf /usr/local/tomcat/webapps/ROOT
CMD ["catalina.sh", "run"]