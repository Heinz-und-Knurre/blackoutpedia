[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
# BlackoutPedia
## Overview
The purpose of BlackoutPedia is to provide a platform-independant software solution that allows users to browse 
Wikipedia content without internet connection.

Therefore BlackoutPedia processes dumps of the Wikipedia text articles into a searchable local index and provides 
access to these pages through a simple but powerful browser interface.

## Prerequisites
Wikipedia provides regular [dumps](https://dumps.wikimedia.org/) of their text content. 

BlackoutPedia requires a XML based dumps that is bzip2 compressed in order to build its local index. Please make sure
download a suitable dump before starting to use BlackoutPedia.

Those dumps are provided in various languages, such as:
- [English](https://dumps.wikimedia.org/enwiki)
- [German](https://dumps.wikimedia.org/dewiki)
- [French](https://dumps.wikimedia.org/frwiki)
- and other languages

Please be aware, that not all language dumps do contain all articles. Then english dump is by far the richest in number 
of articles.
  
When choosing a dump for download, make sure you download the file which name ends
```
-pages-articles.xml.bz2
```
such as the following examples for the languages mentioned above
```
enwiki-20170801-pages-articles.xml.bz2
dewiki-20170801-pages-articles.xml.bz2
frwiki-20170801-pages-articles.xml.bz2
```
There are also smaller dumps for [testing](https://dumps.wikimedia.org/testwiki) if you want to quickstart with a 
limited dump file size and a limited amount of articles (which are of no value if comes to content).

Besides the proper dump file you will need enough disk space. The following table shows the disk space requirements for
the loaded index during our performance testing

| Language | Dump size (GB) | Index size (GB) |
| -------- | -------------- | --------------- |
| English  | 14.4           | 42.7            |
| German   | 4.4            | 14.2            |

As a rule of thumb, you should have free disk space of 4 times the size of the dump to be imported. 

##Performance
The following table shows our performance results when loading a dump into the local index of BlackoutPedia using 
Java 8 Update 141 on a 7th generation Intel Core i5

| Language | Dump size (GB) | # Articles (Mio)| Time (min) |
| -------- | -------------- | --------------- | ---------- |
| English  | 14.4           | 17.7            | 151        |
| German   | 4.4            | 4.1             | 52         |
## Build
The minimum requirements for the build are:
- [Java](http://www.oracle.com/technetwork/java/javase/downloads) JDK 8 Update 144
- [Maven](https://maven.apache.org) 3.5

Ensure that environment variables and path settings are properly configured by running the following commands to verify to minimum versions are available
```
mvn -v
java -version
```

Then clone the git repository to a local working directory
```
git clone https://github.com/christiandmiddel/blackoutpedia.git /Work/blackoutpedia
```

And from that working directory start the build
```
mvn clean install
```

You will find the result of the build as
```
target/blackoutpedia.jar
```

## Usage
The following shows the general command line arguments supported
```
-b (--bind) VAL   : bind address, defaults: 127.0.0.1 (default: 127.0.0.1)
-d (--dump) PATH  : wikipedia dump
-i (--index) PATH : index directory
-p (--port) N     : http port, defaults: 8080 (default: 8080)
```
An index directory must be specified in any case. A dump file must only be specified for the initial loading of the 
local index. If the index directory already contains pages, the dump file is ignored and no loading of the index 
is done.

After the index loading or based on the existing index content, the local HTTP server is started 
listening on the specified port on the given bind address. 

A java main class is properly defined in the resulting JAR archive, so that the software can be started using:
```
-jar blackoutpedia.jar
```

As of later versions of Java 7 the SAX Parser used in order to process the dump into the local index is by default 
limited to certain sizes of XML documents. This must be adjusted by using this parameter:
```
-Djdk.xml.totalEntitySizeLimit=2147483647
```

The parameter turns up the supported XML file size to maximum. 

To summarize, the following command would properly start the software:
```
java -Djdk.xml.totalEntitySizeLimit=2147483647 -jar blackoutpedia.jar --index /path/to/local/index --dump /path/to/local/dump/enwiki-20170801-pages-articles.xml.bz
```

Once started during the index load procedure the console will show log messages indicating the progress
```
progress=1%, eta(min)=151, with pageCount=17080
progress=2%, eta(min)=147, with pageCount=40459
```

This will tell you how many drinks you can have prior to being able to surf the content.