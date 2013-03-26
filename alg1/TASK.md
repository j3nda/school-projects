
---[ en ]---------------------------------------------------------------------
Second award - Decomposition of the sum of Fibonacci numbers
Write a program that prints the decomposition of a natural number between 1 and
10,000 to the sum of Fibonacci numbers. Fibonacci numbers are entered recursive
Prescription:

F0 = 1
F1 = 1
Fn = Fn-1 + Fn-2 for n ≥ 2

In other words, the first two Fibonacci numbers are equal to one, the other is always
the sum of the previous two. Start sequence of these numbers is as follows:
1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765,. . .
For natural numbers apply theorem, which says that every natural number can be written
as the sum of certain Fibonacci numbers.
After starting the program, the user enters a number and your program prints the appropriate
decomposition. Examples of output from your program:

    1 = 1
    2 = 2
    3 = 3
    4 = 3 + 1
    5 = 5
    6 = 5 + 1
    7 = 5 + 2
    8 = 8
    9 = 8 + 1
 6263 = 4181 + 1597 + 377 + 89 + 13 + 5 + 1
10000 = 6765 + 2584 + 610 + 34 + 5 + 2

Notes
• Assume always correct input. Not bother to treating non-correctly entered values.
• Furthermore, the decomposition of natural numbers using the Fibonacci numbers is true that
    this decomposition occur two immediately consecutive Fibo-
    nacciho numbers. The reason is obvious. If such a decomposition in two numbers-you
    provide you, it would further their sum equaled Fibonaccimu number. This
    You really can serve to check that you have correctly calculate. So in-
    Example decomposition number 5 is only very figure 5 rather than 3 + 2 Similarly,
    decomposition occurs in each Fibonacci number exactly once. Thus ob-
    expressed in a similar decomposition number 4 is only possible as 3 + 1 rather than a 2 + 2 In this
    direction can be argued that in the Fibonacci sequence containing the two
    the same number, the first two grades. When the coding sequence of the
    considering only one leader, otherwise the decomposition impossible. They
    two ones are the default values ��for the calculation of the whole sequence
• Fibonacci numbers needed in the program you can always calculated at the beginning
    quote or read from a file or permanently stored in the field as a constant - I leave this to you.

---[ cz ]---------------------------------------------------------------------
2. zadání – Rozklad na součet Fibonacciho čísel
Napište program, který vypíše rozklad daného přirozeného čísla z intervalu 1 až
10000 na součet Fibonacciho čísel. Fibonacciho čísla jsou zadána rekurzivním
předpisem:
F0 =  1
F1 =  1
Fn = Fn−1 + Fn−2 pro n ≥ 2

Jinak řečeno první dvě Fibonacciho čísla jsou rovna jedné, další jsou vždy
součtem dvou předchozích. Začátek posloupnosti těchto čísel vypadá následovně:
1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, . . .
Pro přirozená čísla platí věta, která říká, že každé přirozené číslo lze zapsat
jako součet jistých Fibonacciho čísel.
Po spuštění programu, uživatel zadá číslo a Váš program vytiskne příslušný
rozklad. Ukázky výstupu z Vašeho programu:

    1 = 1
    2 = 2
    3 = 3
    4 = 3 + 1
    5 = 5
    6 = 5 + 1
    7 = 5 + 2
    8 = 8
    9 = 8 + 1
 6263 = 4181 + 1597 + 377 + 89 + 13 + 5 + 1
10000 = 6765 + 2584 + 610 + 34 + 5 + 2

Poznámky
     • Předpokládejte vždy správný vstup. Nezabývejte se ošetřováním ne-správně zadaných hodnot.
     • Dále pro rozklad přirozeného čísla pomocí Fibonacciho čísel platí, že se
         v tomto rozkladu nevyskytují dvě bezprostředně po sobě jdoucí Fibo-
         nacciho čísla. Důvod je zřejmý. Kdyby se v rozkladu taková dvě čísla vy-
         skytovala, tak by se jejich součet rovnal dalšímu Fibonaccimu číslu. Tento
         fakt Vám může sloužit pro kontrolu, že máte výpočet správně. Takže na-
         příklad rozklad čísla 5 je jedině samotné číslo 5, nikoliv 3 + 2. Stejně tak
         se v rozkladu vyskytuje každé Fibonacciho číslo právě jednou. Tudíž ob-
         dobně rozklad čísla 4 je možný pouze jako 3 + 1 nikoliv jako 2 + 2. V tomto
         směru můžete namítnout, že se ve Fibonacciho posloupnosti vyskytují dvě
         čísla stejná, první dvě jedničky. Při kódování se z uvedené posloupnosti
         uvažuje pouze jedna jednička, jinak by byl rozklad neproveditelný. Ony
         dvě jedničky jsou výchozí hodnoty pro výpočet celé posloupnosti
     • Fibonacciho čísla potřebná v programu si můžete vždy na začátku vypo-
         čítat nebo načíst ze souboru nebo napevno uložit do pole jako konstanty – toto nechám na Vás.
