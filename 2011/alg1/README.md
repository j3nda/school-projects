/**
 * main.cc, (c) <jan@smid.sk>
 * -- algoritmy I. - projekt ~ Decomposition of the sum of Fibonacci numbers
 * --
 * -- rev.0.4, 111214-2148jsm - rc2, *num2fibo(), +formatovani a dokumentace
 * -- rev.0.3, 111206-2049jsm - odladeni c/c++ funkcnosti, rc1 (~pro: gcc-4.5.3, sabayon ~gentoo pro lenochy ;-)
 * -- rev.0.2, 111130-1727jsm - hruby prepis fungujiciho reseni z php -> c, +{fibo_next|fibo2{array|find}num2fibo}()
 * -- rev.0.1, 111111-1450jsm - vytvoreni souboru
 *
 *          zadani: http://www.cs.vsb.cz/dvorsky/Download/ALGI/ZadaniProjektu.pdf
 *            task: ./TASK.md
 *  cislo projektu: 2 (~Rozklad na soucet Fibonacciho cisel)
 *
 */

========================================
kompilace: LINUX
    clear; g++-4.5.3 ./main.cc -o ./main && ./main

kompilace: WINDOZE
    - nejaky klikaci postup ve WC ;-)

========================================
poznamky:
    - pokud se vlozi argumenty na prikazove radce (~jako cisla) -> budou tato cisla rozlozena
      a zobrazena na standardni vystup.

    - pokud neni zadan zadny argument, program se na cislo zepta, rozlozi jej
      a zobrazi na standardni vystup.

    - v pripade, ze se zadava cislo vyssi nez 10000 (~definovany limit zadanim), tak
      program na standardni chybovy vystup zobrazi WARNING.

========================================
testovaci data: LINUX   -> ./testovaci-data.sh
testovaci data: WINDOZE -> ./testovaci-data.bat << chybi, viz .sh

========================================
KOREKTNI VYSTUP:

1 = 1
2 = 2
3 = 3
4 = 3 + 1
5 = 5
6 = 5 + 1
7 = 5 + 2
8 = 8
9 = 8 + 1
  6263 =   4181 + 1597 + 377 + 89 + 13 + 5 + 1
 10000 =   6765 + 2584 + 610 + 34 +  5 + 2
123123 = 121393 + 1597 +  89 + 34 +  8 + 2

---
eof.
