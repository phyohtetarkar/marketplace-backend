package com.shoppingcenter.data.misc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.misc.City;
import com.shoppingcenter.domain.misc.CityDao;

@Repository
public class CityDaoImpl implements CityDao {
	
	@Autowired
	private CityRepo repo;

	@Override
	public void save(City city) {
		var entity = repo.findById(city.getId()).orElseGet(CityEntity::new);
		entity.setName(city.getName());
		repo.save(entity);
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
