FROM openjdk:jre-alpine

LABEL com.tingcore.cdc-api.name="cdc-api" \
      com.tingcore.cdc-api.author="Fortum Sverige AB"

ADD build/libs /app
ADD docker-bin /app

RUN apk upgrade --update && \
    apk add --no-cache --virtual=build-dependencies bash && \
    chown -R daemon:daemon /app && \
    chmod -R 0777 /app && \
    chmod 0500 /app/start.sh && \
    rm -rf $JAVA_HOME/bin/jjs \
           $JAVA_HOME/bin/orbd \
           $JAVA_HOME/bin/pack200 \
           $JAVA_HOME/bin/policytool \
           $JAVA_HOME/bin/rmid \
           $JAVA_HOME/bin/rmiregistry \
           $JAVA_HOME/bin/servertool \
           $JAVA_HOME/bin/tnameserv \
           $JAVA_HOME/bin/unpack200 \
           $JAVA_HOME/lib/ext/nashorn.jar \
           /tmp/* /var/cache/apk/* \
           /app/*.jar.original

EXPOSE 8080
WORKDIR /app
USER daemon

CMD /app/start.sh
