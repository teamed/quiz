import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  private FileInputStream fileInputStream;
  private FileOutputStream fileOutputStream;
  private static Parser mInstance;
  private static Object lock = new Object();

  private Parser(){
  }

  public static synchronized Parser getInstance(){
    Parser instance = mInstance;
    if(instance == null){
      synchronized(lock){
        instance = mInstance;
        if(instance == null){
          instance = new Parser();
          mInstance = instance;
        }
      }
    }
    return instance;
  }
  public void setFile(File f) {
    file = f;
  }
  public File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    fileInputStream = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = fileInputStream.read()) > 0) {
      output += (char) data;
    }
    return output;
  }
  public String getContentWithoutUnicode() throws IOException {
    fileInputStream = new FileInputStream(file);
    String output = "";
    int data;
    while ((data = fileInputStream.read()) > 0) {
      if (data < 0x80) {
        output += (char) data;
      }
    }
    return output;
  }
  public void saveContent(String content) throws IOException {
    fileOutputStream = new FileOutputStream(file);
    for (int i = 0; i < content.length(); i += 1) {
      fileOutputStream.write(content.charAt(i));
    }
  }
}
