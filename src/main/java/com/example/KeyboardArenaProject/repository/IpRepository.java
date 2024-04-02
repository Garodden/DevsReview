package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.IP;
import com.example.KeyboardArenaProject.entity.compositeKey.IpCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpRepository extends JpaRepository<IP, IpCompositeKey> {
}
