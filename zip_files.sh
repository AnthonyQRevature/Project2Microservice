#!bin/bash

cat ./zip.lst | while read line 
do
    if [[ $line = *[![:space:]]* ]]
    then
        echo "$line" | zip project.zip -@
    fi
done
