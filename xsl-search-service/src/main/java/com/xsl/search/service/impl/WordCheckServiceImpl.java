package com.xsl.search.service.impl;

import com.xsl.search.export.WordCheckService;
import org.python.core.Py;
import org.python.core.PyByteArray;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordCheckServiceImpl implements WordCheckService {
    public List<String> check(String sentence) throws IOException {
        PythonInterpreter interpreter = new PythonInterpreter();
        PySystemState sys = Py.getSystemState();

        sys.path.add("/opt/py_shell/jieba-master-1/build/lib");
        sys.path.add("/opt/py_shell/jieba-master-1/build/lib/jieba");
        InputStream filepy = new FileInputStream("/opt/py_shell/jieba-master-1/demo/demo3.py");



        ByteArrayOutputStream baos=new   ByteArrayOutputStream();
        interpreter.setOut(baos);


        String str = new String(sentence.getBytes(),"UTF-8");
        str = str.replace("&","");
        str = str.replace("*","");
        str = str.replace("$","");
        str = str.replace("%","");
        byte[] bytes=str.getBytes();

        interpreter.exec("text="+ new PyByteArray(bytes));
        interpreter.execfile(filepy);

        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader in = new BufferedReader(new InputStreamReader(swapStream));
        String line;
        System.out.println("*******************");

        List<String> list = new ArrayList<String>(10);
        while ((line = in.readLine()) != null) {
            String[] strArray = line.split(" ");

            if(strArray[1].equals("nz")){
                System.out.println("unlegal: "+strArray[0]);
                list.add(strArray[0]);
            }
        }

        return list;

    }
}
