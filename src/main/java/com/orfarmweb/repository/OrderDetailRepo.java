package com.orfarmweb.repository;

import com.orfarmweb.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
    @Query(value = "select *" +
            "from order_detail as od left join orders as o " +
            "on od.order_id = o.id left join product as p " +
            "on od.product_id = p.id order by create_at desc LIMIT 0, 10", nativeQuery = true)
    List<OrderDetail> getTopOrder();

    @Query(value = "select sum(quantity) from order_detail where order_id = ?", nativeQuery = true)
    Integer getTotalProduct(int id);

    @Query(value = "select * from order_detail left join orders on order_detail.order_id=orders.id where orders.status = 3", nativeQuery = true)
    List<OrderDetail> getListRevenueOrder();

    @Query(value = "select sum(quantity) " +
            "from order_detail " +
            "left join orders " +
            "on order_detail.order_id = orders.id where order_id = :id and create_at between :start and :end", nativeQuery = true)
    Integer getTotalProductByFilterAndOrderId(int id, Date start, Date end);

    @Query(value = "select sum(quantity) from order_detail left join orders on order_detail.order_id =orders.id order where order_id = ?1 and status.?2", nativeQuery = true)
    Integer getTotalProductByOrdersIdAndStatus(int id, int status);
}
