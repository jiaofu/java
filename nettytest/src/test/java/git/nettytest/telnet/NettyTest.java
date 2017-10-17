package git.nettytest.telnet;

import org.junit.Test;

public class NettyTest {
    @Test
    public void test() {
        NettyTelnetServer nettyTelnetServer = new NettyTelnetServer();
        try {
            nettyTelnetServer.open();
        } catch (InterruptedException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

