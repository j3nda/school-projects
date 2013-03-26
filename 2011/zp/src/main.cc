/**
 *
 *
 * zaklady programovani, projekt: "katalog automobilu pro autobazar"
 * (c) <jan@smid.sk>, SMI051, Jan Smid, FEI - komb., 1. rocnik, LB1IKT04K
 *
 *
 */


#include <stdio.h>
#include <stdlib.h>

#include <iostream>
#include <fstream>
#include <istream>
#include <sstream>
#include <string>
#include <vector>
    using namespace std;


#include "io.h"
#include "main.h"
#include "list.h"
#include "run.h"


/**
 * konvertuje int -> string
 * @param  (int) n = cislo, ktere konvertuju
 * @return (string)
 */
string itoS(int n)
{
    stringstream ss;
    ss << n;
    return ss.str();
}


/**
 * konvertuje char -> string
 * @param  (char) s = retezec, ktery konvertuju
 * @return (string)
 */
string ctoS(const char *s)
{
    stringstream ss;
    ss << s;
    return ss.str();
}


#include "list.cc"
#include "io.cc"
#include "run.cc"


/**
 * inicializace appINFO struktury pro vychozi stav
 * @param (appINFO) info = struktura pro nastaveni
 * @param (int)     page = 1. stranka pro vypis
 */
void init(appINFO &info, int page = 0)
{
    info.state  = STATE_MAIN;
    info.mstate = info.state;
    info.page   = page;
    info.plimit = PAGE_LIMIT;
    info.pmax   = get_pmax(info.plist);

        // pred-nastaveni sloupcu a filtru pro vypis
        int i;
        for(i=0; i<COLS_MAX;   i++) { info.cols[i]      = COL_NONE; }
        for(i=0; i<FILTER_MAX; i++) { info.filtr[i].col = COL_NONE; info.filtr[i].what = ""; }

        i = 0;
        info.cols[(i++)] = COL_ID;
        info.cols[(i++)] = COL_ZNACKA;
        info.cols[(i++)] = COL_MODEL;
        info.cols[(i++)] = COL_DRUH;
        info.cols[(i++)] = COL_OZNACENI;
//        info.cols[(i++)] = COL_MODEL_OD;
//        info.cols[(i++)] = COL_MODEL_DO;
        info.cols[(i++)] = COL_MOTOR;
        info.cols[(i++)] = COL_OBJEM;
        info.cols[(i++)] = COL_PALIVO;
        info.cols[(i++)] = COL_JEDNOTKA;
//        info.cols[(i++)] = COL_KW;
//        info.cols[(i++)] = COL_KWRPM;
//        info.cols[(i++)] = COL_NM;
//        info.cols[(i++)] = COL_NMRPM;
//        info.cols[(i++)] = COL_MOTOR_OD;
//        info.cols[(i++)] = COL_MOTOR_DO;
        info.cmax = i;

//        info.filtr[0].col = COL_ZNACKA;
//        info.filtr[5].col = COL_MODEL;
//        info.filtr[8].col = COL_DRUH;
        info.fmax = 0;
}


/**
 * hlavni spoustec aplikace
 * @return (int)
 */
int main()
{
    appINFO info;
    char key[255];

    info.plist = loadData(DATA_FN);
    if (info.plist == NULL) {
        warning("nelze lokalizovat datovy soubor(" DATA_FN ").");

        printf("zadej uplnou cestu k datovemu (.csv) souboru: ");
        scanf("%s", key);

        info.plist = loadData(key);
        if (info.plist == NULL) {
            fatal("zadany soubor nebylo mozne nacist!");
        }
    }


    init(info, 0);

    key[0] = '\0';
    while (1 == 1) {
        run(info, key);
        display(info);

//printf("s=%d\n", info.state);

        if (info.state == STATE_QUIT) {
            printf("\n\nQuit. Have a nice day ;-)\nbye.\n\n");
            break;
        }
        scanf("%s", key);
    }

    releaseData(info.plist);
    return 0;
}


