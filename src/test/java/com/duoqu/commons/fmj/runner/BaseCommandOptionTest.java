package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.BaseTest;
import org.junit.Test;

import java.util.List;

/**
 * Created by tonydeng on 15/4/20.
 */
public class BaseCommandOptionTest extends BaseTest{
    @Test
    public void getFFprobeBinaryTest(){
        List<String> commands = BaseCommandOption.getFFprobeBinary();

        log.info(commands.toString());
    }

}
