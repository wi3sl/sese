#!/bin/sh
# Startup script for the HSQLDB Server

JARS=lib/hsqldb.jar
DATABASE=data/blue_hotel.db

java -cp $JARS org.hsqldb.Server -database.0 file:$DATABASE

