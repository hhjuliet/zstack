package org.zstack.test.integration.progress

import org.zstack.test.integration.kvm.Env
import org.zstack.testlib.EnvSpec

/**
 * Created by hhjuliet on 2/28/17.
 */
class TestProgressReport {
	def Doc ="use test progress"

	@Override
	void setup() {
		spring {
			sftpBackupStorage()
			localStorage()
			virtualRouter()
			securityGroup()
			kvm()
		}
	}

	@Override
	void environment() {
		env = Env.oneVmBasicEnv()
	}


	@Override
	void test() {
		env.create {
			testOneProgress()
		}
	}



	static EnvSpec env(){

	}
}
