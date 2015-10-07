#!/usr/bin/env bash
for i in *.csv ; do
	echo "$i";
	sed -i 's/\x0//g' "$i";
done
