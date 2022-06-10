# FROM openjdk:11

# ENV SBT_VERSION 1.6.2
# RUN curl -L -o sbt-$SBT_VERSION.zip https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.zip
# RUN unzip sbt-$SBT_VERSION.zip -d ops


FROM hseeberger/scala-sbt:8u312_1.6.2_2.13.8

# For the faker generation
RUN apt-get update && apt-get install -y pip && apt-get clean
RUN python3 -m pip install faker

# WORKDIR /drone_generation
# ADD . /drone_generation
# ENTRYPOINT ["sbt"]
# CMD ["run localhost:9092 reports"]

WORKDIR /drone_generation
ADD target/scala-2.13/drone_generation-assembly-0.1.0-SNAPSHOT.jar /drone_generation
ADD report_generator.py /drone_generation
ENTRYPOINT ["scala", "/drone_generation/drone_generation-assembly-0.1.0-SNAPSHOT.jar"]
CMD ["tcp://host.docker.internal:9092", "reports"]
