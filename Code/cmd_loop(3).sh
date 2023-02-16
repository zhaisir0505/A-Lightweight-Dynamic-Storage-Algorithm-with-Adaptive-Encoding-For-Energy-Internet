#!/bin/bash
for num in `seq 4 5`
do
    cmd_res=`./encoder "/home/njupt1/zhai/C/Simple-Regenerating-Codes-master/SRC_files/Coding/256.mp4" 6 2 cauchy_orig 8 $num 102400`
    values=`echo $cmd_res | grep -Po "\d+\.\d+"`
    echo $values | awk '{print $1}' >> time_value_file.txt
    echo $values | awk '{print $2}' >> speed.txt
    echo "progress: $num / 10000"
done