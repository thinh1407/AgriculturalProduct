package com.orfarmweb.entity;

import com.orfarmweb.constaint.Role;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @NotEmpty(message = "Không được để trống tên")
    private String firstName;
//    @NotEmpty(message = "Không được để trống tên")
    private String lastName;
    @Column(unique = true)
    @NotNull
//    @NotEmpty(message = "Không được để trống email")
//    @Email(message = "Email không hợp lệ")
    private String email;
    @NotNull
//    @NotEmpty(message = "Thiếu password")
    private String password;
    private String address;
    private String phoneNumber;
    private Role role;
    @OneToMany(targetEntity = Cart.class, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Cart> cart;
    @OneToMany(targetEntity = Orders.class, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Order> orders;

    public String getName() {
        return lastName +" "+ firstName;
    }
}
