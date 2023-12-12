package com.example.demo.pojo2;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardVO {

	
	 private int 	b_no         = 0;    
	 private String 	b_title      = null;    
	 private String 	b_writer     = null;    
	 private String 	b_content    = null;    
	 private int 	b_hit       = 0;    
	 private String 	b_date       = null;    
	 private String 	b_file       = null;    
	 
	 
	 @Builder
	 public BoardVO(int b_no, String b_title, String b_writer, String b_content, int b_hit, String b_date, String b_file)
	 {
		 this.b_no = b_no;
		 this.b_title =b_title;
		 this.b_writer = b_writer;
		 this.b_content = b_content;
		 this.b_hit = b_hit;
		 this.b_date = b_date;
		 this.b_file = b_file;
	 }
}
