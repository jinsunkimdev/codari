package com.codari.myapp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	private int user_id;
	private String user_email;
	private String user_nickname;
	private String user_password;
	private String user_role;
}
