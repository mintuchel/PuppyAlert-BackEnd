package seominkim.puppyAlert.domain.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import seominkim.puppyAlert.domain.shop.entity.Shop;
import seominkim.puppyAlert.global.entity.Location;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Market {
    @Id @GeneratedValue
    @Column(name="market_id")
    private int id;

    @Column(columnDefinition = "varchar(225)", nullable=false)
    private String name;

    @Column(columnDefinition = "varchar(225)", nullable = false)
    private String address;

    @Column(columnDefinition = "varchar(225)", nullable = false)
    private String detailAddress;

    @Embedded
    private Location location;

    @Column(columnDefinition = "varchar(225)", nullable = false)
    private String imageURL;

    // 일대다 관계에서 일쪽을 주인으로 하고
    // Shop에 Market의 PK값을 Market에서 FK로 market_id라는 칼럼명으로 놓기
    // CascadeType.All로 영속성 정의 -> Shop 객체까지 persist를 자동으로 다 해줌
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="market_id")
    private List<Shop> shopList = new ArrayList<>();
}
