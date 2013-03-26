<?php


define('FIBO_MAX_INDEX', 30);


// 1,1,2,3,5,8,13,21
function fibo_next($reset=false)
{
    static $a = 1, $b = 1;

    if ($reset) {
        $a = $b = 1;
    }

    $c = $a + $b;
    $a = $b;
    $b = $c;

    return $c;
}


// vraci pole fibonaciho cisel
function fibo2array($max=FIBO_MAX_INDEX)
{
    $a = array(1, 1);
    for($i=0; $i<$max; $i++) {
        $a[] = fibo_next($i == 0 ? true : false);
    }

    return $a;
}


// hleda cislo ve fibo cislech a vraci index v poli. pokud nema dane cislo, tak dospocte fibo cislo
function fibo2find($num, &$fibo)
{
    $idx = 0;
    $max = count($fibo)-1;
    do {

        $fnum = $fibo[$idx];
        $idx++;

        if (!isset($fibo[$idx])) {
            $fnext  = fibo_next();
            $fibo[] = $fnext;
        }

    } while ($fibo[$idx] < $num);

    return ($num == $fibo[$idx] ? $idx : ($idx-1));
}


// rozlozi cislo na soucet fibo cisel a vrati pole
function num2fibo($num, &$fibo=null, $fibo_max=FIBO_MAX_INDEX)
{
    if ($fibo === null || !is_array($fibo)) {
        $fibo = fibo2array($fibo_max);
    }

    $_ret = array();
    $_num = $num;
    while ($_num > 0) {
        $fidx = fibo2find($_num, $fibo);
        $_ret[] = $fibo[$fidx];
        $_num  -= $fibo[$fidx];
    }

    return $_ret;
}


//$fibo = fibo2array(30);
$fibo = fibo2array(3);
/*
print_r(num2fibo(1, $fibo));   //
print_r(num2fibo(2, $fibo));   //
print_r(num2fibo(3, $fibo));   //
print_r(num2fibo(4, $fibo));   //
print_r(num2fibo(5, $fibo));   //
print_r(num2fibo(6, $fibo));
print_r(num2fibo(7, $fibo));
print_r(num2fibo(8, $fibo));   //
print_r(num2fibo(9, $fibo));
print_r(num2fibo(6263, $fibo));
print_r(num2fibo(10000, $fibo));

echo implode(' ', $fibo)."\n";
*/

print_r(num2fibo(1));   //
print_r(num2fibo(2));   //
print_r(num2fibo(3));   //
print_r(num2fibo(4));   //
print_r(num2fibo(5));   //
print_r(num2fibo(6));
print_r(num2fibo(7));
print_r(num2fibo(8));   //
print_r(num2fibo(9));
print_r(num2fibo(6263));
print_r(num2fibo(10000));


?>
