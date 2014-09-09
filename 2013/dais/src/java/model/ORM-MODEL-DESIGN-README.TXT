ORM-MODEL-DESIGN-README.TXT, (c) <jan@smid.sk>
==============================================
zdroje:
	-- http://www.phpguru.cz/clanky/pet-vrstev-modelu
	-- http://dbedu.cs.vsb.cz (Databázové a informační systémy)

overview:
	1 service
	2 entity                         # prepravka na data ~ settery/gettery. logicka entita zaobalujici konkretni data
	3 repository -> dao ~ repository # controller <--> repository (vnitrne resi ktery mapper zavola, popr. jak slouci data)
	4 mapper      +- mapper          # ukladani z/do storage - vstup:objekt <--> vystup:crud <--> storage
	                 +- crud         # CRUD ~ abstraktni mapovani: create/read/update/delete (~rozsirene o: CRUDSEL ~ .../search/exists/list)
	5 storage           +- storage   # fyzicke uloziste: MySQL, Oracle, SQLite, XML, Filesystem, ...


-
repository
    > Byla by ale chyba, pokud bychom se přímo v controlleru aplikace rozhodovali, do kterého uložiště chceme článek uložit, kdybychom v controlleru vytvářeli instanci nějakého konkrétního mapperu. Pokud bychom se totiž například později rozhodli, že chceme místo databáze ukládat články do souborů, museli bychom to přepsat na mnoha místech aplikace.
    > Proto se mezi controller a mapper vkládá ještě jedna vrstva – repository, která nás odstiňuje od konkrétního použitého mapperu. V controlleru pak nikdy nevoláme přímo konkrétní mapper, ale vždy repository:
    > Repository v nejjednodušším případě funguje jako mechanický předavač povelů. Nabízí navenek stejné funkce, jako mappery, akorát se až teprve sám uvnitř sebe rozhoduje, kterému mapperu povel předá. Změna konkrétního mapperu pak může být otázkou přepsání jedné proměnné uvnitř repository.
    > Takové vyčlenění samostatné repository nám pak ale umožňuje dělat mnohem zajímavější věci. Například snadno mohu před databázi předsunout cachování do memcache. 
    -- http://www.phpguru.cz/clanky/pet-vrstev-modelu#comment-6637

service
    > V controllerech pracujeme s instancemi entit
    > A pokud něco potřebujeme načíst, uložit nebo smazat, požádáme o to příslušnou repository
    > dost silná závislost rozhraní modelu na jednotlivých proměnných entity
    > Servisy jsou vlastně fasády postavené nad různými entitami.
-



p = new PremetDAO(new PredmetEntity)

p->setEntity | getEntity << vrati entity se kterou normalne manipuluju


TDG Table Data Gateway ~ 1x tabulka ~ obsahuje kolekce dat
RDG	Row   Data Gateway ~ 1x radek << naplneni daty: Finder



mapper:
	entity -> crud -> storage

Predmet -> new TableRow -> insert|udpate|delete