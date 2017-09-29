[![Build Status](https://travis-ci.org/FenixEdu/fenixedu-commons.png?branch=master)](https://travis-ci.org/FenixEdu/fenixedu-commons)

fenixedu-commons
===============

This project aggregates a set of common useful utilities, tools and libraries that will 
help to simplify your code or avoid repeating typical lines. This project is broken up 
into smaller modules you should incorporate individually into your project depending on 
your needs. In general, it is not useful to incorporate each fenixedu-commons module, 
because they have a broad range, from internationalization tools to spreadsheet processing.


## fenixedu-commons-stream

A set of methods to assist in converting streams into json arrays and to transform objects
into json objects.


## fenixedu-commons-i18n

An implementation of a LocalizedString, in other words a i18n String object. This is very 
usefull for applications that need to represent the same information in multiple languages.


## fenixedu-commons-configuration

A tool and interfaces to help bootstrap a projects configuration using annotations.


## fenixedu-commons-spreadsheet

A tool to simplify the construction of spreadsheets. The output can by set o Excel and/or
CSV, TSV, ...
