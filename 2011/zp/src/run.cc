/**
 *
 * run.cc, (c) <jan@smid.sk>
 * -- ovladani aplikacni logiky
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

#include "main.h"
#include "run.h"


/**
 * aplikacni logika pro ruzne stavy aplikace.
 * (stav je resen pres strukturu: appINFO.state a appINFO.mstate)
 * @param (appINFO) &info = stav aplikace
 * @param (char)     key  = klavesova volba z nabidky
 * @return (bool)
 */
bool run(appINFO &info, const char *key = NULL)
{
    char mkey = '\0';
    string s;

    if (key != NULL) {
        s    = "";
        s    = ctoS(key);
        if (s.length() == 1) {
            mkey = key[0];
        }
    }


    // main menu
    switch(mkey) {
        case 'L':
        case 'l':
            info.state = info.mstate = STATE_LIST;
            info.sekce = "list";
            break;

        case 'F':
        case 'f':
            info.state = info.mstate = STATE_FILTR;
            info.sekce = "filtr";
            break;

        case 'V':
        case 'v':
            info.state = info.mstate = STATE_VYSTUP;
            info.sekce = "vystup";
            break;

        case 'Q':
        case 'q':
        case 'K':
        case 'k':
            info.state = info.mstate = STATE_QUIT;
            info.sekce = "konec";
            break;
    }


    int pmax   = (info.pmax/info.plimit);
    int lstate = info.mstate, i, ii;
    bool logic = false;
    appINFO ai;

    if (((float)info.pmax/(float)info.plimit)-pmax > 0) {
        pmax++;
    }


    // sub-menu
    switch(info.mstate) {
        case STATE_LIST:
            info.hint  = "navigace: p=predhozi, n=nasledujici, g=goto, h=home, e=end, m=limit na stranku";
            info.sekce = "list";

            // logika
            switch(info.state) {
                case STATE_LIST_GOTO:
                    logic     = true;
                    info.page = atoi(key) - 1;
                    if (info.page < 0)     { info.page = 0;      }
                    if (info.page >= pmax) { info.page = pmax-1; }
                    break;

                case STATE_LIST_LIMIT:
                    logic       = true;
                    info.page   = 0;
                    info.plimit = atoi(key);
                    if (info.plimit < 1)    { info.plimit = 1;    }
                    if (info.plimit > 1024) { info.plimit = 1024; }
                    break;
            }
            if (logic) {
                info.state = lstate;
                return logic;
            }


            // volby
            switch(mkey) {
                case 'p':
                case 'P':
                    info.page--;
                    if (info.page < 0) {
                        info.page = 0;
                    }
                    break;

                case 'n':
                case 'N':
                    info.page++;
                    if (info.page >= pmax) {
                        info.page = pmax-1;
                    }
                    break;

                case 'h':
                case 'H':
                    info.page = 0;
                    break;

                case 'e':
                case 'E':
                    info.page = pmax-1;
                    break;

                case 'g':
                case 'G':
                    info.state = STATE_LIST_GOTO;
                    info.hint  = "pro zmenu stranky vloz cislo: <1, ";
                        info.hint.append(itoS(pmax));
                        info.hint.append(">");
                    info.sekce.append("/goto");
                    break;

                case 'm':
                case 'M':
                    info.state = STATE_LIST_LIMIT;
                    info.hint  = "pro zmenu poctu zaznamu na stranku vloz cislo: [0-9]+";
                    info.sekce.append("/limit");
                    break;

            }
            break;


        case STATE_FILTR:
            info.hint  = "@todo: manipulace s filtry";
            info.sekce = "filtr";
            break;


        case STATE_VYSTUP:
            info.hint  = "a=ulozit vse, s=zobr. sloupce a akt. filtry, f=vsechny sloupce s akt. filtry";
            info.sekce = "vystup";

            // logika
            switch(info.state) {
                case STATE_VYSTUP_ALL:
                case STATE_VYSTUP_SELECT:
                case STATE_VYSTUP_FILTR:
                    ii = info.state;

                    logic      = true;
                    lstate     = STATE_LIST;
                    info.state = STATE_VYSTUP_SAVE;
                    s          = OUTPUT_DIR;
                    s.append(ctoS(key));

                    ai         = info;
                        for(i=0; i<COLS_MAX;   i++) { ai.cols[i]      = COL_NONE; }
                        for(i=0; i<FILTER_MAX; i++) { ai.filtr[i].col = COL_NONE; ai.filtr[i].what = ""; }

                        i = 0;
                        ai.cols[(i++)] = COL_ID;
                        ai.cols[(i++)] = COL_ZNACKA;
                        ai.cols[(i++)] = COL_MODEL;
                        ai.cols[(i++)] = COL_DRUH;
                        ai.cols[(i++)] = COL_OZNACENI;
                        ai.cols[(i++)] = COL_MODEL_OD;
                        ai.cols[(i++)] = COL_MODEL_DO;
                        ai.cols[(i++)] = COL_MOTOR;
                        ai.cols[(i++)] = COL_OBJEM;
                        ai.cols[(i++)] = COL_PALIVO;
                        ai.cols[(i++)] = COL_JEDNOTKA;
                        ai.cols[(i++)] = COL_KW;
                        ai.cols[(i++)] = COL_KWRPM;
                        ai.cols[(i++)] = COL_NM;
                        ai.cols[(i++)] = COL_NMRPM;
                        ai.cols[(i++)] = COL_MOTOR_OD;
                        ai.cols[(i++)] = COL_MOTOR_DO;
                        ai.cmax = i;
                        ai.fmax = 0;

                        if (ii == STATE_VYSTUP_SELECT) {
                            ai = info;

                        } else if (ii == STATE_VYSTUP_FILTR) {
                            for(i=0; i<FILTER_MAX; i++) {
                                ai.filtr[i].col  = info.filtr[i].col;
                                ai.filtr[i].what = info.filtr[i].what;
                            }
                            ai.fmax = info.fmax;
                        }

                    ai.fout = fopen(s.c_str(), "w");
                        list_html_header(ai);
                        list(ai);
                        list_html_footer(ai);
                    fclose(ai.fout);

                    info.msg  = "soubor byl uspesne ulozen.";
                    info.hint = "";
                    break;
            }
            if (logic) {
                info.state = lstate;
                return logic;
            }


            // volby
            switch(mkey) {
                case 'a':
                case 'A':
                    info.state = STATE_VYSTUP_ALL;
                    info.hint  = "napis nazev souboru, napr: index.html";
                    info.sekce.append("/vse");
                    break;

                case 's':
                case 'S':
                    info.state = STATE_VYSTUP_SELECT;
                    info.hint  = "napis nazev souboru, napr: index.html";
                    info.sekce.append("/zvolene");
                    break;
            }

            break;
    }


    return true;
}


