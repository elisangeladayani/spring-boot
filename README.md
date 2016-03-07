# spring-boot-web
Example Web Application


# Docker images
Go to folder *docker-images*

Execute *docker-compose up*

##Redis
**port**: 6379
**password**: segredo

##RabbitMQ
**port**: 5672
**admin.port**: 15672
**user**: dojo
**password**: dojo
**vhost**: dojo\_vhost

##MySQL
**port**: 3306
**user**: dojo
**password**: dojo
**database**: dojo

##MongoDB
**port**: 27017
**port**: 27017

##Minio
**ui.port**: 9001

**port**: 9000

**accessKey**: N4BEIOH6PAHSG25I4TFI

**secretKey**: YN65o85VPABXYzYHbs4aNPdGotLknXmKokz91+3m

**region**: us-east-1

##Remote Shell

Use `ssh dojo@localhost -p 2000`

password: dojo

##Metrics And Healthcheck

__health:__ http://localhost:8080/system/health

__metrics:__ http://localhost:8080/system/metrics

##Config Properties

__config props:__ http://localhost:8080/system/configprops

##HTTP-JMX

http://localhost:8080/system/jolokia


