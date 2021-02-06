package com.codari.myapp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardUserVO {
	private int board_id;
	private String board_category_name;
	private String board_title;
	private String board_content;
	private String created_date;
	private String modified_date;
	private int view;
	private int user_id;
	private String user_nickname;

}
