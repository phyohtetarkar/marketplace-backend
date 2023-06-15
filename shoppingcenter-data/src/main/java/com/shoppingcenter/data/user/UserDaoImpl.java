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
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserDao;
import com.shoppingcenter.domain.user.UserQuery;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User create(User user) {
        var entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());
        var result = userRepo.save(entity);

        return UserMapper.toDomain(result);
    }

    @Override
    public void update(long userId, String name, String email) {
        var entity = userRepo.getReferenceById(userId);
        entity.setName(name);
        entity.setEmail(email);
    }

    @Override
    public void updateImage(long userId, String fileName) {
        userRepo.updateImage(userId, fileName);
    }

    @Override
    public void updatePhoneNumber(long userId, String phoneNumber) {
        userRepo.updatePhoneNumber(userId, phoneNumber);
    }

    @Override
    public void updateRole(long userId, User.Role role) {
        userRepo.updateRole(userId, role);
    }
    
    @Override
    public void updatePassword(long userId, String password) {
    	userRepo.updatePassword(userId, password);
    }
    
    @Override
    public void updateVerified(long userId, boolean verified) {
    	userRepo.updateVerified(userId, verified);
    }

    @Override
    public void delete(long id) {
        userRepo.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return userRepo.existsById(id);
    }

    @Override
    public boolean existsByPhone(String phoneNumber) {
        return userRepo.existsByPhone(phoneNumber);
    }

    @Override
    public String getImage(long id) {
        return userRepo.getUserById(id, UserImageView.class).map(UserImageView::getImage).orElse(null);
    }

    @Override
    public User findById(long id) {
        return userRepo.findById(id).map(e -> UserMapper.toDomain(e)).orElse(null);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepo.findByPhone(phone).map(e -> UserMapper.toDomain(e))
                .orElse(null);
    }

    @Override
    public PageData<User> findAll(UserQuery query) {
        Specification<UserEntity> spec = null;

        if (StringUtils.hasText(query.getPhone())) {
            Specification<UserEntity> phoneSpec = new BasicSpecification<>(
                    new SearchCriteria("phone", Operator.EQUAL, query.getPhone()));
            spec = Specification.where(phoneSpec);
        }
        
        if (query.getStaffOnly() == Boolean.TRUE) {
        	Specification<UserEntity> roleSpec = new BasicSpecification<>(
                    new SearchCriteria("role", Operator.NOT_EQ, User.Role.USER));
        	spec = spec != null ? spec.and(roleSpec) : Specification.where(roleSpec);
        }
        
        if (query.getVerified() != null) {
        	Specification<UserEntity> verifiedSpec = new BasicSpecification<>(
                    new SearchCriteria("verified", Operator.EQUAL, query.getVerified().toString().toUpperCase()));
        	spec = spec != null ? spec.and(verifiedSpec) : Specification.where(verifiedSpec);
        }

        if (StringUtils.hasText(query.getName())) {
            String name = query.getName().toLowerCase();
            Specification<UserEntity> nameSpec = new BasicSpecification<>(
                    new SearchCriteria("name", Operator.LIKE, name));
            // spec = spec != null ? spec.and(nameSpec) : Specification.where(nameSpec);
            spec = spec != null ? spec.and(nameSpec) : Specification.where(nameSpec);
        }

        var sort = Sort.by(Order.desc("createdAt"));

        var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

        var pageResult = userRepo.findAll(spec, pageable);
        return PageDataMapper.map(pageResult, e -> UserMapper.toDomain(e));
    }

}
