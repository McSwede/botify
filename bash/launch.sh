heap_size=$(grep -w "botify.preferences.max_heap_size" src/main/resources/application.properties|cut -d'=' -f2)
if [ -z "$heap_size" ]
then
  tmux new -d -s botify java -jar build/libs/botify-1.0-SNAPSHOT.jar
else
  tmux new -d -s botify java -Xmx"$heap_size" -jar build/libs/botify-1.0-SNAPSHOT.jar
fi
