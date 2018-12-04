#include <string>
#include "gtest/gtest.h"
#include "HistoryWithExitPoint.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

HistoryWithExitPoint* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class HistoryWithExitPointTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new HistoryWithExitPoint();
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


TEST_F(HistoryWithExitPointTest, historyEntryAfterExit) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithExitPoint::mr_A_r_X1));
	
	statechart->getDefaultSCI()->raise_push();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithExitPoint::mr_B));
	
	statechart->getDefaultSCI()->raise_back();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithExitPoint::mr_A_r_X1));
	
	statechart->getDefaultSCI()->raise_next();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithExitPoint::mr_A_r_X2));
	
	statechart->getDefaultSCI()->raise_push();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithExitPoint::mr_B));
	
	statechart->getDefaultSCI()->raise_back();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(HistoryWithExitPoint::mr_A_r_X2));
	
	
}

}

