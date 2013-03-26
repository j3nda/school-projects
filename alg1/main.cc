/**
 * main.cc, (c) <jan@smid.sk>
 * -- algoritmy I. - projekt
 * --
 * -- rev.0.4, 111214-2148jsm - rc2, *num2fibo(), +formatovani a dokumentace
 * -- rev.0.3, 111206-2049jsm - odladeni c/c++ funkcnosti, rc1 (~pro: gcc-4.5.3, sabayon ~gentoo pro lenochy ;-)
 * -- rev.0.2, 111130-1727jsm - hruby prepis fungujiciho reseni z php -> c, +{fibo_next|fibo2{array|find}num2fibo}()
 * -- rev.0.1, 111111-1450jsm - vytvoreni souboru
 *
 *          zadani: http://www.cs.vsb.cz/dvorsky/Download/ALGI/ZadaniProjektu.pdf
 *  cislo projektu: 2 (~Rozklad na soucet Fibonacciho cisel)
 *
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>

#ifndef _MSC_VER
    #include <unistd.h>
#endif

using namespace std;


#define FIBO_MAX_WARN       10000                       /** @info: maximalni cislo, ktere mohu vlozit pro rozklad [warn]*/
#define FIBO_MAX_INDEX      30                          /** @info: pocet fibonaciho cisel, ktere se generuji na pocatku */
#define FIBO_NEXT_RECOUNT   5                           /** @info: pocet prvku fib. posl, ktere chci dodatecne dopocist */
#define FIBO_INT            unsigned long long int      /** @info: datovy typ pouzity pro uchovani fibonacciho cisel    */
#define FIBO_INT_SIZE       sizeof(FIBO_INT)            /** @info: velikost datoveho typu                               */


/**
 * vraci dalsi v rade - fibonacciho cislo
 * @param (bool) reset = true, pokud chci radu zacit znovu pocitat
 * @return (FIBO_INT) ~ @see FIBO_INT
 */
FIBO_INT fibo_next(bool reset = false)
{
    static FIBO_INT a = 1, b = 1;
    FIBO_INT c;

    if (reset) {
        a = b = 1;
    }

    c = a + b;
    a = b;
    b = c;

    return c;
}


/**
 * vraci pocet prvku v poli
 * @param (FIBO_INT) array = pole
 * @return (int)
 */
unsigned int count(FIBO_INT *array)
{
    bool cycle = false;
    unsigned int count = 0, i = 0;

    while (array[i] != '\0') {
        cycle = true;
        i++;
        count++;
    }

    return (cycle ? count-1 : count);
}


/**
 * vraci pointer na pole fibonacciho cisel (v pripade potreby alokuje pamet)
 * @param (int)      max   = maximalni pocet prvku fibonacciho cisel. pro realloc pouzij zaporne cislo!, [default: FIBO_MAX_INDEX]
 * @param (FIBO_INT) fibo  = pointer na fibonacciho posloupnost, [default: NULL]
 * @param (bool)     _free = zda chci pouze uvolnit pamet, [default: false]
 * @return (FIBO_INT)
 */
FIBO_INT *fibo2array(int max = FIBO_MAX_INDEX, FIBO_INT *fibo = NULL, bool _free = false)
{
    if (max < 5) {
        if (max < 0) {
            max = -1*max;

        } else {
            max = 5;
        }
    }

    FIBO_INT *a = NULL;
    int from = 2;


    if (fibo != NULL) {
        if (_free) {
            free(fibo);
            return NULL;
        }
        if (max > (from=count(fibo))) {
            a = (FIBO_INT *) realloc(fibo, max * FIBO_INT_SIZE);
            if (a == NULL) {
                fprintf(stderr, "ERROR: Memory exhausted.\n");
                exit(EXIT_FAILURE);
            }
        }

    } else if (_free) {
        return NULL;
    }


    if (a == NULL) {
        a = (FIBO_INT *) calloc(max, FIBO_INT_SIZE);
        if (a == NULL) {
            fprintf(stderr, "ERROR: Memory exhausted.\n");
            exit(EXIT_FAILURE);
        }
    }

    // prvni prvky fibonacciho posloupnosti jsou: 1, 1, 2, 3, ... (odtud 1 a 1)
    if (from <= 2) {
        a[0] = 1;
        a[1] = 1;
    }

    for (int i = from; i < max; i++) {
        a[i] = fibo_next(i == 2 ? true : false);
    }

    return a;
}



