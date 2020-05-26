package com.example.es;

import com.example.es.entity.User;
import com.example.es.repository.UserRepository;
import com.example.es.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.File;
import java.util.*;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Test
    public void testAdd(){
        File msgFile = new File("E:/dev-document/THUOCL_poem_copy.txt");
        if(!msgFile.exists()){
            System.out.println("未找到文件");
            return;
        }

        String msg = FileUtil.readTxtFile(msgFile);
        User user = new User(UUID.randomUUID().toString(),"长文件测试",20,msg,
                msg,new Date(),false);
        Long cur = System.currentTimeMillis();
        userRepository.save(user);
        System.out.println("耗时："+(System.currentTimeMillis()-cur));
        /*user.setId(UUID.randomUUID().toString());
        user.setUserName("小亮");
        user.setAge(21);
        elasticsearchTemplate.save(user);*/
    }

    @Test
    public void testGet(){
        //PageRequest的对象构造函数有多个，page是页数，初始值是0，size是查询结果的条数，后两个参数参考Sort对象的构造方法
        //Pageable pageable = PageRequest.of(0,3, Sort.Direction.DESC,"age");
        //Page<User> users = userRepository.findByUserNameSelf("小明",pageable);
        Iterable<User> users = userRepository.findAll();
        users.forEach(user -> {
            System.out.println(user.getId());
            System.out.println(user.getUserName());
            System.out.println(user.getAge());
        });

    }

    @Test
    public void testGet2(){
        //PageRequest的对象构造函数有多个，page是页数，初始值是0，size是查询结果的条数，后两个参数参考Sort对象的构造方法
        Pageable pageable = PageRequest.of(0,3, Sort.Direction.DESC,"age");
        Page<User> users = userRepository.findByUserName("小明",pageable);
        System.out.println(users.getSize());
        System.out.println(users.getTotalElements());
        users.forEach(user -> {
            System.out.println(user.getId());
            System.out.println(user.getUserName());
            System.out.println(user.getAge());
        });

    }
    @Test
    public void testBulkAdd(){
        //测试用名字文件
        File userNameFile = new File("E:/dev-document/THUOCL_lishimingren_copy.txt");
        //测试用句子
        File wordFile = new File("E:/dev-document/THUOCL_poem_copy.txt");
        //读取100个
        String[] userNames = FileUtil.readFileByLines(userNameFile,0);
        //读取100句话
        String[] txtStr = FileUtil.readFileByLines(wordFile,0);

        //生成随机数
        Random random = new Random();

        int offset = 0;

        //循环读取，直到文件内名字数量不足100
        while(userNames!=null&&userNames.length==100
            &&txtStr!=null&&txtStr.length==100){
            offset+=100;

            // 批量新增数据集合
            List<User> userList = new ArrayList<>();

            // 遍历创建对象
            List<String> nameList = Arrays.asList(userNames);
            String[] finalTxtStr = txtStr;
            nameList.forEach(name->{
                String msg = finalTxtStr[nameList.indexOf(name)];
                Integer age = random.nextInt(100);
                User user = new User(UUID.randomUUID().toString(),name+"7",age,msg,
                        msg,new Date(),age>30);
                userList.add(user);
            });

            // 批量保存
            userRepository.saveAll(userList);

            //break;

            // 重新读取文件保存的信息
            userNames = FileUtil.readFileByLines(userNameFile,offset);
            txtStr = FileUtil.readFileByLines(wordFile,offset);
        };
        System.out.println("共导入数据："+offset);
    }
}
