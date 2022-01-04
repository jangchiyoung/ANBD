package com.anbd.board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "favorites")
public class FavoritesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer favorites_no;
	
	@ManyToOne(targetEntity = ProductEntity.class)
	private ProductEntity favorites_product_no;
	
	@ManyToOne(targetEntity = ClientEntity.class)
	private ClientEntity favorites_client_id;
}
