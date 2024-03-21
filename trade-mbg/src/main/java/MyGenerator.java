
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.util.Collections;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/1/3 19:51
 * @注释
 */
public class MyGenerator {

    public static void main(String[] args) {
        FastAutoGenerator
                .create("jdbc:mysql://localhost:3306/idle_trading_system?serverTimezone=Asia/Shanghai","root", "1234")
                //全局配置
        .globalConfig(builder -> {
            builder.author("xjx")
                    .commentDate("yyyy-MM-dd hh:mm:ss")
                    .outputDir(System.getProperty("user.dir") + "/trade-mbg" + "/src/main/java")
                    .disableOpenDir();
        })
        .packageConfig(builder -> {
            builder.parent("com.trade.mbg")
                    .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/trade-mbg" + "/src/main/resources/mapper"));
        })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("ums_user_chat") // 设置需要生成的表名
                            .addTablePrefix("ums_", "pms_", "oms_") // 设置过滤表前缀
                            // Entity 策略配置
                            .entityBuilder()
                            .enableLombok() //开启 Lombok
                            .enableFileOverride() // 覆盖已生成文件
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命
                            // Mapper 策略配置
                            .mapperBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            // Service 策略配置
                            .serviceBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl
                            // Controller 策略配置
                            .controllerBuilder()
                    ;
                })
                .execute();
    }
}

