import com.easycache.cacheapi.core.annotation.CacheSourceType;
import com.easycache.cacheapi.core.annotation.Cached;

import java.util.concurrent.TimeUnit;

public class Test {

    @Cached(expire = 5, timeUnit = TimeUnit.MINUTES, type = CacheSourceType.MEMORY)
    public void queryUser(String userId) {

    }
}
