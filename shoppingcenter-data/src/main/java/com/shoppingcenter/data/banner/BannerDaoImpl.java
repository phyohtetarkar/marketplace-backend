package com.shoppingcenter.data.banner;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.SortQueryMapper;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.banner.BannerDao;
import com.shoppingcenter.domain.common.AppProperties;

@Repository
public class BannerDaoImpl implements BannerDao {

    @Autowired
    private BannerRepo repo;

    // @Value("${app.image.base-url}")
    // private String imageUrl;

    @Autowired
    private AppProperties properties;

    @Override
    public int save(Banner banner) {
        BannerEntity entity = repo.findById(banner.getId()).orElseGet(BannerEntity::new);
        entity.setLink(banner.getLink());
        entity.setImage(banner.getImage());
        var result = repo.save(entity);
        return result.getId();
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
