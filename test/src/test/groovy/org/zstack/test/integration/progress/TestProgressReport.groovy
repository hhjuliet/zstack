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
			include("TestProgressReport.xml")
			include("KVMRelated.xml")
			include("Progress.xml")
		}
	}


	static EnvSpec env() {
		sftpBackupStorage {
			name = "sftp"
			url = "/sftp"
			username = "root"
			password = "password"
			hostname = "localhost"

			image {
				name = "TestImage"
				url  = "http://zstack.org/download/test.qcow2"
			}
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

	void testOneProgress(){

	}


}
