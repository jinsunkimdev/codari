package com.codari.myapp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardReplyVO {

	private int reply_id;
	private String user_nickname;
	private String reply_content;
	private String replied_date;
	private int board_id;
}
