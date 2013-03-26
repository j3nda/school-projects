<?php

    // MY-PROJECT, (c) <jan@smid.sk>
    // -- UDDB: project ~ scripts to re-generate all data
    // --
    // -- rev.0.11, 121206-1455jsm - add support for: ucitel x predmet x tridni_ucitel
    // -- rev.0.10, 121205-1627jsm - bugfix: *predmet => +predmet, +skplan
    // -- rev.0.9,  121205-1551jsm - bugfix: generovani_znamek_na_1x_pololeti
    // -- rev.0.8,  121205-1519jsm - add support for vicelete_gymnazium(~predmety)
    // -- rev.0.7,  121204-1537jsm - add support for pohlavi, tridni_ucitel
    // -- rev.0.6,  121203-1731jsm - add support for volitelne_predmety
    // -- rev.0.5,  121203-1643jsm - add support for maturitni_predmet
    // -- rev.0.4,  121203-1450jsm - add support for generate: skrok, student, znamka, vysvedceni
    // -- rev.0.3,  121129-1548jsm - add support for generateTrida(count_of_students)
    // -- rev.0.2,  121129-1504jsm - add support for: average number of students in one class-year and number of classes
    // -- rev.0.1,  121128-1647jsm - file created && add support for: get{Jmeno|Prijmeni}()


    date_default_timezone_set('Europe/Berlin');
    include_once './xsql2db_dibi.min.php';

dibi::connect(array(
    'driver'   => 'mysql',
    'host'     => 'localhost',
    'username' => 'devel',
    'password' => '******',
    'database' => '_d-skola_db1projekt',
));
function make_seed() { list($usec, $sec) = explode(' ', microtime()); return (float) $sec + ((float) $usec * 100000); }
mt_srand(make_seed());


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
function getPrijmeni($uniq=false, $fn='xsql2db_prijmeni.list') {
    static $list = array(), $max = array(), $random = array();

    $key = md5($fn);
    if (!isset($list[$key])) {
        $list[$key]   = explode("\n", file_get_contents(__DIR__.'/'.$fn));
        $max[$key]    = count($list[$key]);
        $random[$key] = array();
    }

    do {
        $r = mt_rand(0, ($max[$key]-1));
        if ($uniq && !in_array($r, $random[$key])) {
            $random[$key][] = $r;
            break;

        } else if (!$uniq) {
            break;
        }

    } while(1 == 1);

    return $list[$key][$r];
}

function getJmeno($uniq=false, $fn='xsql2db_jmena.list') {
    return getPrijmeni($uniq, $fn);
}

function getPoznamka($uniq=false, $fn='xsql2db_poznamky.list') {
    return getPrijmeni($uniq, $fn);
}

