// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
#include <vespa/vespalib/testkit/test_kit.h>
#include <vespa/fnet/transport.h>
#include <vespa/fnet/iexecutable.h>
#include <vespa/fastos/thread.h>

struct DoIt : public FNET_IExecutable {
    vespalib::Gate gate;
    void execute() override {
        gate.countDown();
    }
};

TEST("sync execute") {
    DoIt exe1;
    DoIt exe2;
    DoIt exe3;
    DoIt exe4;
    FastOS_ThreadPool pool(128 * 1024 * 1024);
    FNET_Transport transport;
    ASSERT_TRUE(transport.execute(&exe1));
    ASSERT_TRUE(transport.Start(&pool));
    exe1.gate.await();
    ASSERT_TRUE(transport.execute(&exe2));
    transport.sync();
    ASSERT_TRUE(exe2.gate.getCount() == 0u);
    ASSERT_TRUE(transport.execute(&exe3));
    transport.ShutDown(false);
    ASSERT_TRUE(!transport.execute(&exe4));
    transport.sync();
    transport.WaitFinished();
    transport.sync();
    pool.Close();
    ASSERT_TRUE(exe1.gate.getCount() == 0u);
    ASSERT_TRUE(exe2.gate.getCount() == 0u);
    ASSERT_TRUE(exe3.gate.getCount() == 0u);
    ASSERT_TRUE(exe4.gate.getCount() == 1u);
}

TEST_MAIN() { TEST_RUN_ALL(); }
