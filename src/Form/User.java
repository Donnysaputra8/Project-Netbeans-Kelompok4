/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

/**
 *
 * @author ZeeDane
 */
public class User {
    private static String kd;
    public static void setUserLogin(String kode){
    kd = kode;
    }
public static String getUserLogin(){
return kd;
}
}
