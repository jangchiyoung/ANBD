package com.anbd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbd.board.entity.FavoritesEntity;

public interface FavoritesRepository extends JpaRepository<FavoritesEntity, Integer>{

}