function getPredmet($rocnik, $replace=null, $vol=false, $rand=false, $fn='xsql2db_predmety.list') {
    static $list = null;

    if ($list === null) {
        $list = array();
        foreach(explode("\n", file_get_contents(__DIR__.'/'.$fn)) as $line) {
            if (preg_match('/^[[:blank:]]*$/', $line) || preg_match('/^[[:blank:]]*#/', $line)) {
                continue;
            }
            if (preg_match(
                    '/^[[:blank:]]*'
                   .'(\-|[0-9]+)[[:blank:]]+'    // 1: rocnik4
                   .'(\-|[0-9]+)[[:blank:]]+'    // 2: rocnik8
                   .'([ya]|[\-n])[[:blank:]]+'   // 3: volitelny?
                   .'([0-9]+)[[:blank:]]+'       // 4: cetnost         - vyjadruje miru znamek, tj. vyssi cislo vice znamek ~ mysleno na skupiny
                   .'(\-|[a-z0-9]+)[[:blank:]]+' // 5: group           - skupina volitelnych predmetu
                   .'([ya]|[\-n])[[:blank:]]+'   // 6: maturitni?
                   .'(.+)'                       // 7: nazev predmetu
                   .'$/i', $line, $tmp)
                ) {

                $key = (string)($tmp[1] != '-' ? $tmp[1] : ($tmp[2] != '-' ? $tmp[2] : false));
                    if (!isset($list[$key])) {
                        $list[$key] = array();
                    }

                $aaa = array();
                $aaa['volitelny'] = ($tmp[3] == 'Y' || $tmp[3] == 'y' ? true    : false);
                $aaa['group']     = ($tmp[5] != '-'                   ? $tmp[5] : false);
                $aaa['cetnost']   = $tmp[4];
                $aaa['maturitni'] = ($tmp[6] == 'Y' || $tmp[6] == 'y' ? true    : false);
                $aaa['nazev']     = $tmp[7];

                $list[$key][] = $aaa;
            }
        }

    } else if (!isset($list[(string)$rocnik])) {
        $list[(string)$rocnik] = array();
    }

    if ($replace !== null && is_array($replace) && !empty($replace)) {
        $list[(string)$rocnik] = $replace;

    } else if ($vol) {
        $v = array();

        if (is_string($vol) && !empty($vol)) {
            foreach($list[(string)$rocnik] as $item) {
                if ($item['group'] == $vol) {
                    $v[] = $item;
                }
            }

            if ($rand && isset($v[0]['__id'])) {
                return $v[mt_rand(0, count($v)-1)]['__id'];
            }

            return $v;
        }


        // volitelne predmety pro: 1x student x 1x rocnik
        switch($rocnik) {
            case '1': $a = array();
                          $a[] = getPredmet($rocnik, null, 'ev',  true);
                          $a[] = getPredmet($rocnik, null, 'cg1', true);
                      return $a;

            case '2': $a = array();
                        $a[] = getPredmet($rocnik, null, 'ev',  true);
                        $a[] = getPredmet($rocnik, null, 'cg1', true);
                        if (mt_rand(0, 999) % 2 == 0) { $a[] = getPredmet($rocnik, null, 'cg2', true); }
                      return $a;

            case '3': $a = array();
                        $a[] = getPredmet($rocnik, null, 'cg1', true);
                        if (mt_rand(0, 999) % 2 == 0) { $a[] = getPredmet($rocnik, null, 'cg2', true); }
                        $a[] = getPredmet($rocnik, null, 'v1',  true);
                        if (mt_rand(0, 999) % 2 == 0) { $a[] = getPredmet($rocnik, null, 'v2',  true); }
                        if (mt_rand(0, 999) % 5 == 0) { $a[] = getPredmet($rocnik, null, 'v3',  true); }
                      return $a;

            case '4': $a = array();
                        $a[] = getPredmet($rocnik, null, 'cg1', true);
                        if (mt_rand(0, 999) % 2  == 0) { $a[] = getPredmet($rocnik, null, 'cg2', true); }
                        $a[] = getPredmet($rocnik, null, 'v1',  true);
                        if (mt_rand(0, 999) % 2  == 0) { $a[] = getPredmet($rocnik, null, 'v2',  true); }
                        if (mt_rand(0, 999) % 5  == 0) { $a[] = getPredmet($rocnik, null, 'v3',  true); }
                        if (mt_rand(0, 999) % 7  == 0) { $a[] = getPredmet($rocnik, null, 'v4',  true); }
                        if (mt_rand(0, 999) % 11 == 0) { $a[] = getPredmet($rocnik, null, 'v5',  true); }
                      return $a;

            case '55':
            case '66':
            case '77':
            case '88':
            case '11':
            case '22':
            case '33':
            case '44':
                break;
        }
        return array();
    }

    return $list[(string)$rocnik];
}
function getZpetneRocnik($key, $sk_rok) {
    $a = mt_rand(0, $sk_rok);
    $b = ($a % 2 == 0 ? 1 : 0);
    $A = 15;    // 4lete start v 1. rocniku
    $B = 11;    // 8lete start v prime
    switch($key) {
        // 4lete
        case '1':  return array(0, null, $A+0+$b, 0);
        case '2':  return array(1, '1',  $A+1+$b, 0);
        case '3':  return array(2, '2',  $A+2+$b, 0);
        case '4':  return array(3, '3',  $A+3+$b, 1);

        // 8mi lete
        case '55': return array(0, null, $B+0+$b, 0);
        case '66': return array(1, '55', $B+1+$b, 0);
        case '77': return array(2, '66', $B+2+$b, 0);
        case '88': return array(3, '77', $B+3+$b, 1);

        case '11': return array(4, '88', $B+4+$b, 0);
        case '22': return array(5, '11', $B+5+$b, 0);
        case '33': return array(6, '22', $B+6+$b, 0);
        case '44': return array(7, '33', $B+7+$b, 0);
    }
    return null;    // array(zpetne-let; oznaceni; let(~vek)-studenta; maturitni)
}


