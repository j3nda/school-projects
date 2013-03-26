/**
 *
 * list.cc, (c) <jan@smid.sk>
 * -- vypisy dat na obrazovku anebo do souboru
 *
 */


enum STR_PAD {STR_PAD_RIGHT, STR_PAD_LEFT, STR_PAD_BOTH};


/**
 * formatovani retezce vc. zarovnani
 * @param (string) &str        = retezec
 * @param (int)     pad_length = delka pro zarovnani
 * @param (string)  pad_string = text pro zarovnani, [default: " "]
 * @param (STR_PAD) pad_type   = typ zarovnani, @see: enum STR_PAD, [default: STR_PAD_RIGHT]
 * @return (string)
 */
string str_pad(const string &str, int pad_length, string pad_string=" ", STR_PAD pad_type=STR_PAD_RIGHT)
{
    int i,j,x;
    int str_size = str.size();
    int pad_size = pad_string.size();
    if (pad_length<=str_size || pad_size<1)
        return str;

    string o;
    o.reserve(pad_length);  // allocate enough memory only once

    if (pad_type == STR_PAD_RIGHT) {
        for(i=0, x=str_size; i<x; i++)
            o.push_back(str[i]);

        for(i=str_size; i<pad_length;)
            for(j=0; j<pad_size && i<pad_length; j++, i++)
                o.push_back(pad_string[j]);

    } else if (pad_type==STR_PAD_LEFT) {
        int a1= pad_length-str_size;
        for(i=0; i<a1; )
            for(j=0; j<pad_size && i<a1; j++, i++)
                o.push_back(pad_string[j]);

        for(i=0,x=str_size; i<x; i++)
            o.push_back( str[i] );

    } else if (pad_type==STR_PAD_BOTH) {
        int a1 = (pad_length-str_size)/2;
        int a2 =  pad_length-str_size-a1;

        for(i=0; i<a1; )
            for(j=0; j<pad_size && i<a1; j++, i++)
                o.push_back(pad_string[j]);

        for(i=0,x=str_size; i<x; i++)
            o.push_back(str[i]);

        for(i=0; i<a2; )
            for(j=0; j<pad_size && i<a2; j++,i++)
                o.push_back(pad_string[j]);
    }
    return o;
}


/**
 * nahrazeni retezce v retezci
 * @param (string) search  = co hledam
 * @param (string) replace = nahrada
 * @param (string) subject = retezec
 * @return (string)
 */
string& str_replace(const string &search, const string &replace, string &subject)
{
    string buffer;

    int sealeng = search.length();
    int strleng = subject.length();

    if (sealeng == 0)
        return subject; //no change

    for(int i=0, j=0; i<strleng; j=0) {
        while (i+j<strleng && j<sealeng && subject[i+j]==search[j])
            j++;

        if (j==sealeng) {   //found 'search'
            buffer.append(replace);
            i += sealeng;

        } else {
            buffer.append( &subject[i++], 1);
        }
    }
    subject = buffer;
    return subject;
}


/**
 * zjistuje, zda je soubor ke cteni
 * @param (char) filename = nazev souboru
 * @return (bool)
 */
bool file_exists(const char * filename)
{
    if (FILE * file = fopen(filename, "r")) {
        fclose(file);
        return true;
    }
    return false;
}


/**
 * zobrazi 1x zaznam z tabulky
 * @param (appINFO) info  = stav aplikace
 * @param (carITEM) item  = radkovy zaznam s daty
 * @param (bool)    empty = zda chci zobrazit pouze prazdny radek, [default: false]
 * @param (bool)    title = zda chci zobrazit pouze titulky,       [default: false]
 * @param (bool)    ret   = zda chci vratit vysledek,              [default: false]
 */
