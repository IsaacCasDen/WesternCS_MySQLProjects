
# 195
# Ch4 
# CustomersAndProducts - 1 to N Joins
# 1,2,4,5,8,10
# Isaac Denney

#
# 1
#

use customersandproducts;

select 
	c.customerName,
	o.orderDate
from
	orders o
left join
	customers c
on
	o.customerId=c.customerId;

#
# 2
#

use customersandproducts;

select 
	c.customerName,
	o.orderDate
from
	orders o
left join
	customers c
on
	o.customerId=c.customerId
where
	c.customerName='Maximum Leadership';

#
# 4
#

use customersandproducts;

select
    l.productId
from
	orders o
left join
	orderlines l
on
	o.orderId=l.orderId
where
	o.customerId='SCI2000';


#
# 5
#

use customersandproducts;

select distinct
    l.productId
from
	orders o
left join
	orderlines l
on
	o.orderId=l.orderId
where
	o.customerId='SCI2000';


#
# 8
#

use customersandproducts;

select
	l.productId,
    l.quantity
from 
	orders o
left join
	orderlines l
on
	o.orderId=l.orderId
where
	o.orderDate>='2/5/2006';

#
# 10
#

#What products were ordered by which customers in february 2006?

use customersandproducts;

select
	c.customerName,
    o.orderDate,
    p.productName,
    p.price,
    p.weight,
    l.quantity
from 
	orders o
left join
	orderlines l
on
	o.orderId=l.orderId
left join
	products p
on
	p.productId=l.productid
left join 
	customers c
on
	c.customerId=o.customerId
where
	o.orderDate >= '2/5/2006' and 
    o.orderDate < '3/1/2006'
order by
	customerName,
    orderDate