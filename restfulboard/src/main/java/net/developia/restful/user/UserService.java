package net.developia.restful.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;

	public UserDTO getUser(UserDTO userDTO) throws Exception {
		UserDTO userInfo = userDAO.getUser(userDTO);
		if (userInfo == null) {
			throw new RuntimeException("���̵� Ȥ�� ��й�ȣ�� Ʋ���ϴ�.");
		}
		return userInfo;
	}
}