string list_item(appINFO info, carITEM *item, bool empty = false, bool title = false, bool ret = false)
{
    string s, t, ss, sx;// string, title, super-string
    bool is = false;    // is title?
    int L, LL, ii = 0;  // Len, (LL a ii jsou pomocne, viz nize)

    if (info.state == STATE_VYSTUP_SAVE) {
        fprintf(info.fout, "<tr>");
        if (title) {
            fprintf(info.fout, "<th>img</th>");

        } else {
            string src = IMG_DIR;
            string alt = item->zn_nazev;
                alt.append(" ");
                alt.append(item->m_nazev);
                src.append(str_replace(" ", "-", alt));
                src.append(".jpg");

            if (!file_exists(src.c_str())) {
                src = IMG_DIR;
                src.append(HTML_NOIMAGE);
            }

            fprintf(info.fout, "<td><img src=\"%s\" alt=\"%s\" /></td>", src.c_str(), alt.c_str());
        }
    }

    for(int i=0; i<COLS_MAX; i++) {
        switch(info.cols[i]) {
            case COL_NONE:
                L = 0;
                break;

            case COL_ID:        t = "id";     L =  4; s = (!is ? itoS(item->id) : ""); break;
            case COL_ZNACKA:    t = "znacka"; L = 15; s = item->zn_nazev;              break;
            case COL_MODEL:     t = "model";  L = 10; s = item->m_nazev;               break;

            case COL_DRUH:
                t = "druh";
                L = 4;
                switch(item->m_druh) {
                    case CARTYPE_O: s = (!is ? "osob" : "osobni");    break;
                    case CARTYPE_N: s = (!is ? "nakl" : "nakladni");  break;
                    default:        s = (!is ? " -- " : "neuvedeno"); break;
                }
                break;

            case COL_OZNACENI:  t = "typ";    L = 10; s = item->m_oznaceni;     break;
            case COL_MODEL_OD:  t = "od";     L =  4; s = itoS(item->m_rok_od); break;
            case COL_MODEL_DO:  t = "do";     L =  4; s = itoS(item->m_rok_do); break;
            case COL_MOTOR:     t = "motor";  L = 15; s = item->r_nazev;        break;
            case COL_OBJEM:     t = "ccm";    L =  4; s = itoS(item->r_objem);  break;
            case COL_PALIVO:
                t = "P";
                L = 1;
                switch(item->r_palivo) {
                    case CARFUEL_B: s = (!is ? "B" : "benzin");    break;
                    case CARFUEL_N: s = (!is ? "N" : "nafta");     break;
                    case CARFUEL_L: s = (!is ? "L" : "lpg");       break;
                    default:        s = (!is ? "-" : "neuvedeno"); break;
                }
                break;

            case COL_JEDNOTKA:  t = "engine"; L = 15; s = item->r_jednotka;     break;
            case COL_KW:        t = "kw";     L =  4; s = itoS(item->r_kw);     break;
            case COL_KWRPM:     t = "kw/rpm"; L =  4; s = itoS(item->r_kw_rpm); break;
            case COL_NM:        t = "nm";     L =  4; s = itoS(item->r_nm);     break;
            case COL_NMRPM:     t = "nm/rpm"; L =  4; s = itoS(item->r_nm_rpm); break;
            case COL_MOTOR_OD:  t = "od";     L =  4; s = itoS(item->r_rok_od); break;
            case COL_MOTOR_DO:  t = "do";     L =  4; s = itoS(item->r_rok_do); break;
        }

        if (L > 0) {
            if (info.state != STATE_VYSTUP_SAVE && ii > 0) {
                printf("|");
            }
            ii++;

            if (title) {
                if (ret) {
                    return (string) t;
                }
                ss = str_pad(t, L, " ", STR_PAD_RIGHT);
                sx = t;

            } else if (empty) {
                ss = str_pad("", L, " ", STR_PAD_RIGHT);

            } else {
                if (ret) {
                    return (string) s;
                }
                ss = str_pad(s, L, " ", STR_PAD_RIGHT);
                sx = s;
            }
            LL = ss.length();
            if (LL > L) {
                ss = ss.substr(0, L);
            }

            if (info.state == STATE_VYSTUP_SAVE) {
                if (title) {
                    fprintf(info.fout, "<th>");
                    fprintf(info.fout, "%s", sx.c_str());
                    fprintf(info.fout, "</th>");

                } else {
                    fprintf(info.fout, "<td>");
                    fprintf(info.fout, "%s", sx.c_str());
                    fprintf(info.fout, "</td>");
                }

            } else {
                printf("%s", ss.c_str());
            }
        }

    }

    if (info.state == STATE_VYSTUP_SAVE) {
        fprintf(info.fout, "</tr>");
    }

    printf("\n");
    return "";
}


/**
 * zobrazi nastavene filtry
 * @param (appINFO) info = stav aplikace
 */
void list_filtr(appINFO info)
{
    printf(" filtry: %d", info.fmax);
    if (info.fmax > 0) {
        appINFO i;
        string  s;
        int     f = 0;

        i = info;
        for(int ii=0; ii<COLS_MAX; ii++) {
            info.cols[ii] = COL_NONE;
        }
        i.cmax = 1;

        printf(" (");
        for(int ii=0; ii<FILTER_MAX; ii++) {
            if (info.filtr[ii].col != COL_NONE) {
                i.cols[0] = info.filtr[ii].col;
                s = list_item(i, i.plist, false, true, true);

                if (f > 0) {
                    printf(", ");
                }
                printf("%s", s.c_str());
                f++;
            }
        }
        printf(")");
    }
    printf("\n");
}


/**
 * zobrazi strankovani
 * @param (appINFO) info = stav aplikace
 */
void list_paginator(appINFO info)
{
    int   imax = info.pmax/info.plimit;
    float fmax = (float)info.pmax/(float)info.plimit;

    if (fmax-imax > 0) {
        imax++;
    }

    printf("stranka: %d / %d, zaznamu na stranku: %d\n", (info.page+1), imax, info.plimit);
}


/**
 * tiskne/uklada html zahlavi
 */
void list_html_header(appINFO info)
{
    string s =
        "<html><head><title>" HTML_TITLE "</title>" \
        "<body>" \
        "<h1>" HTML_TITLE "</h1>" \
        "<blockquote><strong>copyright:</strong><br/>" \
            "<pre>" COPYRIGHT "</pre>" \
        "</blockquote>" \
        "<hr />";
    s = str_replace("<jan@smid.sk>", "&lt;jan@smid.sk&gt;", s);

    fprintf(info.fout, "%s", s.c_str());
}


/**
 * tiskne/uklada html zapati
 */
void list_html_footer(appINFO info)
{
    fprintf(info.fout,
        "</body></html>"
    );
}


/**
 * zobrazi vypis ~ tabulku
 * @param (appINFO) info = stav aplikace
 */
void list(appINFO info)
{
    carITEM *pfirst, *pcur;
    int i = 0, ii = 0, from, till;


    from = info.page * info.plimit;
    till = from + info.plimit;
    pcur = pfirst = info.plist;


    if (info.state == STATE_VYSTUP_SAVE) {
        fprintf(info.fout, "<table border=\"1\">");
        from = 0;
        till = info.pmax;
    }
    list_item(info, pfirst, false, true);

    while(pcur) {
        if (i >= from && i < till) {
            list_item(info, pcur);
            ii++;
        }

        i++;
        pcur = pcur->pnext;
    }

    if (info.state == STATE_VYSTUP_SAVE) {
        fprintf(info.fout, "</table>");

    } else {
        if (ii%info.plimit > 0) {
            for(int iii = 0; iii < info.plimit-(ii%info.plimit); iii++) {
                list_item(info, pfirst, true, false);
            }
        }
    }

}


