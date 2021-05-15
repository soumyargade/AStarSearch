# aStarSearch

Instructions in order to compile on VCL & create an executable jar:

    javac Edge.java

    javac Node.java

    javac Assign3Search.java

    jar cmf Assign3Search.mf Assign3Search.jar Assign3Search.class Node.class Edge.class Assign3Search\$1.class

    java -jar Assign3Search.jar <inputfile> <startname> <endname>
