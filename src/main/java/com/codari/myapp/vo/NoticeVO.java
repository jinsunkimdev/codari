package com.codari.myapp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO {
	int notice_id;
	String notice_title;
	String notice_content;
	String notice_created_date;
	String user_nickname;
	int notice_view;
	int user_id;
	
	
}
