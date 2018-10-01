#!/bin/bash

echo "Enter project name"
read projectName
echo "Enter groupID"
read groupID
echo "Enter artifactID"
read artifactID

mkdir $projectName
mkdir -p $projectName/src/main/java
mkdir $projectName/src/main/resources
mkdir -p $projectName/src/test/java
touch $projectName/pom.xml

echo "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"
	       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" 
	       xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\"> 

  	       <modelVersion>4.0.0</modelVersion>

	       <groupId>$groupID</groupId>
 	       <artifactId>$artifactID</artifactId>
	       <version>1</version>

		<properties>
			<maven.compiler.source>9</maven.compiler.source>
			<maven.compiler.target>9</maven.compiler.target>
			<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		</properties>

		<dependencies>
			<dependency>
				<groupId>junit</groupId>
				<artifactId>junit</artifactId>
				<version>4.12</version>
				<scope>test</scope>
			</dependency>
		</dependencies>s
	</project>" >> $projectName/pom.xml
