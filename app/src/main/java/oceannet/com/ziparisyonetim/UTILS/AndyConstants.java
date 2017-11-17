package oceannet.com.ziparisyonetim.UTILS;

/**
 * Created by oceannet on 30/10/17.
 */

public class AndyConstants {




    public  static  final   String URL_BASE = "http://ziparisservice.automaatsrv.xyz/" ;





    public  static  final  String URL_LOGIN = URL_BASE+"Token" ;

    public   static  final  String URL_CHANGEPASSWORD = URL_BASE + "api/Account/ChangePassword?username=";

    public  static  final  String URL_RESETPASSWORD = URL_BASE + "api/Account/ResetPassword?email=";



    public  static  final  String URL_GETROOMBEACONBARCODE = URL_BASE + "GetRoomBeaconBarcode" ;



    public  static  final  String URL_CATEGORYPRODCUT = URL_BASE + "CategoryProduct?roomID=";

    public  static  final  String URL_SENDORDER = URL_BASE + "UserSendOrder";





    public  static  final  String URL_GETUSERORDERLIST = URL_BASE + "GetOrderList" ;



    public  static  final  String URL_GETUSERPASTORDER = URL_BASE + "GetMyLastOrderList";

    public  static  final  String URL_GETUSERDETAILS = URL_BASE + "GetUserDetails"  ;





    public  static  final  String URL_PASTORDER = URL_BASE + "GetWaitersAcceptOrderList" ;



    public  static  final  String URL_POSTORDERRATE = URL_BASE + "WaitersAcceptOrder?OrderID=";





    public  static  final  String URL_DELIVERORDER = URL_BASE + "WaitersDeliverOrder?OrderID=" ;



    public  static  final  String URL_EDITUSERDETAIL = URL_BASE + "EditUserDetail";



    public  static  final  String URL_GETUSERNOTIFICATION = URL_BASE + "GetUserNotification";

    public  static  final  String URL_GETMYORDERLIST = URL_BASE + "GetMyOrderList";



  ///  public  static  final  String PREF_PUSHTOKEN = URL_BASE + "GetMyOrderList";
    public  static  final  String  PREF_TOKEN = "tokenpref" ;

    public  static  final  String  PREF_USERNAME = "usernamepref" ;

    public  static  final  String  PREF_PASSWORD = "passwordpref" ;


    public  static  final  String PREF_PUSHTOKEN = "pushtoken" ;



    public  static  final  String  PREF_NAMEE = "prefNamee" ;


    public  static  final  String  PREF_ISSIPARIS = "issiparis" ;



    public  static  final  String  NOTIFICATION_CENTER = "notificationCenter" ;

}
