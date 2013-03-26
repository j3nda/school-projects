---[ en ]---------------------------------------------------------------------
6th Unloading trains
Problem
Freight train arrives at the station, carrying wagons with different price cargo. Suppose that
wagon load consists of a certain number of items, each of which has a fixed price. Cars are divided
prices according to the cost of the siding from which are then gradually unloaded. Time unloading wagon
is proportional to the number of items of cargo.
Implementation






Implements Class Wagon, which will have four methods - the number PocetPolozek, CenaNakladu,
VylozitPolozku. In the constructor, enter the number of car, number of items and fixed price of one item.
Items will be randomly generated and it will be a natural number between 1 and P (eg P = 10). Fixed
price of one item, from C1 to C2 (eg C1 = 100, C2 = 1000). The maximum possible price-cost
du is P * C2. The account number is a natural number.
Tray implement a class for class objects Vagon using indicators. In the program you will have
four trays. The first will represent the arriving train. In the second tray bu-
dou sort freight cars with a price no more than one third of the maximum cost, the third ex-
Magazine wagon with a price no more than two-thirds and the fourth stack all the other cars.
Cycle generate first train (cassette), in which successively generate N wagons
(For example, N = 10). Numbers wagons will start one and gradually will increase by one.
Cycle simulate the distribution of cars on sidings. One step of the cycle will represent
remove one of the wagon train and its inclusion in the proper tray.
Cycle simulate the unloading of all cars. One step of the cycle will represent unloading
one item from each wagon on top of the stack. If not contain any wagon
item will be removed from the tray and connected back to the train. The cycle is finished when all
empty containers.
With numbers C1, C2, N, P, work as global constants.
The output display separated by a space following operations:




Generate new coach in the form of G # 1 [# 2] [# 3], where # is the number one car, # 2 is the number of items
# 3 is a fixed price per item. The third wagon with five skin and the price of one item 500 will you-
printed in the form G3 [5] [500].
Position carriage tray from # 1 [# 2], where # 1 is the number of car, number of the tray # 2 (1, 2, respectively. 3).
The third carriage in a third queue will be printed in the form of the Z3 [3]
Unloading one item from the car V # 1 [# 2], where # is the number one car, # 2 the number of remaining items.
Unloading one item from the third car, which remained five items will be printed in the form
V3 [4]
Removal of the carriage tray X # 1 [# 2], where # 1 is the number wagon # 2 tray number. The third wagon
in the third container will be printed in the form X3 [3].

---[ cz ]---------------------------------------------------------------------
6. Vyložení vlaků
Problém
Nákladní vlak, který přijíždí na nádraží, veze vagony s různou cenou nákladu. Předpokládejme, že
náklad vagonu se skládá z určitého počtu položek, z nichž každá má pevnou cenu. Vagony se rozdělí
podle ceny nákladu na vedlejší koleje, ze kterých jsou poté postupně vyloženy. Doba vyložení vagonu
je úměrná počtu položek nákladu.
Implementace






Implementuje třídu Vagon, která bude mít čtyři metody – Cislo, PocetPolozek, CenaNakladu,
VylozitPolozku. V konstruktoru zadejte číslo vagonu, počet položek a pevnou cenu jedné položky.
Počet položek bude náhodně generován a bude to přirozené číslo mezi 1 a P (např. P = 10). Pevná
cena jedné položky bude od C1 do C2 (např. C1 = 100, C2 = 1000). Maximální možná cena nákla-
du je tedy P*C2. Číslo účtu bude přirozené číslo.
Implementujte třídu Zasobnik pro objekty třídy Vagon pomocí ukazatelů. V programu budete mít
celkem čtyři zásobníky. První bude reprezentovat přijíždějící vlak. Do druhého zásobníku se bu-
dou řadit vagony s cenou nákladu nejvýše jedna třetina maximální ceny nákladu, do třetího zá-
sobníku vagonu s cenou nejvýše dvě třetiny a do čtvrtého zásobníku všechny ostatní vagony.
Pomocí cyklu nejdříve vygenerujte vlak (zásobník), do kterého postupně generujte N vagonů
(např. N = 10). Čísla vagonů budou začínat jedničkou a postupně se budou o jedničku zvyšovat.
Pomocí cyklu simulujte rozdělení vagonů na vedlejší koleje. Jeden krok cyklu bude reprezentovat
odebrání jednoho vagonu z vlaku a jeho zařazení do správného zásobníku.
Pomocí cyklu simulujte vyložení všech vagonů. Jeden krok cyklu bude reprezentovat vyložení
jedné položky z každého vagonu na vrcholu zásobníku. Pokud nebude vagon obsahovat žádnou
položku, bude odebrán ze zásobníku a připojen zpět k vlaku. Cyklus ukončete, až budou všechny
zásobníky prázdné.
S čísly C1, C2, N, P pracujte jako s globálními konstantami.
Na výstupu zobrazte odděleny mezerou tyto operace:




Vygenerování nového vagonu ve formě G#1[#2][#3], kde #1 je číslo vagonu, #2 je počet položek,
#3 je pevná cena jedné položky. Třetí vagon s pěti pokožkami a cenou jedné položky 500 bude vy-
tisknut ve tvaru G3[5][500].
Zařazení vagonu do zásobníku Z#1[#2], kde #1 je číslo vagonu, #2 číslo zásobníku (1, 2, resp. 3).
Třetí vagon ve třetí frontě bude vytisknut ve tvaru Z3[3]
Vyložení jedné položky z vagonu V#1[#2], kde #1 je číslo vagonu, #2 počet zbývajících položek.
Vyložení jedné položky z třetího vagonu, kterému zbývalo pět položek, bude vytisknuto ve tvaru
V3[4]
Odstranění vagonu ze zásobníku X#1[#2], kde #1 je číslo vagonu, #2 číslo zásobníku. Třetí vagon
ve třetím zásobníku bude vytisknut ve tvaru X3[3].
