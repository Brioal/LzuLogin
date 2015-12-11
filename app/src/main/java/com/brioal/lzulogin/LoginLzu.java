package com.brioal.lzulogin;

/**
 * Created by brioal on 15-12-10.
 */
public class LoginLzu {
    public static void main(String[] args) {
        String click_url = "http://10.10.1.254/cgi-bin/srun_portal";
        String logo_parma = "action=login&username=huangj2013@lzu.edu.cn&ac_id=12&type=1&wbaredirect=http://www.nuomi.com/?cid&mac\n" +
                "=&nas_ip=&password=199501&is_ldap=1";
        String username = "";
        String password = "";

        Info from_logo = GetLzu.Post(new Info(click_url, null, logo_parma));
        String data = from_logo.getData();
        System.out.println(data);

    }
}
