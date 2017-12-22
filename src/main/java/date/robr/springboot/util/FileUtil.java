package date.robr.springboot.util;


import org.springframework.beans.factory.annotation.Value;

public class FileUtil {
	@Value("${other.file.imagesPath}")
    private String mImagesPath;
    
   /* public boolean isImagePath(String path){
    	File file = new File(mImagesPath + path);
    	if(file.isDirectory())
    	
    	
    	return false;
    }*/
	//public void get
}
