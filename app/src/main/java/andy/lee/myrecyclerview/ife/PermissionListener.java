package andy.lee.myrecyclerview.ife;

import java.util.List;

/**
 * andy.lee.myrecyclerview.ife
 * Created by andy on 17-1-10.
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> permissionList);

}
