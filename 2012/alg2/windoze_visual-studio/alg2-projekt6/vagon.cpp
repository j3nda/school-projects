#include "vagon.h"


vagon::vagon(int _cislo, int _ppolozek, double _cena1pol)
{
    cvagonu  = _cislo;
    ppolozek = _ppolozek;
    cena1pol = _cena1pol;

    if (_cislo > 0 && _ppolozek <= 0) {
        ppolozek = (rand()%P)+1;
    }

    if (_cislo > 0 && _cena1pol <= 0.0) {
        cena1pol = (rand()%(C2-C1))+C1;
    }
}


vagon::~vagon()
{
}


int vagon::cislo()
{
    return cvagonu;
}


int vagon::pocetPolozek()
{
    return ppolozek;
}


int vagon::cenaPolozky()
{
    return cena1pol;
}


double vagon::cenaNakladu()
{
    return (cena1pol*ppolozek);
}


bool vagon::vylozitPolozku(bool _output)
{
    if (pocetPolozek() > 0) {
        if (_output) {
            printf("V%d[%d] ", cislo(), --ppolozek);
        }
        return true;
    }
    return false;
}

