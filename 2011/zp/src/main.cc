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


#define IMG_DIR         "../img/"                   /** definuje umisteni k obrazkum - pro html vystup */
#define OUTPUT_DIR      "../output/"                /** definuje vystupni adresar, kam porizuji vystup */
#define DATA_DIR        "../data/"                  /** definuje umisteni k datovemu souboru - adresar */
#define DATA_FN         DATA_DIR"rs_auto_motor.csv" /** definuje zdrojova data, ktera se nactou        */

#define STATE_QUIT      0                           /** definuje stav: konec aplikace                  */
#define STATE_LIST      100                         /** definuje stav: prehled ~ seznam                */
#define STATE_LIST_GOTO     101                     /** definuje stav: skok na stranku                 */
#define STATE_LIST_LIMIT    102                     /** definuje stav: zmena poctu zaznamu na 1x str   */
#define STATE_FILTR     200                         /** definuje stav: manipulace s filtry             */
#define STATE_VYSTUP    300                         /** definuje stav: zpracovani html vystupu         */
#define STATE_VYSTUP_ALL    301                     /** definuje stav: ulozit vse do html souboru      */
#define STATE_VYSTUP_SELECT 302                     /** definuje stav: ulozit pouze zvolene do html... */
#define STATE_VYSTUP_SAVE   303                     /** definuje stav: uspesne ulozeni vystupu         */
#define STATE_VYSTUP_FILTR  304                     /** definuje stav: vsechny sloupce, akt. filtry    */
#define STATE_MAIN      STATE_LIST                  /** definuje vychozi stav po spusteni aplikace, [default: STATE_LIST] */

#define HTML_NOIMAGE    "_noimg.jpg"                /** nazev obrazku, kdyz jej nenajde */
#define HTML_TITLE      "zaklady programovani, projekt: &lt;jan@smid.sk&gt;, SMI051, skupina: LB1IKT04K"
#define COPYRIGHT       "zaklady programovani, projekt: \"katalog automobilu pro autobazar\"\n" \
                        "(c) <jan@smid.sk>, SMI051, Jan Smid, FEI - komb., 1. rocnik, skup: LB1IKT04K\n"

#define PAGE_LIMIT      20                          /** vychozi pocet radku na stranku, [20]           */
#define COLS_MAX        20                          /** maximalni pocet sloupcu, ktere kreslim         */
#define FILTER_MAX      20                          /** maximalni pocet filtru                         */


typedef struct
{
    int col;                                        /** col_id, ktereho se filtr tyka             */
    string what;                                    /** co vyhledavam - (string) vc. wildcard '%' */
} colFILTER;                                        /** struktura pro ulozeni stavu filtru        */


typedef struct
{
    carITEM *plist;                                 /** pointer:carITEM = na seznam nactenych dat */

    int state, mstate;                              /** stav aplikace                             */
    int page, plimit, pmax;                         /** strankovani: stranka, pocet radek na str  */
    int cols[COLS_MAX];                             /** sloupce, ktere vykreslim                  */
    colFILTER filtr[FILTER_MAX];                    /** filtry, ktere ovlivnuji vystup            */
    int cmax, fmax;                                 /** maximalni pocet nastavenych: cols, filtrs */

    string msg, hint, sekce;                        /** zpravy - msg, hint, sekce                 */

    FILE *fout;                                     /** soubor                                    */
} appINFO;                                          /** struktura pro ulozeni stavu aplikace      */


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


