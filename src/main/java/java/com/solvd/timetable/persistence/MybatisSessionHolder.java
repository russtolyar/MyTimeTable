package java.com.solvd.timetable.persistence;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisSessionHolder {

    private static final String CONFIG_PATH = "mybatis-config.xml";
    private static final SqlSessionFactory SQL_SESSION_FACTORY;

    static {
        SQL_SESSION_FACTORY = createSessionFactory();
    }

    private static SqlSessionFactory createSessionFactory() {
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(CONFIG_PATH);
        } catch (IOException exception) {
            throw new RuntimeException("Cant load mybatis config.", exception);
        }
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return builder.build(inputStream);
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return SQL_SESSION_FACTORY;
    }

}
