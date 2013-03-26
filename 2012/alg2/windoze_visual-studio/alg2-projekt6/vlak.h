#ifndef __VLAK_H__
#define __VLAK_H__

#include <stdio.h>
#include <stdlib.h>
#include <ctime>
#include "konstanty.h"
#include "zasobnik.h"
#include "vagon.h"

using namespace std;


class vlak {
    public:


        /**
         * konstruktor
         * @param (int)  _pvagonu = pocet vagonu, [default: 0 ~ nahodne generovano: <1 az N>]
         * @param (bool) _output  = true pro zobrazeni prubehovych informaci, [default: false]
         */
        vlak(int _pvagonu=0, bool _output=false);
        ~vlak();


        /**
         * vraci pocet vagonu vlaku
         * @return (int)
         */
        int pocetVagonu();


        /**
         * vraci true, pokud se maji zobrazovat prubehove informace o vlaku. jinak false
         * @return (bool)
         */
        bool isOutput();


        /**
         * rozdelovani vagonu na jednolive koleje podle ceny (~note: plati, ze cena1 < cena2 < cena3)
         *   vraci pocet uspesne rozdelenych vagonu
         * @param (array:double) *pcena = pole cen pro dane koleje (~cena pro prideleni na 1.-n. kolej)
         * @param (int)           max   = pocet prvku v poli pcena
         * @return (int)
         */
        int superTypekNaNadraziRozdelujeVagonyNaKolejePodleCeny(double *pcena, int max);


        /**
         * vylozi vsechny polozky vlaku na nadrazi podle koleji, na kterych vagony stoji
         *   vraci pocet uspesne vylozenych vagonu
         * @return (int)
         */
        int vylozVsechnyVagony();


    protected:
        zasobnik vagony;    /** @info: ze zadani: 1. bude reprezentovat prijizdejici vlak */
        zasobnik *pkoleje;  /** @info: koleje pro rozdeleni podle ceny, @see: superTypekNaNadraziRozdelujeVagonyNaKolejePodleCeny() */
        int  pvagonu;       /** @info: pocet vagonu vlaku              */
        bool output;        /** @info: zobrazeni prubehovych informaci */


    private:
        int mkoleji;        /** @info: pocet koleji */

};


#endif
