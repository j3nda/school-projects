
    /**
     * main.cpp, (c) <jan@smid.sk> ~ http://jan.smid.sk/
     * -- alg2 - projekt, zadani c: 6
     * --
	 * -- rev.0.5, 120512-0008jsm - *visual-studio, rc2
     * -- rev.0.4, 120512-2332jsm - +vlak, rc1
     * -- rev.0.3, 120512-1903jsm - *ladeni
     * -- rev.0.2, 120507-1344jsm - *doc, +zasobnik, +vagon
     * -- rev.0.1, 120402-2244jsm - file was created
     */


#include <stdio.h>
#include <stdlib.h>
#include <ctime>
#include "konstanty.h"
#include "vlak.h"

using namespace std;


int main()
{
    vlak     *pvlak;
    double   *pcena = NULL;
    int      delka_vlaku, cena2kolej;


    printf("%s\n\n", COPYRIGHT);
    srand(time(NULL));


    delka_vlaku = (rand()%N)+1;
    printf("delka vlaku: %d vagon(u)\n", delka_vlaku);

        // pravidla pro rozdelovani vagonu na jedn. koleje - viz zadani
        // (samorzejme bych to mohl definovat i jinak...)
        cena2kolej = 3;
        pcena      = new double[cena2kolej];

        pcena[0]   = (double)(P*C2)/3;
        pcena[1]   = (double)((P*C2)/3)*2;
        pcena[2]   = (double)P*C2;

        for (int i=0; i<cena2kolej; i++) {
            printf("\t%d. limit naklad(%d/%d): %.1f\n", (i+1), (i+1), cena2kolej, pcena[i]);
        }
        printf("\n");


    printf("----------\n");
    pvlak = new vlak(delka_vlaku, true);
        if (pvlak->isOutput()) {
            printf("\n");
        }

    if (pvlak->superTypekNaNadraziRozdelujeVagonyNaKolejePodleCeny(pcena, cena2kolej) != pvlak->pocetVagonu()) {
        fprintf(stderr, "chyba: nesedi pocet vagonu vlaku a pocet rozdelenych vagonu na koleje!\n");
    }
    pvlak->vylozVsechnyVagony();
        if (pvlak->isOutput()) {
            printf("\n");
        }

    delete pvlak;
    delete []pcena;

#ifdef _MSC_VER
		printf("visual-studio: zmackni 2x enter pro ukonceni.\n");
		cin.get();
		cin.get();
#endif

    return 0;
}

