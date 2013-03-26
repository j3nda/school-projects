/**
 *
 * io.cc, (c) <jan@smid.sk>
 * -- input/output (~nacitani z a do souboru)
 *
 */


/**
 * zobrazi warning hlasku na stdout
 * @param (string) msg    = hlaska
 * @param (string) prefix = prefix pro uvozeni 'hlaska', [default: "WARNING: "]
 */
void warning(const char *msg, const char *prefix="WARNING: ")
{
    fprintf(stderr, "%s%s\n", prefix, msg);
}


/**
 * zobrazi fatal error hlasku a ukonci program!
 * @param (string) msg = hlaska
 */
void fatal(const char *msg)
{
    warning(msg, "FATAL: ");
    exit(1);
}


/**
 * parsuje csv radku z dodaneho streamu (~souboru) a vysledek vklada do vektoru<string>
 * @param (vector<string>) &record    = roz-parsovany zaznam
 * @param (string)          line      = nactena radka ze souboru
 * @param (char)            delimiter = oddelovac, [default: ',']
 */
void fgetcsv(std::vector<std::string> &record, const std::string& line, char delimiter = ',')
{
    std::string cur;
    int pos = 0, max = line.length(), inQ = false, i;
    char c;

    record.clear();
    while(line[pos] != 0 && pos < max) {
        c = line[pos];

        if (!inQ && cur.length() == 0 && c == '"') {
            inQ = true;

        } else if (inQ && c == '"') {
            if ((pos+1 < max) && (line[pos+1] == '"')) {
                cur.push_back(c);
                pos++;

            } else {
                inQ = false;
            }

        } else if (!inQ && c == delimiter) {
            record.push_back(cur);
            cur = "";

        } else if (!inQ && (c == '\r' || c == '\n')) {
            record.push_back(cur);
            return;

        } else {
            cur.push_back(c);
        }
        pos++;
    }
    record.push_back(cur);
    return;
}


/*
 * nacte datovy soubor do pameti a vrati pointer na nactena data nebo NULL v pripade neuspechu
 * @param (string) fn    = uplna cesta k souboru s daty (.csv), @see: DATA_FN
 * @param (bool)   skip1 = zda ma preskocit 1. radek ~vetsinou se jedna o popis sloupcu, [default: true]
 * @return (NULL) or (pointer:carITEM)
 */
carITEM *loadData(const char *fn, bool skip1 = true)
{
    ifstream in(fn);
    if (in.fail())  {
        return NULL;
    }

    vector<string> row;
    string line;
    carITEM *pfirst = NULL, *pcur = NULL, *plast = NULL;
    int iline = 0;

    while(getline(in, line) && in.good()) {
        if (skip1 && (iline++) == 0) {
            continue;
        }

        fgetcsv(row, line, ',');

        pcur = new carITEM;
            if (plast != NULL) {
                plast->pnext = pcur;
            }

        // nacteni zaznamu do struktury...
        for(int i = 0, maxi = row.size(); i < maxi; i++) {
            pcur->id = iline;
            switch(i) {
                case 0:  pcur->zn_nazev   = row[i]; break;
                case 1:  pcur->m_nazev    = row[i]; break;

                case 2:
                    pcur->m_druh = TNIC;
                    switch(atoi(row[i].c_str())) {
                        case 'o':
                        case 'O':
                            pcur->m_druh = OSOBNI;
                            break;

                        case 'n':
                        case 'N':
                            pcur->m_druh = NAKLADNI;
                            break;
                    }
                    break;

                case 3:  pcur->m_oznaceni = row[i]; break;
                case 4:  pcur->m_rok_od   = atoi(row[i].c_str()); break;
                case 5:  pcur->m_rok_do   = atoi(row[i].c_str()); break;
                case 6:  pcur->r_nazev    = row[i]; break;
                case 7:  pcur->r_objem    = atoi(row[i].c_str()); break;

                case 8:
                    pcur->r_palivo = FNIC;
                    switch(atoi(row[i].c_str())) {
                        case 'b':
                        case 'B':
                            pcur->r_palivo = BENZIN;
                            break;

                        case 'n':
                        case 'N':
                            pcur->r_palivo = NAFTA;
                            break;
                    }
                    break;

                case 9:  pcur->r_jednotka = row[i]; break;
                case 10: pcur->r_kw       = atoi(row[i].c_str()); break;
                case 11: pcur->r_kw_rpm   = atoi(row[i].c_str()); break;
                case 12: pcur->r_nm       = atoi(row[i].c_str()); break;
                case 13: pcur->r_nm_rpm   = atoi(row[i].c_str()); break;
                case 14: pcur->r_rok_od   = atoi(row[i].c_str()); break;
                case 15: pcur->r_rok_do   = atoi(row[i].c_str()); break;
            }
        }

        if (plast == NULL) {
            pfirst = pcur;
        }
        plast = pcur;
    }
    in.close();

    return pfirst;
}


