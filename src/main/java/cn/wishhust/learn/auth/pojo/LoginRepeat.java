package cn.wishhust.learn.auth.pojo;

import java.util.concurrent.TimeUnit;

public class LoginRepeat {

    private long lastTryTime;
    private int tryCount;
    private long expiredTime;

    public LoginRepeat(){}

    public LoginRepeat(long lastTryTime, int tryCount, long expiredTime) {
        this.lastTryTime = lastTryTime;
        this.tryCount = tryCount;
        this.expiredTime = expiredTime;
    }

    public boolean checkExpired() {
        return System.currentTimeMillis() < lastTryTime + expiredTime;
    }

    public LoginRepeat increase() {
        int tryCount = this.tryCount+1;
        return new LoginRepeat(System.currentTimeMillis(), tryCount, getExpiredTime(tryCount));
    }

    public Long getExpiredTime(int tryCount) {
        if(tryCount == 1) {
            return TimeUnit.SECONDS.toMillis(3);
        }
        if (tryCount == 2) {
            return TimeUnit.SECONDS.toMillis(5);
        }
        if (tryCount >= 3 && tryCount <= 9) {
            return TimeUnit.SECONDS.toMillis(10);
        }
        return TimeUnit.MINUTES.toMillis(1);
    }
}
