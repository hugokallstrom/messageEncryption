#!/bin/sh
#

JARPATH=`find $RESTLETS_LIB -name '*.jar'`
MYCLASSPATH=""
for FILE in $JARPATH; do 
  MYCLASSPATH=$MYCLASSPATH:$FILE
done
MYCLASSPATH=$MYCLASSPATH:`pwd`/build

java -cp "$MYCLASSPATH" FirstResourceMain $1 $2 $3
