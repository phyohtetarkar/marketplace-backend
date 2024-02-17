package com.marketplace.api.admin.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.consumer.general.CityDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/cities")
@Tag(name = "Admin")
public class CityController {

	@Autowired
	private CityControllerFacade cityFacade;

	@PreAuthorize("hasPermission('CITY', 'WRITE')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@RequestBody CityDTO body) {
		cityFacade.save(body);
	}

	@PreAuthorize("hasPermission('CITY', 'WRITE')")
	@PutMapping
	public void update(@RequestBody CityDTO body) {
		cityFacade.save(body);
	}
	
	@PreAuthorize("hasPermission('CITY', 'WRITE')")
	@DeleteMapping("{id:\\d+}")
	public void delete(@PathVariable long id) {
		cityFacade.delete(id);
	}
	
	@PreAuthorize("hasPermission('CITY', 'WRITE') or hasPermission('CITY', 'READ')")
	@GetMapping
	public List<CityDTO> findAll() {
		return cityFacade.findAll();
	}

}
