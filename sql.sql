select p.Positioncode,
       d.theDate,
       count(*) thecount,
       p.Showcount,
       wmsys.wm_concat(o.orderid)
  from orders o,
       product p,
       (select minDate + level - 1 theDate
          from (select min(startdate) minDate, max(enddate) maxDate
                  from orders)
        connect by level <= (maxDate - minDate + 1)) d
 where o.Positioncode = p.Positioncode
   and d.theDate between o.startdate and o.enddate
 group by d.theDate, p.Positioncode, p.Showcount
having count(*) > p.Showcount
 order by p.Positioncode, d.theDate;