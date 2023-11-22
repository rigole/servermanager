package com.example.servermanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.servermanager.model.Server;

public interface ServerRepo extends JpaRepository<Server, Long>{
	
	Server findByIpAddress(String ipAddress);
	Server findByName(String name);
}
