-- 1;1;38
select * from predmet;

-- 1;2;23
select * from ucitel;

-- 1;3;46
select id,rocnik_4,rocnik_8 from trida;

-- 1;4;221
select distinct jmeno from student;


-- 2;1;38
select * from predmet order by nazev;

-- 2;2;23
select * from ucitel order by prijmeni ASC, jmeno DESC;

-- 2;3;46
select id,rocnik_4,rocnik_8,oznaceni from trida order by oznaceni ASC;


-- 3;1;12
select * from ucitel where pohlavi='M';

-- 3;2;168
select * from student where pohlavi='Z' order by prijmeni DESC, jmeno ASC;

-- 3;3;116
select * from student where datum_narozeni >= '1993-01-01' and datum_narozeni <= '1997-12-31' and pohlavi='M';

-- 3;4;50
select * from student where datum_narozeni >= '1993-01-01' and datum_narozeni <= '1997-12-31' and pohlavi='M' order by datum_narozeni desc limit 50;

-- 3;5;601
select * from znamka where poznamka is not null;


-- 4;1;1
select * from student where datum_narozeni >= '1993-01-01' and datum_narozeni <= '1997-12-31' and pohlavi='M' and jmeno like 'Jan%' order by datum_narozeni desc limit 50;

-- 4;2;35
select * from student where pohlavi='Z' and jmeno like '%an%';

-- 4;3;4
select * from student where jmeno in ('Jan', 'Alan', 'Hana') order by jmeno;


-- 5;1;5
select * from student where jmeno like '%en' union select * from student where prijmeni like '%VAN%'


-- 6;1;1
select sum(znamka) / count(*) as prumerna_znamka_bez_vah from znamka;

-- 6;1;1
select sum(znamka) / count(*) as prumerna_znamka_bez_vah_kde_je_poznamka from znamka where poznamka is not null;


-- 7;1;11
select * from znamka group by vaha;

-- 7;2;1137
select rok.datum, s.jmeno, s.prijmeni, p.nazev, u.jmeno, u.prijmeni from skrok as rok join skplan as plan on plan.id=rok.skplan_id join ucitel as u on u.id=rok.ucitel_id join student as s on s.id=rok.student_id join predmet as p on p.id=plan.predmet_id where plan.maturitni=1 group by s.prijmeni, u.prijmeni

-- 7;3;38
select p.nazev, plan.pro_rocnik from predmet as p join skplan as plan on plan.predmet_id=p.id group by p.nazev


-- 8;1;12
select * from trida as t join ucitel as u on u.id=t.tridni_ucitel_id where t.tridni_ucitel_id is not null and t.predchozi_rocnik_trida_id is null

-- 8;2;2660
select rok.datum, s.jmeno, s.prijmeni, p.nazev, u.jmeno, u.prijmeni from skrok as rok join skplan as plan on plan.id=rok.skplan_id join ucitel as u on u.id=rok.ucitel_id join student as s on s.id=rok.student_id join predmet as p on p.id=plan.predmet_id where plan.maturitni=1

-- 8;3;464
select p.nazev, plan.pro_rocnik from predmet as p join skplan as plan on plan.predmet_id=p.id


-- 9;1;5
select * from vysvedceni where pololeti=0 and maturitni=1 and student_id in (select id from student where jmeno like 'Jan%')

-- 9;2;18
select * from skplan where skupina in (select distinct skupina from skplan where skupina is not null) and maturitni=1


-- 10;1;1430
select rok.datum, s.jmeno, s.prijmeni, p.nazev, u.jmeno, u.prijmeni from skrok as rok join skplan as plan on plan.id=rok.skplan_id join ucitel as u on u.id=rok.ucitel_id join student as s on s.id=rok.student_id join predmet as p on p.id=plan.predmet_id where plan.maturitni=1 and s.id in (select distinct student_id from znamka where znamka=1 and vaha > 2.5)
