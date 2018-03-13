import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class RevelUDPPlugin extends CordovaPlugin {
    private static final String DURATION_LONG = "long";
      @Override
      public boolean execute(String action, JSONArray args,
        final CallbackContext callbackContext) {
          if (!action.equals("send")) {
            callbackContext.error("\"" + action + "\" is not a recognized action.");
            return false;
          }
          String message;
          String port;
          String ip;

          try {
            JSONObject options = args.getJSONObject(0);
            message = options.getString("message");
            ip = options.getString("ip");
            port = options.getString("port");
          } catch (JSONException e) {
            callbackContext.error("Error encountered: " + e.getMessage());
            return false;
          }
          new RetrieveFeedTask().execute(port,ip, message);
          // Send a positive result to the callbackContext
          PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
          callbackContext.sendPluginResult(pluginResult);
          return true;
      }

      class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

          private Exception exception;

          protected Void doInBackground(String... args) {
              try {
                  int port = Integer.parseInt(args[0]);
                  String ip = args[1];
                  String msg = args[2];
                  InetAddress group = InetAddress.getByName(ip);

                  MulticastSocket s = new MulticastSocket(port);

                  s.joinGroup(group);
                  DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
                          group, port);
                  s.send(hi);

              } catch (Exception e) {
                  e.printStackTrace();
              }
              return null;
          }

      }
}