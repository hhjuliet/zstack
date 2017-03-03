package org.zstack.test.integration.storage

import org.zstack.testlib.SpringSpec
import org.zstack.testlib.Test

/**
 * Created by hhjuliet on 2017/3/3.
 */
class MigrateStorageTest54 extends Test{
	static SpringSpec springSpec = makeSpring {
		localStorage()
		nfsPrimaryStorage()
		sftpBackupStorage()
		smp()
		ceph()
		virtualRouter()
		vyos()
	}


	@Override
	void setup() {
		useSpring(springSpec)
	}

	@Override
	void environment() {
	}

	@Override
	void test() {
		runSubCases([

		])
	}



}
