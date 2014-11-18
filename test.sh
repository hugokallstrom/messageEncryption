#!/bin/sh

JARPATH=`find $RESTLETS_LIB -name '*.jar'`
MYCLASSPATH=""
for FILE in $JARPATH; do 
  MYCLASSPATH=$MYCLASSPATH:$FILE
done

MYCLASSPATH=$MYCLASSPATH:`pwd`/build.client
MYCLASSPATH=$MYCLASSPATH:`pwd`/build com
MYCLASSPATH=$MYCLASSPATH:`pwd`/build proto
MYCLASSPATH=$MYCLASSPATH:`pwd`/build server

echo $MYCLASSPATH
echo "Creating messages"
java -cp "$MYCLASSPATH" ClientMain POST "Hugo" "Laboration" "Test av laboration" "Daniel"
java -cp "$MYCLASSPATH" ClientMain POST "Daniel" "Fungerar" "Det fungerar" "Hugo"
java -cp "$MYCLASSPATH" ClientMain POST "David" "Till Anders" "Hej anders!" "Anders"

echo "Searching message"
java -cp "$MYCLASSPATH" ClientMain SEARCH "Test"


