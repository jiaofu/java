import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider; 

public class Client {
    public  static  void main  (String[]  args) throws  Exception {
        //JavaԴ����
        String sourceStr = "public class Hello{    public String sayHello (String name) {return \"Hello,\" + name + \"!\";}}";
        //�������ļ���
        String className="Hello";
        //������
        String methodName="sayHello";
        //��ǰ������
        JavaCompiler cmp = ToolProvider.getSystemJavaCompiler(); 
        
              //Java��׼�ļ�������  
                StandardJavaFileManager fm = cmp.getStandardFileManager(null,null,null);  
        //Java�ļ�����
        JavaFileObject jfo=new StringJavaObject(className,sourceStr);
        //���������������javac <options>�е�options
        List<String> optionsList=new ArrayList<String>();
        //�����ļ��Ĵ�ŵط���ע�⣺�˴���ΪEclipse���������
        optionsList.addAll(Arrays.asList("-d","./bin"));
        //Ҫ����ĵ�Ԫ
        List<JavaFileObject> jfos= Arrays.asList(jfo);
        //���ñ��뻷��
        JavaCompiler.CompilationTask task=cmp.getTask(null, fm, null, optionsList,null,jfos);
        //����ɹ�
        if(task.call()){
            //���ɶ���
            Object obj= null;

                obj = Class.forName(className).newInstance();

            Class <? extends Object> cls=obj.getClass();
            //����sayHello����
            Method m=cls.getMethod(methodName,String.class);
          String str=(String) m.invoke(obj,"Dynamic Compilation");
          System.out.println(str);


        }

    }

}
//�ı��е�Java����
class StringJavaObject extends SimpleJavaFileObject{
    //Դ����
    private String content="";
    public StringJavaObject(String _javaFileName,String _content){
        super(_createStringJavaObjectUri(_javaFileName),Kind.SOURCE);
        content=_content;
    }
    //����һ��URL��Դ·��
    private  static   URI _createStringJavaObjectUri(String name){
        //ע��˴�û�����ð���
        return URI.create("String:///"+name+Kind.SOURCE.extension);
    }

    //�ı��ļ�����
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return content;
    }
}
