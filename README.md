# shoebox

##### An app that allows users to keep track of local shoe stores. Date of current version: 09/04/15

#### By **Diana Holland**

## Description

In this app, you can showcase local brands of shoes and keep track of the stores that carry them. Stores can carry multiple brands of shoes, and brands can be sold in multiple stores.

## Setup

* Download and intall the [Java Standard Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Download and install the [Java Runtime Environment](http://www.java.com/en/)
* Run ```$ java -version``` to make sure everything's installed correctly
* To manage this project's dependencies, download and install [Gradle](https://gradle.org/) . If you're using Homebrew, just type ```$ brew install gradle```
* To run this project, first setup the local database (instructions below). Then connect to this folder in terminal and type ```gradle run```.

**To use the database included with this file, follow these instructions:**

* Clone this git repository on your computer ```git clone https://github.com/theholla/shoe-store.git```
* In terminal, start the PostgreSQL server by typing ```postgres``` and ```psql```
* In psql, create a new database by typing ```CREATE DATABASE shoe_stores;```
* In another tab in terminal, connect to this project folder: ```cd path/to/this/folder```
* In this tab, dump the database information from my sql file into your local database by typing the following code: ```psql shoe_stores < shoe_stores.sql```

* To run tests, you can create a new test database in psql: ```CREATE DATABASE shoe_stores_test WITH TEMPLATE shoe_stores;```

## Database Schema

<img src="/src/main/resources/public/img/sql_schema.png">

## Technologies Used

Java, Apache Spark and Velocity templates, PostgreSQL Database, and a little help from Bootstrap.

### Legal

Copyright (c) 2015 **Diana Holland**

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
