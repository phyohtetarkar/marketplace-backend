package com.marketplace.data.banner;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.SortQueryMapper;
import com.marketplace.data.banner.view.BannerImageView;
import com.marketplace.domain.banner.Banner;
import com.marketplace.domain.banner.BannerDao;
import com.marketplace.domain.banner.BannerInput;
import com.marketplace.domain.common.SortQuery;

@Repository
public class BannerDaoImpl implements BannerDao {

    @Autowired
    private BannerRepo repo;

    @Override
    public Banner save(BannerInput values) {
        var entity = repo.findById(values.getId()).orElseGet(BannerEntity::new);
        entity.setId(values.getId());
        entity.setLink(values.getLink());
        entity.setPosition(values.getPosition());
        var result = repo.save(entity);
        return BannerMapper.toDomain(result);
    }
    
    @Override
    public void updateImage(int id, String image) {
    	repo.updateImage(id, image);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    @Override
    public String getBannerImage(int id) {
        return repo.getBannerById(id, BannerImageView.class).map(BannerImageView::getImage).orElse(null);
    }

    @Override
    public Banner findById(int id) {
        return repo.findById(id).map(e -> BannerMapper.toDomain(e)).orElse(null);
    }

    @Override
    public List<Banner> findAll(SortQuery sort) {
        var sortBy = SortQueryMapper.fromQuery(sort);
        return repo.findAll(sortBy).stream()
                .map(e -> BannerMapper.toDomain(e))
                .collect(Collectors.toList());
    }

}
