package org.zstack.test.integration.storage

import org.springframework.http.HttpEntity
import org.zstack.header.vm.VmInstanceState
import org.zstack.header.vm.VmInstanceVO
import org.zstack.kvm.KVMAgentCommands
import org.zstack.kvm.KVMConstant
import org.zstack.sdk.VmInstanceInventory
import org.zstack.testlib.EnvSpec
import org.zstack.testlib.SpringSpec
import org.zstack.testlib.Test
import org.zstack.testlib.VmSpec
import org.zstack.utils.gson.JSONObjectUtil

/**
 * Created by hhjuliet on 2017/3/3.
 */
class MigrateStorageTest54 extends Test{

	EnvSpec migrateStorageenv

	static SpringSpec springSpec = makeSpring {
		localStorage()
		nfsPrimaryStorage()
		sftpBackupStorage()
		smp()
		ceph()
		virtualRouter()
		vyos()

		include("TestLocalStorage54.xml")
		include("KVMRelated.xml")
		include("localStorageSimulator.xml")
		include("localStorage.xml")
	}


	@Override
	void setup() {
		useSpring(springSpec)
	}

	@Override
	void environment() {
		migrateStorageenv = MigrateStorageEnv.migrateStorageBasicEnv();
	}

	@Override
	void test() {
		migrateStorageenv.create {

		}
	}

	void testmigrate(){
		//stop vm
		VmSpec spec = migrateStorageenv.specByName("vm")

		KVMAgentCommands.StopVmCmd cmd = null

		migrateStorageenv.afterSimulator(KVMConstant.KVM_STOP_VM_PATH) { rsp, HttpEntity<String> e ->
			cmd = JSONObjectUtil.toObject(e.body, KVMAgentCommands.StopVmCmd.class)
			return rsp
		}

		VmInstanceInventory inv = stopVmInstance {
			uuid = spec.inventory.uuid
		}

		assert inv.state == VmInstanceState.Stopped.toString()

		assert cmd != null
		assert cmd.uuid == spec.inventory.uuid

		def vmvo = dbFindByUuid(cmd.uuid, VmInstanceVO.class)
		assert vmvo.state == VmInstanceState.Stopped


		localStorageMigrateVolume {

		}

	}



}
