package com.marketplace.data.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.general.City;
import com.marketplace.domain.general.CityDao;

@Repository
public class CityDaoImpl implements CityDao {
	
	@Autowired
	private CityRepo repo;

	@Override
	public City save(City city) {
		var entity = repo.findById(city.getId()).orElseGet(CityEntity::new);
		entity.setName(city.getName());
		
		var result = repo.save(entity);
		
		return CityMapper.toDomain(result);
	}

	@Override
	public void deleteById(long cityId) {
		repo.deleteById(cityId);
	}
	
	@Override
	public boolean existsById(long id) {
		return repo.existsById(id);
	}
	
	@Override
	public City findById(long id) {
		return repo.findById(id).map(CityMapper::toDomain).orElse(null);
	}

	@Override
	public List<City> findAll() {
		return repo.findAll(Sort.by(Direction.DESC, "createdAt")).stream().map(CityMapper::toDomain).toList();
	}

}
