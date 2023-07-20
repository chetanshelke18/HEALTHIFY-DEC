// Java Program to Illustrate Creation Of
// Service implementation class

package com.hms.api.serviceimpl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.api.dao.UserDao;
import com.hms.api.entity.Otp;
import com.hms.api.entity.User;
import com.hms.api.model.EmailDetails;
import com.hms.api.model.ResetPasswordDetail;
import com.hms.api.service.EmailPasswordService;
import com.hms.api.service.UserService;
import com.hms.api.utility.OTPGenerator;

@Service
public class EmailPasswordServiceImpl implements EmailPasswordService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserService userService;

	@Value("${spring.mail.username}")
	private String sender;

	// Method 1
	// To send a simple email
	public boolean sendMail(EmailDetails details) {
		boolean isSent = false;
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());

			javaMailSender.send(mailMessage);
			isSent = true;
		}

		catch (Exception e) {
			e.printStackTrace();
			isSent = false;
		}
		return isSent;
	}

	@Override
	public String resetPasswordByQA(ResetPasswordDetail detail) {
		String message = null;
		try {
			User user = userDao.getUserById(detail.getUserId());
			if (user != null) {
				if (detail.getQuestion().equals(user.getQuestion()) && detail.getAnswer().equals(user.getAnswer())) {
					if (detail.getNewPassword().endsWith(detail.getConfirmPassword())) {
						User updateUser = userDao.getUserById(detail.getUserId());
						
						User updatedUser = userService.updateUser(updateUser);
						if (updatedUser != null) {
							message = "Password Updated Successfully";
						} else {
							message = "Error While Updating Password";
						}
					} else {
						message = "New & Confirm Password Must Be Same";
					}
				} else {
					message = "Wrong Security Q & A";
				}
			} else {
				message = "User Not Exists With Id > " + detail.getUserId();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String sendOtp(String UserId) {
		String msg = null;
		
		try {
			User user = userDao.getUserById(UserId);
			if (user != null) {
				Date date = new Date();
				Timestamp timestamp = new Timestamp(date.getTime());
				int generatedOtp = OTPGenerator.generateOtp();
				Otp otp = new Otp();
				otp.setUserId(UserId);
				otp.setOtp(generatedOtp);
				otp.setTimestamp(timestamp);
				boolean isAdded = userDao.saveOtp(otp);
				if (isAdded) {
					EmailDetails details = new EmailDetails();
					details.setRecipient(user.getEmailid());
					details.setSubject("HMS : Please Verify Your OTP");
					details.setMsgBody("Hey " + UserId + "\n\n Your OTP : " + generatedOtp + "\n\n Otp generated at : "+timestamp);
					boolean isSent = sendMail(details);
					if (isSent) {
						msg = "Otp Has Been Sent To Email > " + user.getEmailid();
					} else {
						msg = "Error Occured While Sending OTP On "+ user.getEmailid()+"! \n Check Email Address Of User "+UserId;
					}

				}
				else {
					msg="Something Wrong While Save OTP";
				}

			} else {
				msg = "User Not Exists ID > " + UserId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public String resetPasswordByOtp(ResetPasswordDetail detail) {
		String message = null;
		try {
			Otp dbOtp = userDao.getOtpByUser(detail.getUserId());
			if (dbOtp != null) {
				if (dbOtp.getOtp() == detail.getOtp()) {
					User user = userDao.getUserById(detail.getUserId());
					if (user != null) {
						if (detail.getNewPassword().equals(detail.getConfirmPassword())) {
							user.setPassword(detail.getNewPassword());
							User updatedUser = userService.updateUser(user);
							if (updatedUser != null) {
								message = "Password Updated Successfully";
							} else {
								message = "Error While Updating Password";
							}
						} else {
							message = "New & Confirm Password Must Be Same";
						}
					} else {
						message = "USer Not Exists UserId : " + detail.getUserId();
					}
				} else {
					message = "Invalid OTP !!";
				}
			} else {
				message = "Otp Not Found For User : " + detail.getUserId() + " Generate Otp First";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

}
