package io.luliin.cubeiawallet;

import org.springframework.boot.SpringApplication;

public class TestCubeiaWalletApplication {

    public static void main(String[] args) {
        SpringApplication.from(CubeiaWalletApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
