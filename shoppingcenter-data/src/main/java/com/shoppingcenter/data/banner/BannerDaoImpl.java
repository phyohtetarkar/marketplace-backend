package com.shoppingcenter.data.banner;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.SortQueryMapper;
import com.shoppingcenter.data.banner.view.BannerImageView;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.banner.BannerDao;
import com.shoppingcenter.domain.common.AppProperties;

@Repository
public class BannerDaoImpl implements BannerDao {

    @Autowired
    private BannerRepo repo;
    
    @Autowired
    private AppProperties properties;

    @Override
    public Banner save(Banner banner) {
        var entity = repo.findById(banner.getId()).orElseGet(BannerEntity::new);
        entity.setId(banner.getId());
        entity.setLink(banner.getLink());
        entity.setPosition(banner.getPosition());
        var result = repo.save(entity);
        return BannerMapper.toDomain(result, null);
    }

    @Override
    public void saveAll(List<Banner> list) {
        var entities = list.stream().map(banner -> {
            var entity = new BannerEntity();
            entity.setId(banner.getId());
            entity.setLink(banner.getLink());
            entity.setImage(banner.getImage());
            entity.setPosition(banner.getPosition());
            return entity;
        }).toList();

        repo.saveAll(entities);
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
        return repo.findById(id).map(e -> BannerMapper.toDomain(e, properties.getImageUrl())).orElse(null);
    }

    @Override
    public List<Banner> findAll(SortQuery sort) {
        Sort sortBy = SortQueryMapper.fromQuery(sort);
        return repo.findAll(sortBy).stream()
                .map(e -> BannerMapper.toDomain(e, properties.getImageUrl()))
                .collect(Collectors.toList());
    }

}
