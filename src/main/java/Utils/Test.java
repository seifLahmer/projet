package Utils;

import java.sql.Connection;

public class Test {
    public static void main(String[] args) {
        DataSource data1=DataSource.getInstance();
        DataSource data2=DataSource.getInstance();

        System.out.println(data1);
        System.out.println(data2);

        Connection con1=DataSource.getInstance().getCon();
        Connection con2=data2.getCon();

        System.out.println(con2);
        System.out.println(con1);
    }
}
