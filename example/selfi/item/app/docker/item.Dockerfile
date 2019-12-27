FROM registry.adavliatov.net/ubuntu:xenial
MAINTAINER contest-dev@adavliatov-team.ru

ENV LANG=ru_RU.UTF-8

RUN locale-gen ru_RU.UTF-8 en_US.UTF-8 \
    && update-locale LANG=ru_RU.UTF-8

# It does not work due the bug in Ubuntu: https://stackoverflow.com/a/42344810
#RUN echo "Europe/Moscow" > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata
RUN ln -fs /usr/share/zoneinfo/Europe/Moscow /etc/localtime \
    && dpkg-reconfigure -f noninteractive tzdata

RUN apt-get update -qq \
    && apt-get install --allow-change-held-packages --allow-unauthenticated -y \
        repo-search-stable \
        repo-contest-stable \
        apt-utils \
        bash-completion \
        config-adavliatov-friendly-bash \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

RUN apt-get update -qq \
    && apt-get install --allow-change-held-packages --allow-unauthenticated -y \
        curl \
        less \
        vim \
        wget \
        mc \
        net-tools \
        netcat \
        openssh-client \
        rsync \
        strace \
        sudo \
        tcpdump \
        host \
        telnet \
        traceroute \
        vim-tiny \
        htop \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Для мастера
# TODO не забыть настроить логи
RUN apt-get update -qq \
    && apt-get install --allow-change-held-packages --allow-unauthenticated -y \
#   пакеты из кондуктора (возможно есть нужные и ненужные)
    libbadger-perl \
    libcss-inliner-perl \
    libhtml-query-perl \
    libhtml-tree-perl \
    \
#   нужные пакеты
        adavliatov-jdk8 \
        pandoc \
        dos2unix \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

RUN mkdir -vp \
    /etc/adavliatov/edu/role/ \
    /var/lib/adavliatov/edu/ \
    /var/run/adavliatov/edu/ \
    /var/log/adavliatov/edu/ \
    /var/spool/adavliatov/edu/role/ \
    /var/spool/adavliatov/edu/role/tmp \
    /var/tmp/adavliatov/edu/role/ \
    /usr/lib/adavliatov/edu/role/lib/ \
    /usr/lib/adavliatov/edu/role/bin/ \
    /usr/lib/adavliatov/edu/role/bin/include \
    /usr/bin/adavliatov/edu


COPY build/libs/role-app.jar /usr/lib/adavliatov/edu/role/lib/

# jmx remote port / role admin script port / lockservice jetty port
EXPOSE 22513 22540 22550
# debug port
EXPOSE 22510
# z-console
EXPOSE 22530
# public jetty port / private jetty port
EXPOSE 80 85

#CMD OR ENTRYPOINT ???
#смотри файл install-ubic-config.sh: ulimits и прочее
ENV SELFI_TODAY_ITEM_ENV development

#скорее всего нужно будет заменить на массив со строки
#чтобы работали stop hook - https://wiki.adavliatov-team.ru/qloud/doc/environment/component/#stop-hook
CMD ulimit -n 8000 && \
    /usr/local/java8/bin/java -cp $(echo /usr/lib/adavliatov/edu/role/lib/*.jar | tr ' ' ':') \
    -Xmx$(test ${SELFI_TODAY_ITEM_ENV} = development && echo 4G || echo 8G) -Xms256m \
    -Djava.net.preferIPv4Stack=false -Djava.net.preferIPv6Addresses=true \
    -Xdebug -Xnoagent -Djava.compiler=NONE \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=22510 \
    -showversion -server -XX:+UseParallelGC -XX:-UseGCOverheadLimit \
    -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/tmp/adavliatov/edu/role \
    -Xloggc:/var/log/adavliatov/edu/role.gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps \
    -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDateStamps \
    -Dnetworkaddress.cache.ttl=60 \
    -Dsun.net.client.defaultConnectTimeout=5000 -Dsun.net.client.defaultReadTimeout=5000 \
    -Dcom.sun.management.jmxremote.port=22513 \
    -Dcom.sun.management.jmxremote.authenticate=false \
    -Dcom.sun.management.jmxremote.ssl=false \
    -Dadavliatov.environment.type=${SELFI_TODAY_ITEM_ENV} \
    ru.adavliatov.role.app.RoleAppKt -Dlog.output=
