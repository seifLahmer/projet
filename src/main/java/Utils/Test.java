package Utils;

import javax.xml.crypto.Data;
import java.sql.Connection;

public class Test {
    public static void main(String[] args) {
        DataSource data1= DataSource.getInstance();
        DataSource data2= DataSource.getInstance();
        System.out.println(data1);
        System.out.println(data2);

        Connection conn1=DataSource.getInstance().getCon();
        Connection conn2=data2.getCon();
        System.out.println(conn1);
        System.out.println(conn2);

    }
}
