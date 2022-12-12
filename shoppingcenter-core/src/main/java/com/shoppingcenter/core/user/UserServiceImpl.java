package com.shoppingcenter.core.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.PageResult;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.core.user.model.User;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.data.user.UserEntity.Role;
import com.shoppingcenter.data.user.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;

	@Value("${app.image.base-url}")
	private String baseUrl;

	@Override
	public void create(User user) {
		if (repo.existsById(user.getId())) {
			throw new ApplicationException(ErrorCodes.USER_ALREADY_CREATED);
		}

		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setName(user.getName());
		entity.setPhone(user.getPhone());
		entity.setEmail(user.getEmail());
		entity.setRole(Role.CONSUMER);

		repo.save(entity);
	}

	@Override
	public void update(User user) {
		UserEntity entity = repo.findById(user.getId())
				.orElseThrow(() -> new ApplicationException(ErrorCodes.USER_NOT_FOUND));
		entity.setName(user.getName());
		entity.setEmail(user.getEmail());
		repo.save(entity);
	}

	@Override
	public void uploadImage(String userId, UploadFile file) {
		// UserEntity entity = repo.getReferenceById(userId);
	}

	@Override
	public void changePhoneNumber(String userId, String phoneNumber) {
		UserEntity entity = repo.getReferenceById(userId);
		entity.setPhone(phoneNumber);
	}

	@Override
	public void delete(String id) {

	}

	@Override
	public void updateRole(String userId, Role role) {
		UserEntity entity = repo.getReferenceById(userId);
		entity.setRole(role);

		// TODO: implement update role in Cognito
	}

	@Override
	public User findById(String id) {
		return repo.findById(id).map(e -> User.create(e, baseUrl)).orElse(null);
	}

	@Override
	public PageResult<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
