#!/bin/bash

# Sometimes I have the urge to look up nonsense on my phone while doing
# homework which can lead to a downward spiral of procrastination.
# That's why I made a script that gets the top twitter trends at the moment
# and prints them to terminal. Mildly entertaining, and minimal procrastination.


# tweetPage is where the the below website will be downloaded and stored
# touch it first to see if it is there, or else it will be created
# rm it because a new one needs to be made if it is already there
# makes a fresh tweetPage ready to be filled

touch tweetPage
rm tweetPage
wget -O tweetPage http://tweeplers.com/hashtags/?cc=US

declare -a ALLTAGS

# opens file and reads all the lines into an array which will house 
# all the tags from the web page, and then closes the file.
exec 10<&0
fileName="tweetPage"
exec < $fileName
let count=0

while read LINE; do
    ALLTAGS[$count]=$LINE
    ((count++))
done

exec 0<&10 10<&-

# Searches for the btags in tweetPage, this is where the trending twitter hastags
# are stored, using regex. Then these btags will go into an array.
regex="<b>[[:print:]]*<\/b>"

touch btags
rm btags
touch btags

ELEMENTS=${#ALLTAGS[@]}
firstLine=0
for((i=0;i<$ELEMENTS;i++)); do
   if [[ ${ALLTAGS[${i}]} =~ $regex ]]; then
      if [[ $firstLine<1 ]]; then
         echo ${BASH_REMATCH[0]} > btags
         let firstLine=$firstLine+1
      else
	 echo ${BASH_REMATCH[0]} >> btags
      fi
   fi
done

# Puts all the btags in an array.
declare -a BARRAY
fileName="btags"

exec 10<&0
exec < $fileName

let count2=0

while read LINE; do
   BARRAY[$count2]=$LINE
   ((count2++))
done

exec 0<&10 10<&-

# Trending hastags will be put in a file called trendy. 
# the BARRAY will be iterated through and regex will be used to find the 
# hashtag and trend.

touch trendy
rm trendy
touch trendy

regex="[\#][0-9a-zA-Z\:\/\.]+"

ELEMENTS2=${#BARRAY[@]}
firstLine2=0

for((i=0;i<$ELEMENTS2;i++)); do
   if [[ ${BARRAY[${i}]} =~ $regex ]]; then
     if [[ $firstLine2<1 ]]; then
        echo ${BASH_REMATCH[0]#<} > trendy
        ((firstLine2++))
     else
        echo ${BASH_REMATCH[0]#<} >> trendy
     fi
   fi
done

# Concatenates trendy and prints it out to terminal for user. 
cat trendy
