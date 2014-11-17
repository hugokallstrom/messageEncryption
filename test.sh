#!/bin/sh

JARPATH=`find $RESTLETS_LIB -name '*.jar'`
MYCLASSPATH=""
for FILE in $JARPATH; do 
  MYCLASSPATH=$MYCLASSPATH:$FILE
done

MYCLASSPATH=$MYCLASSPATH:`pwd`/build
echo "Creating messages"
java -cp "$MYCLASSPATH" ClientMain POST "Hugo" "Laboration" "Test av laboration" "Daniel"
java -cp "$MYCLASSPATH" ClientMain POST "Daniel" "Fungerar" "Det fungerar" "Hugo"
java -cp "$MYCLASSPATH" ClientMain POST "David" "Till Anders" "Hej anders!" "Anders"

echo "Retrieving message"
java -cp "$MYCLASSPATH" ClientMain GET
echo "Searching message"
java -cp "$MYCLASSPATH" ClientMain SEARCH "Test"


