package com.watch.restController;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.watch.service.UploadService;


@CrossOrigin("*")
@RestController
public class UploadRestController {

	@Autowired
	UploadService uploadService;
	
	@PostMapping("/rest/upload/{folder}")
	public JsonNode upload(@PathParam("file") MultipartFile file,
			@PathVariable("folder") String folder,HttpServletRequest request,Model model) {
		File savedFile = uploadService.save(file, folder);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", savedFile.getName());
		node.put("size",savedFile.length());
		model.addAttribute("ckimage",false);
		request.setAttribute("ckimage",false);
		return node;
	}
	
	@PostMapping("/rest/upload/user/{folder}")
	public JsonNode upload1(@PathParam("file") MultipartFile file,
			@PathVariable("folder") String folder,HttpServletRequest request) {
		File savedFile = uploadService.save(file, folder);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", savedFile.getName());
		node.put("size",savedFile.length());
		return node;
	}
}