package com.orfarmweb.entity;

import com.orfarmweb.constaint.Status;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Status status;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @CreatedDate
    private Date createAt;
    private Float totalPrice;
    private String address;
    private String phoneNumber;
    private String note;
    @OneToMany(targetEntity = OrderDetail.class, mappedBy = "orders")
    private Set<OrderDetail> orderDetails;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
