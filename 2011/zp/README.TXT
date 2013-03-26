==============================================================================
STRUKTURA

    data/      = soubor(y) s daty (~.csv)
    docs/      = programatorska dokumentace (generovana dogyxen-em)
    img/       = adresar s obrazky aut
    output/    = adresar. kam generuji vystupni html
    src/       = zdrojove kody

    README.TXT = tento readme soubor


==============================================================================
UZIVATELSKA DOKUMENTACE

    viz soubor: UZIVATELSKA-DOKUMENTACE.TXT

==============================================================================
PROGRAMATORSKA DOKUMENTACE

    viz soubor: docs/index.html

==============================================================================
DATA

data byla ziskana z realneho projektu ~ webova prezentace (jedne nejmenovane) tunningove firmy

z tabulek:
    rs_auto
    rs_auto_model
    rs_auto_motor
    rs_auto_znacka

byly za pomoci sql dotazu a nastroje phpMyAdmin ziskany data a ulozena do '.csv'
souboru v kodovani windows-1250 a nasledne pomoci iconv prevedeny na csascii.

==============================================================================
vyber z db
------------------------------------------------------------------------------
select
  z.nazev,
  --  z.popis, z.historie,
    m.nazev, m.druh, m.oznaceni, m.rok_od, m.rok_do,
      r.nazev, r.objem, r.palivo, r.jednotka, r.kw, r.kw_rpm, r.nm, r.nm_rpm, r.rok_od, r.rok_do
      from rs_auto_motor as r

      left join rs_auto_model as m on m.id=r.idrs_auto_model
      left join rs_auto_znacka as z on z.id=m.idrs_auto_znacka

      where m.jazyk='cz'

------------------------------------------------------------------------------