// ---------------------------------------------------------------------------
function truncateSkola() {
    $db = dibi::getDatabaseInfo()->getName();
    foreach(dibi::query("SHOW TABLES;")->fetchAll() as $item) {
        dibi::query("TRUNCATE [".$item['Tables_in_'.$db]."];");
    }
}

function generatePredmet($rocnik, $config) {
    static $uniq = array(), $UNIQ = array();

    if (!in_array($rocnik, $uniq)) {
        echo "\n\n-- ----------\n-- ".__FUNCTION__.": pro rocnik: ${rocnik}\n";
        $uniq[] = $rocnik;
        $list   = getPredmet($rocnik);
        foreach($list as &$item) {
            $insert = array(
                'pro_rocnik' => $config['rocnik'][$rocnik],
                'volitelny'  => $item['volitelny'],
                'maturitni'  => $item['maturitni'],
                'skupina'    => ($item['group'] ? $item['group'] : null),
                'nazev'      => trim($item['nazev']),
            );
            echo "--\t".($insert['skupina'] ? "  vol(".$insert['skupina']."):\t" : '').$item['nazev']."\n";

            if (!isset($UNIQ[$insert['nazev']])) {
                $INSERT = array(
                    'nazev' => $insert['nazev'],
                );
                dibi::query("INSERT INTO [predmet]", $INSERT);
                $item['__idP']          = dibi::InsertId();
                $UNIQ[$insert['nazev']] = $item['__idP'];
            }

            $insert['predmet_id'] = $UNIQ[$insert['nazev']];
            unset($insert['nazev']);

            dibi::query("INSERT INTO [skplan]", $insert);
            $item['__id'] = dibi::InsertId();

        }
        getPredmet($rocnik, $list);
    }
}

function generateUcitel($config, $add=false) {
    $sk_rok   = $config['sk_rok'];
    $max_s    = $config['zaku_ve_skole'];
    $max_u    = ceil($max_s / $config['ucitel2student']);

    $PREDMETY = dibi::query("SELECT DISTINCT [nazev] FROM [predmet] ORDER BY [nazev];")->fetchAll();
    $MAX_P    = count($PREDMETY);

    static $predmety = null;

        if ($predmety === null) {
            $predmety = $PREDMETY;
        }


    // dodatecne pridavam ucitele
    if ($add) {
        $max_u = (int)$add;

    } else {
        $config['predmet2ucitel'] = array();
        $config['ucitele']        = array();
        $config['UCITELE']        = array();
        echo "\n\n-- ----------\n-- ".__FUNCTION__.": ".$max_u." na cca #".$max_s." studentu\n";
    }


    for($i=0; $i<$max_u; $i++) {
        $p2u      = array();    // predmet x ucitel
        $i_ucitel = array(
            'jmeno'          => getJmeno(),
            'prijmeni'       => getPrijmeni(),
            'datum_narozeni' => sprintf('%d-%02d-%02d', $sk_rok-26-mt_rand(0, 72-26), mt_rand(1, 12), mt_rand(1, 28)),
            'pohlavi'        => $config['pohlavi'][(mt_rand(0, 999) % $config['pohlavi.len'])],
        );

        echo "--\t".$i_ucitel['jmeno']." ".$i_ucitel['prijmeni']."; narozen/a: ".$i_ucitel['datum_narozeni']."\n";

        dibi::query("INSERT INTO [ucitel]", $i_ucitel);
        $id_u = dibi::InsertId();
        $i_p  = mt_rand($config['ucitel2predmet'][0], $config['ucitel2predmet'][1]);

        $config['ucitele'][]      = $id_u;
        $config['UCITELE'][$id_u] = $i_ucitel;

        for($j=0; $j<$i_p; $j++) {
            $pp  = array();
            $m_p = count($predmety);
            $p   = mt_rand(0, $m_p-1);

            $p2u[$predmety[$p]['nazev']] = $id_u;

            for($jj=0; $jj<$m_p; $jj++) {
                if ($jj != $p) {
                    $pp[] = $predmety[$jj];
                }
            }
            $predmety = $pp;
            if (empty($pp)) {
                $predmety = $PREDMETY;
            }
        }
        echo "--\t\t".implode("\n--\t\t", array_keys($p2u))."\n";

        foreach($p2u as $k => $v) {
            if (!isset($config['predmet2ucitel'][$k])) {
                $config['predmet2ucitel'][$k] = array();
            }
            $config['predmet2ucitel'][$k][] = $v;
        }
    }

    return $config;
}

