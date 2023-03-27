package com.shoppingcenter.data.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.user.view.UserImageView;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.common.AppProperties;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.User.Role;
import com.shoppingcenter.domain.user.UserDao;
import com.shoppingcenter.domain.user.UserQuery;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepo userRepo;

    // @Value("${app.image.base-url}")
    // private String imageUrl;

    @Autowired
    private AppProperties properties;

    @Override
    public void create(User user) {
        var entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
        entity.setRole(user.getRole().name());
        userRepo.save(entity);
    }

    @Override
    public void update(String userId, String name, String email) {
        var entity = userRepo.getReferenceById(userId);
        entity.setName(name);
        entity.setEmail(email);
    }

    @Override
    public void updateImage(String userId, String fileName) {
        userRepo.updateImage(userId, fileName);
    }

    @Override
    public void updatePhoneNumber(String userId, String phoneNumber) {
        userRepo.updatePhoneNumber(userId, phoneNumber);
    }

    @Override
    public void updateRole(String userId, Role role) {
        userRepo.updateRole(userId, role.name());
    }

    @Override
    public void delete(String id) {
        userRepo.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return userRepo.existsById(id);
    }

    @Override
    public boolean existsByPhone(String phoneNumber) {
        return userRepo.existsByPhone(phoneNumber);
    }

    @Override
    public String getImage(String id) {
        return userRepo.getUserById(id, UserImageView.class).map(UserImageView::getImage).orElse(null);
    }

    @Override
    public User findById(String id) {
        return userRepo.findById(id).map(e -> UserMapper.toDomain(e, properties.getImageUrl())).orElse(null);
    }

    @Override
    public PageData<User> findAll(UserQuery query) {
        Specification<UserEntity> spec = new BasicSpecification<>(
                new SearchCriteria("confirmed", Operator.EQUAL, true));

        if (StringUtils.hasText(query.getPhone())) {
            Specification<UserEntity> phoneSpec = new BasicSpecification<>(
                    new SearchCriteria("phone", Operator.EQUAL, query.getPhone()));
            spec = spec.and(phoneSpec);
        }

        if (StringUtils.hasText(query.getName())) {
            String name = query.getName().toLowerCase();
            Specification<UserEntity> nameSpec = new BasicSpecification<>(
                    new SearchCriteria("name", Operator.LIKE, name));
            // spec = spec != null ? spec.and(nameSpec) : Specification.where(nameSpec);
            spec = spec.and(nameSpec);
        }

        var sort = Sort.by(Order.desc("createdAt"));

        var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

        var pageResult = userRepo.findAll(spec, pageable);
        return PageDataMapper.map(pageResult, e -> UserMapper.toDomain(e, properties.getImageUrl()));
    }

}
