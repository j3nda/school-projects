#!/bin/bash
# Karel.pm, Prikazovy Manazer, (c) 1999, MCH
#

titulek_okna="Karel, Prikazovy Manazer"
titulek_menu="Seznam prikazu"
okno_xmax=60
okno_ymax=20
okno_zobraz=14	#Pocet radek, ktere se zobrazi.
		#POZOR! Nesmi byt vetsi jak vyska okna!!!
		#Doporucuje se: okno_zobraz=okno_ymax-6
		
end_of_menu="!KONEC_VOLBY_MENU!"

temp1=$HOME"/karel.pm.menu.temp.1"	#Cteni
temp2=$HOME"/karel.pm.menu.temp.2"	#Cteni
temp3=$HOME"/karel.pm.menu.temp.3"	#Hlavni zdroj dat
temp4=$HOME"/karel.pm.menu.temp.4"	#Pro spusteni menu
eof="KONEC-SOUBORU-BORIVOJI"
max_cmd=1

pwd>$temp1
actdir=`cat $temp1`
temp0=$actdir"/karel.cmd"

make_zdroj_dat () {
cp $temp0 $temp1
echo $eof>>$temp1
echo "# (c) 1999, MCH">$temp3
echo "# Hlavni zdroj dat...">>$temp3
i=0
a0=""
a1=""
until test "$a1" = "$eof"
do
    a0=$a1
    read a1 a2 <$temp1
    if test "$a1" != "#"
    then
	if test "$a2" = ""
	then
    	    aa=0
	else
	    if test "$a0" != "$a1"
	    then
		i=`expr $i + 1`
		echo " ${i} \"${a1}\"">>$temp3
	    fi
	fi
    fi
    sed '1d' $temp1 >$temp2
    cp $temp2 $temp1
done
echo $eof>>$temp3
max_cmd=$i
}

gen_menu_file () {
cp $temp3 $temp1
echo "#!/bin/bash">$temp4    
echo "dialog --title \"$titulek_okna\" --menu \"$titulek_menu\" $okno_ymax $okno_xmax $okno_zobraz \\">>$temp4
a1="Q"
pp=0
until test "$a1" = "$eof"
do
    read a1 a2 a3 <$temp1
    sed '1d' $temp1 >$temp2
    cp $temp2 $temp1
    if test "$a1" != "#"
    then
        if test "$a1" != "$eof"
	then	
	    echo " $a1 $a2 \\">>$temp4
	fi
    fi
done
echo "2>>$temp1">>$temp4
chmod +x $temp4
}

run_menu_file () {
volba=""
. $temp4
volba=`cat $temp1`
}

get_index_cmd () {
cp $temp3 $temp1
a1="Q"
pp=0
until test "$a1" = "$eof"
do
    read a1 a2 a3 <$temp1
    sed '1d' $temp1 >$temp2
    cp $temp2 $temp1
    if test "$a1" != "#"
    then
        if test "$a1" != "$eof"
	then
	    if test "$volba" = "$a1"
	    then
		sel_cmd=$a2
		echo $sel_cmd>$temp1
		cat $temp1 | tr \" "#" >$temp2
		sed 's/\#//g' $temp2 >$temp1
		sel_cmd=`cat $temp1`
		break
	    fi
	fi
    fi
done
}

ibox_proc () {
dialog --title "$iboxt" --inputbox "$ibox" 7 60 2>$temp1
ibox=`cat $temp1`
}

add_edit_delete_menu () {
dialog --title "Prikazovy Manazer" --menu "Pridej, Zrus:" 12 30 2 \
1 "Pridej prikaz" 2 "Zrus prikaz" 2>$temp1
add_edit=`cat $temp1`
}

pridej_cmd () {
ibox=""
iboxt="Pridej prikaz [syntax]"
ibox_proc
cmd_syn=$ibox
ibox=""
iboxt="Pridej prikaz [Napoveda]"
ibox_proc
cmd_hlp=$ibox
echo>>$temp0
echo "+${cmd_syn}+ $cmd_hlp">>$temp0
until test $ibox = "KONEC"
do
    ibox=""
    iboxt="Pridej prikaz [Elementarni prikaz], pro konec: k,K,KONEC"
    ibox_proc
    case $ibox in
    k | K | KONEC) ibox="KONEC";;
    *) echo "+${cmd_syn}+ $ibox">>$temp0
    esac
done
make_zdroj_dat
}

zrus_cmd () {
if test $max_cmd -eq 1
then
    dialog --msgbox "Bohuzel, ale nelze rusit 1. prikaz!" 7 30
else
    grep -v "${sel_cmd}" $temp0 >$temp1
    cp $temp1 $temp0
    make_zdroj_dat
fi
}

make_zdroj_dat
sel_cmd="x"
until test "$sel_cmd" = ""
do
    sel_cmd=""
    gen_menu_file
    run_menu_file
    get_index_cmd
    if test "$sel_cmd" != ""
    then
	add_edit_delete_menu
	case $add_edit in
	1) pridej_cmd;;
	2) zrus_cmd;;
	esac
    fi
done

rm $temp1
rm $temp2
rm $temp3
rm $temp4
