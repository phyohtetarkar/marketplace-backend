package com.shoppingcenter.data.misc;

import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.misc.OTPAttempt;
import com.shoppingcenter.domain.misc.OTPAttemptDao;

@Repository
public class OTPAttemptDaoImpl implements OTPAttemptDao {

	private OTPAttemptRepo otpAttemptRepo;

	@Override
	public void save(OTPAttempt attempt) {
		var id = new OTPAttemptEntity.ID(attempt.getDate(), attempt.getPhone());
		var entity = new OTPAttemptEntity();
		entity.setId(id);
		entity.setAttempt(attempt.getAttempt());

		otpAttemptRepo.save(entity);
	}

	@Override
	public OTPAttempt getAttempt(String phone, String date) {
		var id = new OTPAttemptEntity.ID(date, phone);
		return otpAttemptRepo.findById(id).map(e -> {
			var d = new OTPAttempt();
			d.setDate(e.getId().getDate());
			d.setPhone(e.getId().getPhone());
			d.setAttempt(e.getAttempt());
			return d;
		}).orElse(null);
	}

}
