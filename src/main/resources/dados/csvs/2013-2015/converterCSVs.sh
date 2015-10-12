#!/usr/bin/env bash
for i in *.csv ; do
	echo "$i";
	sed -i 's/\x0//g' "$i";
done


for i in *.csv ; do
	cp "$i" "$i".bkp
	NAME=`echo "$i" |  sed  'y/ãāáǎàēéěèīíǐìōóǒòūúǔùǖǘǚǜĀÁǍÀĒÉĚÈĪÍǏÌŌÓǑÒŪÚǓÙǕǗǙǛ/aaaaaeeeeiiiioooouuuuüüüüAAAAEEEEIIIIOOOOUUUUÜÜÜÜ/'`
	echo $NAME
	mv "$i" "$NAME"
done
