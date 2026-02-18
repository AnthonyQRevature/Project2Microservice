#!bin/bash/

cat ./zip.lst | while read line 
do
    if [[ $line = *[![:space:]]* ]]
    then
        ls $line | zip project.zip -@
    fi
done
