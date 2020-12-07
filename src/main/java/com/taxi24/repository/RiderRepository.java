package com.taxi24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taxi24.model.Rider;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Integer> {

}
