#!/bin/bash

if find . -name "*.class" -print | grep -q '.'; then
    rm *.class
fi

javac BankSystem.java
java BankSystem