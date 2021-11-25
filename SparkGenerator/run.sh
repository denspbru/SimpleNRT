../../spark-3.0.3-bin-hadoop3.2/bin/spark-submit \
--master local \
--deploy-mode client \
--class org.denspbru.generators.Simple \
--name SimpleGenerator \
--jars ../lib/spark-sql-kafka-0-10_2.12-3.0.3.jar,../lib/spark-streaming-kafka-0-10_2.12-3.0.3.jar,../lib/kafka-clients-3.0.0.jar,../lib/spark-token-provider-kafka-0-10_2.12-3.0.3.jar,../lib/commons-pool2-2.11.1.jar,../lib/config-1.4.1.jar \
--driver-memory 1024M \
--driver-cores 1 \
--executor-memory 1G \
../lib/sparkgenerator_2.12-0.1.jar \
../data/generator/generator.conf