/**
 * uvolni data z pameti
 * @param (pointer:carITEM) p = pointer na nactene data
 */
void releaseData(carITEM *p = NULL)
{
    carITEM *pcur, *pdel;

    if (p != NULL) {
        pcur = p;
        while(pcur) {
            pdel = pcur;
            pcur = pcur->pnext;

            delete pdel;
        }
    }
}


int get_pmax(carITEM *p = NULL)
{
    carITEM *pcur;
    int m = 0;

    if (p != NULL) {
        pcur = p;
        while(pcur) {
            m++;
            pcur = pcur->pnext;
        }
    }
    return m;
}



/**
 * provede clear screen - vycisteni obrazovky
 * @param (int) max = maximalni pocet radku v terminalu (~na obrazovce), [default: 128]
 */
void display_clear(int max = 128)
{
    /** @todo: vhodnejsi smazani obrazovky, @see: http://www.cplusplus.com/forum/articles/10515/ */
    if (max < 25) {
        max = 25;   /** @info: standardni sirka x vyska terminalu je: 80 x 25 znaku */
    }
    for(int i = 0; i < max; i++) {
        printf("\n");
    }
}


/**
 * zobrazi kompletni ovladaci obrazovku vcetne stavu a nabidky
 * @param (appINFO) info = stav aplikace
 * @param (int) phase = faze vykresleni aplikace, [default: DISPLAY_ALL]
 */
void display(appINFO &info, int phase = DISPLAY_ALL)
{
    static int mleft = 0, mwidth = 0, mstart = 0, mstate = STATE_MAIN;
    string copyright = COPYRIGHT, menu[3];


    switch(phase) {
        case DISPLAY_ALL:
            display(info, DISPLAY_MENU);

            display(info, DISPLAY_TOP);
            display(info, DISPLAY_CONTENT);
            display(info, DISPLAY_BOTTOM);
            break;


        case DISPLAY_TOP:
            display_clear();
//                       012345678901234567890123456789012345678901234567890123456789012345678901234567890
//                             012345678901234567890123456789012345678901234567890123456789012345678901234567890
//                             12345678   123456789   1234567890   123456789
            menu[0]   = "----------------------------------------------------------------------------";
            menu[1]   = "menu: L = LIST | F = FILTR | V = VYSTUP | K = KONEC";
            menu[2]   = "                                                                            ";
            mstart    = 6;

            menu[0].replace(mstart + mleft, 1, mwidth, '=');
            menu[2].replace(mstart + mleft, 1, mwidth, '=');

            printf("%s", copyright.c_str());
            printf("%s\n%s\n%s\n", menu[0].c_str(), menu[1].c_str(), menu[2].c_str());
            break;


        case DISPLAY_MENU:
            mleft  = mwidth = 0;
            mstate = info.state;

            if (info.state >= STATE_QUIT && info.state < STATE_LIST) {
                mstate = info.mstate = STATE_QUIT;
                mleft  = 36;
                mwidth = 9;

            } else if (info.state >= STATE_LIST  && info.state < STATE_FILTR) {
                mstate = info.mstate = STATE_LIST;
                mleft  = 0;
                mwidth = 8;

            } else if (info.state >= STATE_FILTR && info.state < STATE_VYSTUP) {
                mstate = info.mstate = STATE_FILTR;
                mleft  = 11;
                mwidth = 9;

            } else if (info.state >= STATE_VYSTUP) {
                mstate = info.mstate = STATE_VYSTUP;
                mleft  = 23;
                mwidth = 10;
            }
            break;


        case DISPLAY_CONTENT:
            switch(mstate) {
                case STATE_QUIT:
                case STATE_LIST:
                    list_filtr(info);
                    list_paginator(info);
                    list(info);
                    break;

                case STATE_FILTR:
                    break;

                case STATE_VYSTUP:
                    if (info.state == STATE_VYSTUP_SAVE) {
                        list(info);
                    }
                    break;
            }
            break;


        case DISPLAY_BOTTOM:
            printf("\n");

            if (info.hint.length() > 0) {
                printf("hint: %s\n", info.hint.c_str());
            }

            if (info.msg.length() > 0) {
                printf("%s", info.msg.c_str());
            }
            printf("\n");

            if (info.sekce.length() > 0) {
                printf("volba(%s): ", info.sekce.c_str());

            } else {
                printf("volba: ");
            }
            info.hint = info.msg = "";
            break;
    }


}



