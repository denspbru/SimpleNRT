# SimpleNRT
Simple near real time reporting

# Content 

**SparkGenerator** - simple spark stream generator

**lib** - necessary libraries and dependencies

**docker** - docker-comppose files with conteiners configurations and necessary configuration files.

**data** - streams configuration files

# Install

Install Docker.

Setup docker bridge network:

``docker network create -d bridge my-net``

Check that nework with name **my-net** exists:

``docker network ls``

Download Spark 3.0.3 https://spark.apache.org/releases/spark-release-3-0-3.html

extract files to folder in local file system and set path to spark-submit in firt line of ./SparkGenerator/run.sh file

# Run

Go to  folder **docker**:

``cd ./docker``

Start Druid enviroment :

``docker-compose -f docker-compose-druid.yml up``


Start SuperSet enviroment from :

``docker-compose -f docker-compose-superset.yml up``

Create topic **test_topic**:

``docker exec -it kafka kafka-topics.sh --create --topic test_topic --bootstrap-server localhost:9092``

Co to folder **SparkGenerator**

``cd ../SparkGenerator``

Launch generator:

``./run.sh``

Open **Druid** console:

``http://localhost:8888``

Open **SuperSet**:

``http://localhost:8088``