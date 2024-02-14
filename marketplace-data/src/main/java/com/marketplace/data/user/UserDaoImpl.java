package com.marketplace.data.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.JpaSpecificationBuilder;
import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.user.view.UserImageView;
import com.marketplace.data.user.view.UserRoleView;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.user.ProfileUpdateInput;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.User.Role;
import com.marketplace.domain.user.UserPermission;
import com.marketplace.domain.user.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private UserPermissionRepo userPermissionRepo;

    @Override
    public User create(User user) {
        var entity = new UserEntity();
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setRole(user.getRole());
        entity.setUid(user.getUid());
        entity.setImage(user.getImage());
        var result = userRepo.save(entity);

        return UserMapper.toDomain(result);
    }
    
    @Override
    public void updateUid(long userId, String uid) {
    	userRepo.updateUid(userId, uid);
    }

    @Override
    public User update(ProfileUpdateInput values) {
        var entity = userRepo.findById(values.getUserId()).orElseThrow();
        entity.setName(values.getName());
        entity.setPhone(values.getPhone());
        
        var result = userRepo.save(entity);

        return UserMapper.toDomain(result);
    }
    
    @Override
    public void updatePermissions(long userId, List<User.Permission> permissions) {
    	var user = userRepo.getReferenceById(userId);
    	var entities = permissions.stream().map(p -> {
    		var e = new UserPermissionEntity();
    		e.getId().setPermission(p);
    		e.setUser(user);
    		return e;
    	}).toList();
    	
    	userPermissionRepo.saveAll(entities);
    }

    @Override
    public void updateImage(long userId, String fileName) {
        userRepo.updateImage(userId, fileName);
    }

    @Override
    public void updateRole(long userId, User.Role role) {
        userRepo.updateRole(userId, role);
    }

    @Override
    public void delete(long id) {
        userRepo.deleteById(id);
    }
    
    @Override
    public void deletePermissionsByUser(long userId) {
    	userPermissionRepo.deleteByUserId(userId);
    }

    @Override
    public boolean existsById(long id) {
        return userRepo.existsById(id);
    }
    
    @Override
    public boolean existsByUid(String uid) {
    	return userRepo.existsByUid(uid);
    }

    @Override
    public boolean existsByPhone(String phoneNumber) {
        return userRepo.existsByPhone(phoneNumber);
    }
    
    @Override
	public boolean existsByEmail(String email) {
		return userRepo.existsByEmail(email);
	}
    
    @Override
    public boolean existsByIdNotAndEmail(long userId, String email) {
    	return userRepo.existsByIdNotAndEmail(userId, email);
    }
    
    @Override
    public long count() {
    	return userRepo.count();
    }

    @Override
    public String getImage(long id) {
        return userRepo.getUserById(id, UserImageView.class).map(UserImageView::getImage).orElse(null);
    }
    
    @Override
    public Role getRole(long id) {
    	return userRepo.getUserById(id, UserRoleView.class).map(UserRoleView::getRole).orElse(null);
    }

    @Override
    public User findById(long id) {
        return userRepo.findById(id).map(UserMapper::toDomain).orElse(null);
    }
    
    @Override
    public User findByUid(String uid) {
    	return userRepo.findByUid(uid).map(UserMapper::toDomain).orElse(null);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepo.findByPhone(phone).map(UserMapper::toDomain).orElse(null);
    }
    
    @Override
    public User findByEmail(String email) {
    	return userRepo.findByEmail(email).map(UserMapper::toDomain).orElse(null);
    }
    
    @Override
    public UserPermission getUserPermission(long userId, String permission) {
    	var id = new UserPermissionEntity.ID(userId, User.Permission.valueOf(permission));
    	return userPermissionRepo.findById(id).map(e -> {
    		return new UserPermission(e.getPermission());
    	}).orElse(null);
    }
    
    @Override
    public List<User.Permission> getPermissionsByUser(long userId) {
    	return userPermissionRepo.findById_UserId(userId).stream()
    			.map(UserPermissionEntity::getPermission)
    			.toList();
    }

    @Override
    public PageData<User> findAll(SearchQuery searchQuery) {
        var spec = JpaSpecificationBuilder.build(searchQuery.getCriterias(), UserEntity.class);

        var pageable = PageQueryMapper.fromPageQuery(searchQuery.getPageQuery());

        var pageResult = spec != null ? userRepo.findAll(spec, pageable) : userRepo.findAll(pageable);
        
        return PageDataMapper.map(pageResult, e -> UserMapper.toDomain(e));
    }

}
