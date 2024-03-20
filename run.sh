#!/bin/bash

if find . -name "*.class" -print | grep -q '.'; then
    rm *.class
fi

cd '.\implementations' 

if find . -name "*.class" -print | grep -q '.'; then
    rm *.class
fi

cd '..'

javac BankSystem.java
java BankSystem