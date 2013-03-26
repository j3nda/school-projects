#ifndef __VAGON_H__
#define __VAGON_H__

#include <stdio.h>
#include <stdlib.h>
#include <ctime>
#include "konstanty.h"

using namespace std;


class vagon {
    public:


        /**
         * @param (int)      _cislo    = cislo vagonu
         * @param (null|int) _ppolozek = pocet polozek ve vagonu,      [default: 0   ~ nahodne generovano: <1 az P>]
         * @param (null|int) _cena1pol = pevna cena 1x polozky vagonu, [default: 0.0 ~ nahodne generovano: <C1 az C2>]
         */
        vagon(int _cislo=0, int _ppolozek=0, double _cena1pol=0.0);
        ~vagon();


        /**
         * vraci cislo vagonu
         * @return (int)
         */
        int cislo();


        /**
         * vraci pocet polozek ve vagonu
         * @return (int)
         */
        int pocetPolozek();

        /**
         * vraci cenu 1x polozky z nakladu (~note: vsechny polozky maji stejnou cenu!)
         * @return (int)
         */
        int cenaPolozky();

        /**
         * vraci celkovou cenu nakladu ve vagonu
         @return (double)
         */
        double cenaNakladu();


        /**
         * vylozi 1x polozku na nadrazi.
         *   vraci true, pokud byla polozka vylozena. jinak false
         * @param  (bool) _output = zobrazit prubezne informace?
         * @return (bool)
         */
        bool vylozitPolozku(bool _output);


    protected:
        int    cvagonu, ppolozek;   /** @info: cislo vagonu, pocet polozek */
        double cena1pol;            /** @info: cena 1x polozky             */

};


#endif