/**
 * hleda cislo v poli s fibonacciho cisly a vraci index nalezeneho prvku.
 * v pripade, ze neni fibonacciho cislo dostupne (~nema jej), tak jej dopocte a pole zvetsi
 * @param (int)      num  = cislo, ktere chci nalezt
 * @param (FIBO_INT) fibo = pole s fibonacciho cisly
 * @return (int)
 */
unsigned int fibo2find(unsigned int num, FIBO_INT *fibo)
{
    unsigned int idx = 0, max = count(fibo);
    FIBO_INT fnum;

    do {
        fnum = fibo[idx];
        idx++;

        if (idx >= max) {
            /** @todo: optimalizace: v tomto bode by se mohl aplikovat dynamicky seznam, abych fibo. posl. znovu nepocital... */
            fibo      = fibo2array(-1*(max+FIBO_NEXT_RECOUNT), fibo);
            max       = count(fibo);
        }

    } while (fibo[idx] < num);

    return (num == fibo[idx] ? idx : (idx-1));
}



/**
 * rozlozi cislo na soucet fibonacciho cisel a zobrazi jej
 * @param (int)      num  = cislo, ktere chci rozlozit
 * @param (FIBO_INT) fibo = pointer na fibonacciho posloupnost
 */
FIBO_INT *num2fibo(unsigned int num, FIBO_INT *fibo = NULL)
{
    if (fibo == NULL) {
        fibo = fibo2array(FIBO_MAX_INDEX, fibo);
    }


    unsigned int fidx, i = 0;
    long int _num = num;

    printf("%d = ", num);
    while (_num > 0) {
        fidx  = fibo2find(_num, fibo);
        _num -= fibo[fidx];

        printf("%s%lld", (i == 0 ? "" : " + "), fibo[fidx]);
        i++;
    }
    printf("\n");

    /** @todo: funkce by mohla vracet pole pro dalsi manipulaci */

    return fibo;
}


/** ===[ main ]============================================================ */

int main(int argc, char *argv[])
{
    FIBO_INT *fibo = NULL;
    int *fibo_ret = NULL;

    // command-line vs. interactive-input
    if (argc > 1) {
        for(int i = 1; i < argc; i++) {
            if (atoi(argv[i]) > FIBO_MAX_WARN) {
                fprintf(stderr, "WARNING: cislo(%s) je vetsi nez zadanim definovana mez(%d)! program nemusi fungovat spravne!\n", argv[i], FIBO_MAX_WARN);
            }
            fibo = num2fibo(atol(argv[i]), fibo);
        }

    } else {
        // interactive-input
        printf(
            "%s, v.111111-1450, (c) SMI051 <jan@smid.sk>\n" \
            "-- VSB-FEI: algoritmy I. - projekt, cislo projektu: 2 (~Rozklad na soucet Fibonacciho cisel)\n\n",
            argv[0]
        );

        printf("Zadej cislo <1-%d>: ", FIBO_MAX_WARN);
        unsigned int input = 0;
        scanf("%i", &input);
            if (input > FIBO_MAX_WARN) {
                fprintf(stderr, "WARNING: cislo(%d) je vetsi nez zadanim definovana mez(%d)! program nemusi fungovat spravne!\n", input, FIBO_MAX_WARN);
            }
        printf("\n");

        fibo = num2fibo(input, fibo);
#ifdef _MSC_VER
        cin.get();
        cin.get();
#endif
    }

    free(fibo);
    return 0;
}

