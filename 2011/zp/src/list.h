/**
 *
 * list.cc, (c) <jan@smid.sk>
 * -- vypisy dat na obrazovku anebo do souboru (~hlavickovy soubor)
 *
 */


enum STR_PAD {STR_PAD_RIGHT, STR_PAD_LEFT, STR_PAD_BOTH};


string str_pad(const string &str, int pad_length, string pad_string=" ", STR_PAD pad_type=STR_PAD_RIGHT);
string& str_replace(const string &search, const string &replace, string &subject);
bool file_exists(const char * filename);
string list_item(appINFO info, carITEM *item, bool empty = false, bool title = false, bool ret = false);
void list_filtr(appINFO info);
void list_paginator(appINFO info);
void list_html_header(appINFO info);
void list_html_footer(appINFO info);
void list(appINFO info);


