package board;

import lombok.Data;

@Data
public class FilesVO {
	
	private int fileno; 
	private String filename_real;
	private String filename_org; 
	private long filesize;
	private int rel_pk;
	private String type;

}