function generateTrida($rocnik, $oznaceni, $max, $info, $config, $backwards=null) {
    $sk_rok   = $config['sk_rok'];
    $studenti = array();
        if ($backwards) {
            $id_t_last = $backwards[0];
            $sk_rok    = $backwards[1];
            $id_tridni = $backwards[2];
            $studenti  = $backwards[3];
        }
    $zpetne = getZpetneRocnik($rocnik, $sk_rok);
        if (!$zpetne) {
            throw new Exception('FATAL: $zpetne is wrong!');
        }

    echo "\n\n-- ----------\n-- ".__FUNCTION__.": ".$sk_rok.'/'.($sk_rok+1)." ~ trida: ".$config['rocnik'][$rocnik].".${oznaceni} ~ studentu: ${max}".($backwards ? " ~ ZPETNE" : "")."\n";
    $SK_ROK = $sk_rok;
    $SK_ROKY = array(
        sprintf('%d-09-01', $SK_ROK),
        sprintf('%d-01-01', ($SK_ROK+1)),
    );


    // INSERT NEW [trida]
    $i_trida = array(
        'tridni_ucitel_id' => (isset($id_tridni) ? $id_tridni : ($id_tridni=0)),
        'rocnik_4'         => ( in_array((int)$rocnik, array(1,2,3,4)) ? ($zpetne[0]+1) : null),
        'rocnik_8'         => (!in_array((int)$rocnik, array(1,2,3,4)) ? ($zpetne[0]+1) : null),
        'oznaceni'         => $oznaceni,
    );
    dibi::query("INSERT INTO [trida]", $i_trida);
    $id_t = dibi::InsertId();
    if ($backwards) {
        dibi::query("UPDATE [trida] SET [predchozi_rocnik_trida_id]=".$id_t." WHERE [id]=".$id_t_last." LIMIT 1;");

    } else {
        static $TRIDNI = array();

        $mm = count($config['ucitele']);
        do {
            $id = $config['ucitele'][mt_rand(0, $mm-1)];

        } while (in_array($id, $TRIDNI));
        $TRIDNI[]  = $id;
        $id_tridni = $id;
        echo "--\ttridni: ".$id_tridni." ~ ".$config['UCITELE'][$id_tridni]['jmeno'].' '.$config['UCITELE'][$id_tridni]['prijmeni'].'; narozen/a: '.$config['UCITELE'][$id_tridni]['datum_narozeni']."\n";
        dibi::query("UPDATE [trida] SET [tridni_ucitel_id]=".$id_tridni." WHERE [id]=".$id_t." LIMIT 1;");
    }


    // predmety => cetnost
    $predmet = array();
    foreach(getPredmet($rocnik) as $item) {
        $predmet[$item['__id']] = $item['cetnost'];
    }


    // INSERT NEW [student]
    for($i=0; $i<$max; $i++) {
        if (!isset($id_t_last)) {
            $i_student = array(
                'jmeno'            => getJmeno(),
                'prijmeni'         => getPrijmeni(),
                'pohlavi'          => $config['pohlavi'][(mt_rand(0, 999) % $config['pohlavi.len'])],
                'datum_narozeni'   => sprintf('%d-%02d-%02d', $sk_rok-$zpetne[2], mt_rand(1, 12), mt_rand(1, 28)),
            );
            echo "--\t[${i}/${max}]\t".$i_student['jmeno'].' '.$i_student['prijmeni'].'; narozen/a: '.$i_student['datum_narozeni']."\n";
            dibi::query("INSERT INTO [student]", $i_student);
            $id_s       = dibi::InsertId();
            $studenti[] = $id_s;

        } else {
            $id_s = $studenti[$i];
        }


        // student x predmet x znamka
        $volitelne = getPredmet($rocnik, null, true);   // volitelne predmety v ramci 1x rocniku
        $pololeti  = true;
        $POLOLETI  = -1;
        $buffer    = array(
            'znamka' => array(),
            'skrok'  => array(),
            'vysv'   => array(),
            'mat'    => array(),
        );
        foreach($SK_ROKY as $item_skrok) {
            $tt       = 0;
            $iskrok   = substr($item_skrok, 0, 4);
            $pololeti = !$pololeti;
            $POLOLETI++;


            echo "--\t[${i}/${max}]\t  +znamky(${item_skrok}): ";
            // INSERT NEW [znamka] (~per student x predmet)
            foreach(getPredmet($rocnik) as $item_predmet) {
                $P2U = $config['predmet2ucitel'][$item_predmet['nazev']];
                $p2u = $P2U[mt_rand(0, count($P2U)-1)];

                // povinne && volitelne
                if (
                    !$item_predmet['volitelny'] ||
                    ($volitelne && !empty($volitelne) && in_array($item_predmet['__id'], $volitelne))
                   ) {

                    $vysv  = array(0, 0);
                    $pocet = mt_rand(1, 1+(mt_rand(0, $item_predmet['cetnost'])));   // pocet znamek vs cetnost predmetu
                    $zzzzz = array(0, 25, 50, 75);
                    $ZZZZZ = count($zzzzz)-1;
                    $aaaaa = strtotime($iskrok.$config['znamka2time'][$POLOLETI][0]);
                    $bbbbb = strtotime($iskrok.$config['znamka2time'][$POLOLETI][1]);
                    $ppppp = (mt_rand(0, 10000) % 89 /*137,31*/ == 0 ? getPoznamka() : null);
                    for($ii=0; $ii<$pocet; $ii++) {
                        $zn    = ($a=mt_rand(1, 5)).".".($a == 5 ? 0                          : $zzzzz[mt_rand(0, $ZZZZZ)]);
                        $va    = ($a=mt_rand(0, 2)).".".($a == 0 ? $zzzzz[mt_rand(1, $ZZZZZ)] : $zzzzz[mt_rand(0, $ZZZZZ)]);

                        $vysv[0] += $zn*$va;
                        $vysv[1] += $va;

                        $buffer['znamka'][] = array(
                            'znamka'     => $zn,
                            'vaha'       => $va,
                            'datum'      => date('Y-m-d', mt_rand($aaaaa, $bbbbb)),
                            'student_id' => $id_s,
                            'skplan_id'  => $item_predmet['__id'],
                            'poznamka'   => $ppppp,
                        );

                        echo ($ppppp ? "+" : ".");
                        $tt++;
                    }

                    // skrok: trida x student x predmet x ucitel
                    $buffer['skrok'][] = array(
                        'datum'      => $item_skrok,
                        'skplan_id'  => $item_predmet['__id'],
                        'trida_id'   => $id_t,
                        'student_id' => $id_s,
                        'ucitel_id'  => $p2u,
                    );

                    // vysvedceni
                    $buffer['vysv'][] = array(
                        'skrok_datum' => $item_skrok,
                        'pololeti'    => (int)$pololeti,
                        'maturitni'   => 0,
                        'znamka'      => round($vysv[0] / $vysv[1]),
                        'skplan_id'   => $item_predmet['__id'],
                        'student_id'  => $id_s,

                    );
                } // END OF: if(!volitelny)
            } // END OF: foreach(znamka)
            echo "[${tt}x]\n";
        } // END of: foreach(1. a 2. pololeti)
        dibi::query("INSERT INTO [znamka]",     "%ex", $buffer['znamka']);
        dibi::query("INSERT INTO [skrok]",      "%ex", $buffer['skrok']);
        dibi::query("INSERT INTO [vysvedceni]", "%ex", $buffer['vysv']);


        // TODO: resit neklasifikovani studenta?


        // maturitni vysvedceni (posl. rocniky)
        if ($zpetne[3]) {
            static $predmet_mat = null;

            $predmet_MAT = array();
            if ($predmet_mat === null) {
                foreach($config['rocnik'] as $pm_key => $pm_value) {
                    foreach(getPredmet($pm_key) as $pm_item) {
                        if ($pm_item['maturitni']) {
                            $predmet_mat[] = $pm_item['__id'];
                        }
                    }
                }
            }

            $is_vicelete = (strlen($rocnik) == 2 ? true : false);
            foreach($predmet_mat as $pm_key => $pm_item) {
                if (
                    ( $is_vicelete && strlen($pm_key) == 2) ||
                    (!$is_vicelete && strlen($pm_key) == 1)
                   ) {

                    $predmet_MAT[] = $pm_item;
                }
            }

            $m_max = mt_rand(4, 5); // pocet predmetu na maturitu 4,5
            $m_uni = array();       // unikatni mat. predmety
            $m_cnt = count($predmet_MAT)-1;

            while (count($m_uni) < $m_max) {
                while(true) {
                    $m_pp = mt_rand(0, $m_cnt);
                    if (!in_array($m_pp, $m_uni)) {
                        $m_uni[] = $m_pp;
                        break;
                    }
                }
            }

            echo "--\t[${i}/${max}]\t  +matur (${item_skrok}): ";
            foreach($m_uni as $m_item) {
                $m_z = mt_rand(1, 4);
                if ($m_z == 4) {
                    $m_z = mt_rand(1, 4);
                    if ($m_z == 4) {
                        $m_z = mt_rand(1, 4);
                    }
                }

                echo " ".$m_z;
                $buffer['mat'][] = array(
                    'skrok_datum' => $SK_ROKY[0],
                    'pololeti'    => 0,
                    'maturitni'   => 1,
                    'znamka'      => (int)$m_z,
                    'skplan_id'   => $m_item,
                    'student_id'  => $id_s,
                );
            }
            dibi::query("INSERT INTO [vysvedceni]", "%ex", $buffer['mat']);
            echo "\n";
        } // END OF: if(maturita)

    } // END OF: studenti-list


    // generuju tridu o 1x rocnik zpetne, abych mel jeji celkovy prubeh...
    if ($zpetne[0] > 0) {
        generatePredmet($zpetne[1], $config);
        generateTrida($zpetne[1], $oznaceni, $max, $info, $config, array($id_t, $sk_rok-1, $id_tridni, $studenti));
    }


    // TODO: tridni ucitel
    // TODO: seznam ucitelu
    // TODO: ucitel x predmet x sk_rok

}

