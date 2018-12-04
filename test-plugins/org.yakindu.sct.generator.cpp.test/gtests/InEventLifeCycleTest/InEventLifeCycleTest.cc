#include <string>
#include "gtest/gtest.h"
#include "InEventLifeCycle.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

InEventLifeCycle* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class InEventLifeCycleTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new InEventLifeCycle();
		statechart->init();
		runner = new SctUnitRunner(
			statechart,
			false,
			200
		);
	}
	virtual void TearDown() {
		delete statechart;
		delete runner;
	}
};


TEST_F(InEventLifeCycleTest, eventLifeCycle) {
	
	statechart->enter();
	
	statechart->getDefaultSCI()->raise_e();
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_i()== 0l);
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_i()== 1l);
	
	
}

}

