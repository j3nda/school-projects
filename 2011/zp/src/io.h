/**
 *
 * io.h, (c) <jan@smid.sk>
 * -- input/output (~hlavickovy soubor)
 *
 */


#define DISPLAY_ALL     0                       /** definuje fazi vykresleni pro: display() - VSE    */
#define DISPLAY_TOP     1                       /** definuje fazi vykresleni pro: display() - vrsek  */
#define DISPLAY_CONTENT 2                       /** definuje fazi vykresleni pro: display() - stred  */
#define DISPLAY_BOTTOM  3                       /** definuje fazi vykresleni pro: display() - spodek */
#define DISPLAY_MENU    4                       /** definuje fazi vykresleni pro: display() - menu   */


#define CARTYPE_O       1                       /** definuje typ auta - osobni                       */
#define CARTYPE_N       2                       /** definuje typ auta - nakladni                     */
#define CARTYPE_NONE    3                       /** definuje typ auta - undefined                    */

#define CARFUEL_B       1                       /** definuje typ paliva - benzin                     */
#define CARFUEL_N       2                       /** definuje typ paliva - nafta                      */
#define CARFUEL_L       3                       /** definuje typ paliva - LPG                        */
#define CARFUEL_NONE    4                       /** definuje typ paliva - undefined                  */


#define COL_NONE        -1                      /** definuje id sloupce, ZADNY                       */
#define COL_ID          0                       /** definuje id sloupce, id zaznamu                  */
#define COL_ZNACKA      1                       /** definuje id sloupce, @see carITEM::zn_nazev      */
#define COL_MODEL       2                       /** definuje id sloupce, @see carITEM::m_nazev       */
#define COL_DRUH        3                       /** definuje id sloupce, @see carITEM::m_druh        */
#define COL_OZNACENI    4                       /** definuje id sloupce, @see carITEM::m_oznaceni    */
#define COL_MODEL_OD    5                       /** definuje id sloupce, @see carITEM::m_rok_od      */
#define COL_MODEL_DO    6                       /** definuje id sloupce, @see carITEM::m_rok_do      */
#define COL_MOTOR       7                       /** definuje id sloupce, @see carITEM::r_nazev       */
#define COL_OBJEM       8                       /** definuje id sloupce, @see carITEM::r_objem       */
#define COL_PALIVO      9                       /** definuje id sloupce, @see carITEM::r_palivo      */
#define COL_JEDNOTKA    10                      /** definuje id sloupce, @see carITEM::r_jednotka    */
#define COL_KW          11                      /** definuje id sloupce, @see carITEM::r_kw          */
#define COL_KWRPM       12                      /** definuje id sloupce, @see carITEM::r_kw_rpm      */
#define COL_NM          13                      /** definuje id sloupce, @see carITEM::r_nm          */
#define COL_NMRPM       14                      /** definuje id sloupce, @see carITEM::r_nm_rpm      */
#define COL_MOTOR_OD    15                      /** definuje id sloupce, @see carITEM::r_rok_od      */
#define COL_MOTOR_DO    16                      /** definuje id sloupce, @see carITEM::r_rok_do      */


typedef enum
{
    OSOBNI   = CARTYPE_O,                       /** @see: CARTYPE_O    */
    NAKLADNI = CARTYPE_N,                       /** @see: CARTYPE_N    */
    TNIC     = CARTYPE_NONE,                    /** @see: CARTYPE_NONE */
} carTYPE;                                      /** vyctovy typ enum pro typ automobilu, @see: carITEM:m_druh */


typedef enum
{
    BENZIN = CARFUEL_B,                         /** @see: CARFUEL_B    */
    NAFTA  = CARFUEL_N,                         /** @see: CARFUEL_N    */
    LPG    = CARFUEL_L,                         /** @see: CARFUEL_L    */
    FNIC   = CARFUEL_NONE,                      /** @see: CARFUEL_NONE */
} carFUEL;                                      /** vyctovy typ enum pro typ paliva, @see: carITEM:r_palivo */


typedef struct s_carITEM
{
    int id;                                     /** id radku                                        */
    string zn_nazev;                            /** zn_nazev   = (zn)acka nazev                     */
    string m_nazev;                             /** m_nazev    = (m)odel nazev                      */
    carTYPE m_druh;                             /** m_druh     = (m)odel auta @see: carTYPE         */
    string m_oznaceni;                          /** m_oznaceni = (m)odel oznaceni                   */
    unsigned int m_rok_od;                      /** m_rok_od   = (m)odel je v obehu od roku         */
    unsigned int m_rok_do;                      /** m_rok_do   = (m)odel je v obehu do roku         */
    string r_nazev;                             /** r_nazev    = moto(r), nazev motoru              */
    unsigned int r_objem;                       /** r_objem    = objem moto(r)u ~ 1650ccm           */
    carFUEL r_palivo;                           /** r_palivo   = moto(r), typ paliva, @see: carFUEL */
    string r_jednotka;                          /** r_jednotka = moto(r), nazev jednotky            */
    unsigned int r_kw;                          /** r_kw       = moto(r) vykon v kW                 */
    unsigned int r_kw_rpm;                      /** r_kw_rpm   = moto(r) vykon v kW/RPM             */
    unsigned int r_nm;                          /** r_nm       = moto(r), tocivy moment             */
    unsigned int r_nm_rpm;                      /** r_nm_rpm   = moto(r), tocivy moment/RPM         */
    unsigned int r_rok_od;                      /** r_rok_od   = moto(r) je v objehu od roku        */
    unsigned int r_rok_do;                      /** r_rok_do   = moto(r) je v objehu do roku        */

    s_carITEM *pnext;                           /** (pointer:carITEM) pnext pro navaznost, dyn.sezn.*/
    bool       show;                            /** show       = zda budu zaznam zohled. ve filtru  */

} carITEM;                                      /** struktura pro ulozeni 1x zaznamu automobilu     */


