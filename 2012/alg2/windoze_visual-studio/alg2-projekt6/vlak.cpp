#include "vlak.h"

vlak::vlak(int _pvagonu, bool _output)
{

    vagon *pvagon;

    pvagonu = _pvagonu;
    output  = _output;
	pkoleje = NULL;
	mkoleji = 0;

    if (pvagonu <= 0) {
        pvagonu = (rand()%N)+1;
    }

    // projizdejici vlak (jeho vygenerovani/naplneni a vypsani)
    for (int i=0; i<pvagonu; i++) {
        pvagon = vagony.push(new vagon((i+1)));
//        pvagon = vagony.push(new vagon((i+1), ((rand()%P)+1), ((rand()%(C2-C1))+C1)));

        if (isOutput()) {
            printf("G%d[%d][%d] ", pvagon->cislo(), pvagon->pocetPolozek(), pvagon->cenaPolozky());
        }
    }
    if (isOutput()) {
        printf("\n\n");
    }
}


vlak::~vlak()
{
    for (int i=0; i<pvagonu; i++) {
        delete vagony.pop();
    }
}


int vlak::pocetVagonu()
{
    return pvagonu;
}


bool vlak::isOutput()
{
    return output;
}


int vlak::superTypekNaNadraziRozdelujeVagonyNaKolejePodleCeny(double *pcena, int max)
{
    if (pcena == NULL || max == 0) {
        return 0;
    }

    if (mkoleji != 0) {
        fprintf(stderr, "varovani: koleje nejsou prazdne! - natvrdo je rusim!\n");
        delete []pkoleje;
    }
    mkoleji = max;
    pkoleje = new zasobnik[mkoleji];


    vagon *pvagon;
    int ok = 0;

    // rozdelovani vagonu vlaku
    for (int i=0; i<pvagonu; i++) {
        pvagon = vagony.pop();

        // pcena[1..k]
        for (int k=0; k<mkoleji; k++) {
            if (pvagon->cenaNakladu() <= pcena[k]) {
                pkoleje[k].push(pvagon);
                ok++;

                if (isOutput()) {
                    printf("Z%d[%d] ", pvagon->cislo(), (k+1));
                }
                break;
            }
        }
    }
    if (isOutput()) {
        printf("\n\n");
    }

    return ok;
}


int vlak::vylozVsechnyVagony()
{
    if (!vagony.isEmpty()) {
        fprintf(stderr, "chyba: vlak neni prazdny - je nutne rozdelit vagony na koleje!\n");
        return 0;
    }

    if (mkoleji == 0) {
        fprintf(stderr, "chyba: na kolejich nejsou zadne vagony k vylozeni!\n");
        return 0;
    }


    vagon *pvagon;
    int ok = 0;

    if (isOutput()) {
        printf("----------\n");
    }

    for (int i=0; i<mkoleji; i++) {
        if (isOutput()) {
            printf("vykladam vagony z %d.koleje:\n\t", (i+1));
        }
        while (!pkoleje[i].isEmpty()) {
            pvagon = pkoleje[i].pop();

            int v = 0;
            while (pvagon->vylozitPolozku(output)) {
                v++;
            }

            if (v > 0) {
                ok++;
            }

            if (isOutput()) {
                printf("X%d[%d]\n\t", pvagon->cislo(), (i+1));
            }

            vagony.push(pvagon);
        }
        if (isOutput()) {
            printf("\n");
        }
    }

    return ok;
}

