package com.example.servermanager.service.implementation;

import java.io.IOException;
import java.net.InetAddress;

import java.util.*;
import java.util.logging.Logger;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.servermanager.model.Server;
import com.example.servermanager.repo.ServerRepo;
import com.example.servermanager.service.ServerService;

import enumaration.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
  private final ServerRepo serverRepo;
	Logger logger = Logger.getLogger("");
	@Override
	public Server create(Server server) {
		logger.info("Saving new server: " + server.getName());
		server.setImageUrl(setServerImageUrl());
		// TODO Auto-generated method stub
		return serverRepo.save(server);
	}

	


	@Override
	public Collection<Server> list(int limit) {
		logger.info("Fetching all servers");
		return serverRepo.findAll(PageRequest.of(0, limit)).toList() ;
	}

	@Override
	public Server get(Long id) {
		logger.info("Fetching server by Id +  " + id);
		return serverRepo.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		logger.info("Updating server " + server.getName());
		return serverRepo.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		logger.info("Deleting the server with the ID: " + id);
		serverRepo.deleteById(id);
		return Boolean.TRUE;
	}

	@Override
	public Server ping(String ipAddress) throws IOException {
		logger.info("Pinging server IP: " + ipAddress);
		Server server = serverRepo.findByIpAddress(ipAddress);	 	
		InetAddress address = InetAddress.getByName(ipAddress);
		server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		serverRepo.save(server);
		return server;
	}
	
	private String setServerImageUrl() {
		String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
	}

}
