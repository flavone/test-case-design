#!/bin/sh
WORK_DIR="/mwbase/test_design"
JAR_NAME="test-design-0.0.1-SNAPSHOT.jar"
cd  ${WORK_DIR}
ps -ef |grep test-design |grep -v grep |cut -c 9-15 |xargs kill -s 9
sleep 3s
nohup java -jar ${WORK_DIR}/${JAR_NAME} 1>>test_design.log 2>&1 &
