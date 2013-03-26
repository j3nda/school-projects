#ifndef __ZASOBNIK_H__
#define __ZASOBNIK_H__

#include <iostream>
#include "konstanty.h"
#include "vagon.h"

using namespace std;


class zasobnik
{
    public:
        zasobnik();
        ~zasobnik();

        /**
         * prida polozku do zasobniku a vrati ukazatel na vlozenou polozku
         * @param (vagon) *item
         * @return (*vagon)
         */
        vagon *push(vagon *item);

        /**
         * odebere polozku ze zasobniku a vrati ukazatel na odebranou polozku
         * @return (*vagon)
         */
        vagon *pop();

        /**
         * vraci true, pokud je zasobnik prazdny. jinak false
         * @return (bool)
         */
        bool isEmpty();


    private:
        /**
         * dynamicka struktura pro ukladani polozek do zasobniku
         */
        typedef struct t_item {
            vagon   *ptr;       /** @info: (vagon)  *ptr  = ukazatel na data v seznamu/zasobniku          */
            t_item  *next;      /** @info: (t_item) *next = ukazatel na nasl. polozku v seznamu/zasobniku */
        } t_item;
        t_item *head;           /** @info: (t_item) *head = ukazatel na 1. polozku v seznamu/zasobniku    */


};


#endif
