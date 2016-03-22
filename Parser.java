import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public synchronized void setFile(File f) {
    file = f;
  }

  public synchronized File getFile() {
    return file;
  }
  
  public synchronized String getContent() throws IOException {
    FileInputStream i = null;
    String output = "";
    
    try {
      i =   new FileInputStream(file);
      int data;
      while ((data = i.read()) > 0) {
	output += (char) data;
      }
    } finally {
      if( i != null)
	i.close();
    }
    return output;
  }

  public synchronized String getContentWithoutUnicode() throws IOException {
    FileInputStream i = null;
    String output = "";
    try {
      i = new FileInputStream(file);
      int data;
      while ((data = i.read()) > 0) {
	if ((data & 0x80) != 0) {
	  output += (char) data;
	}
      }
    } finally {
      if( i != null )
	i.close();
    return output;
    }
  }
  
  public synchronized void saveContent(String content) throws IOException {
    FileOutputStream o = null;
    try {
      o = new FileOutputStream(file);
      for (int i = 0; i < content.length(); i += 1) {
	o.write(content.charAt(i));
      }
    } finally {
      if( o != null )
	o.close();
    }
  }
}
