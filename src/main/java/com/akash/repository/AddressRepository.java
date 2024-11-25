package com.akash.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
