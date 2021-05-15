# A* Search

### How It Works
* Implementation of A* Search to find the shortest path between individuals.
* Linear distance was used as the heuristic function.
* The input file **fixed_question_data.txt** augments each individual in the social network with latitude and longitude information to indicate their location on Earth.

### Compile Instructions

    javac Edge.java

    javac Node.java

    javac Assign3Search.java

    jar cmf Assign3Search.mf Assign3Search.jar Assign3Search.class Node.class Edge.class Assign3Search\$1.class

    java -jar Assign3Search.jar <inputfile> <startname> <endname>
