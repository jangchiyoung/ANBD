package com.anbd.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anbd.board.entity.AddressEntity;


public interface AddressRepository extends JpaRepository<AddressEntity, Integer>{

	//메인 주소에서 시군 찾기
	@Query("select  a.address_si_gun_name from AddressEntity a where a.address_main_name = :address_main_name and a.address_si_gun_name is not null")
	List<String> mainFsigun(@Param("address_main_name") String address_main_name);
	//메인 주소에서 구 찾기
	@Query("select  a.address_gu_name from AddressEntity a where a.address_main_name = :address_main_name and a.address_gu_name is not null")
	List<String> mainFgu(@Param("address_main_name") String address_main_name);
	//시군 주소에서 구 찾기
	@Query("select  a.address_gu_name from AddressEntity a where a.address_si_gun_name = :address_si_gun_name and a.address_gu_name is not null")
	List<String> sigunFgu(@Param("address_si_gun_name") String address_si_gun_name);
	//시군 주소에서 동면리 찾기
	@Query("select  a.address_dong_mun_ri_name from AddressEntity a where a.address_si_gun_name = :address_si_gun_name and a.address_dong_mun_ri_name is not null")
	List<String> sigunFdongmunri(@Param("address_si_gun_name") String address_si_gun_name);
	//구 주소에서 동면리 찾기
	@Query("select  a.address_dong_mun_ri_name from AddressEntity a where a.address_gu_name = :address_gu_name and a.address_dong_mun_ri_name is not null")
	List<String> guFdongmunri(@Param("address_gu_name") String address_gu_name);
	//메인 주소에서 동면리 찾기
	@Query("select  a.address_dong_mun_ri_name from AddressEntity a where a.address_main_name = :address_main_name and a.address_dong_mun_ri_name is not null")
	List<String> mainFdongmunri(@Param("address_main_name") String address_main_name);
	
	
	
	@Query(value = "select a.address_gu_name from address a where a.address_main_name= '서울'", nativeQuery = true )
	List<String> guname();
	
	
}
