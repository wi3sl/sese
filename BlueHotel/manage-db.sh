#!/bin/sh
# Startup script for the HSQLDB Server

JARS=lib/hsqldb.jar

java -cp $JARS org.hsqldb.util.DatabaseManagerSwing