function generateSkola($c) {
    $max_s        = $c['zaku_ve_skole'];
    $max_r        = count($c['rocnik']);
    $tot          = 0;
    $c['rocniky'] = array();

    // rozlozeni zaku ve skole v jedn. rocnicich
    echo "\n\n-- ----------\n-- ".__FUNCTION__.": pocet studentu ve skole: ${max_s}\n";
    echo "-- ".__FUNCTION__.": rozlozeni studentu v rocnicich\n";
    foreach($c['rocnik'] as $k => $v) {
        $rocnik = $k;

        $odch   = $c['zaku_ve_tride'][1]-mt_rand($c['zaku_ve_tride'][0], $c['zaku_ve_tride'][1]);
        $pocet  = mt_rand(
                    ($max_s/$max_r) - $odch,
                    ($max_s/$max_r) + $odch
        );
        $pocetT = round($pocet/mt_rand($c['zaku_ve_tride'][0], $c['zaku_ve_tride'][1]));

        $c['rocniky'][$rocnik] = array($pocet, $pocetT, $odch);    // (zaku x rocnik; pocet trid x rocnik; odchylka)
        $tot   += $pocet;

        echo "-- \t".str_pad("${v}.", 8)." = ${pocet} (trid: ${pocetT})\n";
    }
    echo "-- TOTAL = ${tot}"
            .($max_s != $tot
                ? ($tot > $max_s
                    ? " (prebyva: +".($tot-$max_s).")"
                    : ($tot < $max_s
                        ? " (chybi: ".($tot-$max_s).")"
                        : ""
                      )
                  )
                : ''
             )."\n";


    // tvorim jednotlive tridy s jasne danym studijnim obsahem
    echo "\n\n-- ----------\n-- ".__FUNCTION__.": tvorba vsech trid\n";
    $_oznac = $_ozNAC = $_fff = array();

    foreach($c['rocniky'] as $k => $v) {
        generatePredmet($k, $c);
    }
    $c = generateUcitel($c);


    foreach($c['rocniky'] as $k => $v) {
        if (!isset($_fff[$k]))   { $_fff[$k]   = true; }
        if (!isset($_oznac[$k])) { $_oznac[$k] = 0;    }
        if (!isset($_ozNAC[$k])) { $_ozNAC[$k] = 1;    }

        $fff   = &$_fff[$k];
        $oznac = &$_oznac[$k];
        $ozNAC = &$_ozNAC[$k];

        $zaku  = $v[0];
        $tot   = 0;
        $i     = 0;
        while ($zaku > 0) {
            $i++;
            $stud  = mt_rand($c['zaku_ve_tride'][0], $c['zaku_ve_tride'][1]);
            $zaku -= $stud;
            if ($zaku > 0) {
                if ($zaku < 15) { //$c['zaku_ve_tride'][0])
                    $stud += $zaku;
                    $zaku -= $zaku;
                }
            }
            $stud += ($zaku < 0 ? $zaku : 0);
            $tot  += $stud;
            $oznac++;
                if ($fff) {
                    $oznac--;
                }

                if ($oznac % strlen($c['oznaceni']) == 0 && !$fff) {
                    $oznac = 0;
                    $ozNAC++;
                }

            $fff   = false;
            $OZNAC = substr($c['oznaceni'], $oznac, 1).($ozNAC > 1 ? '-'.$ozNAC : '');
            echo "\n-- ".__FUNCTION__.": tvorba tridy: ${k}[${i}x/${v[1]}]., studentu: ${stud} ~ (zbyva/kumulace/total) x (${zaku}/${tot}/${v[0]})\n";

            // vice trid nez ucitelu
            $max_u = count($c['ucitele']);
            if ($max_u < $i) {
                $c = generateUcitel($c, 1);
            }

            generateTrida($k, $OZNAC, $stud, $v, $c);
        }
    }

}

