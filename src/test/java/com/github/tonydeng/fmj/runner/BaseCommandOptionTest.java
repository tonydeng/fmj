package com.github.tonydeng.fmj.runner;

import com.github.tonydeng.fmj.BaseTest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by tonydeng on 15/4/20.
 */
@Ignore
public class BaseCommandOptionTest extends BaseTest {
    @Test
    public void getFFprobeBinaryTest(){
        List<String> commands = BaseCommandOption.getFFprobeBinary();

        log.info(commands.toString());
    }

}
