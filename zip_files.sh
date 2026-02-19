#!/bin/sh

cat "$2" | while read line 
do
    if [[ $line = *[![:space:]]* ]]
    then
        line=`echo "$line" | xargs`
        ls $line | zip "$1" -@
    fi
done