// ===========================================================================
//
    $config = array(
        'zaku_ve_skole'   => ($argc > 1 ? $argv[1] : null), // udava mohutnost skoly, tj. pocet trid x predmetu x ucitelu
        'sk_rok'          => ($argc > 2 ? $argv[2] : date('Y')),
        'zaku_ve_tride'   => array(19, 25),                 // roptyl: min, max
        'rocnik'          => array(
            '1'  => 1,        '2'  => 2,         '3'  => 3,         '4'  => 4,
            '55' => 'Prima',  '66' => 'Sekunda', '77' => 'Tercie',  '88' => 'Kvarta',
            '11' => 'Kvinta', '22' => 'Sexta',   '33' => 'Septima', '44' => 'Oktáva',
        ),
        'pohlavi'         => 'MZZMMMZZZMZMZMMZMZZMZMZZMMZZMMZZMZMZMZMM',
        'pohlavi.len'     => null,
        'oznaceni'        => 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
        'ucitel2student'  => mt_rand(13, 16),   // 1x ucitel na cca 13-16 zaku
        'ucitel2predmet'  => array(1, 3),       // 1x ucitel uci vice predmetu
        'znamka2time'     => array(
            0 => array('-09-01', '-12-31'),
            1 => array('-01-01', '-06-30'),
        ),
    );
    $config['pohlavi.len'] = strlen($config['pohlavi']);


    if ($config['zaku_ve_skole'] === null) {
        // INFO: sorry you must specified number of students in whole school! && exit (~default: 333 for this moment!)
        $config['zaku_ve_skole'] = 333;   // 1x trida na vsechny rocniky cca
    }


truncateSkola();
generateSkola($config);

// seznam ucitelu

// 1x student <=> 1x trida <=> 1x tridni ucitel
// 1x trida   <=> N predmetu << ZDE JE PRAVIDLO, ze napr. matematiku
// 1x predmet <=> N ucitelu

