# SimpleSearch
A simple command line tool written in Kotlin to perform text search in the provided file directory.

### System Requirements

1. [Java](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot)

Download the latest version of Java and make sure that JAVA_HOME path is been set.

### Building the Jar 

This application uses the Gradle build tool to compile and build the project.

1. Clone this repository
2. Run `$ ./gradlew jar` command.
3. A jar will be generated which is located at /build/libs

### Running the Jar

1. Open your favourite terminal depending on the OS you are using.
2. Navigate to the jar location which is created using the above step in your terminal.
3. Run command `$ java -jar SimpleSearch-1.0-SNAPSHOT.jar <path to search>`
4. For search, the content provides the input test, and the command-line tool should be returning the results.

### For Example

```
$ java -jar SimpleSearch.jar Searcher /foo/bar
14 files read in directory /foo/bar
search> to be or not to be
file1.txt:100%
file2.txt:90%
search>
search> cats
no matches found
search> :quit
$
```
