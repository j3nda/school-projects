/**
 *
 *
 * zaklady programovani, projekt: "katalog automobilu pro autobazar"
 * (c) <jan@smid.sk>, SMI051, Jan Smid, FEI - komb., 1. rocnik, LB1IKT04K (~hlavickovy soubor)
 *
 *
 */


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


string itoS(int n);
string ctoS(const char *s);
void init(appINFO &info, int page = 0);
int main();


