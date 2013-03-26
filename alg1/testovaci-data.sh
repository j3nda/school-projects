#!/bin/bash

fn="main";
    [ ! -r "./${fn}" ] || [ ! -x "./${fn}" ] && { echo "ERROR: binary-file(${fn}) is not found or execute!"; exit; }

./${fn} 1
./${fn} 2
./${fn} 3
./${fn} 4
./${fn} 5
./${fn} 6
./${fn} 7
./${fn} 8
./${fn} 9
./${fn} 6263
./${fn} 10000
./${fn} 123123
