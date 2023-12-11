package com.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class NoticeVO {
	private int n_no = 0;
	private String n_title = null;
	private String n_content = null;
	private String n_writer = null;
	@Builder
	public NoticeVO(int n_no, String n_title, String n_content, String n_writer) {
		super();
		this.n_no = n_no;
		this.n_title = n_title;
		this.n_content = n_content;
		this.n_writer = n_writer;
	}
	
}
